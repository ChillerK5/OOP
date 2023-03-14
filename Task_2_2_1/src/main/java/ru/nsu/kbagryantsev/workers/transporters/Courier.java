package ru.nsu.kbagryantsev.workers.transporters;

public class Courier implements Transporter {
    /**
     * Courier average delivery speed.
     */
    private int speed;
    /**
     * Amount of orders which may be carried by a courier.
     */
    private int capacity;

    /**
     * Initialises a courier by his/her parameters.
     *
     * @param speed moving speed in conventional units
     * @param capacity maximal amount of orders a courier can transport
     */
    public Courier(final int speed, final int capacity) {
        this.speed = speed;
        this.capacity = capacity;
    }

    @Override
    public void transport() {

    }
}
