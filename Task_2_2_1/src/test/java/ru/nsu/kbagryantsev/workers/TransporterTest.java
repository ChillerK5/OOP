package ru.nsu.kbagryantsev.workers;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.order.drink.Drink;
import ru.nsu.kbagryantsev.order.drink.DrinkOptions;
import ru.nsu.kbagryantsev.utils.Package;
import ru.nsu.kbagryantsev.utils.SynchronizedQueue;

/**
 * Transporter tests.
 */
public class TransporterTest {
    @Test
    void givenOrders_whenDeliverAll_thenSuccess() {
        SynchronizedQueue<Package> sourceQueue = new SynchronizedQueue<>(5);
        Transporter transporter = new Transporter(sourceQueue, 3);
        CompletedOrder completedOrder =
                new CompletedOrder(List.of(new Drink(DrinkOptions.COLA)));
        List<CompletedOrder> completedOrders = List.of(completedOrder);
        Assertions.assertDoesNotThrow(() -> transporter.deliver(completedOrder));
        Assertions.assertDoesNotThrow(() -> transporter.deliverAll(completedOrders));
    }

    @Test
    void givenTransporter_whenStart_thenSuccess_withConcurrentTermination() {
        SynchronizedQueue<Package> sourceQueue = new SynchronizedQueue<>(2);
        Transporter transporterA = new Transporter(sourceQueue, 2);
        Transporter transporterB = new Transporter(sourceQueue, 2);
        Thread threadA = new Thread(transporterA);
        Thread threadB = new Thread(transporterB);
        threadA.start();
        threadB.start();
        Assertions.assertTimeout(Duration.ofSeconds(30), () -> {
            for (int i = 0; i < 5; i++) {
                CompletedOrder completedOrder =
                        new CompletedOrder(List.of(new Drink(DrinkOptions.COLA)));
                sourceQueue.add(new Package(completedOrder));
            }
            sourceQueue.add(Package.terminating());
            sourceQueue.add(Package.terminating());
            Thread.sleep(10000);
            Assertions.assertSame(Thread.State.TERMINATED, threadA.getState());
            Assertions.assertSame(Thread.State.TERMINATED, threadB.getState());
        });
    }
}
