package ru.nsu.kbagryantsev;

import java.util.Objects;

/**
 * Single credit book record. Contains a subject, a teacher tutoring it and a
 * received grade.
 */
public record Record(Subject subject, Teacher teacher, int grade) {
    /**
     * @param subject subject
     * @param teacher teacher
     * @param grade   grade
     */
    public Record {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Record) obj;
        return Objects.equals(this.subject, that.subject) &&
                Objects.equals(this.teacher, that.teacher) &&
                this.grade == that.grade;
    }

    @Override
    public String toString() {
        return "Record[" +
                "subject=" + subject + ", " +
                "teacher=" + teacher + ", " +
                "grade=" + grade + ']';
    }
}
