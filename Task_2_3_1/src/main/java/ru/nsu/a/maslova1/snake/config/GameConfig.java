package ru.nsu.a.maslova1.snake.config;

import java.util.List;

import ru.nsu.a.maslova1.snake.model.Point;

/**
 * Конфигурация игры: размеры поля, ячейки и список стен.
 */
public class GameConfig {
    public static final int COLS = 25;
    public static final int ROWS = 25;

    // Неизменяемый массив стен
    public static final List<Point> WALLS;

    static {

        WALLS = List.of (
                new Point (5, 5),
                new Point (24, 5),
                new Point (3, 10),
                new Point (8, 15),
                new Point (21, 15),
                new Point (5, 20),
                new Point (24, 20),
                new Point (14, 3),
                new Point (10, 8),
                new Point (19, 8),
                new Point (8, 18),
                new Point (21, 18),
                new Point (15, 10),
                new Point (15, 20),
                new Point (4, 15),
                new Point (1, 22)
        );
    }
}