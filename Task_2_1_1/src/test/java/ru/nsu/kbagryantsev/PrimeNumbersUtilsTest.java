package ru.nsu.kbagryantsev;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PrimeNumbersUtilsTest {
    @Test
    @DisplayName("Coverage plug")
    void coveragePlug() throws
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor<PrimeNumbersUtils> constructor;
        constructor = PrimeNumbersUtils.class.getDeclaredConstructor();
        Assertions.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Nested
    @DisplayName("isPrime")
    class IsPrimeTest {
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

        @Test
        @DisplayName("Zero and one")
        void defaultValues() {
            Integer data = 0;

            Assertions.assertFalse(PrimeNumbersUtils.isPrime(data));
        }
    }

    @Nested
    @DisplayName("anyComposite")
    class AnyCompositeTest {
        @Test
        @DisplayName("All numbers are prime")
        void allPrime() {
            List<Integer> data = List.of(13, 23, 97, 3, 127373);

            Assertions.assertFalse(PrimeNumbersUtils.anyComposite(data));
        }

        @Test
        @DisplayName("One of numbers is composite")
        void oneComposite() {
            List<Integer> data = List.of(13, 23, 97, 6, 127373);

            Assertions.assertTrue(PrimeNumbersUtils.anyComposite(data));
        }
    }

}
