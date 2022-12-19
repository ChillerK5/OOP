package ru.nsu.kbagryantsev;

import java.util.Objects;

/**
 * Single credit book record. Contains a subject, a teacher tutoring it and a
 * received grade.
 */
public final class Record {
    private final Subject subject;
    private final Teacher teacher;
    private final int grade;

    /**
     * Instantiates a record with given data.
     *
     * @param subject subject
     * @param teacher teacher
     * @param grade   grade
     */
    public Record(Subject subject, Teacher teacher, int grade) {
        this.subject = subject;
        this.teacher = teacher;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Record) obj;
        return Objects.equals(this.subject, that.subject)
                && Objects.equals(this.teacher, that.teacher)
                && this.grade == that.grade;
    }

    @Override
    public String toString() {
        return "Record["
                +
                "subject=" + subject + ", "
                +
                "teacher=" + teacher + ", "
                +
                "grade=" + grade + ']';
    }

    public Subject subject() {
        return subject;
    }

    public Teacher teacher() {
        return teacher;
    }

    public int grade() {
        return grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, teacher, grade);
    }
}
