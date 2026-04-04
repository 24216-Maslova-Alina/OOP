package ru.nsu.a.maslova1.snake.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.logic.*;
import ru.nsu.a.maslova1.snake.model.Point;
import ru.nsu.a.maslova1.snake.view.AppleDraw;
import ru.nsu.a.maslova1.snake.view.SnakeDraw;

import java.util.ArrayList;

public class GameManager {
    private final SnakeDraw snakeDraw;
    private final AppleDraw appleDraw;

    private final Move move;
    private final Collision collision;
    private final Eat eat;

    private Timeline timeline;

    public GameManager(GraphicsContext brush) {
        this.snakeDraw = new SnakeDraw(brush);
        this.appleDraw = new AppleDraw(brush);

        this.eat = new Eat();
        this.move = new Move(eat);
        this.collision = new Collision();
    }

    public void startGame() {
        if (timeline != null) {
            timeline.stop();
        }

        snakeDraw.initSnake();
        appleDraw.reset();
        eat.resetStore();

        move.setDirCurrent(Directions.RIGHT);

        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void gameLoop() {
        ArrayList<Point> snake = snakeDraw.getSnake();
        ArrayList<Point> apples = appleDraw.getApples();

        move.move(snake, apples);

        Point head = snake.get(0);

        if (collision.collisionWall(head, GameConfig.COLS, GameConfig.ROWS)
                || collision.collisionTail(snake)) {
            stopGame();
            return;
        }

        redraw();
    }

    private void redraw() {
        snakeDraw.clearField();
        snakeDraw.drawSnake();
        appleDraw.drawApple(snakeDraw.getSnake());
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
}