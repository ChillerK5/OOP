package ru.nsu.kbagryantsev;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class SubstringSearcherTest {
    @Test
    void simpleTest() throws IOException {
        String filePath = "src/test/resources/test.txt";
        String pattern = "aab";
        SubstringSearcher search;
        search = new SubstringSearcher(filePath, pattern.toCharArray());
        search.zetFunction();
    }
}
