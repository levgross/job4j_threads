package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RowColSumTest {
    @Test
    public void whenSimpleSum() {
        int[][] matrix = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = RowColSum.simpleSum(matrix);
        int colSum = 0;
        for (Sums s : sums) {
            colSum += s.getColSum();
        }
        int rowSum = 0;
        for (Sums s : sums) {
            rowSum += s.getRowSum();
        }
        assertThat(colSum).isEqualTo(45);
        assertThat(rowSum).isEqualTo(45);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = RowColSum.asyncSum(matrix);
        int colSum = 0;
        for (Sums s : sums) {
            colSum += s.getColSum();
        }
        int rowSum = 0;
        for (Sums s : sums) {
            rowSum += s.getRowSum();
        }
        assertThat(colSum).isEqualTo(45);
        assertThat(rowSum).isEqualTo(45);
    }
}