package ru.nsu.kbagryantsev;

import java.util.Collection;

/**
 * Executes given task via creating several new threads.
 */
public class MultiThreadedTask implements Task {
    @Override
    public boolean anyComposite(Collection<Integer> numbers) {
        return false;
    }
}
