package ru.nsu.kbagryantsev.operations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.nsu.kbagryantsev.Complex;
import ru.nsu.kbagryantsev.Operator;

/**
 * {@link Operator}.
 */
public class Logarithm extends Operator {
    /**
     * {@link Operator#arity}.
     */
    public static final int ARITY = 2;

    /**
     * Default constuctor.
     */
    public Logarithm() {
        arity = ARITY;
    }

    @Override
    public final Complex compute(final Complex... argv) {
        return apply(argv);
    }

    /**
     * {@link Operator#compute(Complex...)}. Calculates the logarithm of
     * the given complex numbers which are actually real. The first argument is
     * the base and the second argument is the logarithmable. Complex
     * logarithm does not support such transformation.
     *
     * @param argv complex numbers
     * @return complex result
     */
    public static Complex apply(final Complex... argv) {
        int argc = argv.length;
        if (argc != ARITY) {
            throw new IllegalArgumentException();
        }

        Complex a = argv[0];
        Complex b = argv[1];

        if (!a.isReal() || !b.isReal()) {
            throw new IllegalArgumentException();
        }

        BigDecimal lnB = BigDecimal.valueOf(NaturalLogarithm.apply(b).real());
        BigDecimal lnA = BigDecimal.valueOf(NaturalLogarithm.apply(a).real());

        RoundingMode mode = RoundingMode.HALF_UP;
        return new Complex.ComplexBuilder()
                .real(lnB.divide(lnA, mode).doubleValue())
                .build();
    }
}
