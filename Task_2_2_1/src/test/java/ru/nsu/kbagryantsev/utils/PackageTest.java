package ru.nsu.kbagryantsev.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Package tests.
 */
public class PackageTest {
    @Test
    void givenSomeData_thenReturnDefaultPackage() {
        Assertions.assertDoesNotThrow(() -> new Package(new Object()));
        Assertions.assertDoesNotThrow(() -> new Package(Package.Header.CONTINUE, new Object()));
    }

    @Test
    void givenTerminationPackage_thenCheckPackage() {
        Assertions.assertTrue(Package.terminating().isTerminating());
        Assertions.assertTrue(new Package(Package.Header.TERMINATE,
                new Object()).isTerminating());
    }
}
