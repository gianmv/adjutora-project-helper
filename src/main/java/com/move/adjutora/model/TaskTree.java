package com.move.adjutora.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TaskTree {
    private final TaskDto node;
    private final List<TaskTree> children = new LinkedList<>();

    public TaskTree(TaskDto node) {
        this.node = node;
    }

    public TaskDto getNode() {
        return node;
    }

    public List<TaskTree> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskTree taskTree = (TaskTree) o;
        return Objects.equals(node, taskTree.node) && Objects.equals(children, taskTree.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, children);
    }

    @Override
    public String toString() {
        return "TaskTree{" +
                "parent=" + node +
                ", children=" + children +
                '}';
    }
}
