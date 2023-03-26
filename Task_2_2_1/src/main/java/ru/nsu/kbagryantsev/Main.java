package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ru.nsu.kbagryantsev.order.*;
import ru.nsu.kbagryantsev.utils.RandomEnumGenerator;
import ru.nsu.kbagryantsev.workers.WorkerQualification;

public final class Main {
    private Main() {
    }

    public static void main(final String[] args) throws Exception {
        Pizzeria pizzeria = new Pizzeria(10, 10);
        pizzeria.addPizzaMaker(WorkerQualification.MIDDLE);
        pizzeria.addPizzaMaker(WorkerQualification.MIDDLE);
        pizzeria.addTransporter(2);
        pizzeria.addTransporter(1);
        pizzeria.start();
        for (int i = 0; i < 10; i++) {
            Future<Order> orderFuture = submitRandomOrder();
            Order order = orderFuture.get();
            pizzeria.addOrder(order);
        }
        pizzeria.stop();
    }

    private static Order createRandomOrder() {
        Random random = new Random();
        int orderSize = random.nextInt(1, 5);
        Collection<MenuItem> orderContent = new ArrayList<>();
        for (int i = 0; i < orderSize; i++) {
            PizzaType pizzaType =
                    RandomEnumGenerator.randomEnum(PizzaType.class);
            PizzaSize pizzaSize =
                    RandomEnumGenerator.randomEnum(PizzaSize.class);
            PizzaCrust pizzaCrust =
                    RandomEnumGenerator.randomEnum(PizzaCrust.class);
            orderContent.add(new Pizza(pizzaType, pizzaSize, pizzaCrust));
        }
        return new Order(orderContent);
    }

    private static Future<Order> submitRandomOrder() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Order> callable = () -> {
            Thread.sleep(new Random().nextInt(1000, 4000));
            return createRandomOrder();
        };
        try {
            return executor.submit(callable);
        } finally {
            executor.shutdown();
        }
    }
}
