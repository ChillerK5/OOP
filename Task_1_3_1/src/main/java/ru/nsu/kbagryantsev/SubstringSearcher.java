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
public class SubstringSearcher {
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
    public SubstringSearcher(String fileName, char[] substring)
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

    private int getNextChar(int i) throws IOException {
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

        int L = 0, R = 0;

        int symbol = 0;
        char symChar = (char) symbol;
        for (int i = 1; symbol != -1; ++i) {
            if (i > R) {

                L = R = i;

                symbol = getNextChar(i);
                symChar = (char) symbol;
                while (symbol != -1 && stringConcat.get(R - L) == symbol) {
                    R++;
                    symbol = getNextChar(R);
                    symChar = (char) symbol;
                }

                zetArray.add(i, R - L);
                R--;
            } else {
                int k = i - L;

                if (zetArray.get(k) < R - i + 1) {
                    zetArray.add(i, zetArray.get(k));
                } else {
                    L = i;
                    symbol = getNextChar(i);
                    symChar = (char) symbol;
                    while (symbol != -1 && stringConcat.get(R - L) == symbol) {
                        R++;
                    }

                    zetArray.add(i, R - L);
                    R--;
                }
            }
        }
        return zetArray;
    }
}