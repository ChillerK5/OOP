package ru.nsu.kbagryantsev;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * Implements Graphable interface. Weighted oriented graph class.
 *
 * @param <V> vertex data
 * @param <E> edge data
 */
public final class Graph<V, E extends Number> implements Graphable<V, E> {
    /**
     * Map of graph relations. Maps vertices to a hashmap,
     * which links termination vertices and edges
     * leading to them from an initial vertex.
     */
    protected Map<Vertex<V>, Map<Vertex<V>, Edge<V, E>>> matrix;

    /**
     * Initialises a graph.
     */
    public Graph() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(final V data) {
        Vertex<V> v = new Vertex<>(data);
        matrix.put(v, new HashMap<>());
        return v;
    }

    @Override
    public Graphable<V, E> addVertex(final Vertex<V> v) {
        matrix.put(v, new HashMap<>());
        return this;
    }

    @Override
    public void cutVertex(final Vertex<V> v) {
        matrix.remove(v);
        for (Vertex<V> u : matrix.keySet()) {
            matrix.get(u).remove(v);
        }
    }

    @Override
    public Set<Vertex<V>> getVertices() {
        return matrix.keySet();
    }

    @Override
    public Edge<V, E> addEdge(final Vertex<V> u,
                              final Vertex<V> v,
                              final E weight) {
        Edge<V, E> e = new Edge<>(u, v, weight);
        matrix.get(u).put(v, e);
        return e;
    }

    @Override
    public void cutEdge(@NotNull final Edge<V, E> e) {
        matrix.get(e.getStart()).remove(e.getEnd());
    }

    @Override
    public void cutEdge(final Vertex<V> u, final Vertex<V> v) {
        matrix.get(u).remove(v);
    }
}
