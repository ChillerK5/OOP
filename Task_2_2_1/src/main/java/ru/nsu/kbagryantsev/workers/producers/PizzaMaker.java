package ru.nsu.kbagryantsev.workers.producers;

public class PizzaMaker implements Producer {
    /**
     * Worker's producing qualification.
     */
    private int qualification;

    /**
     * Initialises a pizza maker by his/her qualification.
     *
     * @param qualification int value in some set range
     */
    public PizzaMaker(final int qualification) {
        this.qualification = qualification;
    }

    @Override
    public void produce() {
    }
}
