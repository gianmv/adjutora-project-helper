package com.move.adjutora.model.expimp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.move.adjutora.model.TaskTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TaskTreeExpImp {
    private final TaskExpImp node;
    private final List<TaskTreeExpImp> children = new LinkedList<TaskTreeExpImp>();

    public TaskTreeExpImp(TaskExpImp node) {
        this.node = node;
    }

    public TaskTreeExpImp(TaskTree node) {
        this.node = new TaskExpImp(node.getNode());
        this.children.addAll(node.getChildren().stream().map(TaskTreeExpImp::new).toList());
    }

    public TaskTreeExpImp(@JsonProperty("node") TaskExpImp node, @JsonProperty("children") List<TaskTreeExpImp> children) {
        this.node = node;
        this.children.addAll(children);
    }

    public TaskExpImp getNode() {
        return node;
    }

    public List<TaskTreeExpImp> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskTreeExpImp that = (TaskTreeExpImp) o;
        return Objects.equals(node, that.node) && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, children);
    }

    @Override
    public String toString() {
        return "TaskTreeExpImp{" +
                "node=" + node +
                ", children=" + children +
                '}';
    }
}
