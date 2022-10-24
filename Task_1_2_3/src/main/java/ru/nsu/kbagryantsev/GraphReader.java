package ru.nsu.kbagryantsev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Instantiating graph by its file representation.
 */
public class GraphReader<V, E extends Number> {
    public Graph<V, E> incidenceMatrix(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            String[] separatedLine = line.split(" ");
            int vertices = parseInt(separatedLine[0]);
            int edges = parseInt(separatedLine[1]);
            for (int i = 0; i < edges; i++) {
                line = br.readLine();
                String edgeData = line;

            }
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
        }
        return new Graph<>();
    }
}
