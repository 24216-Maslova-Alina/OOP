package ru.nsu.a.maslova1.snake.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import java.util.ArrayList;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Eat;
import ru.nsu.a.maslova1.snake.model.Statistic;
import ru.nsu.a.maslova1.snake.model.Point;
import ru.nsu.a.maslova1.snake.model.Directions;
import ru.nsu.a.maslova1.snake.model.AppleLogic;
import ru.nsu.a.maslova1.snake.model.Collision;
import ru.nsu.a.maslova1.snake.model.Move;
import ru.nsu.a.maslova1.snake.model.SnakeInit;
import ru.nsu.a.maslova1.snake.view.AppleDraw;
import ru.nsu.a.maslova1.snake.view.SnakeDraw;
import ru.nsu.a.maslova1.snake.view.Walls;

/**
 * Управляет игровым циклом: запуск, пауза, остановка, столкновения и отрисовка.
 */
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

    /**
     * Создаёт менеджер игры.
     * @param brush контекст для отрисовки
     */
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

    /**
     * Запускает новую игру.
     */
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

    /**
     * Игровой цикл: движение, проверка столкновений, отрисовка.
     */
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

    /**
     * Перерисовывает игровое поле.
     */
    private void redraw() {
        snakeDraw.clearField();
        snakeDraw.drawSnake(snake);

        walls.drawWalls();

        appleDraw.drawApple(appleLogic.getApples(), appleLogic.getGoldApple());
    }

    /**
     * Ставит игру на паузу или снимает с неё.
     */
    public void togglePause() {
        if (timeline == null) {
            return;
        }

        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
        } else if (timeline.getStatus() == Timeline.Status.PAUSED) {
            timeline.play();
        }
    }

    /**
     * Останавливает игру.
     */
    public void stopGame() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Устанавливает направление движения змейки.
     * @param dir новое направление
     */
    public void setDirection(Directions dir) {
        move.setDirNew(dir);
    }

    /**
     * Возвращает текущий счёт.
     * @return текущий счёт
     */
    public int getScore() {
        return eat.countingScore();
    }

    /**
     * Возвращает длину змеи.
     * @return длина змейки
     */
    public int getLength() {
        return snake.size();
    }

    /**
     * Возвращает лучший результат.
     * @return лучший результат
     */
    public int getBestScore() {
        return statistic.getBestResult();
    }

    /**
     * Возвращает статус игры.
     * @return true, если игра запущена
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }
}