package ru.home.geekbrains.java.core_03.threading3.entities.infrastructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TerminalList implements Iterable<Terminal> {

    private Port owner = null;

    private List<Terminal> list = new ArrayList<>();


    public TerminalList(Port owner) {
        this.owner = owner;
    }



    public void add(Terminal terminal) {

        list.add(terminal);
        terminal.setOwner(owner);
    }


    @Override
    public Iterator<Terminal> iterator() {
        return list.iterator();
    }
}
