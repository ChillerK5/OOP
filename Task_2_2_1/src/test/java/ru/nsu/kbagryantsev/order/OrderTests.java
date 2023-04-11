package ru.nsu.kbagryantsev.order;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.order.drink.Drink;
import ru.nsu.kbagryantsev.order.drink.DrinkOptions;

/**
 * Order tests.
 */
public class OrderTests {
    @Nested
    class OrderTest {
        @Test
        void givenArbitraryOrderContent_thenReturnOrder() {
            Collection<MenuItem> menuItemCollection = List.of(new Drink(DrinkOptions.COLA));
            Assertions.assertDoesNotThrow(() -> new Order(menuItemCollection));
        }

        @Test
        void givenArbitraryOrder_thenInvokeToString() {
            Collection<MenuItem> menuItemCollection = List.of(new Drink(DrinkOptions.COLA));
            Order order = new Order(menuItemCollection);
            Assertions.assertDoesNotThrow(order::toString);
        }
    }

    @Nested
    class CompletedOrderTest {
        @Test
        void givenArbitraryCompletedOrderContent_thenReturnCompletedOrder() {
            Collection<MenuItem> menuItemCollection = List.of(new Drink(DrinkOptions.COLA));
            Assertions.assertDoesNotThrow(() -> new Order(menuItemCollection));
        }

        @Test
        void givenArbitraryCompletedOrder_thenInvokeToString() {
            Collection<MenuItem> menuItemCollection = List.of(new Drink(DrinkOptions.COLA));
            CompletedOrder order = new CompletedOrder(menuItemCollection);
            Assertions.assertDoesNotThrow(order::toString);
        }
    }
}
