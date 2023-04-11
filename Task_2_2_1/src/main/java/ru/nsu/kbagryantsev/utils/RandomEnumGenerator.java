package ru.nsu.kbagryantsev.utils;

import java.util.Random;

/**
 * Random enum generator.
 */
public final class RandomEnumGenerator {
    private RandomEnumGenerator() {
    }

    /**
     * Random instance.
     */
    private static final Random RANDOM = new Random();

    /**
     * Gets a random value from given enum.
     *
     * @param enumClass enum
     * @param <E>       enum type
     * @return random enum value
     */
    public static <E> E randomEnum(final Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[RANDOM.nextInt(values.length)];
    }
}
