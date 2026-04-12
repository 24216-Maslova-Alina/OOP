package ru.nsu.a.maslova1.snake.view;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.a.maslova1.snake.model.Point;
import java.util.ArrayList;

/**
 * Тесты для класса SnakeDraw.
 */
class SnakeDrawTest {

    private SnakeDraw snakeDraw;
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
        snakeDraw = new SnakeDraw(brush);
    }

    /**
     * Проверяет создание объекта.
     */
    @Test
    void testConstructor() {
        assertNotNull(snakeDraw);
    }

    /**
     * Проверяет очистку поля.
     */
    @Test
    void testClearField() {
        assertDoesNotThrow(() -> snakeDraw.clearField());
    }

    /**
     * Проверяет отрисовку пустой змейки.
     */
    @Test
    void testDrawSnakeEmpty() {
        ArrayList<Point> snake = new ArrayList<>();
        assertDoesNotThrow(() -> snakeDraw.drawSnake(snake));
    }

    /**
     * Проверяет отрисовку змейки из одного сегмента.
     */
    @Test
    void testDrawSnakeSingleSegment() {
        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        assertDoesNotThrow(() -> snakeDraw.drawSnake(snake));
    }

    /**
     * Проверяет отрисовку змейки из нескольких сегментов.
     */
    @Test
    void testDrawSnakeMultipleSegments() {
        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        snake.add(new Point(4, 5));
        snake.add(new Point(3, 5));
        assertDoesNotThrow(() -> snakeDraw.drawSnake(snake));
    }

    /**
     * Проверяет последовательность операций.
     */
    @Test
    void testClearAndDraw() {
        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 5));

        assertDoesNotThrow(() -> {
            snakeDraw.clearField();
            snakeDraw.drawSnake(snake);
        });
    }
}