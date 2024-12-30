package com.move.adjutora.service;

import com.move.adjutora.model.TaskDto;
import com.move.adjutora.model.TaskTimeField;
import com.move.adjutora.model.TaskCollection;
import com.move.adjutora.model.TaskTree;
import com.move.adjutora.model.database.Task;
import com.move.adjutora.model.expimp.TaskCollectionExportImport;
import com.move.adjutora.model.expimp.TaskTreeExpImp;
import com.move.adjutora.repository.TaskMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDateTime;
import java.util.*;

public class TaskService {

    private final SqlSessionFactory sqlSessionFactory;

    public TaskService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public TaskCollection getAllParentTasksWithoutChildrenAndUndone() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            List<Task> allParentTask = taskMapper.getAllParentTaskUndone();
            TaskCollection taskCollection = new TaskCollection();
            taskCollection.getTasks().addAll(
                    allParentTask.stream()
                            .map(TaskDto::new)
                            .map(TaskTree::new)
                            .toList());
            return taskCollection;
        }
    }

    public TaskCollection getAllParentTasksWithChildrenAndUndone() {
        TaskCollection taskCollection = getAllParentTasksWithoutChildrenAndUndone();
        return getAllTaskWithChildren(taskCollection);
    }

    public TaskCollection getAllTaskWithChildren(TaskCollection taskCollection) {
        Queue<TaskTree> allParentTask = new LinkedList<>(taskCollection.getTasks());
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            while (!allParentTask.isEmpty()) {
                TaskTree currentTask = allParentTask.remove();
                List<Task> children = taskMapper.getAllTaskByParentTaskId(currentTask.getNode().getTaskId());
                List<TaskTree> childrenTaskTree = children.stream()
                        .map(TaskDto::new)
                        .map(TaskTree::new)
                        .toList();
                currentTask.getChildren().addAll(childrenTaskTree);
                allParentTask.addAll(childrenTaskTree);
            }
        }
        return taskCollection;
    }

    public TaskCollection getAllTasks() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            List<Task> allTask = taskMapper.getAllTask();
            TaskCollection taskCollection = new TaskCollection();
            taskCollection.getTasks().addAll(allTask.stream()
                    .map(TaskDto::new)
                    .map(TaskTree::new)
                    .toList());
            return getAllTaskWithChildren(taskCollection);
        }
    }

    public void insertSingleTask(Task task) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            taskMapper.insertSingleTask(task);
            session.commit();
        }
    }

    public TaskCollection getAllTasksWithFilter(boolean doneStatus, TaskTimeField taskTimeField, LocalDateTime start, LocalDateTime end) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            List<Task> tasks = taskMapper.getAllTaskByFilter(doneStatus, taskTimeField, start, end);
            TaskCollection taskCollection = new TaskCollection();
            taskCollection.getTasks().addAll(
                    tasks.stream()
                            .map(TaskDto::new)
                            .map(TaskTree::new)
                            .toList()
            );
            return taskCollection;
        }
    }

    public Optional<TaskDto> updateSingleTask(TaskDto task) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Objects.requireNonNull(task);
            Objects.requireNonNull(task.getTaskId());
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            Optional<Task> taskDb = taskMapper.getTaskById(task.getTaskId());
            if (taskDb.isEmpty()) {
                return Optional.empty();
            }
            Map<String, Object> params = new HashMap<>();
            Optional.ofNullable(task.getParentTaskId()).ifPresent(id -> params.put("PARENTTASKID", id));
            Optional.ofNullable(task.getCreationdate()).ifPresent(date -> params.put("CREATIONDATE", date));
            Optional.ofNullable(task.getDonedate()).ifPresent(date -> params.put("DONEDATE", date));
            Optional.ofNullable(task.getDuedate()).ifPresent(date -> params.put("DUEDATE", date));
            Optional.ofNullable(task.getDescription()).ifPresent(date -> params.put("DESCRIPTION", date));
            Optional.ofNullable(task.getTitle()).ifPresent(date -> params.put("TITLE", date));

            if (params.get("PARENTTASKID") != null) {
                Optional<Task> taskOp = taskMapper.getTaskById((Long) params.get("PARENTTASKID"));
                if (taskOp.isEmpty()) {
                    return Optional.empty();
                }
            }

            if (params.isEmpty()) {
                return Optional.empty();
            }

            taskMapper.updateTaskById(params, task.getTaskId());
            session.commit();
            return taskMapper.getTaskById(task.getTaskId()).map(TaskDto::new);
        }
    }

    public Optional<TaskDto> deleteSingleTask(long taskId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            Optional<Task> taskDb = taskMapper.getTaskById(taskId);
            if (taskDb.isPresent()) {
                List<Task> allChildrenTask = taskMapper.getAllTaskByParentTaskId(taskId);
                if (allChildrenTask.isEmpty()) {
                    taskMapper.deleteTaskById(taskId);
                    return taskDb.map(TaskDto::new);
                } else {
                    return Optional.empty();
                }
            }
            return Optional.empty();
        }
    }

    public void deleteDatabase() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TaskMapper taskMapper = session.getMapper(TaskMapper.class);
            TaskCollection taskCollection = getAllTasks();
            for(TaskTree taskTree : taskCollection.getTasks()) {
                deleteTaskTree(taskTree, taskMapper);
            }
            session.commit();
        }
    }

    private void deleteTaskTree(TaskTree taskTree, TaskMapper taskMapper) {
        if(taskTree.getChildren().isEmpty()) {
            taskMapper.deleteTaskById(taskTree.getNode().getTaskId());
        } else {
            for (TaskTree child : taskTree.getChildren()) {
                deleteTaskTree(child, taskMapper);
            }
            taskMapper.deleteTaskById(taskTree.getNode().getTaskId());
        }
    }

    public void importDatabase(TaskCollectionExportImport taskCollection) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            for (TaskTreeExpImp taskTree : taskCollection.getTasks()) {
                importTaskTreeExpImp(taskTree, session.getMapper(TaskMapper.class));
            }
            session.commit();
        }
    }

    private void importTaskTreeExpImp(TaskTreeExpImp taskTree, TaskMapper taskMapper) {
        Task insertedToInsert = new Task(new TaskDto(taskTree.getNode()));
        taskMapper.insertSingleTask(insertedToInsert);
        for (TaskTreeExpImp it : taskTree.getChildren()) {
            it.getNode().setParentTaskId(insertedToInsert.getTaskId());
            importTaskTreeExpImp(it, taskMapper);
        }
    }

}
