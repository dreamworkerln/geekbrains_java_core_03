package ru.home.geekbrains.java.core_03.ntree;

import java.util.*;

public class NTree<T> {

    // root
    private Node<T> root;

    public NTree(Node<T> root) {

        this.root = root;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void printPreOrder(List<Node<T>> zpc) {

        printPreOrder(root, zpc);

    }

    private void printPreOrder(Node<T> node, List<Node<T>> zpc) {

        if(node == null)
            return;

        Stack<Node<T>> stack = new Stack<>();
        //Node<T> current = node;


        stack.push(node);

        //stack.push(current);

        while(!stack.empty()) {

            Node<T> current = stack.pop();

            System.out.print(current.value + " ");
            zpc.add(current);

            ListIterator<Node<T>> it = current.leafs.listIterator(current.leafs.size());

            while (it.hasPrevious()) {
                stack.push(it.previous());
            }


        }
    }
}



