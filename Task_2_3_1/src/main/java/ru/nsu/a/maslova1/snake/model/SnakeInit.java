package ru.nsu.a.maslova1.snake.model;

import ru.nsu.a.maslova1.snake.config.GameConfig;

import java.util.ArrayList;

public class SnakeInit {
    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    private ArrayList<Point> snake;

    public SnakeInit() {
        this.snake = new ArrayList<>();
    }

    public ArrayList<Point> initSnake() {
        ArrayList<Point> snake = new ArrayList<>();

        Point head = new Point(cols / 2, rows / 2);
        Point tail = new Point(cols / 2 - 1, rows / 2);

        snake.add(head);
        snake.add(tail);

        return snake;
    }

    public void setSnake(ArrayList<Point> snake) {
        this.snake = snake;
    }

    public ArrayList<Point> getSnake() {
        return snake;
    }

    public void reset() {
        snake.clear();
    }
}