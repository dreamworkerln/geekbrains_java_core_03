package ru.home.geekbrains.java.core_03.threading1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread extends Thread {

    private String name;

    private char letter;
    private Invokable jobDone;

    private boolean haveJob  = false;

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    MyThread(char letter, Invokable jobDone) {

        this.letter = letter;
        this.jobDone = jobDone;

        // auto-start
        start();
    }


    @Override
    public void run() {

        name = Thread.currentThread().getName();

        try {

            //noinspection InfiniteLoopStatement
            while (true) {

                try {
                    lock.lock();

                    // waiting for job (if have no one)
                    if (!haveJob)
                        condition.await();


                    // doing job
                    System.out.print(letter);

                    // drop job flag
                    haveJob = false;

                }
                finally {
                    lock.unlock();

                    // tell job done/errord to Tpool
                    jobDone.invoke();
                }
            }
        }
        catch (InterruptedException ignore) {}
        catch (Exception e) {e.printStackTrace();}
    }

    void doJob() {

        try {
            lock.lock();

            // На случай, если на момент выдачи задачи поток еще вообще не запустился
            // и не дошел до condition.await();
            haveJob = true;
            condition.signal();
        }
        finally {
            lock.unlock();
        }

    }

}
