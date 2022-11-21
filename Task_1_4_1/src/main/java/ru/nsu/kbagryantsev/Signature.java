package ru.nsu.kbagryantsev;

public class Signature {
    Teacher teacher;
    int signature;

    public Signature(Teacher teacher) {
        this.teacher = teacher;
        int signature = teacher.hashCode();
    }

    public boolean verify(Signature s1) {
        return this.signature == s1.signature;
    }
}
