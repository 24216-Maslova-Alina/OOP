package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Тесты для класса AppleLogic.
 */
class AppleLogicTest {

    private AppleLogic appleLogic;
    private ArrayList<Point> snake;

    /**
     * Создаёт объекты перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        appleLogic = new AppleLogic();
        snake = new ArrayList<>();
        snake.add(new Point(12, 12));
    }

    /**
     * Проверяет сброс яблок.
     */
    @Test
    void testReset() {
        appleLogic.generateApples(snake);
        appleLogic.reset();
        assertTrue(appleLogic.getApples().isEmpty());
        assertNull(appleLogic.getGoldApple());
    }

    /**
     * Проверяет генерацию обычных яблок.
     */
    @Test
    void testGenerateApplesCreatesApples() {
        appleLogic.generateApples(snake);
        assertEquals(4, appleLogic.getApples().size());
    }

    /**
     * Проверяет, что яблоки не появляются на змейке.
     */
    @Test
    void testApplesNotOnSnake() {
        appleLogic.generateApples(snake);
        for (Point apple : appleLogic.getApples()) {
            assertFalse(snake.contains(apple));
        }
    }

    /**
     * Проверяет удаление золотого яблока.
     */
    @Test
    void testRemoveGoldApple() {
        appleLogic.generateApples(snake);
        appleLogic.removeGoldApple();
        assertNull(appleLogic.getGoldApple());
    }

    /**
     * Проверяет, что getGoldApple возвращает null изначально.
     */
    @Test
    void testGetGoldAppleInitiallyNull() {
        assertNull(appleLogic.getGoldApple());
    }
}