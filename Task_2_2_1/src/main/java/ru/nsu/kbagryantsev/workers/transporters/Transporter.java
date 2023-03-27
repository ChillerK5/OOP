package ru.nsu.kbagryantsev.workers.transporters;

import java.util.Collection;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.utils.ProductionQueue;
import ru.nsu.kbagryantsev.workers.Consumer;
import ru.nsu.kbagryantsev.workers.producers.PizzaMaker;

/**
 * Entity transporting completed orders.
 */
@Consumer
public final class Transporter implements Runnable {
    /**
     * See {@link Logger}.
     */
    private final Logger logger = LogManager.getLogger(PizzaMaker.class);
    /**
     * Shared order storage.
     */
    private final ProductionQueue<CompletedOrder> sourceQueue;
    /**
     * Maximal amount of order a transporter can proceed.
     */
    private final int capacity;
    /**
     * Run flag.
     */
    private boolean isActive = false;

    /**
     * Initialises a courier by his/her parameters.
     *
     * @param sourceQueue shared data structure
     * @param capacity    maximal amount of orders a courier can transport
     */
    public Transporter(final ProductionQueue<CompletedOrder> sourceQueue,
                       final int capacity) {
        this.sourceQueue = sourceQueue;
        this.capacity = capacity;
    }

    /**
     * Gets as many orders as possible within available capacity.
     *
     * @return collection of orders in work
     */
    public Collection<CompletedOrder> consume() {
        Collection<CompletedOrder> completedOrders =
            sourceQueue.removeSome(capacity);
        completedOrders.forEach(this::logReceived);
        return completedOrders;
    }

    /**
     * Terminates a transporter.
     */
    public void stop() {
        isActive = false;
        sourceQueue.notifyAllOnFull();
    }

    /**
     * Considered to be deprecated in the future. Calculates estimated
     * single delivery time bound.
     *
     * @return integer delivery time
     */
    public int getDeliveryTime() {
        final int maxDeliveryTime = 4000;
        final int minDeliveryTime = 1000;
        return new Random().nextInt(minDeliveryTime, maxDeliveryTime);
    }

    /**
     * Delivers an order. Default implementation implies that an order is
     * delivered in a specified time bound.
     *
     * @param completedOrder order to be delivered
     */
    public void deliver(final CompletedOrder completedOrder) {
        try {
            // PRODUCTION PROCESS CODE START
            Thread.sleep(getDeliveryTime());
            // PRODUCTION PROCESS CODE END
        } catch (InterruptedException e) {
            stop();
        }
        logDelivered(completedOrder);
    }

    /**
     * Delivers all orders in given collection.
     *
     * @param completedOrders collection of orders in work
     */
    public void deliverAll(final Collection<CompletedOrder> completedOrders) {
        completedOrders.forEach(this::deliver);
    }

    private void logDelivered(final CompletedOrder completedOrder) {
        logger.info("%-30s\tdelivered\t%s".formatted(this, completedOrder));
    }

    private void logReceived(final CompletedOrder completedOrder) {
        logger.info("%-30s\treceived\t%s".formatted(this, completedOrder));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "@"
            + Integer.toHexString(hashCode());
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
