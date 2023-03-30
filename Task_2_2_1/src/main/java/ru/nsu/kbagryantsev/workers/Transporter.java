package ru.nsu.kbagryantsev.workers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.utils.Package;
import ru.nsu.kbagryantsev.utils.SynchronizedQueue;
import ru.nsu.kbagryantsev.workers.core.Consumer;

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
    private final SynchronizedQueue<Package> sourceQueue;
    /**
     * Maximal amount of order a transporter can proceed.
     */
    private final int capacity;

    /**
     * Initialises a courier by his/her parameters.
     *
     * @param sourceQueue shared data structure
     * @param capacity    maximal amount of orders a courier can transport
     */
    public Transporter(final SynchronizedQueue<Package> sourceQueue,
                       final int capacity) {
        this.sourceQueue = sourceQueue;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Package guaranteedPackage = sourceQueue.remove();
                if (guaranteedPackage.isTerminating()) {
                    break;
                }
                Collection<CompletedOrder> orders = new ArrayList<>();
                orders.add((CompletedOrder) guaranteedPackage.data());
                Optional<Package> packageOptional =
                        sourceQueue.nonBlockingRemove();
                while (packageOptional.isPresent()
                        && orders.size() < capacity) {
                    Package trailingPackage = packageOptional.get();
                    if (trailingPackage.isTerminating()) {
                        sourceQueue.add(trailingPackage);
                        break;
                    }
                    orders.add((CompletedOrder) trailingPackage.data());
                }
                orders.forEach(this::logReceived);
                deliverAll(orders);
            }
        } finally {
            sourceQueue.notifyAllOnFull();
        }
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
            throw new RuntimeException();
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
}
