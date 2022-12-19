package ru.nsu.kbagryantsev;

import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * Educational subject. Contains a list of teachers tutoring it.
 */
public class Subject {
    /**
     * Subject's name.
     */
    private final String name;
    /**
     * List of teachers tutoring this very subject.
     */
    private final Set<Teacher> teachers;

    /**
     * Creates an empty Subject instance with no teachers.
     *
     * @param name subject name
     */
    public Subject(@NotNull final String name) {
        this.name = name;
        this.teachers = new HashSet<>();
    }

    /**
     * Creates Subject instance by its teachers' collection.
     *
     * @param name     subject name
     * @param teachers collection of teachers
     */
    public Subject(@NotNull final String name,
                   final Set<Teacher> teachers) {
        this.name = name;
        this.teachers = teachers;
    }

    /**
     * Adds a teacher by his data to a collection of teachers.
     *
     * @param firstName teacher's first name
     * @param lastName teacher's last name
     * @param patronymic teacher's patronymic
     * @return Subject instance for method chaining
     */
    public Subject addTeacher(@NotNull final String firstName,
                              @NotNull final String lastName,
                              @NotNull final String patronymic) {
        teachers.add(new Teacher(firstName, lastName, patronymic));
        return this;
    }

    /**
     * Adds a teacher by his data to a collection of teachers.
     *
     * @param firstName teacher's first name
     * @param lastName teacher's last name
     * @return Subject instance for method chaining
     */
    public Subject addTeacher(@NotNull final String firstName,
                              @NotNull final String lastName) {
        teachers.add(new Teacher(firstName, lastName));
        return this;
    }

    /**
     * Adds a teacher instance to a collection of teachers.
     *
     * @param teacher Teacher instance
     * @return Subject instance for method chaining
     */
    public Subject addTeacher(@NotNull final Teacher teacher) {
        teachers.add(teacher);
        return this;
    }

    /**
     * Gets the subject's name.
     *
     * @return subject's name
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets a collection of teachers tutoring this subject.
     *
     * @return collection of teachers' instances
     */
    public final Set<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * Excludes a given teacher from a collection of teachers.
     *
     * @param teacher teacher to be excluded
     * @return Subject instance for method chaining
     */
    public Subject removeTeacher(final Teacher teacher) {
        teachers.remove(teacher);
        return this;
    }
}
