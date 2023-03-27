package ru.nsu.kbagryantsev.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

/**
 * Thread-safe queue.
 *
 * @param <T> any type
 */
public final class ProductionQueue<T> {
    /**
     * Queue.
     */
    private final Queue<T> data;
    /**
     * Full queue synchronization monitor.
     */
    private final Object fullQueue = new Object();
    /**
     * Empty queue synchronization monitor.
     */
    private final Object emptyQueue = new Object();
    /**
     * Maximal size of a queue.
     */
    private final int maxSize;

    /**
     * Maximal size of the queue.
     *
     * @param maxSize integer size
     */
    public ProductionQueue(final int maxSize) {
        this.maxSize = maxSize;
        this.data = new ArrayDeque<>(maxSize);
    }

    /**
     * Checks the queue is full.
     *
     * @return true if full
     */
    public boolean isFull() {
        return maxSize == data.size();
    }

    /**
     * Checks the queue is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Waits on empty queue.
     *
     * @throws InterruptedException not handled
     */
    public void waitOnEmpty() throws InterruptedException {
        synchronized (emptyQueue) {
            emptyQueue.wait();
        }
    }

    /**
     * Waits on full queue.
     *
     * @throws InterruptedException not handled
     */
    public void waitOnFull() throws InterruptedException {
        synchronized (fullQueue) {
            fullQueue.wait();
        }
    }

    /**
     * Notifies all threads waiting on empty queue.
     */
    public void notifyAllOnEmpty() {
        synchronized (emptyQueue) {
            emptyQueue.notifyAll();
        }
    }

    /**
     * Notifies all threads waiting on full queue.
     */
    public void notifyAllOnFull() {
        synchronized (fullQueue) {
            fullQueue.notifyAll();
        }
    }

    /**
     * Adds a given element.
     *
     * @param element element
     */
    public void add(final T element) {
        while (isFull()) {
            try {
                waitOnFull();
            } catch (InterruptedException e) {
                //TODO handle properly
                throw new RuntimeException();
            }
        }
        synchronized (data) {
            data.add(element);
        }
        notifyAllOnEmpty();
    }

    /**
     * Removes a single element.
     *
     * @return element
     */
    public T remove() {
        while (isEmpty()) {
            try {
                waitOnEmpty();
            } catch (InterruptedException e) {
                //TODO handle properly
                throw new RuntimeException();
            }
        }
        T element;
        synchronized (data) {
            element = data.poll();
        }
        notifyAllOnFull();
        return element;
    }

    /**
     * Tries to remove as many elements as possible below the given bound.
     *
     * @param n elements bound
     * @return collection of elements
     */
    public Collection<T> removeSome(final int n) {
        while (data.isEmpty()) {
            try {
                waitOnEmpty();
            } catch (InterruptedException e) {
                //TODO handle properly
                throw new RuntimeException();
            }
        }
        Collection<T> elements = new ArrayList<>();
        synchronized (data) {
            while (!data.isEmpty() && elements.size() < n) {
                elements.add(data.poll());
            }
        }
        notifyAllOnFull();
        return elements;
    }
}
