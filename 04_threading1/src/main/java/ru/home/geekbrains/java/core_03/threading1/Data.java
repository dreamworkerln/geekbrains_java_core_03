package ru.home.geekbrains.java.core_03.threading1;

public class Data {

//    synchronized static void doOne() throws InterruptedException {
//
//        String name = Thread.currentThread().getName();
//
//        System.out.println(name + " enter doOne()");
//        Thread.sleep(1000);
//        System.out.println(name + " leave doOne()");
//
//    }
//
//
//    synchronized static void doSecond() throws InterruptedException {
//
//        String name = Thread.currentThread().getName();
//
//        System.out.println(name + " enter doSecond()");
//        Thread.sleep(1000);
//        System.out.println(name + " leave doSecond()");
//    }


    public static void print(String s) {

        System.out.println(s);
    }

}
