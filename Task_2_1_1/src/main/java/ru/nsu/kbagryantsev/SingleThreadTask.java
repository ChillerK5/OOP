package ru.nsu.kbagryantsev;

import java.util.Collection;

/**
 * Executes given task consecutively.
 */
public class SingleThreadTask implements Task {
    @Override
    public boolean anyComposite(Collection<Integer> numbers) {
        return numbers.stream().anyMatch(PrimeChecker::isPrime);
    }
}
