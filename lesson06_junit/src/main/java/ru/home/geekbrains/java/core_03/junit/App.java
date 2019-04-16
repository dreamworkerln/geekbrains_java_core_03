package ru.home.geekbrains.java.core_03.junit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App
{

    private static Logger log;

    static {
        setupLog4j();
    }

//    2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
// Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
// идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
// иначе в методе необходимо выбросить RuntimeException.
// Написать набор тестов для этого метода (по 3-4 варианта входных данных).
// Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].


    public static int[] arrayExtractor(int[] arr) {

        log.traceEntry("Parameters: {}", Arrays.toString(arr));

        int[] result  = null;


        int index = -1; // index of 4

        for (int i = arr.length - 1; i>=0; i--) {

            if (arr[i] == 4) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IllegalArgumentException("Illegal input " + Arrays.toString(arr));
        }

        result = Arrays.copyOfRange(arr, index + 1, arr.length);

        log.traceExit(Arrays.toString(result));

        return result;
    }



    // 3. Написать метод, который проверяет состав массива из чисел 1 и 4.
    // Если в нем нет хоть одной четверки или единицы, то метод вернет false;
    // Написать набор тестов для этого метода (по 3-4 варианта входных данных).


    public static boolean arrayChecker(int[] arr) {


        log.traceEntry("Parameters: {}", arr);

        boolean result  = false;


        boolean found1 = false;
        boolean found4 = false;

        for (int n : arr) {

            found1 = found1 || n == 1;
            found4 = found4 || n == 4;
        }



        result = found1 || found4;

        log.traceExit(result);

        return result;
    }


    private static void setupLog4j() {

        String path_tmp = System.getProperty("user.dir") + "/" + "log/";
        // DONT CALL LOGGERS BEFORE *.folder.path SET BELOW
        System.setProperty("debug.folder.path", path_tmp + "debug.log");
        System.setProperty("info.folder.path", path_tmp + "info.log");
        System.setProperty("error.folder.path", path_tmp + "error.log");

        log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

        //log = Logger.getLogger(MethodHandles.lookup().lookupClass());

        log.info("\n\n\n\n ===================================================================================================");
        log.info("START");
        log.info("===================================================================================================\n");
    }









}