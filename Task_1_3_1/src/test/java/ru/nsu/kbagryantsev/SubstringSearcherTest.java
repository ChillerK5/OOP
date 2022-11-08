package ru.nsu.kbagryantsev;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class SubstringSearcherTest {
    @Test
    void simpleTest() throws IOException {
        SubstringSearcher search = new SubstringSearcher("src/test/resources/test.txt", "aab".toCharArray());
        search.zetFunction();
    }
}