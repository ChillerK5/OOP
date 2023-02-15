package ru.nsu.kbagryantsev;

import java.util.List;

/**
 * Executes benchmark task using ParallelStream.
 */
public final class ParallelStreamTask {
    private ParallelStreamTask() {

    }

    /**
     * Executes isPrime check for all members of a collection
     * via ParallelStream.
     *
     * @param numbers numbers to check
     * @return result of anyComposite
     */
    public static boolean main(final List<Integer> numbers) {
        return numbers.parallelStream().anyMatch(PrimeNumbersUtils::isPrime);
    }
}
