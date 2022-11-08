package ru.nsu.kbagryantsev;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Searches a relatively small pattern in a large data scope using Z function
 * algorithm.
 */
public final class SubstringSearcher {
    /**
     * BufferedReader instance.
     */
    private final BufferedReader bufferedReader;
    /**
     * String to be found in data.
     */
    private final char[] pattern;

    private final ArrayList<Character> stringConcat;

    private final HashMap<Integer, Character> cache;

    /**
     * Initialises a buffered reader and a pattern related fields.
     *
     * @param fileName  data file name
     * @param substring pattern to be found
     * @throws FileNotFoundException is thrown, if a file does not exist
     */
    public SubstringSearcher(final String fileName, final char[] substring)
            throws FileNotFoundException {
        //Initializing a pattern and definition of a buffer size
        pattern = substring;
        stringConcat = new ArrayList<>();
        for (char c : substring) {
            stringConcat.add(c);
        }
        stringConcat.add('#');
        cache = new HashMap<>();
        //Instantiating a buffered reader
        FileReader fileReader = new FileReader(fileName);
        bufferedReader = new BufferedReader(fileReader);
    }

    public void zetFunction() throws IOException {
        ArrayList<Integer> zetArray = constructZetArray();
        int length = zetArray.size();
        for (int i = 0; i < length; ++i) {
            if (zetArray.get(i) == pattern.length) {
                System.out.println("Pattern found at index "
                        + (i - pattern.length - 1));
            }
        }
    }

    private int getNextChar(final int i) throws IOException {
        if (cache.get(i) == null) {
            int inputChar = bufferedReader.read();
            cache.put(i, (char) inputChar);
            return inputChar;
        } else {
            return cache.get(i);
        }
    }

    public ArrayList<Integer> constructZetArray() throws IOException {
        ArrayList<Integer> zetArray = new ArrayList<>();
        zetArray.add(0);
        for (int i = 0; i < pattern.length + 1; i++) {
            cache.put(i, stringConcat.get(i));
        }

        int left = 0;
        int right = 0;

        int symbol = 0;
        char symChar = (char) symbol;
        for (int i = 1; symbol != -1; ++i) {
            if (i > right) {

                left = right = i;

                symbol = getNextChar(i);
                symChar = (char) symbol;
                while (symbol != -1
                        && stringConcat.get(right - left) == symbol) {
                    right++;
                    symbol = getNextChar(right);
                    symChar = (char) symbol;
                }

                zetArray.add(i, right - left);
                right--;
            } else {
                int k = i - left;

                if (zetArray.get(k) < right - i + 1) {
                    zetArray.add(i, zetArray.get(k));
                } else {
                    left = i;
                    symbol = getNextChar(i);
                    symChar = (char) symbol;
                    while (symbol != -1
                            && stringConcat.get(right - left) == symbol) {
                        right++;
                    }

                    zetArray.add(i, right - left);
                    right--;
                }
            }
        }
        return zetArray;
    }
}
