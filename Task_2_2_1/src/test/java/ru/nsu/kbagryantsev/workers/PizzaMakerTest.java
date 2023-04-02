package ru.nsu.kbagryantsev.workers;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.order.drink.Drink;
import ru.nsu.kbagryantsev.order.drink.DrinkOptions;
import ru.nsu.kbagryantsev.utils.Package;
import ru.nsu.kbagryantsev.utils.SynchronizedQueue;
import ru.nsu.kbagryantsev.workers.core.WorkerQualification;

/**
 * Pizza maker tests.
 */
public class PizzaMakerTest {
    @Test
    void givenOrder_whenProduce_thenCalculateProductionTimeAndCheckCompleted() {
        SynchronizedQueue<Package> sourceQueue = new SynchronizedQueue<>(5);
        SynchronizedQueue<Package> productQueue = new SynchronizedQueue<>(5);
        Order order = new Order(List.of(
                new Drink(DrinkOptions.COLA),
                new Drink(DrinkOptions.COLA)));
        PizzaMaker pizzaMaker =
                new PizzaMaker(sourceQueue, productQueue, WorkerQualification.MIDDLE);
        Assertions.assertDoesNotThrow(() -> pizzaMaker.produce(order));
        CompletedOrder completedOrder = pizzaMaker.produce(order);
        Assertions.assertEquals(order.content(), completedOrder.content());
    }

    @Test
    void givenMiddlePizzaMaker_whenGetQualification_thenExpectMiddle() {
        SynchronizedQueue<Package> sourceQueue = new SynchronizedQueue<>(5);
        SynchronizedQueue<Package> productQueue = new SynchronizedQueue<>(5);
        PizzaMaker pizzaMaker =
                new PizzaMaker(sourceQueue, productQueue, WorkerQualification.MIDDLE);
        Assertions.assertEquals(WorkerQualification.MIDDLE, pizzaMaker.getQualification());
    }

    @Test
    void givenPizzaMaker_thenStartWork() throws InterruptedException {
        SynchronizedQueue<Package> sourceQueue = new SynchronizedQueue<>(5);
        SynchronizedQueue<Package> productQueue = new SynchronizedQueue<>(5);
        PizzaMaker pizzaMaker =
                new PizzaMaker(sourceQueue, productQueue, WorkerQualification.MIDDLE);
        Thread thread = new Thread(pizzaMaker);
        thread.start();
        for (int i = 0; i < 10; i++) {
            Order order = new Order(List.of(new Drink(DrinkOptions.COLA)));
            sourceQueue.add(new Package(order));
            Thread.sleep(2000);
            Optional<Package> optionalPackage;
            optionalPackage = productQueue.nonBlockingRemove();
            Assertions.assertTrue(optionalPackage.isPresent());
        }
        sourceQueue.add(Package.terminating());
        Thread.sleep(2000);
        Assertions.assertSame(Thread.State.TERMINATED, thread.getState());
    }
}
