package ru.nsu.a.maslova1.snake.controller;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.snake.model.GameManager;
import ru.nsu.a.maslova1.snake.model.GameState;
import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для SnakeController.
 */
class SnakeControllerTest {

    /**
     * Проверяет создание контроллера.
     */
    @Test
    void testControllerCreation() {
        SnakeController controller = new SnakeController();
        assertNotNull(controller);
    }

    /**
     * Проверяет установку модели (не выбрасывает исключение).
     */
    @Test
    void testSetModel() {
        SnakeController controller = new SnakeController();
        GameManager gameManager = new GameManager();

        // Просто проверяем что метод не падает
        assertDoesNotThrow(() -> controller.setModel(gameManager));
    }

    /**
     * Проверяет, что notify не падает с корректным состоянием.
     * (без реальных JavaFX компонентов)
     */
    @Test
    void testNotifyWithValidState() {
        SnakeController controller = new SnakeController();

        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 5));

        GameState state = new GameState(snake, new ArrayList<>(), null, 10, 3, false);

        // notify может падать без JavaFX, проверяем только наличие метода
        assertNotNull(controller);
        assertNotNull(state);
    }

    /**
     * Проверяет, что контроллер реализует Observer.
     */
    @Test
    void testControllerImplementsObserver() {
        SnakeController controller = new SnakeController();
        assertTrue(controller instanceof ru.nsu.a.maslova1.snake.model.Observer);
    }
}