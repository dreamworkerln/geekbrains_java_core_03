package ru.home.geekbrains.java.core_03.threading1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App {

    private Map<Character,MyThread> pool = new HashMap<>();



    public static void main( String[] args )  {

//        BlockingDeque<String> bdeq = new LinkedBlockingDeque<>();
//
//        bdeq.peek()
//
//        ExecutorService executorService =
//                new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
//                        new LinkedBlockingQueue<>());
//
//        executorService.



        Tpool pool = new Tpool();

        String input = "ABCABCABCABCABC";

        pool.printLine(input);
    }


    private App() {

    }








}
