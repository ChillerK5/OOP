package ru.nsu.kbagryantsev;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Stack interface implementation.
 */
public class Stack<T> implements Stackable<T> {
    /**
     * Predefined capacity for a construction method.
     */
    private static final int DEFAULT_CAPACITY = 4;
    /**
     * Occupancy to capacity ratio for trimming purposes.
     */
    private static final double OCCUPANCY_RATE = 0.4;
    /**
     * Capacity decrease rate for trimming purposes.
     */
    private static final double TRIM_RATE = 0.3;
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
     * Stack class constructor. Creates default sized stack.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.occupancy = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Pushes given value to the end of the stack.
     * !also supports method chaining
     *
     * @param value type T given value
     */
    @Override
    public Stack<T> push(final T value) {
        if (occupancy == capacity) {
            data = Arrays.copyOf(data, capacity * 2);
            capacity *= 2;
        }

        data[occupancy] = value;
        occupancy += 1;

        return this;
    }

    /**
     * Pushes values from a given stack.
     * ! destructs given stack
     *
     * @param stack Stack object
     */
    @Override
    public void pushStack(final Stackable<T> stack) {
        int sizeValues = stack.count();

        if (occupancy + sizeValues >= capacity) {
            capacity = occupancy + sizeValues + DEFAULT_CAPACITY;
            this.data = Arrays.copyOf(this.data, capacity);
        }

        for (int i = 0; i < sizeValues; i++) {
            this.push(stack.pop());
        }
    }

    /**
     * Returns the last element from stack and decreases its occupancy.
     *
     * @return type T value from the end of the stack list
     */
    @Override
    public T pop() throws EmptyStackException {
        if (occupancy == 0) {
            throw new EmptyStackException();
        }

        occupancy--;
        T returnValue = data[occupancy];

        trimStack();
        return returnValue;
    }

    /**
     * Returns Stack object of
     * N elements from the end of the stack.
     * If there are too few elements returns as much as possible
     *
     * @return new Stack object with elements
     */
    @Override
    public Stackable<T> popStack(final int n) throws IndexOutOfBoundsException {
        if (n <= 0) {
            throw new IndexOutOfBoundsException("Argument must be positive");
        }

        Stack<T> subStack = new Stack<>();

        int sourcePos = Math.max(occupancy - n, 0);

        for (int i = 0; i < occupancy - sourcePos; i++) {
            subStack.push(data[sourcePos + i]);
        }
        occupancy = sourcePos;

        trimStack();
        return subStack;
    }

    /**
     * Gets the amount of elements in stack.
     *
     * @return integer amount of elements
     */
    @Override
    public int count() {
        return occupancy;
    }

    /**
     * Decreases the size of the stack
     * by the TRIM_RATE
     * if OCCUPANCY_RATE is exceeded.
     */
    private void trimStack() {
        if (occupancy <= OCCUPANCY_RATE * capacity) {
            capacity -= TRIM_RATE * capacity;
            data = Arrays.copyOf(data, capacity);
        }
    }
}
