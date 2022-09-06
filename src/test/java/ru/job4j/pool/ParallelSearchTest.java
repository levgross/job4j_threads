package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pool.ParallelSearch.searchIndex;

class ParallelSearchTest {
    @Test
    public void whenIntSearch() {
        Integer[] array = new Integer[40];
        int num = 40;
        for (int i = 0; i < array.length; i++) {
            array[i] = num--;
        }
        int index = searchIndex(array, 39);
        assertThat(index).isEqualTo(1);
    }

    @Test
    public void whenArrayLessThen10IntSearch() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5};
        int index = searchIndex(array, 3);
        assertThat(index).isEqualTo(2);
    }

    @Test
    public void whenIntSearchLastElem() {
        Integer[] array = new Integer[15];
        int num = 15;
        for (int i = 0; i < array.length; i++) {
            array[i] = num--;
        }
        int index = searchIndex(array, 1);
        assertThat(index).isEqualTo(14);
    }

    @Test
    public void whenStringSearch() {
        String[] array = new String[] {
                "zero",
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten",
                "eleven"
        };
        int index = searchIndex(array, "two");
        assertThat(index).isEqualTo(2);
    }
}