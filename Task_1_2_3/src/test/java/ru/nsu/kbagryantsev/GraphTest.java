package ru.nsu.kbagryantsev;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GraphTest {
    @Nested
    @DisplayName("GraphReader")
    class GraphReaderTest {
        @Test
        @DisplayName("incidenceMatrix")
        void incidenceMatrix() throws IOException {
            GraphReader reader = new GraphReader();
            String fileName = "src/test/resources/incidence_matrix.txt";
            Graph<Integer, Integer> graph = reader.readGraph(fileName);
            GraphReader.printGraph(graph);
        }

        @Test
        @DisplayName("adjacencyMatrix")
        void adjacencyMatrix() throws IOException {
            GraphReader reader = new GraphReader();
            String fileName = "src/test/resources/adjacency_matrix.txt";
            Graph<Integer, Integer> graph = reader.readGraph(fileName);
            GraphReader.printGraph(graph);
        }

        @Test
        @DisplayName("adjacencyList")
        void adjacencyList() throws IOException {
            GraphReader reader = new GraphReader();
            String fileName = "src/test/resources/adjacency_list.txt";
            Graph<Integer, Integer> graph = reader.readGraph(fileName);
            GraphReader.printGraph(graph);
        }
    }

    @Nested
    @DisplayName("GraphUtils")
    class GraphUtilsTest {

        @Test
        @DisplayName("dijkstra")
        void dijkstraTest() throws IOException {
            GraphReader reader = new GraphReader();
            String fileName = "src/test/resources/adjacency_matrix.txt";
            Graph<Integer, Integer> graph = reader.readGraph(fileName);
            ArrayList<Vertex<Integer>> vertices;
            vertices = new ArrayList<>(graph.getVertices());
            vertices.sort(Comparator.comparing(Vertex::getData));
            HashMap<Vertex<Integer>, Double> shortest;
            shortest = (HashMap<Vertex<Integer>, Double>)
                    GraphUtils.dijkstra(graph, vertices.get(2));
            Assertions.assertEquals(shortest.get(vertices.get(0)), 14);
            Assertions.assertEquals(shortest.get(vertices.get(1)), 10);
            Assertions.assertEquals(shortest.get(vertices.get(2)), 0);
            Assertions.assertEquals(shortest.get(vertices.get(3)), 2);
            Assertions.assertEquals(shortest.get(vertices.get(4)), 4);
            Assertions.assertEquals(shortest.get(vertices.get(5)), 5);
            Assertions.assertEquals(shortest.get(vertices.get(6)), 9);
        }
    }
}
