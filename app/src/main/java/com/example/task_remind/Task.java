package com.example.task_remind;

public class Task {
    private int id;
    private String taskName;
    private String dueDate;
    private String dueTime; // New property
    private String reminderAt;
    private String reminderType;
    private String repeat;

    // Getters and setters for all properties

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
