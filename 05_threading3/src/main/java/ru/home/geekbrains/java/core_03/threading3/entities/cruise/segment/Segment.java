package ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment;

import ru.home.geekbrains.java.core_03.threading3.entities.geo.Nav;

public class Segment {
    private Nav nav;
    private Task task;

    public Segment(Nav nav, Task task) {
        this.nav = nav;
        this.task = task;
    }

    public Nav getNav() {
        return nav;
    }

    public Task getTask() {
        return task;
    }
}
