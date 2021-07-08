package ru.home.geekbrains.java.core_03.threading2;

import java.util.concurrent.Semaphore;

import static ru.home.geekbrains.java.core_03.threading2.App.CARS_COUNT;


public class Tunnel extends Stage {

    private Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT / 2, true);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);

                tunnelSemaphore.acquire(); /////////////////////////////

                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep((long)((double)length / c.getSpeed() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                
                tunnelSemaphore.release(); /////////////////////////////
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
