package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Тесты для класса Eat.
 */
class EatTest {

    private Eat eat;
    private ArrayList<Point> apples;
    private AppleLogic logic;

    /**
     * Создаёт объекты перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        eat = new Eat();
        apples = new ArrayList<>();
        logic = new AppleLogic();
    }

    /**
     * Проверяет начальный счёт.
     */
    @Test
    void testInitialScore() {
        assertEquals(2, eat.countingScore());
    }

    /**
     * Проверяет сброс счёта.
     */
    @Test
    void testResetStore() {
        eat.resetStore();
        assertEquals(2, eat.countingScore());
    }

    /**
     * Проверяет поедание обычного яблока.
     */
    @Test
    void testCollisionAppleNormal() {
        Point head = new Point(5, 5);
        apples.add(new Point(5, 5));
        assertTrue(eat.collisionApple(head, apples, logic));
        assertEquals(3, eat.countingScore());
        assertTrue(apples.isEmpty());
    }

    /**
     * Проверяет отсутствие столкновения с яблоком.
     */
    @Test
    void testCollisionAppleFalse() {
        Point head = new Point(5, 5);
        apples.add(new Point(10, 10));
        assertFalse(eat.collisionApple(head, apples, logic));
        assertEquals(2, eat.countingScore());
        assertEquals(1, apples.size());
    }

    /**
     * Проверяет подсчёт очков.
     */
    @Test
    void testCountingScore() {
        assertEquals(2, eat.countingScore());
    }
}