package ru.nsu.kbagryantsev;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NotebookTest {
    @Nested
    @DisplayName("Notebook tests")
    class NotebookTests {
        @Test
        @DisplayName("Adding a record and getting it")
        void addTrivial() {
            Notebook notebook = new Notebook();
            notebook.addRecord("Test", "Some text");
            Assertions.assertFalse(notebook.showRecords().isEmpty());
        }

        @Test
        @DisplayName("Removing a record")
        void removeTrivial() {
            Notebook notebook = new Notebook();
            notebook.addRecord("Test", "Some text");
            notebook.removeRecord("Test");
            Assertions.assertTrue(notebook.showRecords().isEmpty());
        }

        @Test
        @DisplayName("Showing filtered records")
        void showTrivial() {
            Notebook notebook = new Notebook();
            notebook.addRecord("Test", "Some text");
            Optional<Record> optionalRecord;
            optionalRecord = notebook.showRecords().stream().findAny();
            Record record;
            if (optionalRecord.isPresent()) {
                record = optionalRecord.get();
                Date start = new Date(record.date().getTime() - 1000);
                Date end = new Date(record.date().getTime() + 1000);
                String[] keywords = new String[]{record.title()};

                Collection<Record> filteredRecords;
                filteredRecords = notebook.showRecords(start, end, keywords);
                Assertions.assertFalse(filteredRecords.isEmpty());
            }
        }
    }

    @Test
    @DisplayName("Record 2 string")
    void recordString() {
        Record record = new Record("Test", "Some text");

        Assertions.assertDoesNotThrow(record::toString);
        Assertions.assertDoesNotThrow(record::text);
    }

    @Test
    @DisplayName("Record serializer")
    void recordSerializer() {
        RecordSerializer recordSerializer = new RecordSerializer();

        Record record = new Record("Test", "Some text");
        JsonElement recordJson;
        recordJson = recordSerializer.serialize(record, Record.class, null);
        JsonObject jsonObject = recordJson.getAsJsonObject();
        String title = jsonObject.getAsJsonPrimitive("title").getAsString();
        String text = jsonObject.getAsJsonPrimitive("text").getAsString();
        Long date = jsonObject.getAsJsonPrimitive("date").getAsLong();
        Assertions.assertEquals(record.title(), title);
        Assertions.assertEquals(record.text(), text);
        Assertions.assertEquals(record.date().getTime(), date);
    }

    @Test
    @DisplayName("Record deserializer")
    void recordDeserializer() {
        RecordSerializer recordSerializer = new RecordSerializer();
        RecordDeserializer recordDeserializer = new RecordDeserializer();

        Record test = new Record("Test", "Some text");
        JsonElement recordJson;
        recordJson = recordSerializer.serialize(test, Record.class, null);

        Record model;
        model = recordDeserializer.deserialize(recordJson, Record.class, null);

        Assertions.assertEquals(model, test);
    }

    @Test
    @DisplayName("Notebook storing")
    void notebookStoring() throws IOException {
        Notebook modal = new Notebook();
        modal.addRecord("Record #1", "Road to the Heap sort");
        modal.addRecord("Record #2", "Road to the Stack");

        String path = "./src/test/resources/Notebook.json";
        FileWriter fileWriter = new FileWriter(path);
        NotebookSerializer notebookSerializer = new NotebookSerializer(fileWriter);
        Assertions.assertDoesNotThrow(() -> notebookSerializer.serialize(modal));

        FileReader fileReader = new FileReader(path);
        NotebookDeserializer notebookDeserializer;
        notebookDeserializer = new NotebookDeserializer(fileReader);

        Notebook test = notebookDeserializer.deserialize();
        Assertions.assertEquals(modal.showRecords(), test.showRecords());
    }

    @Test
    @DisplayName("Main test")
    void main() throws IOException, ParseException, java.text.ParseException {
        String path = "./src/test/resources/Notebook.json";

        String[] addRecord = new String[]{
            "-path",
            "\"./src/test/resources/Notebook.json\"",
            "-add",
            "\"Record #3\"",
            "\"Road to the Tree\""};
        Main.main(addRecord);

        FileReader fileReader = new FileReader(path);
        NotebookDeserializer notebookDeserializer;
        notebookDeserializer = new NotebookDeserializer(fileReader);
        Notebook notebook = notebookDeserializer.deserialize();
        Assertions.assertEquals(3, notebook.showRecords().size());

        String[] rmRecord = new String[]{
            "-path",
            "\"./src/test/resources/Notebook.json\"",
            "-rm",
            "\"Record #3\""};
        Main.main(rmRecord);

        fileReader = new FileReader(path);
        notebookDeserializer = new NotebookDeserializer(fileReader);
        notebook = notebookDeserializer.deserialize();
        Assertions.assertEquals(2, notebook.showRecords().size());

        final PrintStream systemOutput = System.out;
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutput));
        String[] showRecord = new String[]{
            "-path",
            "\"./src/test/resources/Notebook.json\"",
            "-show"};
        Main.main(showRecord);

        String result = testOutput.toString();
        Assertions.assertTrue(result.contains("Record #1"));
        Assertions.assertTrue(result.contains("Record #2"));
        System.setOut(systemOutput);

        testOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutput));
        String[] showRecords = new String[]{
            "-path",
            "\"./src/test/resources/Notebook.json\"",
            "-show",
            "\"01.01.1970 00:00\"",
            "\"31.12.2077 00:00\"",
            "Record #1"};
        Main.main(showRecords);

        result = testOutput.toString();
        Assertions.assertTrue(result.contains("Record #1"));
        System.setOut(systemOutput);
    }
}
