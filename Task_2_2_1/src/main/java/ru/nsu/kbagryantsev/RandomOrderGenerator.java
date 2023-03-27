package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import ru.nsu.kbagryantsev.order.MenuItem;
import ru.nsu.kbagryantsev.order.MenuItemOptions;
import ru.nsu.kbagryantsev.order.Order;
import ru.nsu.kbagryantsev.order.drink.Drink;
import ru.nsu.kbagryantsev.order.drink.DrinkOptions;
import ru.nsu.kbagryantsev.order.pizza.Pizza;
import ru.nsu.kbagryantsev.order.pizza.PizzaCrust;
import ru.nsu.kbagryantsev.order.pizza.PizzaOptions;
import ru.nsu.kbagryantsev.order.pizza.PizzaSize;
import ru.nsu.kbagryantsev.order.side.Side;
import ru.nsu.kbagryantsev.order.side.SideOptions;
import ru.nsu.kbagryantsev.utils.RandomEnumGenerator;

/**
 * Random order generator.
 *
 * @param maxOrderSize generated order size upper bound
 * @param delayOrigin  minimal order delay in milliseconds
 * @param delayBound   maximal order delay in milliseconds
 */
public record RandomOrderGenerator(int maxOrderSize,
                                   int delayOrigin,
                                   int delayBound) {
    /**
     * Random instance.
     */
    private static final Random RANDOM = new Random();

    /**
     * Gets random order size between set bounds.
     *
     * @return integer size
     */
    private int getRandomOrderSize() {
        return RANDOM.nextInt(1, maxOrderSize);
    }

    /**
     * Gets a random order with some random delay. Used Executor is
     * autocloseable.
     *
     * @return future order
     */
    public Future<Order> getRandomOrderWithDelay() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Order> callable = () -> {
            Thread.sleep(new Random().nextInt(delayOrigin, delayBound));
            return getRandomOrder();
        };
        try {
            return executor.submit(callable);
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Gets random menu item. See {@link MenuItemOptions}.
     *
     * @return menu item
     */
    private MenuItem getRandomMenuItem() {
        MenuItemOptions menuItemOption =
                RandomEnumGenerator.randomEnum(MenuItemOptions.class);
        switch (menuItemOption) {
            case PIZZA -> {
                PizzaOptions pizzaOption =
                        RandomEnumGenerator.randomEnum(PizzaOptions.class);
                PizzaSize pizzaSize =
                        RandomEnumGenerator.randomEnum(PizzaSize.class);
                PizzaCrust pizzaCrust =
                        RandomEnumGenerator.randomEnum(PizzaCrust.class);
                return new Pizza(pizzaOption, pizzaSize, pizzaCrust);
            }
            case DRINK -> {
                DrinkOptions drinkOption =
                        RandomEnumGenerator.randomEnum(DrinkOptions.class);
                return new Drink(drinkOption);
            }
            case SIDE -> {
                SideOptions sideOptions =
                        RandomEnumGenerator.randomEnum(SideOptions.class);
                return new Side(sideOptions);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    /**
     * Gets random order of random size. Contents of the order are randomly
     * generated from all possible options.
     *
     * @return random order
     */
    public Order getRandomOrder() {
        int orderSize = getRandomOrderSize();
        Collection<MenuItem> orderContent = new ArrayList<>();
        for (int i = 0; i < orderSize; i++) {
            orderContent.add(getRandomMenuItem());
        }
        return new Order(orderContent);
    }
}
