package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Point.
 */
class PointTest {

    /**
     * Проверяет создание точки.
     */
    @Test
    void testConstructor () {
        Point point = new Point (5, 10);
        assertEquals (5, point.getPointX ());
        assertEquals (10, point.getPointY ());
    }

    /**
     * Проверяет равенство одинаковых точек.
     */
    @Test
    void testEqualsSameCoordinates () {
        Point p1 = new Point (5, 10);
        Point p2 = new Point (5, 10);
        assertEquals (p1, p2);
    }

    /**
     * Проверяет неравенство разных точек.
     */
    @Test
    void testEqualsDifferentCoordinates () {
        Point p1 = new Point (5, 10);
        Point p2 = new Point (10, 5);
        assertNotEquals (p1, p2);
    }

    /**
     * Проверяет равенство с самим собой.
     */
    @Test
    void testEqualsSameObject () {
        Point p = new Point (5, 10);
        assertEquals (p, p);
    }

    /**
     * Проверяет неравенство с null.
     */
    @Test
    void testEqualsNull () {
        Point p = new Point (5, 10);
        assertNotEquals (null, p);
    }

    /**
     * Проверяет неравенство с объектом другого класса.
     */
    @Test
    void testEqualsDifferentClass () {
        Point p = new Point (5, 10);
        assertNotEquals ("not a point", p);
    }

    /**
     * Проверяет геттеры.
     */
    @Test
    void testGetters () {
        Point p = new Point (7, 3);
        assertEquals (7, p.getPointX ());
        assertEquals (3, p.getPointY ());
    }

    /**
     * Проверяет отрицательные координаты.
     */
    @Test
    void testNegativeCoordinates () {
        Point p = new Point (- 5, - 10);
        assertEquals (- 5, p.getPointX ());
        assertEquals (- 10, p.getPointY ());
    }
}