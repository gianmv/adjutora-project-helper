package com.move.adjutora.model;

import com.move.adjutora.model.database.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TaskCollection {
    private final List<TaskTree> tasks = new LinkedList<>();

    public List<TaskTree> getTasks() {
        return tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskCollection that = (TaskCollection) o;
        return Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tasks);
    }

    @Override
    public String toString() {
        return "TaskCollection{" +
                "tasks=" + tasks +
                '}';
    }
}
