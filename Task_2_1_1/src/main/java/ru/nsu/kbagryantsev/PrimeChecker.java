package ru.nsu.kbagryantsev;

/**
 * Checks the number for being prime.
 */
public class PrimeChecker {
    /**
     * Performs the simplest check for a number using trivial algorithm.
     *
     * @param number checked number
     * @return true if prime
     */
    public static boolean isPrime(Integer number) {
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
}
