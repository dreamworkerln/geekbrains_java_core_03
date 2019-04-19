package ru.home.geekbrains.java.core_03.threading3.entities.cruise;

import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.Segment;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.TaskEntry;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.GeoMap;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Nav;

import java.util.ArrayList;
import java.util.List;

public class Cruise  {

    public List<Segment> list = new ArrayList<>();

    public List<Segment> actions = new ArrayList<>();


    /**
     * Построение круиза по списку названий локаций для посещения
     * @param route Список названий локаций
     * @return Cruise
     */
    public static Cruise build(List<TaskEntry> route) {

        Cruise result = new Cruise();

        for(TaskEntry te : route) {

            Nav nav = GeoMap.map.get(te.getNavName());

            if(nav == null)
                throw new IllegalArgumentException("Unknown destination: " + te.getNavName());

            result.list.add(new Segment(nav, te.getTask()));
        }

        return result;
    }

}
