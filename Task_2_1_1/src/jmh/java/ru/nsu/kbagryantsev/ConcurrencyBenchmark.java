package ru.nsu.kbagryantsev;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

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
    protected List<Integer> dataset;

    /**
     * Deserializes dataset from proper JSON file.
     *
     * @throws FileNotFoundException JSON file may be missing
     */
    public void readData() throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(DATAPATH));
        Type datasetType = new TypeToken<List<Integer>>() { }.getType();
        dataset = new Gson().fromJson(jsonReader, datasetType);
    }
}
