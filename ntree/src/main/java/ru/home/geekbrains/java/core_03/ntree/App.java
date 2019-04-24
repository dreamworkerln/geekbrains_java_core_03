package ru.home.geekbrains.java.core_03.ntree;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main(String[] args) {

        new App();
    }
    
    

    App() {



        NTree<Integer> tree = new NTree<>(new Node<>(0));
        Node<Integer> root = tree.getRoot();

        Node<Integer> first = new Node<>(1, root);
        new Node<>(2, root);
        new Node<>(6, root);

        Node<Integer> third = new Node<>(3, first);
        Node<Integer> fourth = new Node<>(4, third);
        new Node<>(5, fourth);

        List<Node<Integer>> list = new ArrayList<>();
        tree.printPreOrder(list);

        System.out.println();

        System.out.println((list.toString()));
    }

}