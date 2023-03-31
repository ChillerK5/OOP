package ru.nsu.kbagryantsev.order.pizza;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PizzaTest {
    @Test
    void givenExistingPizzaOption_thenReturnPizza() {
        Assertions.assertDoesNotThrow(() ->
                new Pizza(
                        PizzaOptions.DODO_SUPREME,
                        PizzaSize.MEDIUM,
                        PizzaCrust.STUFFED));
    }
}
