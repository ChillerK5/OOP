package ru.nsu.kbagryantsev;

import java.util.Collection;

/**
 * Interface for all kind of task implementations for a benchmark test.
 */
public interface Task {
    /**
     * Checks a given collection of numbers for containing any composite ones.
     *
     * @return true if contains
     */
    boolean anyComposite(Collection<Integer> numbers);
}
