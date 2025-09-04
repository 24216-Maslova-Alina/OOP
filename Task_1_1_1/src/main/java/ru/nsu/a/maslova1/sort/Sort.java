package ru.nsu.a.maslova1.sort;

/**
 * Класс для сортировки массива.
 * Сортирует массив с помощью алгоритма heapsort.
 */

public class Sort {
    /**
     * Основной этап сортировки.
     *
     * @param array массив.
     * @return отсортированный масссив.
     */

    public static int[] sort(int[] array) {
        int n = array.length;

        for(int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0);
        }

        return array;
    }

    /**
     * Преобразование поддерева в двоичную кучу.
     * Изменяет дерево так, чтобы родительский элемент был больше дочерних.
     */

    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        if (right < n && array[right] > array[largest]) {
            largest = right;
        }

        if(largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest);
        }
    }
    /**
     * Меняем местами два значения.
     */

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}