package ru.job4j.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;
    private final int fromIndex;
    private final int toIndex;

    public ParallelSearch(T[] array, T value, int fromIndex, int toIndex) {
        this.array = array;
        this.value = value;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected Integer compute() {
        int result = -1;

        if (toIndex - fromIndex <= 10) {
            for (int i = fromIndex; i < toIndex; i++) {
                if (value.equals(array[i])) {
                    result = i;
                    break;
                }
            }
            return result;
        }

        int midIndex = (fromIndex + toIndex) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, value, fromIndex, midIndex);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, value, midIndex + 1, toIndex);

        leftSearch.fork();
        rightSearch.fork();

        int left = leftSearch.join();
        int right = rightSearch.join();

        if (left != -1) {
            return left;
        }
        if (right != -1) {
            return right;
        }

        return result;
    }

    public int searchIndex() {
        ForkJoinPool fjp = new ForkJoinPool();
        return fjp.invoke(new ParallelSearch<>(array, value, 0, array.length - 1));
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[40];
        int num = 20;
        for (int i = 0; i < array.length; i++) {
            array[i] = num--;
        }
        ParallelSearch<Integer> ps = new ParallelSearch<>(array, -10, 0, array.length - 1);
        int index = ps.searchIndex();
        System.out.println(index);

    }
}
