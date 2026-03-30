package ru.nsu.a.maslova1.primenumbers.solutions;

import java.util.Arrays;

/**
 * Параллельная реализация проверки наличия не простых чисел в массиве
 * с использованием параллельных стримов Java.
 */
public class ParallelStream {
    private final PrimeNumbers prime = new PrimeNumbers();

    /**
     * Проверяет, содержит ли массив хотя бы одно не простое число
     * с использованием параллельных стримов.
     *
     * @param arr массив чисел для проверки
     * @return true если найдено хотя бы одно не простое число, иначе false
     */
    public boolean hasNotPrimeParallelStream(int[] arr) {
        return Arrays.stream(arr)
                .parallel()
                .anyMatch(n -> !prime.isPrime(n));
    }
}