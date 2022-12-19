package ru.nsu.kbagryantsev;

/**
 * Record class teacher assigned with teacher's ids and his signature.
 *
 * @param firstName first name
 * @param lastName last name
 * @param patronymic patronymic if present
 */
record Teacher(String firstName, String lastName, String patronymic) {
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
