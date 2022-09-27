package ru.nsu.kbagryantsev;

/**
 * Interface of a stack data structure.
 */
public interface Stackable<T> {
    Stackable<T> push(T value);

    void pushStack(Stackable<T> stack);

    T pop();

    Stackable<T> popStack(int n);

    int count();
}
