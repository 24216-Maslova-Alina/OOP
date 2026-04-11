package ru.nsu.a.maslova1.snake.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.*;
import ru.nsu.a.maslova1.snake.view.AppleDraw;
import ru.nsu.a.maslova1.snake.view.SnakeDraw;
import ru.nsu.a.maslova1.snake.view.Walls;

import java.util.ArrayList;

public class GameManager {
    private final SnakeDraw snakeDraw;
    private final SnakeInit snakeInit;
    private final AppleDraw appleDraw;
    private final AppleLogic appleLogic;
    private final Walls walls;

    private final Move move;
    private final Collision collision;
    private final Eat eat;
    private final Statistic statistic;

    private Timeline timeline;

    private ArrayList<Point> snake;

    private boolean isGameRunning = false;

    public GameManager(GraphicsContext brush) {
        this.snakeDraw = new SnakeDraw(brush);
        this.snakeInit = new SnakeInit();
        this.appleDraw = new AppleDraw(brush);
        this.appleLogic = new AppleLogic();
        this.walls = new Walls(brush);

        this.eat = new Eat();
        this.move = new Move(eat);
        this.collision = new Collision();
        this.statistic = new Statistic(eat);

        this.snake = new ArrayList<>();
    }

    public void startGame() {
        if (timeline != null) {
            timeline.stop();
        }

        snake = snakeInit.initSnake();
        appleLogic.reset();

        eat.resetStore();
        isGameRunning = true;

        move.setDirCurrent(Directions.RIGHT);

        timeline = new Timeline(new KeyFrame(Duration.millis(150),
                e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void gameLoop() {
        appleLogic.checkGoldAppleLifetime();
        appleLogic.generateApples(snake);

        move.move(snake, appleLogic.getApples(), appleLogic);

        Point head = snake.get(0);

        if (collision.collisionWall(GameConfig.WALLS, head)
                || collision.collisionBorder(head, GameConfig.COLS, GameConfig.ROWS)
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

        walls.drawWalls();

        appleDraw.drawApple(appleLogic.getApples(), appleLogic.getGoldApple());
    }

    public void togglePause() {
        if (timeline == null) return;

        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
        } else if (timeline.getStatus() == Timeline.Status.PAUSED) {
            timeline.play();
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

    public int getLength() {
        return snake.size();
    }

    public int getBestScore() {
        return statistic.getBestResult();
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }
}