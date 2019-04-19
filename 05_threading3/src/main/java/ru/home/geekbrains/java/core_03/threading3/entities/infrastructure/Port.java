package ru.home.geekbrains.java.core_03.threading3.entities.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Port {

    private String name;

    private ConcurrentMap<ProductType,Integer> goods = new ConcurrentHashMap<>();

    private DockList dockList = new DockList(this);

    public Port(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public DockList getDockList() {
        return dockList;
    }

    public ConcurrentMap<ProductType, Integer> getGoods() {
        return goods;
    }

    @Override
    public String toString() {
        return "Port{" +
               "name='" + name + '\'' +
               ", goods=" + goods +
               '}';
    }
}
