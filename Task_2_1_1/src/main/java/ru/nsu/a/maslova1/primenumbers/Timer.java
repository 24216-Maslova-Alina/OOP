package ru.nsu.a.maslova1.primenumbers;

/**
 * Класс для измерения времени выполнения операций.
 * Используется для сравнения производительности различных реализаций.
 */
public class Timer {
    private long time;

    /**
     * Конструктор таймера.
     */
    public Timer() {
        this.time = 0;
    }

    /**
     * Начинает отсчет времени.
     */
    public void start() {
        time = System.currentTimeMillis();
    }

    /**
     * Завершает отсчет времени и возвращает прошедшее время.
     *
     * @return прошедшее время в миллисекундах
     */
    public long end() {
        return System.currentTimeMillis() - time;
    }
}