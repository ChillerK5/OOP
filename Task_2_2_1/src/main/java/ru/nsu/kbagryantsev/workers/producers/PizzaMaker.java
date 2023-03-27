package ru.nsu.kbagryantsev.workers.producers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.utils.ProductionQueue;
import ru.nsu.kbagryantsev.workers.Consumer;
import ru.nsu.kbagryantsev.workers.Producer;
import ru.nsu.kbagryantsev.workers.WorkerQualification;

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
    private final ProductionQueue<CompletedOrder> productQueue;
    /**
     * Incoming orders storage.
     */
    private final ProductionQueue<Order> sourceQueue;
    /**
     * Run flag.
     */
    private Boolean isActive = false;

    /**
     * Initializes a pizza maker.
     *
     * @param sourceQueue   incoming orders storage
     * @param productQueue  completed orders storage
     * @param qualification pizza maker qualification
     */
    public PizzaMaker(final ProductionQueue<Order> sourceQueue,
                      final ProductionQueue<CompletedOrder> productQueue,
                      final WorkerQualification qualification) {
        this.sourceQueue = sourceQueue;
        this.productQueue = productQueue;
        this.qualification = qualification;
    }

    @Override
    public void run() {
        isActive = true;
        try {
            while (isActive) {
                Order incomingOrder = consume();
                CompletedOrder completedOrder = produce(incomingOrder);
                productQueue.add(completedOrder);
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
            stop();
        }
        CompletedOrder completedOrder = new CompletedOrder(order.content());
        logCompleted(order, completedOrder);
        return completedOrder;
    }

    /**
     * Gets an order from incoming order pool.
     *
     * @return incoming order
     */
    public Order consume() {
        Order order = sourceQueue.remove();
        logReceived(order);
        return order;
    }

    /**
     * Considered to deprecated in the future. Calculates estimated
     * single order production time bound.
     *
     * @param order order
     * @return estimated time
     */
    private int getProductionTime(final @NotNull Order order) {
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
     * @param order incoming order
     * @param completedOrder completed order
     */
    private void logCompleted(final Order order,
                              final CompletedOrder completedOrder) {
        logger.info("%-25s\tcompleted\t%s -> %s"
                .formatted(this, order, completedOrder));
    }

    /**
     * Terminates pizza maker. All current operations will be executed.
     */
    public void stop() {
        isActive = false;
        sourceQueue.notifyAllOnFull();
        productQueue.notifyAllOnEmpty();
    }

    /**
     * Qualification getter.
     *
     * @return worker qualification
     */
    @SuppressWarnings("unused")
    private WorkerQualification getQualification() {
        return qualification;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + Integer.toHexString(hashCode());
    }
}
