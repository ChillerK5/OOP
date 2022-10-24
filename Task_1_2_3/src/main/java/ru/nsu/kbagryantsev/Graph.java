package ru.nsu.kbagryantsev;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Graph<V,E extends Number> implements Graphable<V,E>{
    Map<Vertex<V>, Map<Vertex<V>, Edge<V,E>>> matrix;


    public Graph() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> v = new Vertex<>(data);
        matrix.put(v, new HashMap<>());
        return v;
    }

    @Override
    public Graphable<V, E> addVertex(Vertex<V> v) {
        matrix.put(v, new HashMap<>());
        return this;
    }

    @Override
    public void cutVertex(Vertex<V> v) {
        matrix.remove(v);
        for (Vertex<V> u : matrix.keySet()) {
            matrix.get(u).remove(v);
        }
    }

    @Override
    public Edge<V, E> addEdge(Vertex<V> u, Vertex<V> v, E weight) {
        Edge<V,E> e = new Edge<>(u, v, weight);
        matrix.get(u).put(v, e);
        return e;
    }

    @Override
    public void cutEdge(@NotNull Edge<V, E> e) {
        matrix.get(e.getStart()).remove(e.getEnd());
    }

    @Override
    public void cutEdge(Vertex<V> u, Vertex<V> v) {
        matrix.get(u).remove(v);
    }


}