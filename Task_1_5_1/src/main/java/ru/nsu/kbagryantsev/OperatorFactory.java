package ru.nsu.kbagryantsev;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

/**
 * Operators factory. Returns an operator instance by given synonym.
 */
public class OperatorFactory {
    /**
     * Maps operator name to an {@link Operator} instance.
     */
    private final Map<String, Operator> factory;

    /**
     * Instantiates a factory with default algebraic functions.
     */
    public OperatorFactory() {
        factory = new HashMap<>();
        factory.put("+", new Addition());
        factory.put("cos", new Cosine());
        factory.put("/", new Division());
        factory.put("log", new Logarithm());
        factory.put("*", new Multiplication());
        factory.put("ln", new NaturalLogarithm());
        factory.put("pow", new Power());
        factory.put("sin", new Sine());
        factory.put("root", new Root());
        factory.put("-", new Subtraction());
    }

    /**
     * Gets an opeartor instance by its name. If none of the operators match
     * given name return null.
     *
     * @param operatorName operator name
     * @return Optional Operator instance
     */
    public final Optional<Operator> get(final String operatorName) {
        return Optional.ofNullable(factory.get(operatorName));
    }

    /**
     * Adds custom operators, which implement {@link Operator} contract.
     *
     * @param synonym operator name
     * @param operator Operator isntance
     */
    public final void addOperator(final String synonym,
                                  final Operator operator) {
        factory.put(synonym, operator);
    }
}
