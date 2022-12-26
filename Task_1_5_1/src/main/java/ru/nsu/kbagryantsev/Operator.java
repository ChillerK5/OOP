package ru.nsu.kbagryantsev;

/**
 * Abstract n-ary algebraic operator for Complex numbers.
 */
public abstract class Operator {
    /**
     * Arity of an operator.
     */
    protected int arity;

    /**
     * Main operational method, which must take exactly as many arguments as
     * its arity is.
     *
     * @param argv arguments of an operator
     * @return result as a Complex number
     */
    public abstract Complex compute(Complex... argv);
}
