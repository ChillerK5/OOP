package ru.nsu.kbagryantsev;

import java.util.Date;

/**
 * Single notebook record. Unique key is creation date.
 *
 * @param title record title
 * @param text record text
 * @param date date of creation
 */
public record Record(String title, String text, Date date) {
    /**
     * Creates a record by its title and text. Attaches the date automatically.
     *
     * @param title title
     * @param text text
     */
    public Record(final String title, final String text) {
        this(title, text, new Date());
    }
}
