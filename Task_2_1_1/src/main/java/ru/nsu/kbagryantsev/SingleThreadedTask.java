package ru.nsu.kbagryantsev;

import java.util.List;

/**
 * Executes given task consecutively.
 */
public final class SingleThreadedTask {
    private SingleThreadedTask() {

    }
    /**
     * Executes anyComposite in a consecutive way.
     *
     * @param numbers list of numbers to check
     * @return result of anyComposite
     */
    public static boolean main(final List<Integer> numbers) {
        return PrimeNumbersUtils.anyComposite(numbers);
    }
}
