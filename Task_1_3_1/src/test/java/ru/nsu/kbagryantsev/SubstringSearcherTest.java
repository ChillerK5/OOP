package ru.nsu.kbagryantsev;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubstringSearcherTest {
    SubstringSearcher initSearch(final String path, final String pattern)
            throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(path);
        return new SubstringSearcher(inputStream, pattern);
    }

    @Test
    @DisplayName("Single occurence of a distinct substring")
    void simplestTest() throws IOException {
        SubstringSearcher search;
        search = initSearch("src/test/resources/test-1.txt", "dad");
        Object[] model = {2};
        Object[] test = new ArrayList<>(search.search()).toArray();
        Assertions.assertArrayEquals(model, test);
    }

    @Test
    @DisplayName("Multiple occurencies of a distinct substring")
    void multipleOccurencies() throws IOException {
        SubstringSearcher search;
        search = initSearch("src/test/resources/test-2.txt", "dog");
        Object[] model = {3, 9, 12};
        Object[] test = new ArrayList<>(search.search()).toArray();
        Assertions.assertArrayEquals(model, test);
    }

    @Test
    @DisplayName("Substring in a block of its prefixes")
    void mimicString() throws IOException {
        SubstringSearcher search;
        search = initSearch("src/test/resources/test-3.txt", "cumbersome");
        Object[] model = {6};
        Object[] test = new ArrayList<>(search.search()).toArray();
        Assertions.assertArrayEquals(model, test);
    }

    @Test
    @DisplayName("Substring with suffix equal to its prefix")
    void selfRepeating() throws IOException {
        SubstringSearcher search;
        search = initSearch("src/test/resources/test-4.txt", "ahaha");
        Object[] model = {0, 2, 4};
        Object[] test = new ArrayList<>(search.search()).toArray();
        Assertions.assertArrayEquals(model, test);
    }
}
