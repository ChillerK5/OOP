package ru.nsu.kbagryantsev;

import java.math.BigDecimal;
import org.jetbrains.annotations.NotNull;

/**
 * Complex numbers representation. Contains real and imaginary parts as Double
 * coefficient values in algebraic form. Supports cast to default types only if
 * imaginary part
 * is equal to zero.
 */
public class Complex extends Number {
    /**
     * Real part of complex number.
     */
    private final Double real;
    /**
     * Imaginary part of complex number.
     */
    private final Double imaginary;

    /**
     * Instantiates a complex number via its builder instance.
     * See {@link ComplexBuilder}
     *
     * @param builder ComplexBuilder instance
     */
    public Complex(final @NotNull ComplexBuilder builder) {
        if (builder.real == null || builder.imaginary == null) {
            throw new IllegalArgumentException();
        }
        this.real = builder.real;
        this.imaginary = builder.imaginary;
    }

    /**
     * Calculates absolute value of a complex number.
     *
     * @return Double absolute value
     */
    public Double abs() {
        BigDecimal r = BigDecimal.valueOf(this.real);
        BigDecimal i = BigDecimal.valueOf(this.imaginary);
        return Math.sqrt(
                r.multiply(r).add(i.multiply(i)).doubleValue());
    }

    /**
     * Gets complex number's imaginary part as a coefficient of
     * <strong><i>i</i></strong>.
     *
     * @return Double imaginary value
     */
    public Double imaginary() {
        return imaginary;
    }

    /**
     * Gets the real part of a complex number.
     *
     * @return Double real part
     */
    public Double real() {
        return real;
    }

    /**
     * Checks whether instance of complex number is actually real. This
     * implies that its imaginary part equals to zero.
     *
     * @return true if it's real
     */
    public boolean isReal() {
        return imaginary == 0.0;
    }

    /**
     * Parses given string into a complex number. Evaluates either pure real
     * numbers or pure complex numbers with no real part. Coefficient may
     * contain any floating number. Imaginary values must end with an
     * <strong><i>i</i></strong>.
     *
     * @param string string complex number
     * @return Complex instance
     */
    public static Complex parseComplex(final String string) {
        final int semiCircle = 180;
        ComplexBuilder builder = new ComplexBuilder();
        if (string.endsWith("i")) {
            String imaginaryValue = string.replace('i', '\0');
            builder.imaginary(Double.parseDouble(imaginaryValue));
        } else if (string.endsWith("'")) {
            String realValue = string.replace('\'', '\0');
            builder.real(Double.parseDouble(realValue) * Math.PI / semiCircle);
        } else {
            builder.real(Double.parseDouble(string));
        }
        return builder.build();
    }

    /**
     * Builder for a {@link Complex} number. If none of the fields were
     * specified builds a zero Complex number.
     */
    public static class ComplexBuilder {
        /**
         * Complex real part.
         */
        protected Double real;
        /**
         * Complex imaginary part.
         */
        protected Double imaginary;

        /**
         * Instantiates a builder.
         */
        public ComplexBuilder() {
            super();
        }

        /**
         * Adds real part.
         *
         * @param real Complex real part
         * @return this instance for method chaining
         */
        public ComplexBuilder real(final Double real) {
            this.real = real;
            return this;
        }

        /**
         * Adds imaginary part.
         *
         * @param imaginary Complex imaginary part
         * @return this instance for method chaining
         */
        public ComplexBuilder imaginary(final Double imaginary) {
            this.imaginary = imaginary;
            return this;
        }

        /**
         * Builds a Complex instance based on given arguments.
         *
         * @return Complex instance
         */
        public Complex build() {
            real = real == null ? 0.0 : real;
            imaginary = imaginary == null ? 0.0 : imaginary;
            return new Complex(this);
        }
    }

    /**
     * Checks for the equality of Complex numbers by their real and imaginary
     * part values. References Double::equals.
     *
     * @param o other Complex number
     * @return true if equals
     */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Complex compared)) {
            throw new ClassCastException();
        }
        return compared.real.equals(this.real)
                && compared.imaginary.equals(this.imaginary);
    }

    /**
     * Stringifies Complex number. The result matches expression
     * <strong>(Double)([+-](Double)i)?|(Double)?([+-](Double)i)</strong>.
     *
     * @return String instance
     */
    @Override
    public String toString() {
        if (real == 0.0) {
            return imaginary + "i";
        } else if (imaginary == 0.0) {
            return real.toString();
        } else {
            return real + (imaginary > 0 ? "+" : "") + imaginary + "i";
        }
    }

    @Override
    public final int intValue() {
        if (this.isReal()) {
            return real.intValue();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public final long longValue() {
        if (this.isReal()) {
            return real.longValue();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public final float floatValue() {
        if (this.isReal()) {
            return real.floatValue();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public final double doubleValue() {
        if (this.isReal()) {
            return real;
        }
        throw new UnsupportedOperationException();
    }
}
