// Разделить класс на генерацию и отрисовку яблок
package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;
import java.util.Random;

public class AppleDraw {
    private final int cellSize = GameConfig.CELL_SIZE;

    private ArrayList<Point> apples = new ArrayList<>();
    private final int count = 3;
    private final GraphicsContext brush;

    Random random = new Random();

    public AppleDraw(GraphicsContext brush) {
        this.brush = brush;
    }

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

    public void drawApple(ArrayList<Point> snake) {
        if (apples.size() < count) {
            for (int i = 0; i < (count - apples.size()); i++) {
                generateFreeCells(snake);
            }

        }

        for (Point fruit : apples) {
            int x = fruit.getX() * cellSize;
            int y = fruit.getY() * cellSize;

            brush.setFill(Color.rgb(255, 100, 100));  // кораллово-красный
            brush.fillRect(x, y, cellSize - 1, cellSize - 1);
        }
    }
    public void reset() {
        apples.clear();
    }

    public ArrayList<Point> getApples() {
        return apples;
    }
}
