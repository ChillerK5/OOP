package ru.nsu.kbagryantsev.order.side;

import ru.nsu.kbagryantsev.order.MenuItem;

/**
 * Side instance.
 *
 * @param sideOption side option
 */
public record Side(SideOptions sideOption) implements MenuItem {
}
