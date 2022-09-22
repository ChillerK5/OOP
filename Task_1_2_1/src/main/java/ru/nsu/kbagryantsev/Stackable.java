package ru.nsu.kbagryantsev;

/**
 * Interface of a stack data structure.
 */
public interface Stackable<T> {
    void push(T value);

    void pushStack(T[] value);

    T pop();

    T[] popStack(int n);

    int count();
}
