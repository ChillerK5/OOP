package ru.nsu.kbagryantsev;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * Calculator util class which evaluates algebraic expressions in infix
 * notation.
 */
public class Calculator {
    /**
     * Calculates the result of the given arithmetic expression in prefix form.
     *
     * @param expression String expression
     * @return complex result
     */
    public static Complex calculate(final String expression) {
        //Initializing a stack of Complex values
        Stack<Complex> stack = new Stack<>();
        //Converting to postfix notation
        List<String> operands = Arrays.asList(expression.split(" "));
        Collections.reverse(operands);
        //Instantiating an operator factory
        OperatorFactory factory = new OperatorFactory();
        //Operating parsed Strings
        for (String string: operands) {
            Optional<Operator> operator = factory.get(string);
            //Appliying necessary operator
            if (operator.isPresent()) {
                Operator function = operator.get();
                if (stack.size() >= function.arity) {
                    Complex[] argv = new Complex[function.arity];
                    for (int i = 0; i < function.arity; i++) {
                        argv[i] = stack.pop();
                    }
                    stack.push(function.compute(argv));
                } else {
                    throw new IllegalArgumentException();
                }
                //Pushing parsed Complex value onto the stack
            } else {
                    stack.push(Complex.parseComplex(string));
            }
        }
        if (stack.isEmpty()) {
            throw new IllegalArgumentException();
        }
        //Last value of the stack is the result of the expression
        return stack.pop();
    }
}
