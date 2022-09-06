package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

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
                () -> {
                    int sum = 0;

                    for (int i = 0; i < matrix[num].length; i++) {
                        sum += matrix[num][i];
                    }
                    Sums sums = new Sums();
                    sums.setRowSum(sum);
                    sum = 0;
                    for (int i = 0; i < matrix.length; i++) {
                        sum += matrix[i][num];
                    }
                    sums.setColSum(sum);
                    return sums;
                }
        );
    }

    public static Sums[] simpleSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
               sum += matrix[i][j];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(sum);
        }
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrix[j][i];
            }
            sums[i].setColSum(sum);
        }
        return sums;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = asyncSum(matrix);

        for (Sums i : sums) {
            System.out.println("column " + i.colSum);
            System.out.println("row " + i.rowSum);
            System.out.println();
        }
    }
}
