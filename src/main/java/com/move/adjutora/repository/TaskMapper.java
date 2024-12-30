package com.move.adjutora.repository;

import com.move.adjutora.model.TaskTimeField;
import com.move.adjutora.model.database.Task;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskMapper {
    List<Task> getAllParentTaskUndone();
    void insertSingleTask(Task task);
    List<Task> getAllTaskByParentTaskId(Long parentTaskId);
    List<Task> getAllTask();
    List<Task> getAllTaskByFilter(
            @Param("doneStatus") boolean doneStatus,
            @Param("taskTimeField") TaskTimeField taskTimeField,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    Optional<Task> getTaskById(Long taskId);
    void updateTaskById(@Param("parameterMap") Map<String, Object> parameterMap, @Param("taskId") Long taskId);
    void deleteTaskById(Long taskId);
}
