package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
       public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < n; i++) {
            futures.put(i, getSum(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getSum(int[][] matrix, int num) {
        return CompletableFuture.supplyAsync(
                () -> getRowColSums(matrix, num));
    }

    public static Sums[] simpleSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = getRowColSums(matrix, i);
        }
        return sums;
    }

    private static Sums getRowColSums(int[][] matrix, int num) {
        int n = matrix.length;
        int raw = 0;
        int column = 0;
        for (int i = 0; i < n; i++) {
            raw += matrix[num][i];
            column += matrix[i][num];
        }
        Sums sums = new Sums();
        sums.setRowSum(raw);
        sums.setColSum(column);
        return sums;
    }
}
