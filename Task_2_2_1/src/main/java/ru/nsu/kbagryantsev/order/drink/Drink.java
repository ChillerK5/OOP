package ru.nsu.kbagryantsev.order.drink;

import ru.nsu.kbagryantsev.order.MenuItem;

/**
 * Drink instance.
 *
 * @param drinkOption drink option
 */
public record Drink(DrinkOptions drinkOption) implements MenuItem {
}
