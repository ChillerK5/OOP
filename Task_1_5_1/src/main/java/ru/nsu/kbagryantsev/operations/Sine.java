package ru.nsu.kbagryantsev.operations;

import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

import java.math.BigDecimal;

public class Sine extends Operator {
    /**
     * {@link Operator#arity}.
     */
    public static final int ARITY = 1;

    /**
     * Default constuctor.
     */
    public Sine() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the sine of the given
     * complex number.
     *
     * @param argv complex numbers
     * @return complex result
     */
    public static Complex apply(final Complex... argv) {
        int argc = argv.length;
        if (argc != ARITY) {
            throw new IllegalArgumentException();
        }

        Double x = argv[0].real();
        Double y = argv[0].imaginary();

        BigDecimal sin = BigDecimal.valueOf(Math.sin(x));
        BigDecimal cos = BigDecimal.valueOf(Math.cos(x));
        BigDecimal sinh = BigDecimal.valueOf(Math.sinh(y));
        BigDecimal cosh = BigDecimal.valueOf(Math.cosh(y));

        return new Complex.ComplexBuilder()
                .real(sin.multiply(cosh).doubleValue())
                .imaginary(cos.multiply(sinh).doubleValue())
                .build();
    }
}
