package ru.home.geekbrains.java.core_03;

import java.util.Arrays;

public class ElementSwitch {

    public static <T> void elSwitch( T[] a, int from, int to) {

        T tmp = a[from];
        a[from] = a[to];
        a[to] = tmp;
    }
}
