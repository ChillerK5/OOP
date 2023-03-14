package ru.nsu.kbagryantsev.workers.producers;

/**
 * Describes a worker which is able to manufacture goods.
 */
public interface Producer {
    /**
     * Produces a single product.
     */
    void produce();
}
