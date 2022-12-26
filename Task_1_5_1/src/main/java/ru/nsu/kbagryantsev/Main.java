package ru.nsu.kbagryantsev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader console = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(console);
        String expression = input.readLine();
        System.out.println(Calculator.calculate(expression));
    }
}
