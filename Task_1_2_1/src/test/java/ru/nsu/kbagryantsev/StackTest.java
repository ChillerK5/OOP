package ru.nsu.kbagryantsev;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StackTest {
    @Test
    @Tag("initializationTest")
    @DisplayName("Initializing stack")
    public void initTest() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("pushTest")
    @DisplayName("Single element")
    public void singlePush() {
        Stack<Integer> test = new Stack<>();

        test.push(1);
        Assertions.assertEquals(test.count(), 1);
    }

    @Test
    @Tag("pushTest")
    @DisplayName("Default size exceeded")
    public void exceededPush() {
        Stack<Integer> test = new Stack<>();

        test.push(1).push(2).push(3).push(4).push(5);
        Assertions.assertEquals(test.count(), 5);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Simple list push")
    public void simplePushStack() {
        Stack<Integer> test = new Stack<Integer>().push(1).push(2).push(3);
        Stack<Integer> sample = new Stack<Integer>().push(4).push(5).push(6);

        test.pushStack(sample);

        Assertions.assertEquals(test.count(), 6);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Push empty stack into empty stack")
    public void empty2emptyPushStack() {
        Stack<Integer> test = new Stack<>();

        test.pushStack(new Stack<>());

        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Push empty stack into non-empty stack")
    public void emptyPushStack() {
        Stack<Integer> test = new Stack<Integer>().push(1).push(2).push(3);

        test.pushStack(new Stack<>());
        test.pushStack(new Stack<>());

        Assertions.assertEquals(test.count(), 3);
    }

    @Test
    @Tag("pushStackTest")
    @DisplayName("Default size exceeded")
    public void defaultListSizeExceeded() {
        Stack<Integer> test = new Stack<>();

        Stack<Integer> sample = new Stack<>();
        sample.push(1).push(2).push(3).push(4).push(5);

        test.pushStack(sample);
        Assertions.assertEquals(test.count(), 5);
    }

    @Test
    @Tag("popTest")
    @DisplayName("Single pop")
    public void singlePop() {
        Stack<Integer> test = new Stack<Integer>().push(1).push(2).push(3);

        Assertions.assertEquals(test.pop(), 3);

        Assertions.assertEquals(test.count(), 2);
    }

    @Test
    @Tag("popTest")
    @DisplayName("Pop from empty stack")
    public void emptyPop() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertEquals(test.count(), 0);
        Assertions.assertThrows(Throwable.class, test::pop);
    }

    @Test
    @Tag("popTest")
    @DisplayName("Pop last element and pop again")
    public void doublePop() {
        Stack<Integer> test = new Stack<Integer>().push(1);

        Assertions.assertEquals(test.pop(), 1);

        Assertions.assertEquals(test.count(), 0);
        Assertions.assertThrows(Throwable.class, test::pop);

        Assertions.assertEquals(test.count(), 0);
        Assertions.assertThrows(Throwable.class, test::pop);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Simple list pop")
    public void simplePopStack() {
        Stack<Integer> test = new Stack<>();
        test.push(1).push(2).push(3).push(4).push(5);

        test.popStack(3);
        Assertions.assertEquals(test.count(), 2);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Pop from an empty list")
    public void emptyPopStack() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertEquals(test.popStack(1).count(), 0);

        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Pop more elements than are present")
    public void overflowPopStack() {
        Stack<Integer> test = new Stack<Integer>().push(1).push(2).push(3);

        Assertions.assertEquals(test.popStack(255).count(), 3);
        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("popStackTest")
    @DisplayName("Pop negative amount of elements")
    public void negativePopStack() {
        Stack<Integer> test = new Stack<Integer>().push(1).push(2).push(3);

        Assertions.assertThrows(Throwable.class, () -> test.popStack(-10));
        Assertions.assertEquals(test.count(), 3);
    }

    @Test
    @Tag("countTest")
    @DisplayName("Normally sized stack")
    public void normalCount() {
        Stack<Integer> test = new Stack<Integer>().push(1).push(2).push(3);

        Assertions.assertEquals(test.count(), 3);
    }

    @Test
    @Tag("countTest")
    @DisplayName("Empty stack")
    public void emptyCount() {
        Stack<Integer> test = new Stack<>();

        Assertions.assertEquals(test.count(), 0);
    }

    @Test
    @Tag("trimStackTest")
    @DisplayName("Trimming a stack of extended size")
    public void extendedTrim() throws NoSuchFieldException, IllegalAccessException {
        Stack<Integer> test = new Stack<>();
        test.push(1).push(2).push(3).push(4).push(5);

        Field capacityField = test.getClass().getDeclaredField("capacity");
        capacityField.setAccessible(true);

        test.popStack(2);

        int capacity = capacityField.getInt(test);
        Assertions.assertTrue(test.count() <= capacity);
    }

    @Test
    @Tag("trimStackTest")
    @DisplayName("Trimming a default sized stack")
    public void defaultTrim() throws NoSuchFieldException, IllegalAccessException {
        Stack<Integer> test = new Stack<>();
        test.push(1).push(2).push(3).push(4);

        Field capacityField = test.getClass().getDeclaredField("capacity");
        capacityField.setAccessible(true);

        test.pop();
        int capacity = capacityField.getInt(test);
        Assertions.assertTrue(test.count() <= capacity);

        test.pop();
        capacity = capacityField.getInt(test);
        Assertions.assertTrue(test.count() <= capacity);

        test.pop();
        capacity = capacityField.getInt(test);
        Assertions.assertTrue(test.count() <= capacity);

        test.pop();
        capacity = capacityField.getInt(test);
        Assertions.assertTrue(test.count() <= capacity);
    }

    @Test
    @Tag("trimStackTest")
    @DisplayName("Trimming a stack of multiple-ways-extended size")
    public void oddTrim() throws NoSuchFieldException, IllegalAccessException {
        Stack<Integer> test = new Stack<>();
        test.push(1).push(2).push(3).push(4).push(5);
        Stack<Integer> sample = new Stack<>();
        sample.push(6).push(7).push(8);

        test.pushStack(sample);

        Field capacityField = test.getClass().getDeclaredField("capacity");
        capacityField.setAccessible(true);

        test.popStack(4);

        int capacity = capacityField.getInt(test);
        Assertions.assertTrue(test.count() <= capacity);
    }
}