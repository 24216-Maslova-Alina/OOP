package ru.nsu.a.maslova1.snake.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.a.maslova1.snake.model.Directions;

/**
 * Тесты для класса GameManager.
 */
class GameManagerTest {

    private GameManager gameManager;

    /**
     * Инициализирует JavaFX Toolkit перед всеми тестами.
     */
    @BeforeAll
    static void initJavaFX() {
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // JavaFX уже запущен
        }
    }

    /**
     * Создаёт GameManager перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        Canvas canvas = new Canvas(625, 625);
        GraphicsContext brush = canvas.getGraphicsContext2D();
        gameManager = new GameManager(brush);
    }

    /**
     * Проверяет, что игра не запущена сразу после создания.
     */
    @Test
    void testInitialStateNotRunning() {
        assertFalse(gameManager.isGameRunning());
    }

    /**
     * Проверяет запуск игры.
     */
    @Test
    void testStartGame() {
        gameManager.startGame();
        assertTrue(gameManager.isGameRunning());
    }

    /**
     * Проверяет начальный счёт.
     */
    @Test
    void testInitialScore() {
        assertEquals(2, gameManager.getScore());
    }

    /**
     * Проверяет начальную длину змейки.
     */
    @Test
    void testInitialLength() {
        gameManager.startGame();
        assertEquals(2, gameManager.getLength());
    }

    /**
     * Проверяет установку направления движения.
     */
    @Test
    void testSetDirection() {
        gameManager.startGame();
        gameManager.setDirection(Directions.UP);
        assertTrue(gameManager.isGameRunning());
    }

    /**
     * Проверяет, что пауза не влияет на неактивную игру.
     */
    @Test
    void testTogglePauseWhenNotStarted() {
        gameManager.togglePause();
        assertFalse(gameManager.isGameRunning());
    }

    /**
     * Проверяет корректность повторного запуска игры.
     */
    @Test
    void testMultipleStarts() {
        gameManager.startGame();
        gameManager.startGame();
        assertTrue(gameManager.isGameRunning());
    }

    /**
     * Проверяет, что лучший результат не отрицательный.
     */
    @Test
    void testBestScoreNotNegative() {
        assertTrue(gameManager.getBestScore() >= 0);
    }
}