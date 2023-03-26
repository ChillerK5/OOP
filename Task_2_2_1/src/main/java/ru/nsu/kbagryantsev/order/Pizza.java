package ru.nsu.kbagryantsev.order;

/**
 * Pizza instance. Customizable with options.
 */
public record Pizza(PizzaType pizzaType,
                    PizzaSize pizzaSize,
                    PizzaCrust pizzaCrust) implements MenuItem {
}
