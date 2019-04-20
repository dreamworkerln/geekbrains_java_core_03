package ru.home.geekbrains.java.core_03.threading3.entities.geo;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.Semaphore;


/**
 * Канал с ограничением числа одновременно находящихся в нем судов
 */
public class Channel extends Geo implements Semaphorable {

    private Semaphore semaphore = null;

    /**
     * Широкий пролив без ограничений
     */
    public Channel(String name, int length) {
        super(name, length);
    }

    /**
     * Узкий пролив
     * @param name
     * @param length
     * @param maxShip максимальное количество одновременно находящихся кораблей
     */
    public Channel(@NotNull String name, int length, int maxShip) {
        super(name, length);
        this.semaphore = new Semaphore(maxShip);
        this.cumbersome = true;
    }


    @Override
    public void enter(String shipName) {

        try {
            if (semaphore!= null)
                semaphore.acquire();
            super.enter(shipName);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    

    @Override
    public void leave(String shipName) {
        if (semaphore!= null)
            semaphore.release();
        super.leave(shipName);
    }

}
