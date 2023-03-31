package ru.nsu.kbagryantsev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    void givenMain_thenInvokeMain() {
        Assertions.assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
