package ru.nsu.kbagryantsev;

import java.util.Arrays;

/**
 * Stack interface implementation.
 */
public class Stack<T> implements Stackable<T> {
    /**
     * Predefined capacity for a construction method.
     */
    private static final int DEFAULT_CAPACITY = 4;
    /**
     * List of stack elements.
     */
    private T[] data;
    /**
     * Amount of elements in stack.
     */
    private int occupancy;
    /**
     * Size of a stack list.
     */
    private int capacity;

    /**
     * Stack initializer.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.occupancy = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Accessor method for data field.
     *
     * @return private field data
     */
    public T[] getData() {
        return this.data;
    }

    /**
     * Pushes given value to the end of the stack.
     *
     * @param value type T given value
     */
    @Override
    public void push(final T value) {
        if (this.occupancy == this.capacity) {
            this.data = Arrays.copyOf(data, capacity * 2);
            this.capacity *= 2;
        }

        this.add(value);
    }

    /**
     * Pushes a list of given values to the end of the stack.
     *
     * @param values list of type T values to be pushed
     */
    @Override
    public void pushStack(final T[] values) {
        int sizeValues = values.length;

        if (this.occupancy + sizeValues >= this.capacity) {
            this.capacity = occupancy + sizeValues + DEFAULT_CAPACITY;
            this.data = Arrays.copyOf(data, capacity);
        }

        for (T value : values) {
            this.add(value);
        }
    }

    /**
     * Returns the last element from stack and decreases its occupancy.
     */
    @Override
    public T pop() {
        if (this.occupancy == 0) {
            return null;
        }

        this.occupancy--;
        return this.data[occupancy];
    }

    /**
     * Returns n elements from the end of the stack
     * or as much as possible if there are too few of them.
     *
     * @return list of elements
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] popStack(final int n) {
        if (n <= 0) {
            return null;
        }

        this.occupancy = this.occupancy > n ? this.occupancy - n : 0;

        T[] returnValues = (T[]) new Object[n];
        System.arraycopy(this.data, occupancy, returnValues, 0, n);

        return returnValues;
    }

    /**
     * Gets the amount of elements in stack.
     *
     * @return integer amount of elements
     */
    @Override
    public int count() {
        return this.occupancy;
    }

    /**
     * Auxiliary method, which adds an element into a stack list.
     *
     * @param value type T value
     */
    private void add(final T value) {
        this.data[occupancy] = value;
        this.occupancy += 1;
    }

    /**
     * Plug for tests.
     */
    public void idle() {
    }
}
