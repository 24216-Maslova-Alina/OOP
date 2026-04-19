package ru.nsu.a.maslova1.snake.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для GameState.
 */
class GameStateTest {

    /**
     * Проверяет создание GameState и корректность геттеров.
     */
    @Test
    void testGameStateCreation() {
        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        snake.add(new Point(4, 5));

        ArrayList<Point> apples = new ArrayList<>();
        apples.add(new Point(10, 10));

        Point goldApple = new Point(15, 15);

        GameState state = new GameState(snake, apples, goldApple, 10, 3, false);

        assertEquals(snake, state.getSnake());
        assertEquals(apples, state.getApples());
        assertEquals(goldApple, state.getGoldApple());
        assertEquals(10, state.getScore());
        assertEquals(3, state.getLength());
        assertFalse(state.isGameOver());
    }

    /**
     * Проверяет состояние с null вместо золотого яблока.
     */
    @Test
    void testGameStateWithoutGoldApple() {
        GameState state = new GameState(new ArrayList<>(), new ArrayList<>(), null, 0, 0, false);

        assertNull(state.getGoldApple());
        assertFalse(state.isGameOver());
    }

    /**
     * Проверяет состояние окончания игры.
     */
    @Test
    void testGameOverState() {
        GameState state = new GameState(new ArrayList<>(), new ArrayList<>(), null, 5, 2, true);

        assertTrue(state.isGameOver());
    }

    /**
     * Проверяет пустые списки змейки и яблок.
     */
    @Test
    void testEmptySnakeAndApples() {
        GameState state = new GameState(new ArrayList<>(), new ArrayList<>(), null, 0, 0, false);

        assertTrue(state.getSnake().isEmpty());
        assertTrue(state.getApples().isEmpty());
    }
}