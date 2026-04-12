package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Тесты для enum Directions.
 */
class DirectionsTest {

    /**
     * Проверяет значения смещений.
     */
    @Test
    void testDirectionsValues() {
        assertEquals(0, Directions.UP.getPointX());
        assertEquals(-1, Directions.UP.getPointY());
        assertEquals(0, Directions.DOWN.getPointX());
        assertEquals(1, Directions.DOWN.getPointY());
        assertEquals(-1, Directions.LEFT.getPointX());
        assertEquals(0, Directions.LEFT.getPointY());
        assertEquals(1, Directions.RIGHT.getPointX());
        assertEquals(0, Directions.RIGHT.getPointY());
    }

    /**
     * Проверяет противоположные направления.
     */
    @Test
    void testIsOpposite() {
        assertTrue(Directions.UP.isOpposite(Directions.DOWN));
        assertTrue(Directions.DOWN.isOpposite(Directions.UP));
        assertTrue(Directions.LEFT.isOpposite(Directions.RIGHT));
        assertTrue(Directions.RIGHT.isOpposite(Directions.LEFT));
    }

    /**
     * Проверяет не противоположные направления.
     */
    @Test
    void testIsNotOpposite() {
        assertFalse(Directions.UP.isOpposite(Directions.LEFT));
        assertFalse(Directions.UP.isOpposite(Directions.RIGHT));
        assertFalse(Directions.DOWN.isOpposite(Directions.LEFT));
        assertFalse(Directions.LEFT.isOpposite(Directions.UP));
    }

    /**
     * Проверяет количество значений.
     */
    @Test
    void testDirectionsCount() {
        assertEquals(4, Directions.values().length);
    }
}