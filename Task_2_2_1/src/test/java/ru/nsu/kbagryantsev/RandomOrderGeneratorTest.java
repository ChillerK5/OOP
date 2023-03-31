package ru.nsu.kbagryantsev;

import java.util.concurrent.Future;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.order.Order;

public class RandomOrderGeneratorTest {
    @Test
    void givenAverageParameters_whenQuierOrderDelay_thenSuccess() throws InterruptedException {
        RandomOrderGenerator orderGenerator = new RandomOrderGenerator(
                100,
                1000,
                2000);
        Future<Order> orderFuture = orderGenerator.getRandomOrderWithDelay();
        Assertions.assertFalse(orderFuture.isDone());
        Thread.sleep(3000);
        Assertions.assertTrue(orderFuture.isDone());
    }
}
