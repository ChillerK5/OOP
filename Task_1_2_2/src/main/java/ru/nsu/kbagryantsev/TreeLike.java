package ru.nsu.kbagryantsev;

import java.util.ArrayList;

/**
 * Extendable custom generic tree data structure.
 * Has no limitations on its arity.\
 *
 * @param <T> any type
 */
public abstract class TreeLike<T> implements Iterable<T> {
    /**
     * Head element of the tree structure.
     */
    private NodeLike<T> root;

    /**
     * Generic type Node of a tree-like data structure.
     *
     * @param <T> any type
     */
    public abstract static class NodeLike<T> {
        /**
         * Object tree node value.
         */
        private T data;
        /**
         * Link to the object's parent node.
         */
        private NodeLike<T> parent;
        /**
         * List of children nodes.
         */
        private ArrayList<NodeLike<T>> children;

        /**
         * Adds an element to a node's list.
         *
         * @param value data field of an added node
         *
         * @return an added element
         */
        public abstract NodeLike<T> add(T value);

        /**
         * Adds a subtree of elements to a node's list.
         *
         * @param subtree tree to be added via its root node
         *
         * @return root of an added subtree
         */
        public abstract NodeLike<T> addSubtree(Tree<T> subtree);

        /**
         * Removes current node from a tree and all its descendants.
         *
         * @return removed note as a subtree
         */
        public abstract NodeLike<T> remove();
    }
}
