package ru.nsu.kbagryantsev;

/**
 * Edge of a weighted directed graph.
 *
 * @param <V> vertex data
 * @param <E> edge data
 */
public class Edge<V,E extends Number> {
    /**
     * Initial vertex of an edge.
     */
    private final Vertex<V> start;
    /**
     * Terminal vertex of an edge.
     */
    private final Vertex<V> end;
    /**
     * Weight of an edge or some other data.
     */
    private E weight;

    /**
     * Creates and edge by its adjacent vertices and a given weight.
     *
     * @param start initial vertex
     * @param end terminal vertex
     * @param weight data stored on an edge
     */
    public Edge(Vertex<V> start, Vertex<V> end, E weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    /**
     * Puts a new weight value onto an edge.
     *
     * @param weight new weight
     */
    public void setWeight(E weight) {
        this.weight = weight;
    }

    /**
     * Gets the weight stored on an edge.
     *
     * @return edge's weight
     */
    public E getWeight() {
        return weight;
    }

    /**
     * Gets initial vertex of an oriented edge.
     *
     * @return initial vertex
     */
    public Vertex<V> getStart() {
        return start;
    }

    /**
     * Gets terminal vertex of an oriented edge.
     *
     * @return terminal vertex
     */
    public Vertex<V> getEnd() {
        return end;
    }
}
