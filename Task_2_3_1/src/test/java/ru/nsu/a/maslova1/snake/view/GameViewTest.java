package ru.nsu.a.maslova1.snake.view;

import org.junit.jupiter.api.Test;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.a.maslova1.snake.model.GameState;
import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для GameView.
 */
class GameViewTest {

    /**
     * Проверяет создание GameView.
     */
    @Test
    void testGameViewCreation() {
        Canvas canvas = new Canvas(900, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameView gameView = new GameView(gc);

        assertNotNull(gameView);
    }

    /**
     * Проверяет, что notify не выбрасывает исключений.
     */
    @Test
    void testNotifyDoesNotThrow() {
        Canvas canvas = new Canvas(900, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameView gameView = new GameView(gc);

        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        snake.add(new Point(4, 5));

        ArrayList<Point> apples = new ArrayList<>();
        apples.add(new Point(10, 10));

        GameState state = new GameState(snake, apples, null, 0, 2, false);

        assertDoesNotThrow(() -> gameView.notify(state));
    }

    /**
     * Проверяет, что notify работает с пустыми списками.
     */
    @Test
    void testNotifyWithEmptyLists() {
        Canvas canvas = new Canvas(900, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameView gameView = new GameView(gc);

        GameState state = new GameState(new ArrayList<>(), new ArrayList<>(), null, 0, 0, false);

        assertDoesNotThrow(() -> gameView.notify(state));
    }

    /**
     * Проверяет, что notify работает с золотым яблоком.
     */
    @Test
    void testNotifyWithGoldApple() {
        Canvas canvas = new Canvas(900, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameView gameView = new GameView(gc);

        ArrayList<Point> snake = new ArrayList<>();
        snake.add(new Point(5, 5));

        ArrayList<Point> apples = new ArrayList<>();
        apples.add(new Point(10, 10));

        Point goldApple = new Point(15, 15);

        GameState state = new GameState(snake, apples, goldApple, 5, 2, false);

        assertDoesNotThrow(() -> gameView.notify(state));
    }
}