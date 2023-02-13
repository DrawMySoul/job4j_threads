package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {

    @Test
    void whenSmallArray() {
        Integer[] array = {57, 34, 94, 17, 61, 94, 42, 9, 91, 85};
        int element = 17;
        assertThat(ParallelIndexSearch.search(array, element)).isEqualTo(3);
    }

    @Test
    void whenLargeArray() {
        Integer[] array = {10, 7, 43, 72, 5, 6, 62, 74, 20, 14, 57, 99, 39, 75, 39, 2, 60, 56, 84, 24};
        int element = 14;
        assertThat(ParallelIndexSearch.search(array, element)).isEqualTo(9);
    }

    @Test
    void whenDifferentDataType() {
        Object[] array = {13, 'y', true, "job4j", 1.0, 4, 'n', false, "java", 1.1};
        String element = "job4j";
        assertThat(ParallelIndexSearch.search(array, element)).isEqualTo(3);
    }

    @Test
    void whenElementDidNotFound() {
        Object[] array = {13, 'y', true, "job4j", 1.0, 4, 'n', false, "java", 1.1, 'e', "junior"};
        String element = "middle";
        assertThat(ParallelIndexSearch.search(array, element)).isEqualTo(-1);
    }
}