package ru.nsu.kbagryantsev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Handwritten unit tests.
 */
public class HeapsortTest {
    @Test
    @DisplayName("Null list")
    public void list_null() {
        int[] test = null;

        Heapsort.sort(test);
    }

    @Test
    @DisplayName("Empty list")
    public void empty() {
        int[] test = {};
        int[] reference = {};

        Heapsort.sort(test);

        Assertions.assertArrayEquals(test, reference);
    }

    @Test
    @DisplayName("Simple list")
    public void basic() {
        int[] test = {5, 8, 20, 26, 1, 6};
        int[] reference = {1, 5, 6, 8, 20, 26};

        Heapsort.sort(test);

        Assertions.assertArrayEquals(test, reference);
    }

    @Test
    @DisplayName("Homogenous list")
    public void homogenous() {
        int[] test = {5, 5, 5, 5, 1, 5, 1, 6};
        int[] reference = {1, 1, 5, 5, 5, 5, 5, 6};

        Heapsort.sort(test);

        Assertions.assertArrayEquals(test, reference);
    }

    @Test
    @DisplayName("Descending list")
    void descending() {
        int[] test = {10, 9, 8, 7, 6, 5, 4};
        int[] reference = {4, 5, 6, 7, 8, 9, 10};

        Heapsort.sort(test);

        Assertions.assertArrayEquals(test, reference);
    }

    @Test
    @DisplayName("List forms a non-complete binary tree")
    public void non_complete() {
        int[] test = {17, 8, 5, 1, 3};
        int[] reference = {1, 3, 5, 8, 17};

        Heapsort.sort(test);

        Assertions.assertArrayEquals(test, reference);
    }

    @Test
    @DisplayName("Sorted list")
    public void sorted() {
        int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] reference = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        Heapsort.sort(test);

        Assertions.assertArrayEquals(test, reference);
    }
}