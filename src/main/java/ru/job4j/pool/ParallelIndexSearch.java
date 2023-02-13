package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private static final int MAX_SIZE = 10;
    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public ParallelIndexSearch(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if (to - from <= MAX_SIZE) {
            return linealSearch();
        }
        int mid = (from + to) / 2;
        var leftSearch = new ParallelIndexSearch<>(array, from, mid, element);
        var rightSearch = new ParallelIndexSearch<>(array, mid + 1, to, element);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    private int linealSearch() {
        return IntStream.range(from, to)
            .filter(i -> array[i] == element)
            .findFirst().orElse(-1);
    }

    public static <T> int search(T[] array, T element) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, 0, array.length, element));
    }
}
