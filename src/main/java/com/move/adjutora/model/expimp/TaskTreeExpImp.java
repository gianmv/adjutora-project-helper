package com.move.adjutora.model.expimp;

import com.move.adjutora.model.TaskTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TaskTreeExpImp {
    private final TaskTreeExpImp node;
    private final List<TaskTree> children = new LinkedList<>();

    public TaskTreeExpImp(TaskTreeExpImp node) {
        this.node = node;
    }

    public TaskTreeExpImp(TaskTree node) {
        this.node = new TaskTreeExpImp(node);
    }

    public TaskTreeExpImp getNode() {
        return node;
    }

    public List<TaskTree> getChildren() {
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
