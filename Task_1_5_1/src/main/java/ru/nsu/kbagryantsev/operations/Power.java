package ru.nsu.kbagryantsev.operations;

import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

import java.math.BigDecimal;

public class Power extends Operator {
    /**
     * {@link Operator#arity}.
     */
    public static final int ARITY = 2;

    /**
     * Default constuctor.
     */
    public Power() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the power of the given
     * complex numbers. The first argument is the empowered complex and the
     * second is the power value. Returns default result of k = 0. Supports
     * only real power values.
     *
     * @param argv complex numbers
     * @return complex result
     */
    public static Complex apply(final Complex... argv) {
        int argc = argv.length;
        if (argc != ARITY) {
            throw new IllegalArgumentException();
        }
        if (!argv[1].isReal()) {
            throw new IllegalArgumentException();
        }

        Complex z = argv[0];
        Double a = argv[0].real();
        Double b = argv[0].imaginary();
        Double n = argv[1].real();
        Double phi = Math.atan2(b, a);

        BigDecimal absn = BigDecimal.valueOf(Math.pow(z.abs(), n));
        BigDecimal cos = BigDecimal.valueOf(Math.cos(n * phi));
        BigDecimal sin = BigDecimal.valueOf(Math.sin(n * phi));

        return new Complex.ComplexBuilder()
                .real(absn.multiply(cos).doubleValue())
                .imaginary(absn.multiply(sin).doubleValue())
                .build();
    }
}
