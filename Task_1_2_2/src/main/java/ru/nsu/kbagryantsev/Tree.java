package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public final class Tree<T> extends TreeLike<T> {
    private Node<T> root;

    public Tree () {
        root = new Node<>();
    }
    public Tree (Node<T> rootNode) {
        root = rootNode;
    }

    /**
     * @return
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private ArrayList<Node<T>> children;

        public Node() {
            this.data = null;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public Node<T> add(final T value) {
            Node<T> newNode = new Node<>();

            newNode.data = value;
            newNode.parent = this;

            this.children.add(newNode);

            return newNode;
        }

        public Node<T> add(Node<T> rootNode, final T value) {
            Node<T> newNode = new Node<>();

            newNode.data = value;
            newNode.parent = rootNode;

            rootNode.children.add(newNode);

            return newNode;
        }

        public Node<T> addSubtree(Tree<T> subtree) {
            subtree.root.parent = this;
            this.children.add(subtree.root);

            return subtree.root;
        }

        public Tree<T> remove() {
            return null;
        }
    }
}
