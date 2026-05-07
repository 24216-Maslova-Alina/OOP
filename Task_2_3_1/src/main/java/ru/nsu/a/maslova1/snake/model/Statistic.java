package ru.nsu.a.maslova1.snake.model;

/**
 * Хранение и обновление лучшего результата игры.
 */
public class Statistic {
    private Eat eat;
    private int resultBest = 0;

    /**
     * Создаёт объект статистики.
     *
     * @param eat счётчик очков
     */
    public Statistic(Eat eat) {
        this.eat = eat;
    }

    /**
     * Возвращает лучший результат, обновляя его при необходимости.
     *
     * @return лучший счёт за игру
     */
    public int getBestResult() {
        int resultCurrent = eat.countingScore();
        if (resultBest < resultCurrent) {
            resultBest = resultCurrent;
        }
        return resultBest;
    }
}