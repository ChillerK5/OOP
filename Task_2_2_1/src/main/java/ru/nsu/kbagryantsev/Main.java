package ru.nsu.kbagryantsev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.util.concurrent.Future;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.utils.PizzeriaDeserializer;

/**
 * Main.
 */
public final class Main {
    private Main() {
    }

    /**
     * Runs a pizzeria to process set amount of orders.
     *
     * @param args CLI args
     * @throws Exception not handled
     */
    public static void main(final String[] args) throws Exception {
        // Deserializing pizzeria properties
        Gson gson =
            new GsonBuilder()
                .registerTypeAdapter(Pizzeria.class, new PizzeriaDeserializer())
                .create();
        Pizzeria pizzeria;
        String propertiesPath = "./src/main/resources/properties.json";
        try (FileReader fileReader = new FileReader(propertiesPath)) {
            pizzeria = gson.fromJson(fileReader, Pizzeria.class);
        }

        // Running pizzeria
        pizzeria.start();

        // Submiting orders
        RandomOrderGenerator randomOrderGenerator =
                new RandomOrderGenerator(5, 1000, 2000);
        for (int i = 0; i < 30; i++) {
            Future<Order> orderFuture =
                    randomOrderGenerator.getRandomOrderWithDelay();
            Order order = orderFuture.get();
            pizzeria.addOrder(order);
        }

        // Shutting down pizzeria
        pizzeria.stop();
    }
}
