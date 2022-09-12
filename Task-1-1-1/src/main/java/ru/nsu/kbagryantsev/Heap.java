package ru.nsu.kbagryantsev;

public class Heap{
    public static void sort(int[] arr){
        int size = arr.length;

        for (int i = size / 2 - 1; i >= 0; i--){
            heapify(arr, size, i);
        }

        for (int i = size - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    static void heapify(int[] data, int size, int i){
        int root = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && data[left] > data[root]){
            root = left;
        }

        if (right < size && data[right] > data[root]){
            root = right;
        }

        if (root != i) {
            int temp = data[i];
            data[i] = data[root];
            data[root] = temp;

            heapify(data, size, root);
        }
    }
}
