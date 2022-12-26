package ru.nsu.kbagryantsev.operations;

import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

public class NaturalLogarithm extends Operator {
    /**
     * {@link Operator#arity}.
     */
    public static final int ARITY = 1;

    /**
     * Default constuctor.
     */
    public NaturalLogarithm() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the natural logarithm
     * result of the given complex number. Supports complex numbers which are
     * actually real.
     *
     * @param argv complex number
     * @return complex result
     */
    public static Complex apply(final Complex... argv) {
        int argc = argv.length;
        if (argc != ARITY) {
            throw new IllegalArgumentException();
        }

        Complex z = argv[0];
        Double a = argv[0].real();
        Double b = argv[0].imaginary();

        if (a == 0.0 && b == 0.0) {
            throw new ArithmeticException();
        }

        Double phi = Math.atan2(b, a);

        return new Complex.ComplexBuilder()
                .real(Math.log(z.abs()))
                .imaginary(phi)
                .build();
    }
}
