package ru.nsu.kbagryantsev;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches a relatively small pattern in a large data scope using Z function
 * algorithm.
 */
public final class SubstringSearcher {
    /**
     * Input stream of a text to search substring in.
     */
    private final InputStream inputStream;
    /**
     * Searched substring.
     */
    private final String pattern;
    /**
     * Stores essential amount of read characters.
     */
    private final List<Character> cache;
    /**
     * Stores the position at which the cache started to expand.
     */
    private int cacheMark;
    /**
     * Last read input stream symbol.
     */
    private int symbol;
    /**
     * Left border of a substring matching a pattern.
     */
    private int left;
    /**
     * Rightmost border of a substring matching a pattern.
     */
    private int right;

    /**
     * Initialises a buffered reader and pattern related fields.
     *
     * @param text      data file name
     * @param substring pattern to be found
     */
    public SubstringSearcher(final InputStream text, final String substring) {
        //Terminate a pattern with a symbol which will never be met in text
        pattern = substring.concat("#");
        //Construct a sequence stream from both pattern and given text
        ByteArrayInputStream patternStream;
        patternStream = new ByteArrayInputStream(pattern.getBytes());
        inputStream = new SequenceInputStream(patternStream, text);
        //Init cache
        cache = new ArrayList<>();
        cacheMark = 0;
    }

    /**
     * Finds all entries of a given pattern in a string.
     *
     * @return list of found substrings' entryIndices
     * @throws IOException buffered reader crashed
     */
    public List<Integer> search() throws IOException {
        left = 0;
        right = 0;
        List<Integer> entryIndices = zetFunction();
        inputStream.close();
        return entryIndices;
    }

    /**
     * Converts a global sequence cell index into a respective cache cell index.
     *
     * @param i sequence stream index
     * @return index of a cache data cell
     */
    private int cacheIndex(final int i) {
        return i - cacheMark;
    }

    /**
     * Clears cache to prevent memory limit exceedings.
     * Also stores last read symbol, so that it could be accessed again.
     *
     * @param i index of a last read character
     */
    private void clearCache(final int i) {
        if (cacheIndex(i) >= cache.size()) {
            cache.clear();
        } else {
            char c = cache.get(cacheIndex(i));
            cache.clear();
            cache.add(0, c);
        }
        cacheMark = i;
    }

    /**
     * Searches a substring in a text stream. No text related data is stored, so
     * this method does not rely on input data size.
     *
     * @return list of substring occurences' indices
     * @throws IOException input stream is corrupted
     */
    private List<Integer> zetFunction() throws IOException {
        List<Integer> entryIndices = new ArrayList<>();
        List<Integer> zetArray = new ArrayList<>();
        zetArray.add(0);
        symbol = inputStream.read();
        for (int i = 1; symbol != -1; i++) {
            if (i > right) {
                left = i;
                right = i;
                clearCache(right);
                int sequenceSize = sequenceSize();
                if (i < pattern.length()) {
                    zetArray.add(i, sequenceSize);
                } else if (sequenceSize == pattern.length() - 1) {
                    entryIndices.add(i - pattern.length());
                }
                right--;
            } else {
                int k = i - left;
                if (zetArray.get(k) < right - i + 1) {
                    if (i < pattern.length()) {
                        zetArray.add(i, zetArray.get(k));
                    }
                } else {
                    left = i;
                    int sequenceSize = sequenceSize();
                    if (i < pattern.length()) {
                        zetArray.add(i, sequenceSize);
                    } else if (sequenceSize == pattern.length() - 1) {
                        entryIndices.add(i - pattern.length());
                    }
                    right--;
                }
            }
        }
        return entryIndices;
    }

    /**
     * Gets the next character. If there is cached one returns it,
     * otherwise reads the next character from an input stream.
     *
     * @param i char index
     * @return next char integer value
     * @throws IOException unable to read from a stream
     */
    private int nextChar(final int i) throws IOException {
        if (i >= cache.size()) {
            int inputChar = inputStream.read();
            cache.add(i, (char) inputChar);
            return inputChar;
        } else {
            return cache.get(i);
        }
    }

    /**
     * Gets the amount of characters next to the current matching the pattern.
     *
     * @return amount of characters met
     * @throws IOException unable to read from a stream
     */
    private int sequenceSize() throws IOException {
        symbol = nextChar(cacheIndex(right));
        while (symbol != -1 && pattern.charAt(right - left) == symbol) {
            right++;
            symbol = nextChar(cacheIndex(right));
        }
        return right - left;
    }
}
