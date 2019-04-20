package ru.home.geekbrains.java.core_03.threading3.entities.cruise;

import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.Segment;
import ru.home.geekbrains.java.core_03.threading3.entities.cruise.segment.TaskEntry;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.Geo;
import ru.home.geekbrains.java.core_03.threading3.entities.geo.GeoMap;

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

            Geo geo = GeoMap.map.get(te.getNavName());

            if(geo == null)
                throw new IllegalArgumentException("Unknown destination: " + te.getNavName());

            result.list.add(new Segment(geo, te.getTask()));
        }

        return result;
    }

}
