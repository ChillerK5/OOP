package ru.nsu.kbagryantsev.workers.transporters;

import java.util.Collection;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.utils.ProductionQueue;
import ru.nsu.kbagryantsev.workers.Consumer;
import ru.nsu.kbagryantsev.workers.producers.PizzaMaker;

@Consumer
public final class Transporter implements Runnable {
    private final Logger logger = LogManager.getLogger(PizzaMaker.class);
    private final ProductionQueue<CompletedOrder> sourceQueue;
    private final int capacity;
    private boolean isActive = false;

    /**
     * Initialises a courier by his/her parameters.
     *
     * @param capacity maximal amount of orders a courier can transport
     */
    public Transporter(final ProductionQueue<CompletedOrder> sourceQueue,
                       final int capacity) {
        this.sourceQueue = sourceQueue;
        this.capacity = capacity;
    }

    public Collection<CompletedOrder> consume() {
        Collection<CompletedOrder> completedOrders = sourceQueue.removeSome(capacity);
        completedOrders.forEach(this::logReceived);
        return completedOrders;
    }

    public void stop() {
        isActive = false;
        sourceQueue.notifyAllOnFull();
    }

    public int getDeliveryTime() {
        final int maxDeliveryTime = 4000;
        final int minDeliveryTime = 1000;
        return new Random().nextInt(minDeliveryTime, maxDeliveryTime);
    }

    public void deliver(CompletedOrder completedOrder) {
        try {
            // PRODUCTION PROCESS CODE START
            Thread.sleep(getDeliveryTime());
            // PRODUCTION PROCESS CODE END
        } catch (InterruptedException e) {
            stop();
        }
        logDelivered(completedOrder);
    }

    public void deliverAll(Collection<CompletedOrder> completedOrders) {
        completedOrders.forEach(this::deliver);
    }

    private void logDelivered(CompletedOrder completedOrder) {
        logger.info("%-30s\tdelivered\t%s".formatted(this, completedOrder));
    }

    private void logReceived(CompletedOrder completedOrder) {
        logger.info("%-30s\treceived\t%s".formatted(this, completedOrder));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + Integer.toOctalString(hashCode());
    }

    @Override
    public void run() {
        isActive = true;
        try {
            while (isActive) {
                Collection<CompletedOrder> completedOrders = consume();
                deliverAll(completedOrders);
            }
        } finally {
            sourceQueue.notifyAllOnFull();
        }
    }
}
