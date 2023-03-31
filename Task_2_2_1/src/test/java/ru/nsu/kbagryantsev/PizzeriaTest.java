package ru.nsu.kbagryantsev;

import java.time.Duration;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.workers.core.WorkerQualification;

class PizzeriaTest {
    @Test
    void givenPizzeria_then() {
        Pizzeria pizzeria = new Pizzeria(5, 5);
        pizzeria.addPizzaMaker(WorkerQualification.MIDDLE);
        pizzeria.addPizzaMaker(WorkerQualification.MIDDLE);
        pizzeria.addTransporter(2);
        pizzeria.addTransporter(2);

        Assertions.assertTimeout(Duration.ofMinutes(1), () -> {
            // Running pizzeria
            pizzeria.start();

            // Submiting orders
            RandomOrderGenerator randomOrderGenerator =
                    new RandomOrderGenerator(2, 1000, 2000);
            for (int i = 0; i < 5; i++) {
                Future<Order> orderFuture =
                        randomOrderGenerator.getRandomOrderWithDelay();
                Order order = orderFuture.get();
                pizzeria.addOrder(order);
            }

            // Shutting down pizzeria
            pizzeria.stop();
        });
    }
}