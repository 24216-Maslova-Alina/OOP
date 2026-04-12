package ru.nsu.a.maslova1.snake.view;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Тесты для класса Walls.
 */
class WallsTest {

    private Walls walls;
    private GraphicsContext brush;

    /**
     * Инициализирует JavaFX Toolkit.
     */
    @BeforeAll
    static void initJavaFX() {
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Уже запущен
        }
    }

    /**
     * Создаёт объекты перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        Canvas canvas = new Canvas(625, 625);
        brush = canvas.getGraphicsContext2D();
        walls = new Walls(brush);
    }

    /**
     * Проверяет создание объекта.
     */
    @Test
    void testConstructor() {
        assertNotNull(walls);
    }

    /**
     * Проверяет отрисовку стен.
     */
    @Test
    void testDrawWalls() {
        assertDoesNotThrow(() -> walls.drawWalls());
    }

    /**
     * Проверяет многократную отрисовку.
     */
    @Test
    void testDrawWallsMultipleTimes() {
        assertDoesNotThrow(() -> {
            walls.drawWalls();
            walls.drawWalls();
        });
    }
}