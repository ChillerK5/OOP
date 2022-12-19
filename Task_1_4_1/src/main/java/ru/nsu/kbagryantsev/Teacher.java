package ru.nsu.kbagryantsev;

/**
 * Record class teacher assigned with teacher's ids and his signature.
 */
public record Teacher(String firstName, String lastName, String patronymic) {
    /**
     * @param firstName firstName
     * @param lastName lastName
     * @param patronymic patronymic
     */
    public Teacher {

    }
    /**
     * Support for a case of patromynic absense.
     *
     * @param firstName first name
     * @param lastName last name
     */
    Teacher(final String firstName, final String lastName) {
        this(firstName, lastName, null);
    }
}
