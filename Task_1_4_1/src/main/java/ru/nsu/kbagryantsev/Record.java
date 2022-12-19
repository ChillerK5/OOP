package ru.nsu.kbagryantsev;

/**
 * Single credit book record. Contains a subject, a teacher tutoring it and a
 * received grade.
 *
 * @param subject subject
 * @param teacher teacher
 * @param grade grade
 */
public record Record(Subject subject, Teacher teacher, int grade) { }
