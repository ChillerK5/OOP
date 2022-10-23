package ru.nsu.kbagryantsev;

public class Edge<V,E> {
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

    public Edge(Vertex<V> start, Vertex<V> end, E weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public void setEdge(E weight) {
        this.weight = weight;
    }

    public E getEdge() {
        return weight;
    }

    public Vertex<V> getStart() {
        return start;
    }

    public Vertex<V> getEnd() {
        return end;
    }
}
