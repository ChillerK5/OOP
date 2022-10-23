package ru.nsu.kbagryantsev;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AdjacencyMatrixGraph<V,E> implements Graph<V,E>{
    Map<Vertex<V>, Map<Vertex<V>, Edge<V,E>>> vertices;

    public AdjacencyMatrixGraph() {
        vertices = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> v = new Vertex<>(data);
        vertices.put(v, new HashMap<>());
        return v;
    }

    @Override
    public Graph<V, E> addVertex(Vertex<V> v) {
        vertices.put(v, new HashMap<>());
        return this;
    }

    @Override
    public void cutVertex(Vertex<V> v) {
        vertices.remove(v);
    }

    @Override
    public Edge<V, E> addEdge(Vertex<V> u, Vertex<V> v, E weight) {
        Edge<V,E> e = new Edge<>(u, v, weight);
        vertices.get(u).put(v, e);
        return e;
    }

    @Override
    public void cutEdge(@NotNull Edge<V, E> e) {
        vertices.get(e.getStart()).remove(e.getEnd());
    }

    @Override
    public void cutEdge(Vertex<V> u, Vertex<V> v) {
        vertices.get(u).remove(v);
    }
}