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
 * Тесты для класса AppleDraw.
 */
class AppleDrawTest {

    private AppleDraw appleDraw;
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
        appleDraw = new AppleDraw(brush);
    }

    /**
     * Проверяет создание объекта.
     */
    @Test
    void testConstructor() {
        assertNotNull(appleDraw);
    }

    /**
     * Проверяет отрисовку без яблок.
     */
    @Test
    void testDrawAppleEmpty() {
        ArrayList<Point> apples = new ArrayList<>();
        assertDoesNotThrow(() -> appleDraw.drawApple(apples, null));
    }

    /**
     * Проверяет отрисовку обычных яблок.
     */
    @Test
    void testDrawAppleNormal() {
        ArrayList<Point> apples = new ArrayList<>();
        apples.add(new Point(5, 5));
        apples.add(new Point(10, 10));
        assertDoesNotThrow(() -> appleDraw.drawApple(apples, null));
    }

    /**
     * Проверяет отрисовку золотого яблока.
     */
    @Test
    void testDrawAppleGolden() {
        ArrayList<Point> apples = new ArrayList<>();
        Point golden = new Point(7, 7);
        assertDoesNotThrow(() -> appleDraw.drawApple(apples, golden));
    }

    /**
     * Проверяет отрисовку обычных и золотого яблока вместе.
     */
    @Test
    void testDrawAppleBoth() {
        ArrayList<Point> apples = new ArrayList<>();
        apples.add(new Point(5, 5));
        Point golden = new Point(7, 7);
        assertDoesNotThrow(() -> appleDraw.drawApple(apples, golden));
    }
}