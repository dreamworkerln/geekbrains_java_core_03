package ru.home.geekbrains.java.core_03.threading1;

public class TestStop {
    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
//                    if(Thread.currentThread().isInterrupted()) {
//                        break;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        break;
//                    }
                    Thread.interrupted();

                    System.out.println(Thread.currentThread().isInterrupted());
                    System.out.println("A");
                }
            }
        });



        t1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
        System.out.println("END");
    }
}
