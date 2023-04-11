package ru.nsu.kbagryantsev.order.pizza;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Pizza tests.
 */
public class PizzaTest {
    @Test
    void givenExistingPizzaOption_thenReturnPizza() {
        Assertions.assertDoesNotThrow(() ->
                new Pizza(
                        PizzaOptions.DODO_SUPREME,
                        PizzaSize.MEDIUM,
                        PizzaCrust.STUFFED));
        Pizza pizza = new Pizza(
                PizzaOptions.DODO_SUPREME,
                PizzaSize.MEDIUM,
                PizzaCrust.STUFFED);
        Assertions.assertSame(PizzaOptions.DODO_SUPREME, pizza.pizzaType());
        Assertions.assertSame(PizzaSize.MEDIUM, pizza.pizzaSize());
        Assertions.assertSame(PizzaCrust.STUFFED, pizza.pizzaCrust());
    }
}
