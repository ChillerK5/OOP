package ru.nsu.kbagryantsev;

import ru.nsu.kbagryantsev.enums.Pizza;

import java.util.Collection;

/**
 * Stores the content of the order.
 *
 * @param content order contents
 */
public record Order(Collection<Pizza> content) {
}
