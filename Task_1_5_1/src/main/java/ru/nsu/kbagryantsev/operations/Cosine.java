package ru.nsu.kbagryantsev.operations;

import java.math.BigDecimal;
import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

/**
 * {@link Operator}.
 */
public class Cosine extends Operator {
    /**
     * {@link Operator#arity}.
     */
    public static final int ARITY = 1;

    /**
     * Default constuctor.
     */
    public Cosine() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the cosine of the given
     * complex number.
     *
     * @param argv complex number
     * @return complex result
     */
    public static Complex apply(final Complex... argv) {
        int argc = argv.length;
        if (argc != ARITY) {
            throw new IllegalArgumentException();
        }

        Double a = argv[0].real();
        Double b = argv[0].imaginary();

        BigDecimal cos = BigDecimal.valueOf(Math.cos(a));
        BigDecimal sin = BigDecimal.valueOf(Math.sin(a));
        BigDecimal cosh = BigDecimal.valueOf(Math.cosh(b));
        BigDecimal sinh = BigDecimal.valueOf(Math.sinh(b));

        return new Complex.ComplexBuilder()
                .real(cos.multiply(cosh).doubleValue())
                .imaginary(sin.multiply(sinh).doubleValue())
                .build();
    }
}
