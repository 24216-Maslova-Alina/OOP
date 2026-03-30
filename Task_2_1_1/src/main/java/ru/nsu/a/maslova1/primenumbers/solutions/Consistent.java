package ru.nsu.a.maslova1.primenumbers.solutions;

/**
 * Последовательная реализация проверки наличия не простых чисел в массиве.
 * Проверяет каждый элемент массива по порядку.
 */
public class Consistent {
    PrimeNumbers prime = new PrimeNumbers();

    /**
     * Проверяет, содержит ли массив хотя бы одно не простое число.
     *
     * @param arr массив чисел для проверки
     * @param len длина массива
     * @return true если найдено хотя бы одно не простое число, иначе false
     */
    public boolean hasNotPrimeNumber(int[] arr, int len) {
        for (int i = 0; i < len; i++) {
            if (!prime.isPrime(arr[i])) {
                return true;
            }
        }
        return false;
    }
}