package ru.nsu.kbagryantsev.workers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a class which implements methods which may be treated as
 * Producer-Consumer pattern reference. Class must implement produce method,
 * which adds some data into a shared data structure.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Producer {
}
