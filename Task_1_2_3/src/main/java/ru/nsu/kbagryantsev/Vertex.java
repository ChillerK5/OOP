package ru.nsu.kbagryantsev;

public class Vertex<V>{
    /**
     * Data stored in a vertex.
     */
    private V data;

    public Vertex(V data) {
        this.data = data;
    }

    public void setVertex(V data) {
        this.data = data;
    }

    public V getVertex() {
        return data;
    }
}
