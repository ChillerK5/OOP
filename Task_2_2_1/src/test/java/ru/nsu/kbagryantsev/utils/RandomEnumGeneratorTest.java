package ru.nsu.kbagryantsev.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.order.pizza.PizzaOptions;

/**
 * Random enum generator tests.
 */
public class RandomEnumGeneratorTest {
    @Test
    void givenEnum_thenReturnRandomValue() {
        Assertions.assertDoesNotThrow(() -> RandomEnumGenerator.randomEnum(PizzaOptions.class));
    }
}
