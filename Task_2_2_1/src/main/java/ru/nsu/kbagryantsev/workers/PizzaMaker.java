package ru.nsu.kbagryantsev.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.utils.Package;
import ru.nsu.kbagryantsev.utils.SynchronizedQueue;
import ru.nsu.kbagryantsev.workers.core.Consumer;
import ru.nsu.kbagryantsev.workers.core.Producer;
import ru.nsu.kbagryantsev.workers.core.WorkerQualification;

/**
 * Pizza maker. Produces completed orders from incoming orders.
 */
@Producer
@Consumer
public final class PizzaMaker implements Runnable {
    /**
     * See {@link Logger}.
     */
    private final Logger logger = LogManager.getLogger(PizzaMaker.class);
    /**
     * Pizza maker qualification.
     */
    private final WorkerQualification qualification;
    /**
     * Completed orders storage.
     */
    private final SynchronizedQueue<Package> productQueue;
    /**
     * Incoming orders storage.
     */
    private final SynchronizedQueue<Package> sourceQueue;

    /**
     * Initializes a pizza maker.
     *
     * @param sourceQueue   incoming orders storage
     * @param productQueue  completed orders storage
     * @param qualification pizza maker qualification
     */
    public PizzaMaker(final SynchronizedQueue<Package> sourceQueue,
                      final SynchronizedQueue<Package> productQueue,
                      final WorkerQualification qualification) {
        this.sourceQueue = sourceQueue;
        this.productQueue = productQueue;
        this.qualification = qualification;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Package incomingPackage = sourceQueue.remove();
                if (incomingPackage.isTerminating()) {
                    break;
                }
                Order incomingOrder = (Order) incomingPackage.data();
                logReceived(incomingOrder);
                CompletedOrder completedOrder = produce(incomingOrder);
                Package outcomingPackage = new Package(completedOrder);
                productQueue.add(outcomingPackage);
            }
        } finally {
            sourceQueue.notifyAllOnFull();
            productQueue.notifyAllOnEmpty();
        }
    }

    /**
     * Produces an order. Default implementation implies that an order is
     * produced in a specified time bound.
     *
     * @param order incoming order
     * @return completed order
     */
    public CompletedOrder produce(final Order order) {
        try {
            // PRODUCTION PROCESS CODE START
            Thread.sleep(getProductionTime(order));
            // PRODUCTION PROCESS CODE END
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        CompletedOrder completedOrder = new CompletedOrder(order.content());
        logCompleted(order, completedOrder);
        return completedOrder;
    }

    /**
     * Considered to deprecated in the future. Calculates estimated
     * single order production time bound.
     *
     * @param order order
     * @return estimated time
     */
    private int getProductionTime(final @NonNull Order order) {
        final int averagePizzaProductionTime = 1000;
        return averagePizzaProductionTime * order.content().size();
    }

    /**
     * Logs incoming order.
     *
     * @param order incoming order
     */
    private void logReceived(final Order order) {
        logger.info("%-25s\tstarted\t%s".formatted(this, order));
    }

    /**
     * Logs completed order.
     *
     * @param order          incoming order
     * @param completedOrder completed order
     */
    private void logCompleted(final Order order,
                              final CompletedOrder completedOrder) {
        logger.info("%-25s\tcompleted\t%s -> %s"
                .formatted(this, order, completedOrder));
    }

    /**
     * Qualification getter.
     *
     * @return worker qualification
     */
    @SuppressWarnings("unused")
    public WorkerQualification getQualification() {
        return qualification;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + Integer.toHexString(hashCode());
    }
}
