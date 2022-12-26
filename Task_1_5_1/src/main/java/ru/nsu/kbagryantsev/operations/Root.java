package ru.nsu.kbagryantsev.operations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

/**
 * {@link Operator}.
 */
public class Root extends Operator {
    /**
     * {@link Operator#arity}.
     */
    public static final int ARITY = 2;

    /**
     * Default constuctor.
     */
    public Root() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the root of the given
     * complex number. The first argument is the complex number and the
     * second is the real root power.
     *
     * @param argv complex numbers
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
        Double n = argv[1].real();

        RoundingMode mode = RoundingMode.HALF_UP;

        BigDecimal absn = BigDecimal.valueOf(Math.pow(z.abs(), 1 / n));
        BigDecimal phi = BigDecimal.valueOf(Math.atan2(b, a));
        BigDecimal arg = phi.divide(BigDecimal.valueOf(n), mode);
        BigDecimal sin = BigDecimal.valueOf(Math.sin(arg.doubleValue()));
        BigDecimal cos = BigDecimal.valueOf(Math.cos(arg.doubleValue()));

        return new Complex.ComplexBuilder()
                .real(absn.multiply(cos).doubleValue())
                .imaginary(absn.multiply(sin).doubleValue())
                .build();
    }
}
