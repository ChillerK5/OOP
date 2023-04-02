package ru.nsu.kbagryantsev.order.side;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Side tests.
 */
public class SideTest {
    @Test
    void givenExistingDrinkOption_thenReturnDrink() {
        Assertions.assertDoesNotThrow(() -> new Side(SideOptions.DODSTER));
        Side side = new Side(SideOptions.DODSTER);
        Assertions.assertSame(SideOptions.DODSTER, side.sideOption());
    }
}
