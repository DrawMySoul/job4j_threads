package ru.job4j.pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        var sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(
                getRowSum(matrix[i]),
                getColSum(i, matrix)
            );
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        var sums = new Sums[matrix.length];
        var futures = new HashMap<Integer, CompletableFuture<Sums>>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(i, matrix));
        }

        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int index, int[][] matrix) {
        return CompletableFuture.supplyAsync(
            () -> new Sums(
                getRowSum(matrix[index]),
                getColSum(index, matrix)
            )
        );
    }

    private static int getRowSum(int[] data) {
        return Arrays.stream(data).sum();
    }

    private static int getColSum(int index, int[][] matrix) {
        return Arrays.stream(matrix).mapToInt(row -> row[index]).sum();
    }
}
