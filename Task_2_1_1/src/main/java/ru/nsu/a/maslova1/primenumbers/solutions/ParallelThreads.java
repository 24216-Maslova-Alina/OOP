package ru.nsu.a.maslova1.primenumbers.solutions;

/**
 * Параллельная реализация проверки наличия не простых чисел в массиве
 * с использованием потоков (Thread).
 */
public class ParallelThreads extends Thread {
    private int[] arr;
    private final int start;
    private final int step;
    private final int len;
    private boolean foundPrime = false;

    /**
     * Конструктор по умолчанию.
     */
    public ParallelThreads() {
        this.start = 0;
        this.step = 0;
        this.len = 0;
        this.arr = null;
    }

    /**
     * Конструктор с параметрами для работы потока.
     *
     * @param arr массив чисел для проверки
     * @param start начальный индекс для потока
     * @param step шаг между проверяемыми индексами
     * @param len длина массива
     */
    public ParallelThreads(int[] arr, int start, int step, int len) {
        this.arr = arr;
        this.start = start;
        this.step = step;
        this.len = len;
    }

    PrimeNumbers prime = new PrimeNumbers();

    /**
     * Метод выполнения потока.
     * Проверяет числа в массиве с заданным шагом.
     */
    @Override
    public void run() {
        for(int i = start; i < len; i += step) {
            if (!prime.isPrime(arr[i])){
                foundPrime = true;
                break;
            }
        }
    }

    /**
     * Проверяет, содержит ли массив хотя бы одно не простое число
     * с использованием указанного количества потоков.
     *
     * @param arr массив чисел для проверки
     * @param len длина массива
     * @param count количество потоков для использования
     * @return true если найдено хотя бы одно не простое число, иначе false
     */
    public boolean hasNotPrimeThreads(int[] arr, int len, int count) {
        if (count > len) {
            count = len;
        }

        this.arr = arr;

        ParallelThreads[] threads = new ParallelThreads[count];
        for (int i = 0; i < count; i++){
            threads[i] = new ParallelThreads(arr, i, count, len);
        }

        for (ParallelThreads thread : threads) {
            thread.start();
        }

        for (ParallelThreads thread : threads) {
            try {
                thread.join();
                if (thread.getResult()) {
                    return true;
                }
            } catch (InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Получает результат проверки потока.
     *
     * @return true если поток нашел не простое число, иначе false
     */
    private boolean getResult() {
        return foundPrime;
    }
}