package ru.nsu.kbagryantsev.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import ru.nsu.kbagryantsev.Pizzeria;
import ru.nsu.kbagryantsev.workers.core.WorkerQualification;

/**
 * Deserializer for {@link Pizzeria}.
 */
public final class PizzeriaDeserializer implements JsonDeserializer<Pizzeria> {
    @Override
    public Pizzeria deserialize(final JsonElement json,
                                final Type typeOfT,
                                final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int completedOrderStorageCapacity =
                jsonObject.get("completedOrderStorageCapacity").getAsInt();
        int orderStorageCapacity =
                jsonObject.get("orderStorageCapacity").getAsInt();
        Pizzeria pizzeria = new Pizzeria(orderStorageCapacity,
                completedOrderStorageCapacity);
        deserializePizzaMakers(pizzeria, jsonObject);
        deserializeTransporters(pizzeria, jsonObject);
        return pizzeria;
    }

    private void deserializePizzaMakers(final Pizzeria pizzeria,
                                        final JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("pizzaMakers");
        for (JsonElement jsonElement : jsonArray) {
            String qualification = jsonElement
                    .getAsJsonObject()
                    .get("qualification")
                    .getAsString();
            WorkerQualification workerQualification;
            switch (qualification) {
                case "SENIOR" -> workerQualification =
                        WorkerQualification.SENIOR;
                case "MIDDLE" -> workerQualification =
                        WorkerQualification.MIDDLE;
                case "JUNIOR" -> workerQualification =
                        WorkerQualification.JUNIOR;
                default -> throw new IllegalArgumentException();
            }
            pizzeria.addPizzaMaker(workerQualification);
        }
    }

    private void deserializeTransporters(final Pizzeria pizzeria,
                                         final JsonObject jsonObject) {
        JsonArray transporters = jsonObject
                .getAsJsonArray("transporters");
        for (JsonElement jsonElement : transporters) {
            int capacity = jsonElement
                    .getAsJsonObject()
                    .get("capacity")
                    .getAsInt();
            pizzeria.addTransporter(capacity);
        }
    }
}
