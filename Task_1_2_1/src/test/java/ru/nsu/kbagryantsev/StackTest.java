package ru.nsu.kbagryantsev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StackTest {
    @Test
    @Tag("initializationTest")
    @DisplayName("init")
    public void initTest() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{});
        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("pushTest")
    @DisplayName("Single element")
    public void singlePush() {
        Stack<Integer> test = new Stack<>();

        test.push(1);
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1});
        Assertions.assertEquals(test.count(), 1);
    }

    @Test
    @Tag("pushTest")
    @DisplayName("Default size exceeded")
    public void exceededPush() {
        Stack<Integer> test = new Stack<>();

        test.push(1);
        test.push(2);
        test.push(3);
        test.push(4);
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2, 3, 4});
        Assertions.assertEquals(test.count(), 4);

        test.push(5);
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2, 3, 4, 5});
        Assertions.assertEquals(test.count(), 5);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Simple list push")
    public void simplePushStack() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3});

        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2, 3});
        Assertions.assertEquals(test.count(), 3);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Push empty list into empty stack")
    public void empty2emptyPushStack() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{});

        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{});
        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Push empty list into non-empty stack")
    public void emptyPushStack() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3});
        test.pushStack(new Integer[]{});

        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2, 3});
        Assertions.assertEquals(test.count(), 3);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Default size exceeded")
    public void defaultListSizeExceeded() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3, 4});
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2, 3, 4});
        Assertions.assertEquals(test.count(), 4);

        test.pushStack(new Integer[]{5, 6, 7});
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Assertions.assertEquals(test.count(), 7);
    }

    @Test
    @Tag("popTest")
    @DisplayName("Single pop")
    public void singlePop() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3});

        Assertions.assertEquals(test.pop(), 3);
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2});
        Assertions.assertEquals(test.count(), 2);
    }

    @Test
    @Tag("popTest")
    @DisplayName("Pop from empty stack")
    public void emptyPop() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertNull(test.pop());
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{});
        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("popTest")
    @DisplayName("Pop last element and pop again")
    public void doublePop() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1});

        Assertions.assertEquals(test.pop(), 1);
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{});
        Assertions.assertEquals(test.count(), 0);

        Assertions.assertNull(test.pop());
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{});
        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Simple list pop")
    public void simplePopStack() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3, 4, 5});

        Assertions.assertArrayEquals(test.popStack(3), new Integer[]{3, 4, 5});
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2});
        Assertions.assertEquals(test.count(), 2);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Pop from an empty list")
    public void emptyPopStack() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertArrayEquals(test.popStack(42), new Integer[]{});
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{});
        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Pop more elements than are present")
    public void overflowPopStack() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3, 4, 5});

        Assertions.assertArrayEquals(test.popStack(255), new Integer[]{1, 2, 3, 4, 5});
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{});
        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Pop negative amount of elements")
    public void negativePopStack() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3, 4, 5});

        Assertions.assertNull(test.popStack(-10));
        Assertions.assertArrayEquals(test.peekStack(), new Integer[]{1, 2, 3, 4, 5});
        Assertions.assertEquals(test.count(), 5);
    }

    @Test
    @Tag("countTest")
    @DisplayName("Normally sized stack")
    public void normalCount() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Integer[]{1, 2, 3, 4, 5});

        Assertions.assertEquals(test.count(), 5);
    }

    @Test
    @Tag("countTest")
    @DisplayName("Empty stack")
    public void emptyCount() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertEquals(test.count(), 0);
    }
}