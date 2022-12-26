package ru.nsu.kbagryantsev;

/**
 * Signum function for real number in complex form.
 */
public class Signum extends Operator {
    /**
     * {@link Operator#arity}.
     */
    private static final int ARITY = 1;

    /**
     * Default constructor.
     */
    public Signum() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
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
