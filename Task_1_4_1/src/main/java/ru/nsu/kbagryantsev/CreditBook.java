package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implements a credit book of a higher education institution
 * with a five-point grading scale and established grading rules.
 */
public class CreditBook {
    /**
     * Unique credit book identity number.
     */
    private final UUID identity;
    /**
     * Mark for the student's qualifying work.
     */
    private int qualifyingWork;
    /**
     * List of terms. Increments on moving onto the next term. Last element of
     * the list must be only the current term.
     */
    private final List<Term> terms;
    /**
     * Excellent mark value.
     */
    private static final int EXCELLENT_MARK = 5;
    /**
     * Satisfactory mark value.
     */
    private static final int SATISFACTORY_MARK = 3;
    /**
     * Excellent grades to all grades established ratio.
     */
    private static final double EXCELLENCE_RATIO = 0.75;

    /**
     * Instantiates a credit book with some unique identity number. Creates
     * one term by default.
     */
    public CreditBook() {
        identity = UUID.randomUUID();
        qualifyingWork = 0;
        terms = new ArrayList<>(List.of(new Term()));
    }

    /**
     * Adds a record by its instance into a current term.
     *
     * @param record Record instance
     */
    public final void addRecord(final Record record) {
        Term currentTerm = getCurrentTerm();
        currentTerm.addRecord(record);
    }

    /**
     * Adds a record by its fields' data into a current term.
     *
     * @param subject subject
     * @param teacher teacher
     * @param grade   grade
     */
    public final void addRecord(final Subject subject,
                                final Teacher teacher,
                                final int grade) {
        Term currentTerm = getCurrentTerm();
        currentTerm.addRecord(new Record(subject, teacher, grade));
    }

    /**
     * Adds a new term at the end of the list. It becomes current term
     * immediately.
     *
     * @return added term instance
     */
    public final Term addTerm() {
        Term term = new Term();
        terms.add(term);
        return term;
    }

    /**
     * Arithmetic mean among all term grades.
     *
     * @return double mean value
     */
    public final double averageGrade() {
        double sum = 0;
        for (Term term : terms) {
            sum += term.getRecords().stream().mapToInt(Record::grade).sum();
        }
        if (totalGrades() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return sum / totalGrades();
    }

    /**
     * Evaluates whether student is allowed to get an excellent diploma grade.
     * Checks are: no satisfactory marks in any terms, amount of excellent
     * grades is above 75% of others, qualifying work is evaluated for an
     * excellent grade.
     *
     * @return true if all criteria passed
     */
    public boolean excellentDiploma() {
        boolean noSatisfactory = terms
                .stream()
                .allMatch(t -> t
                        .getRecords()
                        .stream()
                        .allMatch(r -> r
                                .grade() > SATISFACTORY_MARK));
        boolean geRate = terms
                .stream()
                .mapToDouble(
                        t -> (int) t
                                .getRecords()
                                .stream()
                                .filter(r -> r
                                        .grade() == EXCELLENT_MARK)
                                .count())
                .sum() / totalGrades() >= EXCELLENCE_RATIO;
        boolean qualifyingExcellent = qualifyingWork == EXCELLENT_MARK;
        return noSatisfactory && geRate && qualifyingExcellent;
    }

    /**
     * Evaluates whether student fulfils all extra grant criteria or not for a
     * certain term.
     *
     * @param term term to be checked
     * @return true if responds
     */
    public boolean extraGrant(final Term term) {
        return term
                .getRecords()
                    .stream()
                    .allMatch(r -> r.grade() > SATISFACTORY_MARK);
    }

    /**
     * Gets the current term instance.
     *
     * @return current term
     */
    public final Term getCurrentTerm() {
        return terms.get(terms.size() - 1);
    }

    /**
     * Gets the unique identity number of a book.
     *
     * @return unique ID
     */
    public final UUID getIdentity() {
        return identity;
    }

    /**
     * Gets a list of terms.
     *
     * @return Term instance
     */
    public List<Term> getTerms() {
        return terms;
    }

    /**
     * Gets qualifying work value.
     *
     * @return qualifying work grade
     */
    public int getQualifyingWork() {
        return qualifyingWork;
    }

    /**
     * Updates the value for the qualifying work grade.
     *
     * @param grade newer grade
     */
    public void setQualifyingWork(final int grade) {
        qualifyingWork = grade;
    }

    /**
     * Total amount of grades.
     *
     * @return total amount of given grades among all terms
     */
    private int totalGrades() {
        return terms.stream().mapToInt(t -> t.getRecords().size()).sum();
    }
}
