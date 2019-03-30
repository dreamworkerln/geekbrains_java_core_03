package ru.home.geekbrains.java.core_03;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ArrayToListTest {


    @Test
    public void TestConvert() {

        Integer[] a1 = new Integer[] {10, 20};
        List<Integer> tmp = ArrayToList.convert(a1);
        Assert.assertTrue(tmp.get(0) == 10 && tmp.get(1) == 20);

        String[] a2 = new String[] {"a", "b"};
        List<String> tmp2 = ArrayToList.convert(a2);
        Assert.assertTrue(tmp2.get(0).equals("a") &&  tmp2.get(1).equals("b"));
    }
}
