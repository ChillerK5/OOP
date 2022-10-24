package ru.nsu.kbagryantsev;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;

public class GraphUtils {
    public static <V, E extends Number> Map<Vertex<V>, Double> dijkstra(Graph<V, E> graph, Vertex<V> init) {
        Map<Vertex<V>, Double> shortest = new HashMap<>();
        Comparator<Vertex<V>> comparator = Comparator.comparing(shortest::get);
        PriorityQueue<Vertex<V>> heap = new PriorityQueue<>(comparator);
        BiConsumer<Graph<V, E>, Vertex<V>> initializeSingleSource = (g, v) -> {
            g.matrix.keySet().forEach(x -> shortest.put(x, Double.POSITIVE_INFINITY));
            shortest.replace(v, 0d);
            heap.addAll(g.matrix.keySet());
        };
        initializeSingleSource.accept(graph, init);
        while (!heap.isEmpty()) {
            Vertex<V> u = heap.remove();
            Map<Vertex<V>, Edge<V,E>> edges = graph.matrix.get(u);
            for (Vertex<V> v : edges.keySet()) {
                Edge<V,E> e = edges.get(v);
                if (shortest.get(v) > shortest.get(u) + e.getEdge().doubleValue()) {
                    shortest.replace(v, shortest.get(u) + e.getEdge().doubleValue());
                    heap.remove(v);
                    heap.add(v);
                }
            }
        }
        return shortest;
    }
}
