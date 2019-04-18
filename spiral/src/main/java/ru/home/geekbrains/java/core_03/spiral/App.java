package ru.home.geekbrains.java.core_03.spiral;

import com.google.common.collect.Iterables;

import java.awt.*;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class App
{

    private static final int  SIZE = 5;
    //  -------------- x
    //  |
    //  |
    //  |
    //  |
    //  |
    //  |
    //  y
    //
    //  Here defined that map[x][y] mean x is abscissa, y is ordinate (like in true array)
    private int[][] map = new int[SIZE][SIZE];

    private int step;
    private int counter;
    //private int length;

    // current coordinates
    private Point pos;

    private Iterator<Direction> it;

    public static void main(String[] args) {
        new App();
    }



    App() {

        // праворезьбовая спираль
        Direction[] tmp = new Direction[] {
                Direction.RIGHT,
                Direction.UP,
                Direction.LEFT,
                Direction.DOWN};

        // infinite cycle iterator
        it = Iterables.cycle(tmp).iterator();

        counter = 0;
        pos = new Point(0,0);

        step = 1;
        // first step
        map[0][0] = step;
        step++;

        printMap();

        //noinspection StatementWithEmptyBody
        while(traversal());
    }

    // Обход куска массива по прямой (горизонтальный или вертикальный кусок)
    private boolean traversal() {

        // continue traversal;
        boolean result = false;

        int length = SIZE - 1;

        if (counter > 2) {
            length = SIZE - (counter -1 ) / 2 - 1;
        }


        // current direction
        Direction dir = it.next();




        //length -= bti(coundown == 0);

        result = length > 0;
        if (result) {
            moveChunk(dir, length);
        }
        counter++;

        return result;
    }




    private void moveChunk(Direction dir, int length) {

        switch (dir) {
            case UP:
                for (int i = 1; i <= length; i++) {
                    pos.y++;
                    map[pos.x][pos.y] = step++;
                }
                break;

            case DOWN:
                for (int i = 1; i <= length; i++) {
                    pos.y--;
                    map[pos.x][pos.y] = step++;
                }
                break;

            case LEFT:
                for (int i = 1; i <= length; i++) {
                    pos.x--;
                    map[pos.x][pos.y] = step++;
                }

                break;

            case RIGHT:
                for (int i = 1; i <= length; i++) {
                    pos.x++;
                    map[pos.x][pos.y] = step++;
                }
                break;
        }

        printMap();
    }

    // =========================================================

    private void printMap() {

        clearScreen();

        // header -----------------------------------------

        System.out.print("  |");
        for(int i = 0; i < SIZE; i++) {
            System.out.print(String.format("%1$2d|", i));
        }
        System.out.println();

        for(int i = 0; i <= SIZE * 3 + 2 ; i++) {
            System.out.print("-");
        }

        System.out.println();

        // -------------------------------------------------

        for(int j = 0; j < SIZE; j++) {

            System.out.print(String.format("%1$2d|", j));

            for(int i = 0; i < SIZE; i++){
                //System.out.print(map[i][j] + "|");

                System.out.print(String.format("%1$02d|",map[i][j]));
            }

            System.out.println();
            for(int i = 0; i <= SIZE * 3 + 2 ; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println();
    }



    /**
     * Will clear when run in standalone console
     */
    static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }



}