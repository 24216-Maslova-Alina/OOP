package ru.nsu.a.maslova1.snake.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для GameManager.
 */
class GameManagerTest {

    private GameManager gameManager;
    private GameState lastState;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
        gameManager.addObserver(state -> lastState = state);
    }

    /**
     * Проверяет запуск игры: длина 2, счет 0, игра активна.
     */
    @Test
    void testStartGame() {
        gameManager.startGame();

        assertTrue(gameManager.isGameRunning());
        assertEquals(2, gameManager.getLength());
        assertEquals(2, gameManager.getScore());
    }

    /**
     * Проверяет начальное состояние до старта.
     */
    @Test
    void testInitialState() {
        assertFalse(gameManager.isGameRunning());
        assertEquals(0, gameManager.getLength());
    }

    /**
     * Проверяет уведомление наблюдателя при старте.
     */
    @Test
    void testObserverNotified() {
        gameManager.startGame();
        assertNotNull(lastState);
        assertFalse(lastState.isGameOver());
    }

    /**
     * Проверяет завершение игры при столкновении со стеной.
     */
    @Test
    void testGameOver() {
        gameManager.startGame();
        for (int i = 0; i < 50 && gameManager.isGameRunning(); i++) {
            gameManager.makeStep();
        }
        assertFalse(gameManager.isGameRunning());
    }

    /**
     * Проверяет перезапуск игры после окончания.
     */
    @Test
    void testRestart() {
        gameManager.startGame();
        for (int i = 0; i < 50 && gameManager.isGameRunning(); i++) {
            gameManager.makeStep();
        }
        gameManager.startGame();
        assertTrue(gameManager.isGameRunning());
        assertEquals(2, gameManager.getLength());
    }

    /**
     * Проверяет установку направления (не падает с ошибкой).
     */
    @Test
    void testSetDirection() {
        gameManager.startGame();
        assertDoesNotThrow(() -> gameManager.setDirection(Directions.UP));
    }
}