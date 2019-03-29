package ru.home.geekbrains.java.core_03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToList {

    public static <T> List<T> convert(T[] array) {

        List<T> tt =  Arrays.asList(array);

        return tt;
    }
}
