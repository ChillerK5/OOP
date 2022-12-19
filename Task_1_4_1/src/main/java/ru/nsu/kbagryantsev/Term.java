package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Educational term. Contains a list of records.
 */
public class Term {
    /**
     * Records with marks on some subjects. Unique key is subject name.
     */
    private final Collection<Record> records;

    /**
     * Initialises a term with an empty collection of records.
     */
    public Term() {
        records = new ArrayList<>();
    }

    /**
     * Adds a record by its instance. Checks for subject's duplicates and
     * wether teacher is allowed to make a record.
     *
     * @param record record to be added
     * @return same term instance for method chaining
     */
    public final Term addRecord(final Record record) {
        boolean subjectHasDuplicate = records.stream()
                .anyMatch(r -> r.subject() == record.subject());
        boolean teacherHasAccess =
                record.subject().getTeachers().contains(record.teacher());
        if (subjectHasDuplicate || !teacherHasAccess) {
            throw new IllegalArgumentException();
        }
        records.add(record);
        return this;
    }

    /**
     * Deletes a record by its link.
     *
     * @param record record instance
     */
    public final void deleteRecord(final Record record) {
        records.remove(record);
    }

    /**
     * Gets all records of a term.
     *
     * @return collection of records
     */
    public final Collection<Record> getRecords() {
        return records;
    }
}
