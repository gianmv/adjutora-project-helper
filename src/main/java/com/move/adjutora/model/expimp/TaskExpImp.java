package com.move.adjutora.model.expimp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.move.adjutora.model.TaskDto;
import com.move.adjutora.model.database.Task;

import java.time.LocalDateTime;
import java.util.Objects;

public class TaskExpImp {
    private Long taskId;
    private String title;
    private String description;
    private LocalDateTime creationdate;
    private LocalDateTime duedate;
    private LocalDateTime donedate;
    private Long parentTaskId;

    public TaskExpImp(TaskDto task) {
        this.taskId = task.getTaskId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.creationdate = task.getCreationdate();
        this.duedate = task.getDuedate();
        this.donedate = task.getDonedate();
        this.parentTaskId = task.getParentTaskId();
    }

    public TaskExpImp(
            @JsonProperty("taskId") Long taskId,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("creationdate") LocalDateTime creationdate,
            @JsonProperty("duedate") LocalDateTime duedate,
            @JsonProperty("donedate") LocalDateTime donedate,
            @JsonProperty("parentTaskId") Long parentTaskId
    ) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creationdate = creationdate;
        this.duedate = duedate;
        this.donedate = donedate;
        this.parentTaskId = parentTaskId;
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
        TaskExpImp that = (TaskExpImp) o;
        return Objects.equals(taskId, that.taskId) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(creationdate, that.creationdate) && Objects.equals(duedate, that.duedate) && Objects.equals(donedate, that.donedate) && Objects.equals(parentTaskId, that.parentTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, title, description, creationdate, duedate, donedate, parentTaskId);
    }

    @Override
    public String toString() {
        return "TaskExpImp{" +
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
