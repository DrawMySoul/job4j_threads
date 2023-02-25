package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenSyncSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] expected = {
            new Sums(6, 12),
            new Sums(15, 15),
            new Sums(24, 18)
        };
        Sums[] actual = RolColSum.sum(matrix);
        assertThat(actual[0]).isNotNull()
            .isEqualTo(expected[0]);
        assertThat(actual[1]).isNotNull()
            .isEqualTo(expected[1]);
        assertThat(actual[2]).isNotNull()
            .isEqualTo(expected[2]);
    }

    @Test
    void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] expected = {
            new Sums(6, 12),
            new Sums(15, 15),
            new Sums(24, 18)
        };
        Sums[] actual = RolColSum.asyncSum(matrix);
        assertThat(actual[0]).isNotNull()
            .isEqualTo(expected[0]);
        assertThat(actual[1]).isNotNull()
            .isEqualTo(expected[1]);
        assertThat(actual[2]).isNotNull()
            .isEqualTo(expected[2]);
    }

    @Test
    void whenGetResultsSyncSumAndAsyncSumThenResultsMustBuEquals() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] syncSum = RolColSum.sum(matrix);
        Sums[] asyncSum = RolColSum.asyncSum(matrix);
        assertThat(syncSum[0]).isEqualTo(asyncSum[0]);
        assertThat(syncSum[1]).isEqualTo(asyncSum[1]);
        assertThat(syncSum[2]).isEqualTo(asyncSum[2]);
    }
}