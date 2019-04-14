package ru.home.geekbrains.java.core_03.threading2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Race implements Iterable<Stage>{
    private ArrayList<Stage> stageList;
    public Race(Stage... stages) {
        this.stageList = new ArrayList<>(Arrays.asList(stages));
    }


    @Override
    public Iterator<Stage> iterator() {
        return stageList.iterator();
    }

    public int size() {
        return stageList.size();
    }

    public Stage get(int i) {
        return stageList.get(i);
    }

}
