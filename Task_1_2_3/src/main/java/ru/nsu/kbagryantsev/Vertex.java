package ru.nsu.kbagryantsev;

/**
 * Vertex of a directed weighted graph.
 *
 * @param <V> vertex data
 */
public class Vertex<V>{
    /**
     * Data stored in a vertex.
     */
    private V data;

    /**
     * Creates a vertex by its data.
     *
     * @param data vertex data
     */
    public Vertex(V data) {
        this.data = data;
    }

    /**
     * Puts a new value into a vertex.
     *
     * @param data new vertex value
     */
    public void setVertex(V data) {
        this.data = data;
    }

    /**
     * Gets the data stored in a vertex.
     *
     * @return vertex data
     */
    public V getVertex() {
        return data;
    }
}
