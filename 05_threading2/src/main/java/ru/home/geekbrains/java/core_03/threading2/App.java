package ru.home.geekbrains.java.core_03.threading2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntUnaryOperator;

/**
 * Hello world!
 *
 */
public class App {

    static final int CARS_COUNT = 4;


//    private static AtomicReference<Car> atomicWinner = new AtomicReference<>();

    private static String[] messages = new String[] {
            "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!",
            "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!",
            "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!"};

    private static ReentrantLock finishLock = new ReentrantLock();
    private static Car winner = null;


    public static void main(String[] args) {

        Phaser phaser = new Phaser() {
            protected boolean onAdvance(int phase, int parties) {
                //System.out.println("phase " + phase + " finished");
                System.out.println(messages[phase]);
                return false;
            }
        };
        phaser.register();

        // phase 0 - prepare
        phaser.arriveAndAwaitAdvance();




        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        List<Car> carList = new ArrayList<>();


        for (int i = 0; i < CARS_COUNT; i++){
            carList.add(new Car(race,
                    ThreadLocalRandom.current().nextInt(20, 30),
                    i,
                    phaser));

            carList.get(i).registerOnRace();
        }



        for (Car car : carList) {
            new Thread(car).start();
        }


        // phase 1 - start
        phaser.arriveAndAwaitAdvance();


        // phase 2 - finish
        phaser.arriveAndAwaitAdvance();


    }


    static void finish(Car car) {

        try {
            finishLock.lock();

            if (winner == null) {
                winner = car;
                System.out.println("WINNER: " + car.getName());
            }
        }
        finally {
            finishLock.unlock();
        }




//        atomicWinner.getAndAccumulate(car, (previous, x) -> {
//
//            if (previous == null)
//                System.out.println("WINNER: " + car.getName());
//            return previous == null ? x : previous;
//
//        });
    }


}
