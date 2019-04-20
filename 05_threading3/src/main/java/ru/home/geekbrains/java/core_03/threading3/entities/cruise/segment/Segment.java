package ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment;

import ru.home.geekbrains.java.core_03.threading3.entities.geo.Geo;

public class Segment {
    private Geo geo;
    private Task task;

    public Segment(Geo geo, Task task) {
        this.geo = geo;
        this.task = task;
    }

    public Geo getGeo() {
        return geo;
    }

    public Task getTask() {
        return task;
    }
}
