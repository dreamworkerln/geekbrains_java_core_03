package ru.home.geekbrains.java.core_03;

import org.junit.Assert;
import org.junit.Test;
import ru.home.geekbrains.java.core_03.pak03.Apple;
import ru.home.geekbrains.java.core_03.pak03.Box;
import ru.home.geekbrains.java.core_03.pak03.Orange;

public class BoxTesting {

    @Test
    public void TestGetWeight() {

        Box<Apple> boxApple = new Box<>();
        boxApple.add(new Apple());
        Assert.assertEquals(1, Math.round(boxApple.getWeight()));

        Box<Orange> boxOrange = new Box<>();
        boxOrange.add(new Orange());
        boxOrange.add(new Orange());
        Assert.assertEquals(3, Math.round(boxOrange.getWeight()));

        Assert.assertFalse(boxApple.compare(boxOrange));

        boxApple.add(new Apple());
        boxApple.add(new Apple());
        Assert.assertTrue(boxApple.compare(boxOrange));



        int tmpCnt = boxApple.size();
        Box<Apple> newBoxApple = new Box<>();
        boxApple.move(newBoxApple);
        Assert.assertEquals(tmpCnt, newBoxApple.size());
    }
}
