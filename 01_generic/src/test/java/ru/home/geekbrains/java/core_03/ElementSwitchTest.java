package ru.home.geekbrains.java.core_03;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ElementSwitchTest {



    @Test
    public void TestExchange() {

        Integer[] a1 = new Integer[] {10, 20};
        ElementSwitch.elSwitch(a1, 0, 1);
        Assert.assertTrue(a1[0] == 20 &&  a1[1] == 10);


        String[] a2 = new String[] {"a", "b"};
        ElementSwitch.elSwitch(a2, 0, 1);

        Assert.assertTrue(a2[0].equals("b") &&  a2[1].equals("a"));
    }

}
