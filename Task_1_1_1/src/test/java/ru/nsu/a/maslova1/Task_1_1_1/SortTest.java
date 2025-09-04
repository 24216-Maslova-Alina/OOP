package ru.nsu.a.maslova1.Task_1_1_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    @Test
    void sort_SimplestArray() {
        int[] array = new int[]{1, 3, 2};
        var result = Sort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3}, result);
    }

    @Test
    void sort_SortedWithDuplicates() {
        int[] array = new int[]{2, 4, 5, 4, 9, 8, 8};
        var result = Sort.sort(array);
        assertArrayEquals(new int[]{2, 4, 4, 5, 8, 8, 9}, result);
    }

    @Test
    void  sort_SortOneNumber(){
        int[] array = new int[]{32};
        var result = Sort.sort(array);
        assertArrayEquals(new int[]{32}, result);
    }

    @Test
    void sort_NegativeNumbers(){
        int[] array = new int[]{-3, -2, -567, 12, 32, 0, -42};
        var result = Sort.sort(array);
        assertArrayEquals(new int[]{-567, -42, -3, -2, 0, 12, 32}, result);
    }

    @Test
    void sort_SameNumbers(){
        int[] array = new int[]{1, 1, 1, 1, 1};
        var result = Sort.sort(array);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, result);
    }
}