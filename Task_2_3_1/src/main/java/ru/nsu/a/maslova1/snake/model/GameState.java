package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;

/**
 * Объект для передачи данных о текущем состоянии игрового мира.
 */
public class GameState {
    private final ArrayList<Point> snake;
    private final ArrayList<Point> apples;
    private final Point goldApple;
    private final int score;
    private final int length;
    private final boolean isGameOver;

    /**
     * Создает неизменяемый снимок состояния игры для передачи наблюдателям.
     *
     * @param snake список точек, составляющих тело змейки.
     * @param apples список координат обычных яблок.
     * @param goldApple координата золотого яблока.
     * @param score текущий счет игрока.
     * @param length текущая длина змейки.
     * @param isGameOver флаг завершения игры.
     */
    public GameState(ArrayList<Point> snake, ArrayList<Point> apples, Point goldApple, int score, int length, boolean isGameOver) {
        this.snake = snake;
        this.apples = apples;
        this.goldApple = goldApple;
        this.score = score;
        this.length = length;
        this.isGameOver = isGameOver;
    }

    /**
     * Возвращает список координат всех сегментов змейки.
     */
    public ArrayList<Point> getSnake() {
        return snake;
    }

    /**
     * Возвращает список координат активных обычных яблок.
     */
    public ArrayList<Point> getApples() {
        return apples;
    }

    /**
     * Возвращает координаты золотого яблока или null, если оно отсутствует.
     */
    public Point getGoldApple() {
        return goldApple;
    }

    /**
     * Возвращает количество очков на момент создания снимка состояния.
     */
    public int getScore() {
        return score;
    }

    /**
     * Возвращает длину змейки на момент создания снимка состояния.
     */
    public int getLength() {
        return length;
    }

    /**
     * Проверяет, является ли данное состояние финальным для игровой сессии.
     */
    public boolean isGameOver() {
        return isGameOver;
    }
}