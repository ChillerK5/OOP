package ru.nsu.kbagryantsev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrimeNumbersUtilsTest {
    @Test
    @DisplayName("Pure prime")
    void primeCheck() {
        Integer data = 31;

        Assertions.assertTrue(PrimeNumbersUtils.isPrime(data));
    }

    @Test
    @DisplayName("Any composite")
    void compositeCheck() {
        Integer data = 57;

        Assertions.assertFalse(PrimeNumbersUtils.isPrime(data));
    }

    @Test
    @DisplayName("Tricky prime")
    void trickyPrime() {
        Integer data = 25013 * 25013;

        Assertions.assertFalse(PrimeNumbersUtils.isPrime(data));
    }
}