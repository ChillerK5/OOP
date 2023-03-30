package ru.nsu.kbagryantsev.workers.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a class which executes operations which may be treated as
 * Producer-Consumer pattern reference. Class must implement operation,
 * which fetches some data from a shared data structure.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {
}
