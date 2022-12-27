package ru.nsu.kbagryantsev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Writer;

class NotebookSerializer {
    /**
     * Output stream.
     */
    private final Writer writer;
    /**
     * Gson serializer.
     */
    private final Gson gson;

    NotebookSerializer(final Writer writer) {
        this.writer = writer;
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Record.class, new RecordSerializer())
                .create();
    }

    public void serialize(final Notebook notebook) throws IOException {
        String json = gson.toJson(notebook);
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
