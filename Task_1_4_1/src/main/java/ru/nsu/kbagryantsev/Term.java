package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.List;

public class Term {
    private final List<Record> records;

    public Term() {
        records = new ArrayList<>();
    }

    public Term addRecord(Record r) {
        records.add(r);
        return this;
    }

    public Term editRecord(Record r) {
        return this;
    }
}
