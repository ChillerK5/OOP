package ru.nsu.kbagryantsev;

public interface Graph<V,E> {
    Vertex<V> addVertex(V data);

    Graph<V,E> addVertex(Vertex<V> v);

    void cutVertex(Vertex<V> v);

    Edge<V,E> addEdge (Vertex<V> u, Vertex<V> v, E weight);

    void cutEdge (Edge<V,E> e);

    void cutEdge (Vertex<V> u, Vertex<V> v);
}
