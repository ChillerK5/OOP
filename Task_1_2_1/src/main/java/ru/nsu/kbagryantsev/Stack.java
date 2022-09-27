package ru.nsu.kbagryantsev;

import java.lang.reflect.Field;
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
        if (this.occupancy == this.capacity) {
            this.data = Arrays.copyOf(data, capacity * 2);
            this.capacity *= 2;
        }

        this.data[occupancy] = value;
        this.occupancy += 1;

        return this;
    }

    /**
     * Pushes values from a given stack.
     *
     * @param stack Stack object
     */
    @Override
    @SuppressWarnings("unchecked")
    public void pushStack(final Stackable<T> stack) {
        int sizeValues = stack.count();

        if (this.occupancy + sizeValues >= this.capacity) {
            this.capacity = this.occupancy + sizeValues + DEFAULT_CAPACITY;
            this.data = Arrays.copyOf(data, capacity);
        }

        Field data;
        try {
            data = stack.getClass().getDeclaredField("data");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        data.setAccessible(true);

        T[] stackData;
        try {
            stackData = (T[]) data.get(stack);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(stackData, 0, this.data, this.occupancy, sizeValues);

        this.occupancy += sizeValues;
    }

    /**
     * Returns the last element from stack and decreases its occupancy.
     *
     * @return type T value from the end of the stack list
     */
    @Override
    public T pop() throws IndexOutOfBoundsException {
        if (this.occupancy == 0) {
            throw new IndexOutOfBoundsException("Empty stack");
        }

        this.occupancy--;
        T returnValue = this.data[occupancy];

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

        int sourcePos = Math.max(this.occupancy - n, 0);

        for (int i = 0; i < this.occupancy - sourcePos; i++) {
            subStack.push(this.data[sourcePos + i]);
        }
        this.occupancy = sourcePos;

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
        return this.occupancy;
    }

    /**
     * Decreases the size of the stack
     * by the TRIM_RATE
     * if OCCUPANCY_RATE is exceeded.
     */
    private void trimStack() {
        if (this.occupancy <= OCCUPANCY_RATE * this.capacity) {
            this.capacity -= TRIM_RATE * this.capacity;
            this.data = Arrays.copyOf(this.data, this.capacity);
        }
    }
}
