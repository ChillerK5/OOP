package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.List;

public class CreditBook {
    private final int identity_number;
    private List<Term> terms;

    public CreditBook(int identity_number) {
        this.identity_number = identity_number;
        terms = new ArrayList<>();
    }

    public Term getTerm(int sequence_number) throws IndexOutOfBoundsException {
        if (sequence_number == 0) {
            throw new IndexOutOfBoundsException();
        }
        return terms.get(sequence_number - 1);
    }
}