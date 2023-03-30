package ru.nsu.kbagryantsev;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.utils.Package;
import ru.nsu.kbagryantsev.utils.SynchronizedQueue;
import ru.nsu.kbagryantsev.workers.PizzaMaker;
import ru.nsu.kbagryantsev.workers.Transporter;
import ru.nsu.kbagryantsev.workers.core.WorkerQualification;

/**
 * Pizzeria class. Aggregates pizza makers and transporters. Processes and
 * delivers incoming orders using concurrency and shared data structures.
 */
public final class Pizzeria {
    /**
     * Incoming orders storage.
     */
    private final SynchronizedQueue<Package> orderStorage;
    /**
     * Completed orders storage.
     */
    private final SynchronizedQueue<Package> completedOrderStorage;
    /**
     * Associates pizza maker instance with its thread.
     */
    private final Map<PizzaMaker, Thread> pizzaMakers;
    /**
     * Associates transporter instance with its thread.
     */
    private final Map<Transporter, Thread> transporters;

    /**
     * Intialises a pizzeria.
     *
     * @param orderStorageCapacity          incoming orders storage capacity
     * @param completedOrderStorageCapacity completed orders storage capacity
     */
    public Pizzeria(final int orderStorageCapacity,
                    final int completedOrderStorageCapacity) {
        this.pizzaMakers =
                new HashMap<>();
        this.orderStorage =
                new SynchronizedQueue<>(orderStorageCapacity);
        this.completedOrderStorage =
                new SynchronizedQueue<>(completedOrderStorageCapacity);
        this.transporters =
                new HashMap<>();
    }

    /**
     * Adds a pizza maker. Initializes worker's thread.
     *
     * @param qualification pizza maker's qualification
     */
    public void addPizzaMaker(final WorkerQualification qualification) {
        PizzaMaker pizzaMaker = new PizzaMaker(
                orderStorage,
                completedOrderStorage,
                qualification);
        Thread thread = new Thread(pizzaMaker);
        pizzaMakers.put(pizzaMaker, thread);
    }

    /**
     * Adds a transporter. Intializes a worker's thread.
     *
     * @param capacity transporter capacity
     */
    public void addTransporter(final int capacity) {
        Transporter transporter = new Transporter(
                completedOrderStorage,
                capacity);
        Thread thread = new Thread(transporter);
        transporters.put(transporter, thread);
    }

    /**
     * Adds an unprocessed order.
     *
     * @param order unprocessed order
     */
    public void addOrder(final Order order) {
        orderStorage.add(new Package(order));
    }

    /**
     * Starts all workers. Starts wokers threads in arbitrary order.
     */
    public void start() {
        pizzaMakers.values().forEach(Thread::start);
        transporters.values().forEach(Thread::start);
    }

    /**
     * Stops all workers. Invokes workers stop method.
     */
    public void stop() throws InterruptedException {
        for (int i = 0; i < pizzaMakers.size(); i++) {
            orderStorage.add(Package.terminating());
        }
        for (Thread thread : pizzaMakers.values()) {
            thread.join();
        }
        for (int i = 0; i < transporters.size(); i++) {
            completedOrderStorage.add(Package.terminating());
        }
        for (Thread thread : transporters.values()) {
            thread.join();
        }
    }
}
