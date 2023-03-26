package ru.nsu.kbagryantsev;

import java.util.HashMap;
import java.util.Map;

import ru.nsu.kbagryantsev.utils.ProductionQueue;
import ru.nsu.kbagryantsev.workers.WorkerQualification;
import ru.nsu.kbagryantsev.order.CompletedOrder;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.workers.producers.PizzaMaker;
import ru.nsu.kbagryantsev.workers.transporters.Transporter;

public final class Pizzeria {
    /**
     * Queue of customers orders.
     */
    private final ProductionQueue<Order> orderStorage;
    private final ProductionQueue<CompletedOrder> completedOrderStorage;
    private final Map<PizzaMaker, Thread> pizzaMakers;
    private final Map<Transporter, Thread> transporters;

    public Pizzeria(final int orderStorageCapacity,
                    final int completedOrderStorageCapacity) {
        this.pizzaMakers = new HashMap<>();
        this.orderStorage = new ProductionQueue<>(orderStorageCapacity);
        this.completedOrderStorage = new ProductionQueue<>(completedOrderStorageCapacity);
        this.transporters = new HashMap<>();
    }

    public void addPizzaMaker(WorkerQualification qualification) {
        PizzaMaker pizzaMaker = new PizzaMaker(
                orderStorage,
                completedOrderStorage,
                qualification);
        Thread thread = new Thread(pizzaMaker);
        pizzaMakers.put(pizzaMaker, thread);
    }

    public void addTransporter(int capacity) {
        Transporter transporter = new Transporter(
                completedOrderStorage,
                capacity);
        Thread thread = new Thread(transporter);
        transporters.put(transporter, thread);
    }

    public void addOrder(Order order) {
        orderStorage.add(order);
    }

    public void start() {
        pizzaMakers.values().forEach(Thread::start);
        transporters.values().forEach(Thread::start);
    }

    public void stop() {
        //TODO wait for all workers termination
        pizzaMakers.keySet().forEach(PizzaMaker::stop);
        transporters.keySet().forEach(Transporter::stop);
    }
}
