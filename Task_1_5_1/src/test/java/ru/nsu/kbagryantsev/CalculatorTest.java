package ru.nsu.kbagryantsev;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.nsu.kbagryantsev.operations.Addition;
import ru.nsu.kbagryantsev.operations.Cosine;
import ru.nsu.kbagryantsev.operations.Division;
import ru.nsu.kbagryantsev.operations.Logarithm;
import ru.nsu.kbagryantsev.operations.Multiplication;
import ru.nsu.kbagryantsev.operations.NaturalLogarithm;
import ru.nsu.kbagryantsev.operations.Power;
import ru.nsu.kbagryantsev.operations.Root;
import ru.nsu.kbagryantsev.operations.Sine;
import ru.nsu.kbagryantsev.operations.Subtraction;

class CalculatorTest {
    /**
     * Complex comparison accuracy.
     */
    static final Double ULP = 0.0001;

    static boolean equals(final Complex expected, final Complex actual) {
        return Math.abs(actual.real() - expected.real()) < ULP
                && Math.abs(actual.imaginary() - expected.imaginary()) < ULP;
    }

    @Nested
    @DisplayName("Addition test")
    class AdditionTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealAddition() {
            Complex a = new Complex.ComplexBuilder().real(5.0).build();
            Complex b = new Complex.ComplexBuilder().real(5.0).build();

            Complex sample = Addition.apply(a, b);
            Complex model = new Complex.ComplexBuilder().real(10.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginaryAddition() {
            Complex a = new Complex.ComplexBuilder()
                    .real(5.0)
                    .imaginary(5.0)
                    .build();
            Complex b = new Complex.ComplexBuilder()
                    .real(5.0)
                    .imaginary(5.0)
                    .build();

            Complex sample = Addition.apply(a, b);
            Complex model = new Complex.ComplexBuilder()
                    .real(10.0)
                    .imaginary(10.0)
                    .build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Subtraction test")
    class SubtractionTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealSubtraction() {
            Complex a = new Complex.ComplexBuilder().real(5.0).build();
            Complex b = new Complex.ComplexBuilder().real(5.0).build();

            Complex sample = Subtraction.apply(a, b);
            Complex model = new Complex.ComplexBuilder().real(0.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginarySubtraction() {
            Complex a = new Complex.ComplexBuilder()
                    .real(5.0)
                    .imaginary(5.0)
                    .build();
            Complex b = new Complex.ComplexBuilder()
                    .real(5.0)
                    .imaginary(4.0)
                    .build();

            Complex sample = Subtraction.apply(a, b);
            Complex model = new Complex.ComplexBuilder()
                    .real(0.0)
                    .imaginary(1.0)
                    .build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Multiplication test")
    class MultiplicationTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealMultiplication() {
            Complex a = new Complex.ComplexBuilder().real(5.0).build();
            Complex b = new Complex.ComplexBuilder().real(-5.0).build();

            Complex sample = Multiplication.apply(a, b);
            Complex model = new Complex.ComplexBuilder().real(-25.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginaryMultiplication() {
            Complex a = new Complex.ComplexBuilder()
                    .real(1.0)
                    .imaginary(5.0)
                    .build();
            Complex b = new Complex.ComplexBuilder()
                    .real(2.0)
                    .imaginary(4.0)
                    .build();

            Complex sample = Multiplication.apply(a, b);
            Complex model = new Complex.ComplexBuilder()
                    .real(-18.0)
                    .imaginary(14.0)
                    .build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Division test")
    class DivisionTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealDivision() {
            Complex a = new Complex.ComplexBuilder().real(5.0).build();
            Complex b = new Complex.ComplexBuilder().real(-5.0).build();

            Complex sample = Division.apply(a, b);
            Complex model = new Complex.ComplexBuilder().real(-1.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginaryDivision() {
            Complex a = new Complex.ComplexBuilder()
                    .real(-1.0)
                    .imaginary(5.0)
                    .build();
            Complex b = new Complex.ComplexBuilder()
                    .real(2.0)
                    .imaginary(-4.0)
                    .build();

            Complex sample = Division.apply(a, b);
            Complex model = new Complex.ComplexBuilder()
                    .real(-1.1)
                    .imaginary(0.3)
                    .build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Sine test")
    class SineTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealSine() {
            Complex a = new Complex.ComplexBuilder().real(Math.PI / 6).build();

            Complex sample = Sine.apply(a);
            Complex model = new Complex.ComplexBuilder().real(0.5).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginarySine() {
            Complex a = new Complex.ComplexBuilder()
                    .real(1.0)
                    .imaginary(Math.PI / 2)
                    .build();

            Complex sample = Sine.apply(a);
            Complex model = new Complex.ComplexBuilder()
                    .real(2.11140088552062)
                    .imaginary(1.24339710340216).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Cosine test")
    class CosineTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealCosine() {
            Complex a = new Complex.ComplexBuilder().real(Math.PI / 3).build();

            Complex sample = Cosine.apply(a);
            Complex model = new Complex.ComplexBuilder().real(0.5).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginaryCosine() {
            Complex a = new Complex.ComplexBuilder()
                    .real(1.0)
                    .imaginary(Math.PI / 2).build();

            Complex sample = Cosine.apply(a);
            Complex model = new Complex.ComplexBuilder()
                    .real(1.35571491781655)
                    .imaginary(1.93647625316722).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Power test")
    class PowerTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealPower() {
            Complex a = new Complex.ComplexBuilder().real(5.0).build();
            Complex b = new Complex.ComplexBuilder().real(4.0).build();

            Complex sample = Power.apply(a, b);
            Complex model = new Complex.ComplexBuilder().real(625.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginaryPower() {
            Complex a = new Complex.ComplexBuilder()
                    .real(7.0)
                    .imaginary(1.0)
                    .build();
            Complex b = new Complex.ComplexBuilder()
                    .real(3.0)
                    .build();

            Complex sample = Power.apply(a, b);
            Complex model = new Complex.ComplexBuilder()
                    .real(322.0)
                    .imaginary(146.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Square root test")
    class RootTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealSquareRoot() {
            Complex a = new Complex.ComplexBuilder().real(625.0).build();
            Complex b = new Complex.ComplexBuilder().real(2.0).build();

            Complex sample = Root.apply(a, b);
            Complex model = new Complex.ComplexBuilder().real(25.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginarySquareRoot() {
            Complex a = new Complex.ComplexBuilder()
                    .real(7.0)
                    .imaginary(1.0)
                    .build();
            Complex b = new Complex.ComplexBuilder()
                    .real(3.0)
                    .build();

            Complex sample = Root.apply(a, b);
            Complex model = new Complex.ComplexBuilder()
                    .real(1.91723648470389)
                    .imaginary(0.090751089510652).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Natural logarithm test")
    class NaturalLogarithmTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealNaturalLogarithm() {
            Complex a = new Complex.ComplexBuilder().real(5.0).build();

            Complex sample = NaturalLogarithm.apply(a);
            Complex model = new Complex.ComplexBuilder()
                    .real(1.60943791243410)
                    .build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }

        @Test
        @DisplayName("Complex numbers")
        void simpleImaginaryNaturalLogarithm() {
            Complex a = new Complex.ComplexBuilder()
                    .real(5.0)
                    .imaginary(1.0)
                    .build();

            Complex sample = NaturalLogarithm.apply(a);
            Complex model = new Complex.ComplexBuilder()
                    .real(1.62904826901074)
                    .imaginary(0.197395559835422).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Nested
    @DisplayName("Logarithm test")
    class LogarithmTest {
        @Test
        @DisplayName("Real numbers")
        void simpleRealLogarithm() {
            Complex a = new Complex.ComplexBuilder().real(2.0).build();
            Complex b = new Complex.ComplexBuilder().real(32.0).build();

            Complex sample = Logarithm.apply(a, b);
            Complex model = new Complex.ComplexBuilder().real(5.0).build();

            Assertions.assertTrue(CalculatorTest.equals(model, sample));
        }
    }

    @Test
    @DisplayName("Wrong arity")
    void wrongArity() {
        Complex a = new Complex.ComplexBuilder().real(0.0).build();
        Complex b = new Complex.ComplexBuilder().real(0.0).build();
        Complex z = new Complex.ComplexBuilder().imaginary(1.0).build();

        Class<? extends Throwable> argument = IllegalArgumentException.class;
        Class<? extends Throwable> arithmetic = ArithmeticException.class;
        Assertions.assertThrows(argument, () -> Addition.apply(a));
        Assertions.assertThrows(argument, () -> Cosine.apply(a, b));
        Assertions.assertThrows(argument, () -> Division.apply(a));
        Assertions.assertThrows(arithmetic, () -> Division.apply(a, b));
        Assertions.assertThrows(argument, () -> Logarithm.apply(a));
        Assertions.assertThrows(argument, () -> Logarithm.apply(z, z));
        Assertions.assertThrows(argument, () -> Multiplication.apply(a));
        Assertions.assertThrows(argument, () -> NaturalLogarithm.apply(a, b));
        Assertions.assertThrows(arithmetic, () -> NaturalLogarithm.apply(a));
        Assertions.assertThrows(argument, () -> Power.apply(a));
        Assertions.assertThrows(argument, () -> Power.apply(a, z));
        Assertions.assertThrows(argument, () -> Root.apply(a));
        Assertions.assertThrows(argument, () -> Sine.apply(a, b));
        Assertions.assertThrows(argument, () -> Subtraction.apply(a));
    }

    @Test
    @DisplayName("Simple real arithmetics")
    void simpleRealArithmetics() throws IOException {
        String s = "+ - / * 7.8 9.1 * 3.6 5.4 0.7 0.65\0";

        final InputStream systemInput = System.in;
        final PrintStream systemOutput = System.out;
        ByteArrayInputStream testInput = new ByteArrayInputStream(s.getBytes());
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        System.setIn(testInput);
        System.setOut(new PrintStream(testOutput));

        Main.main(new String[]{""});
        String result = testOutput.toString();
        Complex sample = Complex.parseComplex(result);
        Complex model = new Complex.ComplexBuilder()
                .real(3.60123456)
                .build();

        Assertions.assertTrue(equals(model, sample));

        System.setIn(systemInput);
        System.setOut(systemOutput);
    }

    @Test
    @DisplayName("Simple trigonometry")
    void simpleRealTrigonometry() {
        String expression = "+ sin 30' cos - 60' 60'";

        Complex sample = Calculator.calculate(expression);
        Complex model = new Complex.ComplexBuilder()
                .real(1.5)
                .build();

        Assertions.assertTrue(equals(model, sample));
    }

    @Test
    @DisplayName("Complicated complex arithmetics")
    void complicatedComplexArithmetics() {
        String expression = "ln root pow 2i 4 2";

        Complex sample = Calculator.calculate(expression);
        Complex model = new Complex.ComplexBuilder()
                .real(1.38629436)
                .build();

        Assertions.assertTrue(equals(model, sample));
    }

    @Test
    @DisplayName("Complicated complex arithmetics")
    void complicatedComplexArithmetics2() {
        String expression = "+ log sin / 3.14159265358 6 32 cos 2i";

        Complex sample = Calculator.calculate(expression);
        Complex model = new Complex.ComplexBuilder()
                .real(-1.2378043)
                .build();

        Assertions.assertTrue(equals(model, sample));
    }

    @Test
    @DisplayName("Illegal expression")
    void illegalExpression() {
        String expression = "+ - / * 7.8 9.1 * 3.6 5.4 0.7";

        Class<? extends Throwable> arg = IllegalArgumentException.class;
        Assertions.assertThrows(arg, () -> Calculator.calculate(expression));
        Assertions.assertThrows(arg, () -> Calculator.calculate(" "));
    }

    @Test
    @DisplayName("Complex class tests")
    void complexTests() {
        Complex.ComplexBuilder builder =  new Complex.ComplexBuilder();

        Class<? extends Throwable> argument = IllegalArgumentException.class;
        Assertions.assertThrows(argument, () -> new Complex(builder));

        Complex a = new Complex.ComplexBuilder().real(5.0).build();
        Complex b = new Complex.ComplexBuilder().real(5.0).build();
        Assertions.assertEquals(a, b);

        Character c = '$';
        Class<? extends Throwable> cast = ClassCastException.class;
        //noinspection ResultOfMethodCallIgnored,EqualsBetweenInconvertibleTypes
        Assertions.assertThrows(cast, () -> a.equals(c));

        Complex z = new Complex.ComplexBuilder()
                .real(1.0)
                .imaginary(1.0)
                .build();
        Assertions.assertEquals("1.0+1.0i", z.toString());
        Complex r = new Complex.ComplexBuilder()
                .real(1.0)
                .build();
        Assertions.assertEquals("1.0", r.toString());
        Complex i = new Complex.ComplexBuilder()
                .imaginary(1.0)
                .build();
        Assertions.assertEquals("1.0i", i.toString());

        Class<? extends Throwable> unsupported;
        unsupported = UnsupportedOperationException.class;
        Assertions.assertEquals(1, r.intValue());
        Assertions.assertEquals(1, r.longValue());
        Assertions.assertEquals(1.0, r.floatValue());
        Assertions.assertEquals(1.0, r.doubleValue());
        Assertions.assertThrows(unsupported, i::intValue);
        Assertions.assertThrows(unsupported, i::longValue);
        Assertions.assertThrows(unsupported, i::floatValue);
        Assertions.assertThrows(unsupported, i::doubleValue);
    }

    @Test
    @DisplayName("Adding new operator test")
    void addOperator() {
        OperatorFactory factory = new OperatorFactory();

        Operator signum = new Signum();
        factory.addOperator("sgn", signum);

        Optional<Operator> returnedOperator = factory.get("sgn");
        Assertions.assertTrue(returnedOperator.isPresent());
    }
}
