package ru.nsu.kbagryantsev;

import java.util.Objects;

/**
 * Record class teacher assigned with teacher's ids and his signature.
 */
public final class Teacher {
    private final String firstName;
    private final String lastName;
    private final String patronymic;

    /**
     * Instantiates the teacher by his names.
     *
     * @param firstName  firstName
     * @param lastName   lastName
     * @param patronymic patronymic
     */
    public Teacher(String firstName, String lastName, String patronymic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    /**
     * Support for a case of patromynic absense.
     *
     * @param firstName first name
     * @param lastName  last name
     */
    Teacher(final String firstName, final String lastName) {
        this(firstName, lastName, null);
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String patronymic() {
        return patronymic;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Teacher) obj;
        return Objects.equals(this.firstName, that.firstName)
                && Objects.equals(this.lastName, that.lastName)
                && Objects.equals(this.patronymic, that.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic);
    }

    @Override
    public String toString() {
        return "Teacher["
                +
                "firstName=" + firstName + ", "
                +
                "lastName=" + lastName + ", "
                +
                "patronymic=" + patronymic + ']';
    }

}
