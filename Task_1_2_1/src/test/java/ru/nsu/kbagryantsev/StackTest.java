package ru.nsu.kbagryantsev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StackTest {
    @Test
    @DisplayName("init")
    public void initTest() {
        Stack<Integer> test = new Stack<>();
        test.idle();

        assertAll();
    }

    @Test
    @DisplayName("push")
    public void pushTest() {
        Stack<Integer> test = new Stack<>();
        test.push(1);

        assertAll();
    }

    @Test
    @DisplayName("pushStack")
    public void pushStackTest() {
        Stack<Integer> test = new Stack<>();
        Integer[] sample = new Integer[]{1,2,3,4,5};

        test.pushStack(sample);

        assertArrayEquals(test.getData(), new Integer[]{1,2,3,4,5});
    }

    @Test
    @DisplayName("pop")
    public void popTest() {
        Stack<Integer> test = new Stack<>();
        Integer[] sample = new Integer[]{1,2,3,4,5};

        test.pushStack(sample);
        Integer reference = test.pop();

        assertEquals(reference, 5);
    }

    @Test
    @DisplayName("popStack")
    public void popStackTest() {
        Stack<Integer> test = new Stack<>();
        Integer[] sample = new Integer[]{1,2,3,4,5};

        test.pushStack(sample);
        Integer[] reference = test.popStack(3);

        assertArrayEquals(reference, new Integer[]{3,4,5});
    }

    @Test
    @DisplayName("count")
    public void countTest() {
        Stack<Integer> test = new Stack<>();
        Integer[] sample = new Integer[]{1,2,3,4,5};

        test.pushStack(sample);
        int reference = test.count();

        assertEquals(reference, 5);
    }
}