package ru.nsu.kbagryantsev;

import java.util.List;
import java.util.function.Predicate;

/**
 * Executes benchmark task using ParallelStream.
 */
public final class ParallelStreamTask {
    private ParallelStreamTask() { }

    /**
     * Executes isPrime check for all members of a collection
     * via ParallelStream.
     *
     * @param numbers numbers to check
     * @return result of anyComposite
     */
    public static boolean call(final List<Integer> numbers) {
        Predicate<Integer> isComposite = n -> !PrimeNumbersUtils.isPrime(n);
        return numbers.parallelStream().anyMatch(isComposite);
    }
}
