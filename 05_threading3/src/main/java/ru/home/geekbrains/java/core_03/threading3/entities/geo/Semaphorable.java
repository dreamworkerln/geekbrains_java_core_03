package ru.home.geekbrains.java.core_03.threading3.entities.geo;

public interface Semaphorable {

    void enter(String name);
    void leave(String name);

}
