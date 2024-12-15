package com.example.uts;

public class Task {
    private int id;
    private String task;
    private int priority;
    private long deadline;
    private boolean completed;

    public Task(int id, String task, int priority, long deadline, boolean completed) {
        this.id = id;
        this.task = task;
        this.priority = priority;
        this.deadline = deadline;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public int getPriority() {
        return priority;
    }

    public long getDeadline() {
        return deadline;
    }

    public boolean isCompleted() {
        return completed;
    }
}