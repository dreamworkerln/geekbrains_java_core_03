package ru.home.geekbrains.java.core_03.threading1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Tpool {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private Map<Character,MyThread> pool = new HashMap<>();

    public Tpool() {

        Invokable jobDone = this::jobDone;
        char[] tmp = {'A', 'B', 'C'};

        // MyThread have auto-start in it's constructor
        for (char c : tmp) {
            pool.put(c, new MyThread(c, jobDone));
        }
    }

    // Thread tells me that job done
    // ответка от thread что он выполнил работу
    private void jobDone() {
        try {
            lock.lock();
            condition.signal();
        }
        finally {
            lock.unlock();
        }
    }




    public void printLine(String line) {

        for (char c : line.toCharArray()) {


            try {
                lock.lock();

                // tell thread to do job
                // обязательно внутри критической секции
                // иначе ответка от thread может приехать,
                // а мы еще не начали ждать condition.await();
                pool.get(c).doJob();

                // awaiting thread to finish job
                condition.await();

            }
            catch (Exception ignore) {}
            finally {
                lock.unlock();
            }
        }



        // release pool threads
        for (MyThread thread : pool.values()) {
            thread.interrupt();
        }
    }
   


}
