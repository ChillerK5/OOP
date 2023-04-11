package ru.nsu.kbagryantsev.utils;

import java.time.Duration;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Synchronized queue tests.
 */
public class SynchronizedQueueTest {
    @Test
    void givenFullQueue_whenCheckIsFull_thenReturnTrue() {
        SynchronizedQueue<Object> queue = new SynchronizedQueue<>(1);
        queue.add(new Object());
        Assertions.assertTrue(queue.isFull());
    }

    @Test
    void givenNotFullQueue_whenCkeckIsFull_thenReturnFalse() {
        SynchronizedQueue<Object> queue = new SynchronizedQueue<>(2);
        queue.add(new Object());
        Assertions.assertFalse(queue.isFull());
    }

    @Test
    void givenEmptyQueue_whenCheckIsEmpty_thenReturnTrue() {
        SynchronizedQueue<Object> queue = new SynchronizedQueue<>(2);
        Assertions.assertTrue(queue.isEmpty());
    }

    @Test
    void givenNotEmptyQueue_whenCheckIsEmpty_thenReturnFalse() {
        SynchronizedQueue<Object> queue = new SynchronizedQueue<>(2);
        queue.add(new Object());
        Assertions.assertFalse(queue.isEmpty());
    }

    @Test
    void givenFullQueue_whenAddingElement_thenExpectWait_untilElementRemoved()
            throws InterruptedException {
        SynchronizedQueue<Object> queue = new SynchronizedQueue<>(1);
        queue.add(new Object());
        Thread thread = new Thread(() -> queue.add(new Object()));
        thread.start();
        Thread.sleep(3000);
        Assertions.assertSame(Thread.State.WAITING, thread.getState());
        queue.remove();
        Thread.sleep(3000);
        Assertions.assertSame(Thread.State.TERMINATED, thread.getState());
    }

    @Test
    void givenEmptyQueue_whenRemovingElement_thenWait_untilElementAdded()
            throws InterruptedException {
        SynchronizedQueue<Object> queue = new SynchronizedQueue<>(1);
        Thread thread = new Thread(queue::remove);
        thread.start();
        Thread.sleep(3000);
        Assertions.assertSame(Thread.State.WAITING, thread.getState());
        queue.add(new Object());
        Thread.sleep(3000);
        Assertions.assertSame(Thread.State.TERMINATED, thread.getState());
    }

    @Test
    void givenEmptyQueue_whenNonBlockingRemove_thenGetAllElements() {
        SynchronizedQueue<Object> queue = new SynchronizedQueue<>(1);
        queue.add(new Object());
        Assertions.assertTrue(queue.nonBlockingRemove().isPresent());
        Assertions.assertTimeout(Duration.ofSeconds(3), () -> {
            Optional<Object> objectOptional = queue.nonBlockingRemove();
            Assertions.assertFalse(objectOptional.isPresent());
        });
    }
}
