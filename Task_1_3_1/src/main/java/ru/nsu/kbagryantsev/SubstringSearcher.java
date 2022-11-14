package ru.nsu.kbagryantsev;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Searches a relatively small pattern in a large data scope using Z function
 * algorithm.
 */
public final class SubstringSearcher {
    /**
     * BufferedReader instance.
     */
    private final InputStream inputStream;
    /**
     * Substring, which needs to be found.
     */
    private String pattern;
    /**
     * Stores essential amount of read characters.
     */
    private HashMap<Integer, Character> cache;
    /**
     * Contains a number of pattern matched chars from each position.
     */
    private final List<Integer> zetArray;
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
     * @param source        data file name
     * @param substring     pattern to be found
     */
    public SubstringSearcher(final InputStream source, final String substring) {
        inputStream = source;
        pattern = substring;
        zetArray = new ArrayList<>(List.of(0));
        cache = new HashMap<>();
    }

    /**
     * Finds all entries of a given pattern in a string.
     *
     * @return              list of found substrings' indices
     * @throws IOException  buffered reader crashed
     */
    public List<Integer> search() throws IOException {
        ArrayList<Integer> entryIndices = new ArrayList<>();
        pattern = pattern.concat("\0");
        for (int i = 0; i < pattern.length(); i++) {
            cache.put(i, pattern.charAt(i));
        }
        left = 0;
        right = 0;
        constructArrayPrefix();
        constructArraySuffix();
        for (int i = 0; i < zetArray.size(); i++) {
            if (zetArray.get(i) == pattern.length() - 1) {
                entryIndices.add(i - pattern.length());
            }
        }
        inputStream.close();
        return entryIndices;
    }

    private void clearCache(final int i) {
        if (cache.get(i) == null) {
            cache = new HashMap<>();
        } else {
            char c = cache.get(i);
            cache = new HashMap<>();
            cache.put(i, c);
        }
    }

    /**
     * Processing Z array values for a zero-terminating pattern.
     */
    private void constructArrayPrefix() {
        for (int i = 1; i < pattern.length(); i++) {
            if (i > right) {
                left = i;
                right = i;
                int symbol = cache.get(right);
                while (pattern.charAt(right - left) == symbol) {
                    right++;
                    symbol = cache.get(right);
                }
                zetArray.add(i, right - left);
                right--;
            } else {
                int k = i - left;
                if (zetArray.get(k) < right - i + 1) {
                    zetArray.add(i, zetArray.get(k));
                } else {
                    left = i;
                    int symbol = cache.get(right);
                    while (pattern.charAt(right - left) == symbol) {
                        right++;
                        symbol = cache.get(right);
                    }
                    zetArray.add(i, right - left);
                    right--;
                }
            }
        }
    }

    /**
     * Constructs the rest of a Z array.
     *
     * @throws IOException input stream corruption
     */
    private void constructArraySuffix() throws IOException {
        for (int i = pattern.length(); inputStream.available() != 0; i++) {
            if (i > right) {
                left = i;
                right = i;
                clearCache(right);
                zetArray.add(i, sequenceSize());
                right--;
            } else {
                int k = i - left;
                if (zetArray.get(k) < right - i + 1) {
                    zetArray.add(i, zetArray.get(k));
                } else {
                    left = i;
                    zetArray.add(i, sequenceSize());
                    right--;
                }
            }
        }
    }

    /**
     * Gets the next char either from cache or input stream.
     *
     * @param i             char index
     * @return              next char integer value
     * @throws IOException  input stream corruption
     */
    private int nextChar(final int i) throws IOException {
        if (cache.get(i) == null) {
            int inputChar = inputStream.read();
            cache.put(i, (char) inputChar);
            return inputChar;
        } else {
            return cache.get(i);
        }
    }

    private int sequenceSize() throws IOException {
        int symbol = nextChar(right);
        while (symbol != -1 && pattern.charAt(right - left) == symbol) {
            right++;
            symbol = nextChar(right);
        }
        return right - left;
    }
}
