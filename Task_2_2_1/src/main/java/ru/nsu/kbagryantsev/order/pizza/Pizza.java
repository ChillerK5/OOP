package ru.nsu.kbagryantsev.order.pizza;

import ru.nsu.kbagryantsev.order.MenuItem;

/**
 * Pizza instance.
 *
 * @param pizzaType pizza type
 * @param pizzaSize pizza size
 * @param pizzaCrust pizza crust
 */
public record Pizza(PizzaOptions pizzaType,
                    PizzaSize pizzaSize,
                    PizzaCrust pizzaCrust) implements MenuItem {
}
