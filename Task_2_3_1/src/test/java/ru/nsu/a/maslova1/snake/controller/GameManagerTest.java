package ru.nsu.a.maslova1.snake.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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
    private static boolean javafxAvailable = false;

    /**
     * Пытается инициализировать JavaFX Toolkit.
     */
    @BeforeAll
    static void initJavaFX() {
        try {
            System.setProperty("java.awt.headless", "false");
            javafx.application.Platform.startup(() -> {});
            javafxAvailable = true;
        } catch (Exception e) {
            javafxAvailable = false;
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
     * Проверяет запуск игры (только если JavaFX доступен).
     */
    @Test
    void testStartGame() {
        assumeTrue(javafxAvailable, "JavaFX не доступен, тест пропущен");
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
     * Проверяет начальную длину змейки (только если JavaFX доступен).
     */
    @Test
    void testInitialLength() {
        assumeTrue(javafxAvailable, "JavaFX не доступен, тест пропущен");
        gameManager.startGame();
        assertEquals(2, gameManager.getLength());
    }

    /**
     * Проверяет установку направления движения (только если JavaFX доступен).
     */
    @Test
    void testSetDirection() {
        assumeTrue(javafxAvailable, "JavaFX не доступен, тест пропущен");
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
     * Проверяет остановку неактивной игры.
     */
    @Test
    void testStopGameWhenNotStarted() {
        gameManager.stopGame();
        assertFalse(gameManager.isGameRunning());
    }

    /**
     * Проверяет, что лучший результат не отрицательный.
     */
    @Test
    void testBestScoreNotNegative() {
        assertTrue(gameManager.getBestScore() >= 0);
    }

    /**
     * Проверяет геттеры без запуска игры.
     */
    @Test
    void testGettersDoNotThrowException() {
        assertDoesNotThrow(() -> {
            gameManager.getScore();
            gameManager.getLength();
            gameManager.getBestScore();
        });
    }
}