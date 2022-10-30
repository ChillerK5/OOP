package ru.nsu.kbagryantsev;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class GraphUtils {
    /**
     * Finds all shortest paths from a single-source vertex.
     *
     * @param graph graph to be operated
     * @param init source vertex
     * @return map from vertices onto distances from the source
     * @param <V> vertex data
     * @param <E> edge data
     */
    public static <V, E extends Number>
    Map<Vertex<V>, Double> dijkstra(Graph<V, E> graph, final Vertex<V> init) {
        //Initialising graph's shortest paths collection and its comparator
        Map<Vertex<V>, Double> shortest = new HashMap<>();
        Comparator<Vertex<V>> comparator = Comparator.comparing(shortest::get);

        //Initialising priority queue
        PriorityQueue<Vertex<V>> heap = new PriorityQueue<>(comparator);

        //Initialising single source shortest
        Double infinity = Double.POSITIVE_INFINITY;
        graph.getVertices().forEach(x -> shortest.put(x, infinity));
        shortest.replace(init, 0d);
        heap.addAll(graph.getVertices());

        //Continuous relaxation of edges
        while (!heap.isEmpty()) {
            Vertex<V> u = heap.remove();
            Map<Vertex<V>, Edge<V, E>> edges = graph.matrix.get(u);
            for (Vertex<V> v : edges.keySet()) {
                Edge<V, E> e = edges.get(v);
                if (shortest.get(v) > shortest.get(u) + e.getWeight().doubleValue()) {
                    shortest.replace(v, shortest.get(u) + e.getWeight().doubleValue());
                    heap.remove(v);
                    heap.add(v);
                }
            }
        }
        return shortest;
    }
}
