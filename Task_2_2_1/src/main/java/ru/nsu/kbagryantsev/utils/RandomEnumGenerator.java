package ru.nsu.kbagryantsev.utils;

import java.util.Random;

public class RandomEnumGenerator {
    private static final Random RANDOM = new Random();

    public static <E> E randomEnum(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[RANDOM.nextInt(values.length)];
    }
}
