package ru.nsu.kbagryantsev.operations;

import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Division extends Operator {
    /**
     * {@link Operator#arity}.
     */
    public static final int ARITY = 2;

    /**
     * Default constuctor.
     */
    public Division() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the division result of
     * the given complex numbers.
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

        BigDecimal denominator = c.multiply(c).add(d.multiply(d));

        if (argv[1].real() == 0 && argv[1].imaginary() == 0) {
            throw new ArithmeticException();
        }

        RoundingMode mode = RoundingMode.HALF_UP;
        return new Complex.ComplexBuilder()
                .real(
                        (a.multiply(c).add(b.multiply(d)))
                        .divide(denominator, mode).doubleValue())
                .imaginary(
                        (b.multiply(c).subtract(a.multiply(d)))
                        .divide(denominator, mode).doubleValue())
                .build();
    }
}
