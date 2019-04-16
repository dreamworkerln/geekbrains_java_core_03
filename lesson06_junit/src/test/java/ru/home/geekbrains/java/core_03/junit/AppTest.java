package ru.home.geekbrains.java.core_03.junit;

import org.apache.logging.log4j.core.util.Assert;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {}

    // provider
    private static List<Arguments> arrayCheckerProvider() {

        ArrayList<Arguments> list = new ArrayList<>();

        list.add(Arguments.of(new int[] {0, 2, 3, 5}, false));
        list.add(Arguments.of(new int[] {0, 2, 3, 1}, true));
        list.add(Arguments.of(new int[] {4, 2, 3, 5}, true));
        list.add(Arguments.of(new int[] {1, 2, 3, 4}, true));


        return list;
    }

    @Test
    void arrayExtractor() {
    }


    @ParameterizedTest
    @MethodSource("arrayCheckerProvider")
    void arrayChecker(int[] arg, boolean expected) {

        assertEquals(expected, App.arrayChecker(arg));
    }
}