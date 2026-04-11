package ru.nsu.a.maslova1.snake.model;

import ru.nsu.a.maslova1.snake.config.GameConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppleLogic {
    private final ArrayList<Point> apples = new ArrayList<>();
    private Point goldApple = null;
    private final List<Point> walls = GameConfig.WALLS;

    private final int count = 4;
    private final Random random = new Random();
    private final int goldFlag = 3;

    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    private long goldAppleSpawnTime = 0;
    private final long GOLD_LIFETIME = 5000;

    private Point generateFreeCells(ArrayList<Point> snake) {
        ArrayList<Point> freeCells = new ArrayList<>();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Point cell = new Point(x, y);

                boolean isGolden = (goldApple != null && goldApple.equals(cell));
                if (!snake.contains(cell) && !apples.contains(cell)
                        && !walls.contains(cell) && !isGolden) {
                    freeCells.add(cell);
                }
            }
        }

        if (freeCells.isEmpty()) return null;
        return freeCells.get(random.nextInt(freeCells.size()));
    }

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

    public void reset() {
        apples.clear();
        this.goldApple = null;
    }

    public ArrayList<Point> getApples() {
        return apples;
    }

    public Point getGoldApple() {
        return goldApple;
    }

    public void removeGoldApple() {
        this.goldApple = null;
    }

    public void checkGoldAppleLifetime() {
        if (goldApple != null) {
            if (System.currentTimeMillis() - goldAppleSpawnTime > GOLD_LIFETIME) {
                removeGoldApple(); // Время вышло — удаляем!
            }
        }
    }
}