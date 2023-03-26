package ru.nsu.kbagryantsev.workers.producers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.utils.ProductionQueue;
import ru.nsu.kbagryantsev.workers.WorkerQualification;
import ru.nsu.kbagryantsev.workers.Consumer;
import ru.nsu.kbagryantsev.workers.Producer;

@Producer
@Consumer
public final class PizzaMaker implements Runnable {
    private final Logger logger = LogManager.getLogger(PizzaMaker.class);
    private final WorkerQualification qualification;
    private final ProductionQueue<CompletedOrder> productQueue;
    private final ProductionQueue<Order> sourceQueue;
    private Boolean isActive = false;

    /**
     * Initializes a pizza maker.
     *
     * @param sourceQueue   incoming orders queue
     * @param productQueue  completed orders storage queue
     * @param qualification int value in some set range
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

    public Order consume() {
        Order order = sourceQueue.remove();
        logReceived(order);
        return order;
    }

    private int getProductionTime(final @NotNull Order order) {
        final int averagePizzaProductionTime = 2000;
        return averagePizzaProductionTime * order.content().size();
    }

    private void logReceived(Order order) {
        logger.info("%-25s\tstarted\t%s".formatted(this, order));
    }

    private void logCompleted(Order order, CompletedOrder completedOrder) {
        logger.info("%-25s\tcompleted\t%s -> %s"
                .formatted(this, order, completedOrder));
    }

    public void stop() {
        isActive = false;
        sourceQueue.notifyAllOnFull();
        productQueue.notifyAllOnEmpty();
    }

    @SuppressWarnings("unused")
    private WorkerQualification getQualification() {
        return qualification;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + Integer.toOctalString(hashCode());
    }
}
