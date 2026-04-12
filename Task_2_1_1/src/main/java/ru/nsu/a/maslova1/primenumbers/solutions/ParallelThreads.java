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
    private static volatile boolean foundGlobal = false; // Общий флаг для всех потоков

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
     * Останавливается, если другой поток уже нашел составное число.
     */
    @Override
    public void run() {
        for (int i = start; i < len && !foundGlobal; i += step) {
            if (!prime.isPrime(arr[i])) {
                foundPrime = true;
                foundGlobal = true; // Сигнализируем остальным потокам остановиться
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
        // Сбрасываем глобальный флаг перед началом проверки
        foundGlobal = false;

        if (count > len) {
            count = len;
        }

        this.arr = arr;

        ParallelThreads[] threads = new ParallelThreads[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new ParallelThreads(arr, i, count, len);
        }

        // Запускаем все потоки
        for (ParallelThreads thread : threads) {
            thread.start();
        }

        boolean result = false;

        // Ждем завершения всех потоков
        for (ParallelThreads thread : threads) {
            try {
                thread.join();
                if (thread.getResult()) {
                    result = true;
                    // Прерываем остальные потоки, если они еще работают
                    for (ParallelThreads t : threads) {
                        if (t != thread && t.isAlive()) {
                            t.interrupt();
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return result; // Возвращаем то, что уже нашли
            }
        }
        return result;
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