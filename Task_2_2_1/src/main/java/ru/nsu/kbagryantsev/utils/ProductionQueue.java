package ru.nsu.kbagryantsev.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

public final class ProductionQueue<E> {
    private final Queue<E> data;
    private final Object FULL_QUEUE = new Object();
    private final Object EMPTY_QUEUE = new Object();
    private final int maxSize;

    public ProductionQueue(final int maxSize) {
        this.maxSize = maxSize;
        this.data = new ArrayDeque<>(maxSize);
    }

    public boolean isFull() {
        return maxSize == data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void waitOnEmpty() throws InterruptedException {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.wait();
        }
    }

    public void waitOnFull() throws InterruptedException {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.wait();
        }
    }

    public void notifyAllOnEmpty() {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.notifyAll();
        }
    }

    public void notifyAllOnFull() {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.notifyAll();
        }
    }

    public void add(final E element) {
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

    public E remove() {
        while (isEmpty()) {
            try {
                waitOnEmpty();
            } catch (InterruptedException e) {
                //TODO handle properly
                throw new RuntimeException();
            }
        }
        E element;
        synchronized (data) {
            element = data.poll();
        }
        notifyAllOnFull();
        return element;
    }

    public Collection<E> removeSome(int n) {
        while (data.isEmpty()) {
            try {
                waitOnEmpty();
            } catch (InterruptedException e) {
                //TODO handle properly
                throw new RuntimeException();
            }
        }
        Collection<E> elements = new ArrayList<>();
        synchronized (data) {
            while (!data.isEmpty() && elements.size() < n) {
                elements.add(data.poll());
            }
        }
        notifyAllOnFull();
        return elements;
    }
}
