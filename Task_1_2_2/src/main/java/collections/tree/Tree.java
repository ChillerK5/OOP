package collections.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

public class Tree<T> implements Collection<T> {
    private Node<T> root;

    public static class Node<T> {
        private T data;
        private Tree<T> parent;
        private ArrayList<Tree<T>> children;

        public Node() {
            this.data = null;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public Node(T data, Tree<T> parent) {
            this.data = data;
            this.parent = parent;
            this.children = new ArrayList<>();
        }
    }

    public Tree() {
        root = new Node<>();
    }

    public Tree(Node<T> node) {
        root = node;
    }

    /**
     * Evaluates size of a tree recursively via Stream API
     * by calling Tree.size() method for each element in node's children
     * and summing it.
     *
     * @return amount of the elements in a tree
     */
    @Override
    public int size() {
        int recursiveSize = root.children.stream().mapToInt(Tree::size).sum();
        int childrenSize = root.children.size();
        return recursiveSize + childrenSize;
    }

    /**
     * Checks whether a tree is empty.
     *
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Checks recursively whether some value is present in a tree.
     *
     * @param o element whose presence in this collection is to be tested
     * @return true if it does
     */
    @Override
    public boolean contains(final Object o) {
        if (root.children.contains(o) || root.data == o) {
            return true;
        }

        return root.children.stream().anyMatch(x -> x.contains(o));
    }

    //TODO Rewrite using Dequeue for BFS and DFS both
    /**
     * Returns an iterator for a data field of a tree.
     *
     * @return iterator object
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int currentIndex = 1;
            private Tree<T> currentTree;

            /**
             * Checks whether a node has next node or not.
             *
             * @return true if it does
             */
            @Override
            public boolean hasNext() {
                Node<T> currentNode = currentTree.root;

                boolean hasDescendants = !currentNode.children.isEmpty();

                ArrayList<Tree<T>> brothers = currentNode.parent.root.children;
                boolean hasBrothers = currentIndex != brothers.size() - 1;

                return hasDescendants || hasBrothers;
            }

            /**
             * Gets the next element in a tree.
             *
             * @return next element
             */
            @Override
            public T next() throws NoSuchElementException {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }

                Node<T> currentNode = currentTree.root;

                ArrayList<Tree<T>> brothers = currentNode.parent.root.children;
                boolean hasBrothers = currentIndex != brothers.size() - 1;

                if (hasBrothers) {
                    currentIndex++;
                    currentTree = brothers.get(currentIndex);
                    return currentTree.root.data;
                } else {
                    T returns = currentNode.data;
                    currentIndex = 0;
                    currentTree = currentNode.children.get(currentIndex);
                    return returns;
                }
            }
        };
    }

    /**
     * Returns array of values in a tree in reverse polish notation.
     *
     * @return array of values
     */
    @Override
    public Object[] toArray() {
        int treeSize = this.size();
        Object[] returnArray = new Object[treeSize];

        Iterator<T> iterator = this.iterator();
        int i = 1;

        returnArray[0] = this.root.data;
        while (iterator.hasNext()) {
            returnArray[i] = iterator.next();
            i++;
        }

        return returnArray;
    }

    /**
     * @param a    the array into which the elements of this collection
     *             are to be stored, if it is big enough;
     *             otherwise, a new array of the same
     *             runtime type is allocated for this purpose.
     * @param <T1>
     * @return
     */
    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return null;
    }

    /**
     * @param t element whose presence in this collection is to be ensured
     * @return
     */
    @Override
    public boolean add(T t) {
        Node<T> newNode = new Node<>(t, this);
        Tree<T> newTree = new Tree<>(newNode);

        this.root.children.add(newTree);
        return true;
    }

    /**
     * @param o element to be removed from this collection, if present
     * @return
     */
    @Override
    public boolean remove(Object o) {
        return false;
    }

    /**
     * @param c collection to be checked for containment in this collection
     * @return
     */
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    /**
     * @param c collection containing elements to be added to this collection
     * @return
     */
    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return false;
    }

    /**
     * @param c collection containing elements to be removed from this collection
     * @return
     */
    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    /**
     * @param c collection containing elements to be retained in this collection
     * @return
     */
    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    /**
     *
     */
    @Override
    public void clear() {

    }
}
