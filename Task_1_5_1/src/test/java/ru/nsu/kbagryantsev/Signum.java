package ru.nsu.kbagryantsev;

public class Signum extends Operator {
    private static final int ARITY = 1;

    public Signum() {
        arity = ARITY;
    }

    @Override
    public Complex compute(Complex... argv) {
        int argc = argv.length;
        if (argc != ARITY) {
            throw new IllegalArgumentException();
        }

        Double a = argv[0].real();

        if (!argv[0].isReal()) {
            throw new IllegalArgumentException();
        }

        return new Complex.ComplexBuilder().real(a >= 0 ? 1.0 : -1.0).build();
    }
}
