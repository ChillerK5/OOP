package ru.nsu.kbagryantsev;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Abstract class for a concurrency benchmark executing prime checker on a
 * set dataset.
 */
public abstract class ConcurrencyBenchmark {
    /**
     * Dataset path.
     */
    private static final String DATAPATH = "./src/jmh/resources/dataset.json";

    /**
     * Prime numbers dataset.
     */
    protected final List<Integer> dataset;

    /**
     * Deserializes dataset from proper JSON file.
     *
     * @throws FileNotFoundException JSON file may be missing
     */
    public ConcurrencyBenchmark() throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(DATAPATH));
        Type datasetType = new TypeToken<List<Integer>>() { }.getType();
        dataset = new Gson().fromJson(jsonReader, datasetType);
    }

    /**
     * Main benchmark method.
     *
     * @param blackhole Blackhole instance
     */
    public abstract void benchmark(Blackhole blackhole);
}
