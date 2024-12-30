package com.move.adjutora.model.database;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private Long taskId;
    private String title;
    private String description;
    private LocalDateTime creationdate;
    private LocalDateTime duedate;
    private LocalDateTime donedate;
    private Long parentTaskId;

    public Task(Long taskId, Long parentTaskId, String title, String description, LocalDateTime creationdate, LocalDateTime duedate, LocalDateTime donedate) {
        this.taskId = taskId;
        this.parentTaskId = parentTaskId;
        this.title = title;
        this.description = description;
        this.creationdate = creationdate;
        this.duedate = duedate;
        this.donedate = donedate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(LocalDateTime creationdate) {
        this.creationdate = creationdate;
    }

    public LocalDateTime getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDateTime duedate) {
        this.duedate = duedate;
    }

    public LocalDateTime getDonedate() {
        return donedate;
    }

    public void setDonedate(LocalDateTime donedate) {
        this.donedate = donedate;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(creationdate, task.creationdate) && Objects.equals(duedate, task.duedate) && Objects.equals(donedate, task.donedate) && Objects.equals(parentTaskId, task.parentTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, title, description, creationdate, duedate, donedate, parentTaskId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationdate=" + creationdate +
                ", duedate=" + duedate +
                ", donedate=" + donedate +
                ", parentTaskId=" + parentTaskId +
                '}';
    }
}
