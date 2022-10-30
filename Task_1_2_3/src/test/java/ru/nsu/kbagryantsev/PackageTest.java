package ru.nsu.kbagryantsev;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PackageTest {
    @Nested
    @DisplayName("Vertex")
    class VertexTest {
        @Test
        void setData() {
            Graph<Character, Integer> graph = new Graph<>();
            Vertex<Character> v = graph.addVertex('A');
            v.setData('B');
        }

        @Test
        void getData() {
            Graph<Character, Integer> graph = new Graph<>();
            Vertex<Character> v = graph.addVertex('A');
            Assertions.assertEquals('A', v.getData());
        }
    }

    @Nested
    @DisplayName("Edge")
    class EdgeTest {
        @Test
        void setWeight() {
            Graph<Character, Integer> graph = new Graph<>();
            Vertex<Character> u = graph.addVertex('A');
            Vertex<Character> v = graph.addVertex('B');
            Edge<Character, Integer> e = graph.addEdge(u, v, 1);
            e.setWeight(2);
        }

        @Test
        void getWeight() {
            Graph<Character, Integer> graph = new Graph<>();
            Vertex<Character> u = graph.addVertex('A');
            Vertex<Character> v = graph.addVertex('B');
            Edge<Character, Integer> e = graph.addEdge(u, v, 1);
            Assertions.assertEquals(e.getWeight(), 1);
        }
    }
    @Nested
    @DisplayName("Graph")
    class GraphTest {
        @Nested
        @DisplayName("addVertex")
        class AddVertexTest {
            @Test
            void simple() {
                Graph<Character, Integer> graph = new Graph<>();
                Vertex<Character> v = graph.addVertex('A');
                HashSet<Vertex<Character>> modelSet = new HashSet<>();
                modelSet.add(v);
                Set<Vertex<Character>> testSet = graph.getVertices();
                Assertions.assertEquals(modelSet,testSet);
            }
        }

        @Nested
        @DisplayName("addVertex2")
        class AddVertex2Test {
            @Test
            void simple() {
                Graph<Character, Integer> graph = new Graph<>();
                Vertex<Character> v = new Vertex<>('A');
                graph.addVertex(v);
                HashSet<Vertex<Character>> modelSet = new HashSet<>();
                modelSet.add(v);
                Set<Vertex<Character>> testSet = graph.getVertices();
                Assertions.assertEquals(modelSet,testSet);
            }
        }

        @Nested
        @DisplayName("cutVertex")
        class CutVertexTest {
            @Test
            void simple() {
                Graph<Character, Integer> graph = new Graph<>();
                Vertex<Character> v = graph.addVertex('A');
                HashSet<Vertex<Character>> modelSet = new HashSet<>();
                graph.cutVertex(v);
                Set<Vertex<Character>> testSet = graph.getVertices();
                Assertions.assertEquals(modelSet,testSet);
            }
        }

        @Nested
        @DisplayName("addEdge")
        class AddEdgeTest {
            @Test
            void simple() {
                Graph<Character, Integer> graph = new Graph<>();
                Vertex<Character> u = graph.addVertex('A');
                Vertex<Character> v = graph.addVertex('B');
                graph.addEdge(u, v, 1);
            }
        }

        @Nested
        @DisplayName("cutEdge")
        class CutEdgeTest {
            @Test
            void simple() {
                Graph<Character, Integer> graph = new Graph<>();
                Vertex<Character> u = graph.addVertex('A');
                Vertex<Character> v = graph.addVertex('B');
                Edge<Character, Integer> e = graph.addEdge(u, v, 1);
                graph.cutEdge(e);
            }
        }

        @Nested
        @DisplayName("cutEdge2")
        class CutEdge2Test {
            @Test
            void simple() {
                Graph<Character, Integer> graph = new Graph<>();
                Vertex<Character> u = graph.addVertex('A');
                Vertex<Character> v = graph.addVertex('B');
                graph.addEdge(u, v, 1);
                graph.cutEdge(u, v);
            }
        }
    }
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
