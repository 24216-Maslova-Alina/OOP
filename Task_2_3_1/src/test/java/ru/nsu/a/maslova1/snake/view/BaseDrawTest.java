package ru.nsu.a.maslova1.snake.view;

import org.junit.jupiter.api.Test;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.a.maslova1.snake.config.GameConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для BaseDraw.
 */
class BaseDrawTest {

    /**
     * Проверяет создание BaseDraw.
     */
    @Test
    void testBaseDrawCreation() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BaseDraw baseDraw = new BaseDraw(gc);

        assertNotNull(baseDraw);
    }

    /**
     * Проверяет вычисление размера клетки при нормальных размерах.
     */
    @Test
    void testGetCellSizeNormal() {
        Canvas canvas = new Canvas(900, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BaseDraw baseDraw = new BaseDraw(gc);

        double cellSize = baseDraw.getCellSize();

        assertEquals(900.0 / GameConfig.COLS, cellSize, 0.01);
    }

    /**
     * Проверяет, что размер клетки использует меньшую сторону холста.
     */
    @Test
    void testGetCellSizeUsesSmallerDimension() {
        Canvas canvas = new Canvas(600, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BaseDraw baseDraw = new BaseDraw(gc);

        double cellSize = baseDraw.getCellSize();

        assertEquals(600.0 / GameConfig.COLS, cellSize, 0.01);
    }

    /**
     * Проверяет возврат значения по умолчанию при неинициализированном холсте.
     */
    @Test
    void testGetCellSizeDefault() {
        Canvas canvas = new Canvas(0, 0);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BaseDraw baseDraw = new BaseDraw(gc);

        double cellSize = baseDraw.getCellSize();

        assertEquals(25.0, cellSize, 0.01);
    }

    /**
     * Проверяет вычисление смещения по X.
     */
    @Test
    void testGetOffsetX() {
        Canvas canvas = new Canvas(1000, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BaseDraw baseDraw = new BaseDraw(gc);

        double cellSize = baseDraw.getCellSize();
        double offsetX = baseDraw.getOffsetX();
        double expected = (1000.0 - cellSize * GameConfig.COLS) / 2;

        assertEquals(expected, offsetX, 0.01);
    }

    /**
     * Проверяет вычисление смещения по Y.
     */
    @Test
    void testGetOffsetY() {
        Canvas canvas = new Canvas(900, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BaseDraw baseDraw = new BaseDraw(gc);

        double cellSize = baseDraw.getCellSize();
        double offsetY = baseDraw.getOffsetY();
        double expected = (1000.0 - cellSize * GameConfig.ROWS) / 2;

        assertEquals(expected, offsetY, 0.01);
    }

    /**
     * Проверяет, что смещение неотрицательное.
     */
    @Test
    void testOffsetsNotNegative() {
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BaseDraw baseDraw = new BaseDraw(gc);

        assertTrue(baseDraw.getOffsetX() >= 0);
        assertTrue(baseDraw.getOffsetY() >= 0);
    }
}