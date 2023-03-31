package ru.nsu.kbagryantsev.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.Pizzeria;

public class PizzeriaDeserializerTest {
    @Test
    void givenCorrectPizzeriaProperties_thenReturnPizzeria() throws IOException {
        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(Pizzeria.class, new PizzeriaDeserializer())
                        .create();
        String propertiesPath = "./src/test/resources/correct_properties.json";
        try (FileReader fileReader = new FileReader(propertiesPath)) {
            Assertions.assertDoesNotThrow(() -> gson.fromJson(fileReader, Pizzeria.class));
        }
    }

    @Test
    void givenIncorrectPizzeriaProperties_thenThrowExceptions() throws IOException {
        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(Pizzeria.class, new PizzeriaDeserializer())
                        .create();
        String propertiesPath = "./src/test/resources/incorrect_properties.json";
        try (FileReader fileReader = new FileReader(propertiesPath)) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> gson.fromJson(fileReader, Pizzeria.class));
        }
    }
}
