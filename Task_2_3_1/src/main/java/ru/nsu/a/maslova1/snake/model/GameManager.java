package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.a.maslova1.snake.config.GameConfig;

/**
 * Основной класс модели, управляющий состоянием и логикой игры "Змейка".
 */
public class GameManager {
    private final SnakeInit snakeInit;
    private final AppleLogic appleLogic;
    private final Move move;
    private final Collision collision;
    private final Eat eat;
    private final Statistic statistic;

    private ArrayList<Point> snake;
    private boolean isGameRunning = false;

    private final List<Observer> observers = new ArrayList<>();

    /**
     * Инициализирует компоненты игровой логики и пустой список змейки.
     */
    public GameManager() {
        this.snakeInit = new SnakeInit();
        this.appleLogic = new AppleLogic();
        this.eat = new Eat();
        this.move = new Move(eat);
        this.collision = new Collision();
        this.statistic = new Statistic(eat);
        this.snake = new ArrayList<>();
    }

    /**
     * Регистрирует нового наблюдателя для получения обновлений состояния игры.
     *
     * @param observer объект, реализующий интерфейс Observer.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Сбрасывает параметры и запускает новую игровую сессию.
     */
    public void startGame() {
        snake = snakeInit.initSnake();
        appleLogic.reset();
        eat.resetStore();
        isGameRunning = true;
        move.setDirCurrent(Directions.RIGHT);

        notifyObservers(false);
    }

    /**
     * Вычисляет изменения игрового мира за один шаг времени и проверяет столкновения.
     */
    public void makeStep() {
        if (! isGameRunning) {
            return;
        }

        appleLogic.checkGoldAppleLifetime();
        appleLogic.generateApples(snake);
        move.move(snake, appleLogic.getApples(), appleLogic);

        Point head = snake.get(0);

        if (collision.collisionWall(GameConfig.WALLS, head)
                || collision.collisionBorder(head, GameConfig.COLS, GameConfig.ROWS)
                || collision.collisionTail(snake)) {
            isGameRunning = false;
            notifyObservers(true);
            return;
        }

        notifyObservers(false);
    }

    /**
     * Создает снимок состояния игры и рассылает его всем зарегистрированным наблюдателям.
     *
     * @param gameOver флаг, указывающий на окончание игры.
     */
    private void notifyObservers(boolean gameOver) {
        GameState state = new GameState(
                snake,
                appleLogic.getApples(),
                appleLogic.getGoldApple(),
                getScore(),
                getLength(),
                gameOver
        );

        for (Observer observer : observers) {
            observer.notify(state);
        }
    }

    /**
     * Устанавливает новое направление движения змейки для следующего шага.
     *
     * @param dir значение из перечисления Directions.
     */
    public void setDirection(Directions dir) {
        move.setDirNew(dir);
    }

    /**
     * Возвращает текущее количество набранных очков.
     */
    public int getScore() {
        return eat.countingScore();
    }

    /**
     * Возвращает текущее количество сегментов змейки.
     */
    public int getLength() {
        return snake.size();
    }

    /**
     * Возвращает лучший результат за все время.
     */
    public int getBestScore() {
        return statistic.getBestResult();
    }

    /**
     * Проверяет, активен ли в данный момент игровой процесс.
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }
}