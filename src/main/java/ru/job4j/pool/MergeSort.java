package ru.job4j.pool;

public class MergeSort {

    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    private static int[] sort(int[] array, int from, int to) {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;
        return merge(
                sort(array, from, mid),
                sort(array, mid + 1, to)
        );
    }

    public static int[] merge(int[] left, int[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resIndex = 0;
        int[] result = new int[left.length + right.length];
        while (resIndex != result.length) {
            if (leftIndex == left.length) {
                result[resIndex++] = right[rightIndex++];
            } else if (rightIndex == right.length) {
                result[resIndex++] = left[leftIndex++];
            } else if (left[leftIndex] <= right[rightIndex]) {
                result[resIndex++] = left[leftIndex++];
            } else {
                result[resIndex++] = right[rightIndex++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = new int[40];
        int num = 20;
        for (int i = 0; i < array.length; i++) {
            array[i] = num--;
        }
        int[] sortedArray = sort(array);
        for (int i : sortedArray) {
            System.out.println(i);
        }
    }
}
