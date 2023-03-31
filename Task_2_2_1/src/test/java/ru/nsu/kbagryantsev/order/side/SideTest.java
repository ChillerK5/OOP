package ru.nsu.kbagryantsev.order.side;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SideTest {
    @Test
    void givenExistingDrinkOption_thenReturnDrink() {
        Assertions.assertDoesNotThrow(() -> new Side(SideOptions.DODSTER));
    }
}
