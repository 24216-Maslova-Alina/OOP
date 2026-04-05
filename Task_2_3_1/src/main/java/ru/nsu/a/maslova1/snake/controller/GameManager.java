package ru.nsu.a.maslova1.snake.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.*;
import ru.nsu.a.maslova1.snake.view.AppleDraw;
import ru.nsu.a.maslova1.snake.view.SnakeDraw;

import java.util.ArrayList;

public class GameManager {
    private final SnakeDraw snakeDraw;
    private final SnakeInit snakeInit;
    private final AppleDraw appleDraw;
    private final AppleLogic appleLogic;

    private final Move move;
    private final Collision collision;
    private final Eat eat;
    private final Statistic statistic;

    private Timeline timeline;

    private ArrayList<Point> snake;
    private ArrayList<Point> apples;

    private boolean isGameRunning = false;

    public GameManager(GraphicsContext brush) {
        this.snakeDraw = new SnakeDraw(brush);
        this.snakeInit = new SnakeInit();
        this.appleDraw = new AppleDraw(brush);
        this.appleLogic = new AppleLogic();

        this.eat = new Eat();
        this.move = new Move(eat);
        this.collision = new Collision();
        this.statistic = new Statistic(eat);

        this.snake = new ArrayList<>();
        this.apples = new ArrayList<>();
    }

    public void startGame() {
        if (timeline != null) {
            timeline.stop();
        }

        snake = snakeInit.initSnake();
        appleLogic.reset();
        apples = appleLogic.getApples();

        eat.resetStore();
        isGameRunning = true;

        move.setDirCurrent(Directions.RIGHT);

        timeline = new Timeline(new KeyFrame(Duration.millis(150),
                e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void gameLoop() {
        appleLogic.generateApples(snake);
        apples = appleLogic.getApples();

        move.move(snake, apples);

        Point head = snake.get(0);

        if (collision.collisionWall(head, GameConfig.COLS, GameConfig.ROWS)
                || collision.collisionTail(snake)) {
            isGameRunning = false;
            stopGame();
            return;
        }

        redraw();
    }

    private void redraw() {
        snakeDraw.clearField();
        snakeDraw.drawSnake(snake);

        appleDraw.drawApple(apples, snake);
    }

    public void pauseGame() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    public void stopGame() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void setDirection(Directions dir) {
        move.setDirNew(dir);
    }

    public int getScore() {
        return eat.countingScore();
    }

    public int getBestScore() {
        return statistic.getBestResult();
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }
}