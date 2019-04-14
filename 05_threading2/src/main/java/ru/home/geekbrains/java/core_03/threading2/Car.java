package ru.home.geekbrains.java.core_03.threading2;

import java.util.concurrent.Phaser;
import java.util.function.Consumer;

public class Car implements Runnable {

    private Race race;
    private int speed;
    private String name;
    private Consumer<Car> onFinish;
    private Phaser phaser;


    String getName() {
        return name;
    }
    int getSpeed() {
        return speed;
    }

    Car(Race race, int speed, int no, Consumer<Car> onFinish, Phaser phaser) {
        this.race = race;
        this.speed = speed;
        this.name = "Участник #" + no;
        this.onFinish = onFinish;
        this.phaser = phaser;
    }


    void registerOnRace() {
        phaser.register();
    }


    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится (speed=" + speed + ")");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Waiting to start
        phaser.arriveAndAwaitAdvance();

        for (int i = 0; i < race.size(); i++) {
            race.get(i).go(this);
        }


        onFinish.accept(this);

        // Сход с трассы на финише
        phaser.arriveAndDeregister();
    }
}


