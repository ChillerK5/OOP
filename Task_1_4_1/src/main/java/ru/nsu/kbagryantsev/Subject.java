package ru.nsu.kbagryantsev;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    /**
     * Subject name.
     */
    private final String name;
    /**
     * List of teachers tutoring this very subject.
     */
    private final List<Teacher> teachers;

    /**
     * Creates an empty Subject instance.
     *
     * @param pName subject name
     */
    public Subject(final String pName) {
        this.name = pName;
        this.teachers = new ArrayList<>();
    }

    /**
     * Creates Subject instance by its teachers.
     *
     * @param pName subject name
     * @param pTeachers list of teachers
     */
    public Subject(final String pName, final List<Teacher> pTeachers) {
        this.name = pName;
        this.teachers = pTeachers;
    }

    /**
     * Adds a given teacher to a list of teachers.
     *
     * @param t teacher to be added
     * @return Subject instance for method chaining
     */
    public Subject addTeacher(final Teacher t) {
        teachers.add(t);
        return this;
    }

    /**
     * Excludes a given teacher from a list of teachers.
     *
     * @param t teacher to be excluded
     * @return Subject instance for method chaining
     */
    public Subject removeTeacher(final Teacher t) {
        teachers.remove(t);
        return this;
    }
}
