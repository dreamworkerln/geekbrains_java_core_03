package ru.home.geekbrains.java.core_03.ntree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node<T> {
    T value;
    List<Node<T>> leafs;

    public Node(T value)
    {
        this.value = value;
        leafs = new LinkedList<>();
    }

    public Node(T value, Node<T> parent)
    {
        this(value);
        parent.add(this);

    }


    public void add(Node<T> node) {
        leafs.add(node);
    }

    /**
     * Get child by index
     * @param i index
     * @return
     */
    public Node<T> get(int i) {
        return leafs.get(i);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
