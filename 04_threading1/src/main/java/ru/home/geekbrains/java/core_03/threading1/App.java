package ru.home.geekbrains.java.core_03.threading1;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {

    private Map<Character,MyThread> pool = new HashMap<>();



    public static void main( String[] args )  {

        Tpool pool = new Tpool();

        String input = "ABCABCABCABCABC";

        pool.printLine(input);
    }


    private App() {

    }








}
