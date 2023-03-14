package ru.nsu.kbagryantsev;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import ru.nsu.kbagryantsev.enums.Pizza;

public class Pizzeria {
    /**
     * Queue of customers orders.
     */
    private final Queue<Order> orders;
    /**
     * Queue maintaining product's transition from pizza makers to transporters.
     */
    private final Queue<Pizza> endProductStorage;

    /**
     * Initialises a pizzeria by its ordering capacity and storage capacity.
     * The definition responds to company's manufacturing parameters.
     * @param orderingCapacity maximal amount of orders a pizzeria can fulfill
     * @param storageCapacity maximal amount of orders a storage can maintain
     *                       before they expire
     */
    public Pizzeria(final int orderingCapacity, final int storageCapacity) {
        this.orders = new ArrayBlockingQueue<>(orderingCapacity);
        this.endProductStorage = new ArrayBlockingQueue<>(storageCapacity);
    }
}
