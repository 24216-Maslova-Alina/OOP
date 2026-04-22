package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса SnakeInit.
 */
class SnakeInitTest {

    private SnakeInit snakeInit;

    /**
     * Создаёт объект перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        snakeInit = new SnakeInit();
    }

    /**
     * Проверяет инициализацию змейки.
     */
    @Test
    void testInitSnake() {
        ArrayList<Point> snake = snakeInit.initSnake();
        assertEquals(2, snake.size());
    }

    /**
     * Проверяет, что голова находится правее хвоста.
     */
    @Test
    void testHeadRightOfTail() {
        ArrayList<Point> snake = snakeInit.initSnake();
        Point head = snake.get(0);
        Point tail = snake.get(1);
        assertEquals(head.getPointX() - 1, tail.getPointX());
        assertEquals(head.getPointY(), tail.getPointY());
    }

    /**
     * Проверяет установку змейки.
     */
    @Test
    void testSetSnake() {
        ArrayList<Point> newSnake = new ArrayList<>();
        newSnake.add(new Point(10, 10));
        snakeInit.setSnake(newSnake);
        assertEquals(newSnake, snakeInit.getSnake());
    }

    /**
     * Проверяет получение змейки.
     */
    @Test
    void testGetSnake() {
        assertNotNull(snakeInit.getSnake());
        assertTrue(snakeInit.getSnake().isEmpty());
    }

    /**
     * Проверяет сброс змейки.
     */
    @Test
    void testReset() {
        snakeInit.initSnake();
        snakeInit.reset();
        assertTrue(snakeInit.getSnake().isEmpty());
    }
}