package ru.nsu.kbagryantsev;

public class Heapsort {
    public static void sort(int[] data) {
        int size = data.length;

        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(data, size, i);
        }

        for (int i = size - 1; i >= 0; i--) {
            int temp = data[i];
            data[i] = data[0];
            data[0] = temp;

            heapify(data, i, 0);
        }
    }

    private static void heapify(int[] data, int size, int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int tracked = index;

        if (left < size && data[left] > data[tracked]) {
            tracked = left;
        }
        if (right < size && data[right] > data[tracked]) {
            tracked = right;
        }
        if (tracked != index) {
            int temp = data[tracked];
            data[tracked] = data[index];
            data[index] = temp;

            heapify(data, size, tracked);
        }
    }
}
