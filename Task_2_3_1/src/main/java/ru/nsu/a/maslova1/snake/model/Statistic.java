package ru.nsu.a.maslova1.snake.model;

public class Statistic {
    private Eat eat;
    private int resultBest = 0;

    public Statistic(Eat eat) {
        this.eat = eat;
    }

    public int getBestResult() {
        int resultCurrent = eat.countingScore();
        if (resultBest < resultCurrent) {
            resultBest = resultCurrent;
        }
        return resultBest;
    }
}