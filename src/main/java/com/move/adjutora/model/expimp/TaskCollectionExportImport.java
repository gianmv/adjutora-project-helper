package com.move.adjutora.model.expimp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.move.adjutora.model.TaskCollection;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TaskCollectionExportImport {
    private final String version = "1.0";
    private final List<TaskTreeExpImp> tasks = new LinkedList<>();

    public TaskCollectionExportImport(TaskCollection taskCollection) {
        Objects.requireNonNull(taskCollection);
        this.tasks.addAll(taskCollection.getTasks().stream().map(TaskTreeExpImp::new).toList());
    }

    public TaskCollectionExportImport(@JsonProperty("tasks") List<TaskTreeExpImp> tasks) {
        this.tasks.addAll(tasks);
    }

    public String getVersion() {
        return version;
    }

    public List<TaskTreeExpImp> getTasks() {
        return tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskCollectionExportImport that = (TaskCollectionExportImport) o;
        return Objects.equals(version, that.version) && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, tasks);
    }

    @Override
    public String toString() {
        return "TaskCollectionExportImport{" +
                "version='" + version + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
