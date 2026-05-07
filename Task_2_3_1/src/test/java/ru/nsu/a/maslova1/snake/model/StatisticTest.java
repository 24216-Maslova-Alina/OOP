package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Statistic.
 */
class StatisticTest {

    private Statistic statistic;
    private Eat eat;

    /**
     * Создаёт объекты перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        eat = new Eat();
        statistic = new Statistic(eat);
    }

    /**
     * Проверяет начальный лучший результат.
     */
    @Test
    void testGetBestResultInitial() {
        assertEquals(2, statistic.getBestResult());
    }

    /**
     * Проверяет обновление лучшего результата.
     */
    @Test
    void testGetBestResultUpdates() {
        assertEquals(2, statistic.getBestResult());

        // Увеличиваем счёт через поедание яблок
        ArrayList<Point> apples = new ArrayList<>();
        AppleLogic logic = new AppleLogic();
        Point head = new Point(5, 5);
        apples.add(new Point(5, 5));
        eat.collisionApple(head, apples, logic);

        assertEquals(3, statistic.getBestResult());
    }

    /**
     * Проверяет, что лучший результат не уменьшается.
     */
    @Test
    void testBestResultNeverDecreases() {
        statistic.getBestResult();
        int best = statistic.getBestResult();

        eat.resetStore();

        assertEquals(best, statistic.getBestResult());
    }

    /**
     * Проверяет многократный вызов.
     */
    @Test
    void testMultipleCalls() {
        assertEquals(2, statistic.getBestResult());
        assertEquals(2, statistic.getBestResult());
        assertEquals(2, statistic.getBestResult());
    }
}