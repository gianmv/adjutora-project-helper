package com.move.adjutora.controller.read;

import com.move.adjutora.model.TaskCollection;
import com.move.adjutora.model.TaskDto;
import com.move.adjutora.model.TaskTree;
import com.move.adjutora.model.TaskViewType;
import org.apache.commons.lang3.StringUtils;

public class ReadCommandUtility {
    public static String printTaskTreeCollection(TaskCollection taskCollection, TaskViewType taskViewType) {
        StringBuilder output = new StringBuilder();
        for (TaskTree taskTree : taskCollection.getTasks()) {
            String currentTaskTree = printTaskTreeWithChildren(taskTree, taskViewType, 0);
            output.append(currentTaskTree);
        }
        return output.toString();
    }

    public static String printTaskTreeWithChildren(TaskTree taskTree, TaskViewType taskViewType, int depth) {
        StringBuilder currentTaskTreeView = new StringBuilder();
        currentTaskTreeView
                .append(StringUtils.repeat("\t",depth))
                .append(taskViewType == TaskViewType.SHORT ? getTaskView(taskTree.getNode()) : getDetailTaskView(taskTree.getNode()))
                .append("\n");
        for (TaskTree childTaskTree : taskTree.getChildren()) {
            String childTaskTreeView = printTaskTreeWithChildren(childTaskTree, taskViewType, depth + 1);
            currentTaskTreeView.append(childTaskTreeView);
        }
        return currentTaskTreeView.toString();
    }

    public static String getTaskView(TaskDto task) {
        return "{" + String.format("%4d", task.getTaskId()) + "}" +
                " " +
                "[" +
                (task.getDonedate() == null ? " " : "X") +
                "]" +
                " " +
                task.getTitle() +
                " " +
                (task.getDuedate() == null ? "" : "(" + task.getDuedate() + ")");
    }

    public static String getDetailTaskView(TaskDto task) {
        return "{" + String.format("%4d", task.getTaskId()) + "}" +
                "{" + (task.getParentTaskId() != null ? String.format("%4d", task.getParentTaskId()) : " ")+ "}" +
                " " +
                "[" +
                (task.getDonedate() == null ? " " : "X") +
                "]" +
                " " +
                task.getTitle() +
                " " +
                "[" + (task.getDescription() == null ? "" : task.getDescription()) + "]" +
                " " +
                (task.getDuedate() == null ? "()" : "(" + task.getDuedate() + ")") +
                (task.getDonedate() == null ? "()" : "(" + task.getDonedate() + ")") +
                (task.getCreationdate() == null ? "()" : "(" + task.getCreationdate() + ")");
    }
}
