package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.nsu.a.maslova1.snake.config.GameConfig;

/**
 * Логика генерации и управления яблоками на игровом поле.
 */
public class AppleLogic {

    private final ArrayList<Point> apples = new ArrayList<>();
    private Point goldApple = null;
    private final List<Point> walls = GameConfig.WALLS;

    private final int count = 5;
    private final Random random = new Random();
    private final int goldFlag = 3;

    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    private long goldAppleSpawnTime = 0;
    private final long goldLifetime = 5000;

    /**
     * Находит случайную свободную клетку на поле.
     *
     * @param snake текущее положение змейки
     *
     * @return свободная клетка или null, если свободных нет
     */
    private Point generateFreeCells(ArrayList<Point> snake) {
        ArrayList<Point> freeCells = new ArrayList<>();

        for (int x = 0;
             x < cols;
             x++) {
            for (int y = 0;
                 y < rows;
                 y++) {
                Point cell = new Point(x, y);

                boolean isGolden = (goldApple != null && goldApple.equals(cell));
                if (! snake.contains(cell) && ! apples.contains(cell)
                        && ! walls.contains(cell) && ! isGolden) {
                    freeCells.add(cell);
                }
            }
        }

        if (freeCells.isEmpty()) {
            return null;
        }
        return freeCells.get(random.nextInt(freeCells.size()));
    }

    /**
     * Генерирует обычные яблоки (до count штук) и золотое яблоко с заданным шансом.
     *
     * @param snake текущее положение змейки
     */
    public void generateApples(ArrayList<Point> snake) {
        while (apples.size() < count) {
            Point p = generateFreeCells(snake);
            if (p != null) {
                apples.add(p);
            }
        }

        if (goldApple == null && random.nextInt(1000) < goldFlag) {
            this.goldApple = generateFreeCells(snake);

            if (this.goldApple != null) {
                this.goldAppleSpawnTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Сбрасывает все яблоки (очищает списки).
     */
    public void reset() {
        apples.clear();
        this.goldApple = null;
    }

    /**
     * Возвращает список обычных яблок.
     *
     * @return список координат обычных яблок
     */
    public ArrayList<Point> getApples() {
        return apples;
    }

    /**
     * Возвращает координаты золотого яблока.
     *
     * @return координаты золотого яблока или null, если его нет
     */
    public Point getGoldApple() {
        return goldApple;
    }

    /**
     * Удаляет золотое яблоко с поля.
     */
    public void removeGoldApple() {
        this.goldApple = null;
    }

    /**
     * Проверяет, не истекло ли время жизни золотого яблока, и удаляет его при необходимости.
     */
    public void checkGoldAppleLifetime() {
        if (goldApple != null) {
            if (System.currentTimeMillis() - goldAppleSpawnTime > goldLifetime) {
                removeGoldApple();
            }
        }
    }
}