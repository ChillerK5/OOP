package ru.nsu.kbagryantsev.order;

import java.util.Collection;

/**
 * Stores the content of the order.
 *
 * @param content order contents
 */
public record Order(Collection<MenuItem> content) {
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + Integer.toHexString(hashCode());
    }
}
