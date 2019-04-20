package ru.home.geekbrains.java.core_03.threading3.entities.infrastructure;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Port {

    private String name;

    private ConcurrentMap<ProductType,Integer> goods = new ConcurrentHashMap<>();

    private TerminalList terminalList = new TerminalList(this);

    public Port(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public TerminalList getTerminalList() {
        return terminalList;
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
