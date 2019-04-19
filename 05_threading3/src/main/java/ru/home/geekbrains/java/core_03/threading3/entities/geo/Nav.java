package ru.home.geekbrains.java.core_03.threading3.entities.geo;

import java.util.concurrent.Semaphore;

public abstract class Nav implements Semaphorable {

    protected String name = "NULL";
    protected int length = -1;
    protected boolean cumbersome = false;
    Semaphore semaphore = null;

    //


    public Nav(String name) {
        this.name = name;
        length = 0;

    }

    public Nav(String name, int length) {
        this.name = name;
        this.length = length;
    }

//    public Nav(String name, int length, Semaphore semaphore) {
//        this.name = name;
//        this.length = length;
//        this.semaphore = semaphore;
//
//        cumbersome = true;
//    }


    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public boolean isCumbersome() {
        return cumbersome;
    }

//    /**
//     * Пройти сегмент
//     */
//    public void passThru(double speed) {
//
//        System.out.println("Swimming: " + this.getClass().getSimpleName());
//    }


    /**
     * Зайти в участок пути
     */
    @Override
    public void enter(String name) {
        System.out.println("Ship '" + name + "' entering: '" + this.getName() + "'");
    }

    /**
     * Выйти из участка пути
     */
    @Override
    public void leave(String name) {
        System.out.println("Ship '" + name + "' leaving: '" + this.getName()+"'");
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
