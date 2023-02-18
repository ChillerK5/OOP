package ru.nsu.kbagryantsev;

import java.util.List;

/**
 * Checks the number for being prime.
 */
public final class PrimeNumbersUtils {
    private PrimeNumbersUtils() { }

    /**
     * Performs the simplest check for a number using trivial algorithm.
     *
     * @param number checked number
     * @return true if prime
     */
    public static boolean isPrime(final Integer number) {
        if (number == 0 || number == 1) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(number) + 1; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks a collection for containing any composite number. Naive and
     * dull implementation.
     *
     * @param numbers collection to be checked
     * @return true if contains a composite
     */
    public static boolean anyComposite(final List<Integer> numbers) {
        return numbers.stream().anyMatch(number -> !isPrime(number));
    }
}
