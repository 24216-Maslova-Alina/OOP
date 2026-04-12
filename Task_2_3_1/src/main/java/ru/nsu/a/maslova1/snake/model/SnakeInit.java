package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;
import ru.nsu.a.maslova1.snake.config.GameConfig;

/**
 * Инициализация начального состояния змейки.
 */
public class SnakeInit {
    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    private ArrayList<Point> snake;

    /**
     * Создаёт объект инициализации змейки.
     */
    public SnakeInit() {
        this.snake = new ArrayList<>();
    }

    /**
     * Создаёт змейку в начальной позиции: голова в центре, хвост слева.
     * @return список точек змейки
     */
    public ArrayList<Point> initSnake() {
        ArrayList<Point> snake = new ArrayList<>();

        Point head = new Point(cols / 2, rows / 2);
        Point tail = new Point(cols / 2 - 1, rows / 2);

        snake.add(head);
        snake.add(tail);

        return snake;
    }

    /**
     * Устанавливает список точек змейки.
     * @param snake список точек
     */
    public void setSnake(ArrayList<Point> snake) {
        this.snake = snake;
    }

    /**
     * Возвращает массив точек змеи.
     * @return список точек змейки
     */
    public ArrayList<Point> getSnake() {
        return snake;
    }

    /**
     * Очищает список точек змейки.
     */
    public void reset() {
        snake.clear();
    }
}