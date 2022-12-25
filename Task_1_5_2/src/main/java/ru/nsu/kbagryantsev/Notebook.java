package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Notebook class.
 */
public class Notebook {
    /**
     * Collection of Record records containing title, text and creation date.
     */
    private final Collection<Record> records;

    /**
     * Initializes a notebook.
     */
    public Notebook() {
        records = new ArrayList<>();
    }

    /**
     * Adds a record by its title and text. Date is attached automatically.
     *
     * @param title title
     * @param text text
     */
    public void addRecord(final String title, final String text) {
        records.add(new Record(title, text));
    }

    /**
     * Removes a record by its title.
     *
     * @param title title
     */
    public void removeRecord(final String title) {
        records.removeIf(record -> record.title().equals(title));
    }

    /**
     * Shows all records sorted by creation date in ascending order.
     *
     * @return collection of records
     */
    public Collection<Record> showRecords() {
        Comparator<Record> compareDates = Comparator.comparing(Record::date);
        return records
                .stream().sorted(compareDates).collect(Collectors.toList());
    }

    /**
     * Shows records, which contain given keyword in the title and were
     * created in a certain period.
     *
     * @param start period start timestamp
     * @param end period end timestamp
     * @param keywords keywords to be checked
     */
    public void showRecords(final Date start, final Date end,
                            final String[] keywords) {
        Comparator<Record> compareDates = Comparator.comparing(Record::date);
        records.stream()
                .filter(r -> Arrays.stream(keywords)
                        .allMatch(k -> r.title().contains(k)))
                .filter(r -> r.date().after(start))
                .filter(r -> r.date().before(end))
                .sorted(compareDates)
                .forEach(System.out::println);
    }
}
