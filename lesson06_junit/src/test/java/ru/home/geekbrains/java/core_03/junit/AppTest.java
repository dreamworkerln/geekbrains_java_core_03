package ru.home.geekbrains.java.core_03.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Запускать класс целиком (методы с параметрами по-отдельности у меня не запускаются)
 */

class AppTest {

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    // provider
    private static List<Arguments> arrayCheckerProvider() {

        ArrayList<Arguments> list = new ArrayList<>();

        list.add(Arguments.of(new int[] {0, 2, 3, 5}, false));
        list.add(Arguments.of(new int[] {0, 2, 3, 1}, true ));
        list.add(Arguments.of(new int[] {4, 2, 3, 5}, true ));
        list.add(Arguments.of(new int[] {1, 2, 3, 4}, true ));

        return list;
    }

    private static List<Arguments> arrayExtractorProvider() {

        ArrayList<Arguments> list = new ArrayList<>();
        list.add(Arguments.of( new int[] {0, 2, 5, 7, 4, 1}, new int[] {1}, null ));
        list.add(Arguments.of( new int[] {0, 2, 4, 7, 4, 1}, new int[] {1}, null ));
        list.add(Arguments.of( new int[] {0, 2, 5, 7, 4},    new int[] {},  null));
        list.add(Arguments.of( new int[] {0, 2, 5, 7},       null,          new IllegalArgumentException() ));
        return list;
    }


    @ParameterizedTest
    @MethodSource("arrayExtractorProvider")
    void arrayExtractor(int[] arg, int[] expected, Throwable e) {

        if (e != null) {
            Exception thrown =
                    assertThrows(((Exception)e).getClass(),
                            () -> App.arrayExtractor(arg),
                            "Should be exception here");

            System.out.println(thrown.toString());
        }
        else {
            assertArrayEquals(expected, App.arrayExtractor(arg));
        }

    }


    @ParameterizedTest
    @MethodSource("arrayCheckerProvider")
    void arrayChecker(int[] arg, boolean expected) {

        assertEquals(expected, App.arrayChecker(arg));
    }
}