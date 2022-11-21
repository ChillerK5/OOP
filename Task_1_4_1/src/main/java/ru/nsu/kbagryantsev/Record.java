package ru.nsu.kbagryantsev;

public class Record {
    Grade grade;
    Signature signature;
    Subject subject;

    public Record(Grade grade, Signature signature, Subject subject) {
        this.grade = grade;
        this.signature = signature;
        this.subject = subject;
    }
}
