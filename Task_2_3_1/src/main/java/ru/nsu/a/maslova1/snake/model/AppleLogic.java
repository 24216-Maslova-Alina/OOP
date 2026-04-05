package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;
import java.util.Random;

public class AppleLogic {
    private ArrayList<Point> apples = new ArrayList<>();
    private final int count = 3;
    private Random random = new Random();

    private void generateFreeCells(ArrayList<Point> snake) {
        ArrayList<Point> freeCells = new ArrayList<>();

        for (int x = 0; x < 45; x++) {
            for (int y = 0; y < 45; y++) {
                Point cell = new Point(x, y);
                if (!snake.contains(cell) && !apples.contains(cell)) {
                    freeCells.add(cell);
                }
            }
        }

        int index = random.nextInt(freeCells.size());
        Point drawApple = freeCells.get(index);

        apples.add(drawApple);
    }

    public void generateApples(ArrayList<Point> snake) {
        if (apples.size() < count) {
            for (int i = 0; i < (count - apples.size()); i++) {
                generateFreeCells(snake);
            }
        }
    }

    public void reset() {
        apples.clear();
    }

    public ArrayList<Point> getApples() {
        return apples;
    }
}