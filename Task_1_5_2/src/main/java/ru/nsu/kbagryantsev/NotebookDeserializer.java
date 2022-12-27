package ru.nsu.kbagryantsev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;

class NotebookDeserializer {
    /**
     * Input reader.
     */
    private final Reader reader;
    /**
     * Gson deserializer.
     */
    private final Gson gson;

    NotebookDeserializer(final Reader reader) {
        this.reader = reader;
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Record.class, new RecordDeserializer())
                .create();
    }

    public Notebook deserialize() throws IOException {
        Notebook notebook = gson.fromJson(reader, Notebook.class);
        reader.close();
        return notebook;
    }
}
