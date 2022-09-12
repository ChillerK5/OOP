package ru.nsu.kbagryantsev;

public class Main {
    public static void main(String[] args){
        int[] data = new int[]{1, 6, 3, 6, 5, 4, 2, 7, 9, 11};
        Heap.sort(data);
        for (int i : data) {
            System.out.print(i + " ");
        }
    }
}