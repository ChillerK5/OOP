package ru.nsu.kbagryantsev.order.drink;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Drink tests.
 */
public class DrinkTest {
    @Test
    void givenExistingDrinkOption_thenReturnDrink() {
        Assertions.assertDoesNotThrow(() -> new Drink(DrinkOptions.COLA));
        Drink drink = new Drink(DrinkOptions.COLA);
        Assertions.assertSame(DrinkOptions.COLA, drink.drinkOption());
    }
}
