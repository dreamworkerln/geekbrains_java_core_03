package ru.home.geekbrains.java.core_03.threading3.entities.geo;

import java.util.concurrent.Semaphore;

/**
 * Географический объект
 */
public abstract class Geo implements Semaphorable {

    protected String name = "NULL";
    protected int length = -1;
    protected boolean cumbersome = false;
    Semaphore semaphore = null;

    //


    public Geo(String name) {
        this.name = name;
        length = 0;

    }

    public Geo(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public boolean isCumbersome() {
        return cumbersome;
    }

    /**
     * Зайти в участок пути
     */
    @Override
    public void enter(String shipName) {
        System.out.println("Ship '" + shipName + "' entering: '" + this.getName() + "'");
    }

    /**
     * Выйти из участка пути
     */
    @Override
    public void leave(String shipName) {
        System.out.println("Ship '" + shipName + "' leaving: '" + this.getName()+"'");
    }


    // No modifications
    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public String toString() {
        return name;
    }
}
