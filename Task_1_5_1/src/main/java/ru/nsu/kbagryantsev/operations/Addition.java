package ru.nsu.kbagryantsev.operations;

import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

import java.math.BigDecimal;

public class Addition extends Operator {
    /**
     * {@link Operator#arity}.
     */
    private static final int ARITY = 2;

    /**
     * Default constuctor.
     */
    public Addition() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the sum of the given
     * complex numbers.
     *
     * @param argv complex numbers
     * @return complex result
     */
    public static Complex apply(final Complex... argv) {
        int argc = argv.length;
        if (argc != ARITY) {
            throw new IllegalArgumentException();
        }

        BigDecimal a = BigDecimal.valueOf(argv[0].real());
        BigDecimal b = BigDecimal.valueOf(argv[0].imaginary());
        BigDecimal c = BigDecimal.valueOf(argv[1].real());
        BigDecimal d = BigDecimal.valueOf(argv[1].imaginary());

        return new Complex.ComplexBuilder()
                .real(a.add(c).doubleValue())
                .imaginary(b.add(d).doubleValue())
                .build();
    }
}
