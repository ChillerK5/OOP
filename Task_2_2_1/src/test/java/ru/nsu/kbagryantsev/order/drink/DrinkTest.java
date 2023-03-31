package ru.nsu.kbagryantsev.order.drink;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static ru.nsu.kbagryantsev.order.drink.DrinkOptions.COLA;

public class DrinkTest {
    @Test
    void givenExistingDrinkOption_thenReturnDrink() {
        Assertions.assertDoesNotThrow(() -> new Drink(COLA));
    }
}
