package ru.nsu.kbagryantsev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Instantiating graph by its file representation.
 */
public final class GraphReader {
    /**
     * Prints a graph in <i>vertex1 - vertex2: edge</i> format.
     *
     * @param graph graph to be printed
     */
    public static void printGraph(final Graph<Integer, Integer> graph) {
        for (Vertex<Integer> u : graph.getVertices()) {
            for (Vertex<Integer> v : graph.matrix.get(u).keySet()) {
                System.out.printf("%s - %s: %s\n",
                        u.getData().toString(),
                        v.getData().toString(),
                        graph.matrix.get(u).get(v).getWeight().toString());
            }
        }
        System.out.print("\n");
    }

    /**
     * Reads all graph representations. Calls needed graph parsing methods to
     * instantiate a certain graph.
     *
     * @param fileName file containing a graph
     * @return graph instance
     * @throws IOException file may be not found
     */
    public Graph<Integer, Integer> readGraph(final String fileName)
            throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        //Determines graph representation type
        String line = bufferedReader.readLine();
        if (Objects.equals(line, "AM")) {
            return adjacencyMatrix(fileName);
        } else if (Objects.equals(line, "AL")) {
            return adjacencyList(fileName);
        } else if (Objects.equals(line, "IM")) {
            return incidenceMatrix(fileName);
        } else {
            throw new IllegalStateException("Invalid graph representation");
        }
    }

    /**
     * Builds a graph based on its file description.
     * File contains an incidence matrix.
     * All the weights are integer and equal to 1.
     * All the vertices contain a number of a string, which describes it
     *
     * @param fileName file containing incidence matrix
     * @return graph instance
     * @throws IOException file may be not found
     */
    public Graph<Integer, Integer> incidenceMatrix(final String fileName)
            throws IOException {
        //Creating buffered reader of a graph file
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();

        //Instantiating new graph
        Graph<Integer, Integer> graph = new Graph<>();

        //Getting amount of vertices and edges
        String[] separatedLine;
        separatedLine = bufferedReader.readLine().split(" ");
        int vertices    = Integer.parseInt(separatedLine[0]);
        int edges       = Integer.parseInt(separatedLine[1]);

        //Completion of a graph's incidence matrix
        int[][] matrix = new int[vertices][edges];
        for (int i = 0; i < vertices; i++) {
            //Processing single vertex incident edges
            graph.addVertex(i + 1);
            separatedLine = bufferedReader.readLine().split(" ");
            for (int j = 0; j < separatedLine.length; j++) {
                matrix[i][j] = Integer.parseInt(separatedLine[j]);
            }
        }
        //Constructing a graph
        ArrayList<Vertex<Integer>> verticesArray;
        verticesArray = new ArrayList<>(graph.getVertices());
        verticesArray.sort(Comparator.comparing(Vertex::getData));
        for (int j = 0; j < edges; j++) {
            int start   = 0;
            int end     = 0;
            for (int i = 0; i < vertices; i++) {
                if (matrix[i][j] == 1) {
                    start = i;
                } else if (matrix[i][j] == -1) {
                    end = i;
                }
            }
            graph.addEdge(verticesArray.get(start), verticesArray.get(end), 1);
        }
        return graph;
    }

    /**
     * Builds a graph based on its file description.
     * File contains an adjacency matrix.
     * All vertices are assigned with number of the row of their occurrence.
     * Single matrix cell represents a direct edge from i vertex to j vertex
     * with some numerical weight on it.
     *
     * @param fileName file containing adjacency matrix
     * @return graph instance
     * @throws IOException file may be not found
     */
    public Graph<Integer, Integer> adjacencyMatrix(final String fileName)
            throws IOException {
        //Creating buffered reader of a graph file
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();

        //Instantiating new graph
        Graph<Integer, Integer> graph = new Graph<>();

        //Getting amount of vertices
        int vertices = Integer.parseInt(bufferedReader.readLine());

        ArrayList<Vertex<Integer>> verticesList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            verticesList.add(graph.addVertex(i + 1));
        }

        //Constructing a graph
        for (int i = 0; i < vertices; i++) {
            Vertex<Integer> u = verticesList.get(i);
            String[] line = bufferedReader.readLine().split(" ");
            for (int j = 0; j < vertices; j++) {
                if (!Objects.equals(line[j], "-")) {
                    Vertex<Integer> v = verticesList.get(j);
                    int weight = Integer.parseInt(line[j]);
                    graph.addEdge(u, v, weight);
                }
            }
        }
        return graph;
    }

    /**
     * Builds a graph based on its file description.
     * File contains an adjacency list.
     * All vertices are assigned with integers in an arbitrary order.
     * All edges are numerical and come after the initial and terminal vertices'
     * description.
     *
     * @param fileName file containing adjacency list
     * @return graph instance
     * @throws IOException file may be not found
     */
    public Graph<Integer, Integer> adjacencyList(final String fileName)
            throws IOException {
        //Creating buffered reader of a graph file
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();
        String line = bufferedReader.readLine();

        //Instantiating new graph
        Graph<Integer, Integer> graph = new Graph<>();

        //Getting amount of vertices and edges
        String[] separatedLine = line.split(" ");
        int vertices = Integer.parseInt(separatedLine[0]);
        int edges = Integer.parseInt(separatedLine[1]);

        ArrayList<Vertex<Integer>> verticesList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            verticesList.add(graph.addVertex(i + 1));
        }

        //Constructing a graph
        for (int i = 0; i < edges; i++) {
            line = bufferedReader.readLine();
            separatedLine = line.split(" ");
            int uData = Integer.parseInt(separatedLine[0]);
            int vData = Integer.parseInt(separatedLine[1]);
            int weight = Integer.parseInt(separatedLine[2]);
            Vertex<Integer> u = verticesList.get(uData);
            Vertex<Integer> v = verticesList.get(vData);
            graph.addEdge(u, v, weight);
        }

        return graph;
    }
}
