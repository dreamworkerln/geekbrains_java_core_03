package ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment;

public class TaskEntry {

    String NavName;
    Task task;

    public TaskEntry(String navName, Task task) {
        NavName = navName;
        this.task = task;
    }

    public String getNavName() {
        return NavName;
    }

    public Task getTask() {
        return task;
    }
}
