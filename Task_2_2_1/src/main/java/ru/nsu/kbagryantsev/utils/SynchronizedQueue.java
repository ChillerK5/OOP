package ru.nsu.kbagryantsev.utils;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import org.checkerframework.checker.index.qual.Positive;

/**
 * Thread-safe queue.
 *
 * @param <T> any type
 */
public final class SynchronizedQueue<T> {
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
    public SynchronizedQueue(@Positive final int maxSize) {
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
            emptyQueue.notify();
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
     * Adds a given element. This is a blocking operation.
     *
     * @param element element
     */
    public void add(final T element) {
        while (isFull()) {
            try {
                waitOnFull();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
        synchronized (data) {
            data.add(element);
        }
        notifyAllOnEmpty();
    }

    /**
     * Guarantees a single element from the queue. This is a blocking
     * operation.
     *
     * @return element
     */
    public T remove() {
        while (isEmpty()) {
            try {
                waitOnEmpty();
            } catch (InterruptedException e) {
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
     * Returns optional element from the queue.
     *
     * @return element
     */
    public Optional<T> nonBlockingRemove() {
        if (isEmpty()) {
            return Optional.empty();
        }
        Optional<T> optionalT;
        synchronized (data) {
            optionalT = Optional.ofNullable(data.poll());
        }
        notifyAllOnFull();
        return optionalT;
    }
}
