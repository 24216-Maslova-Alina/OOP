package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

public class AppleDraw {
    private final int cellSize = GameConfig.CELL_SIZE;
    private final GraphicsContext brush;

    public AppleDraw(GraphicsContext brush) {
        this.brush = brush;
    }

    public void drawApple(ArrayList<Point> apples, ArrayList<Point> snake) {
        for (Point fruit : apples) {
            int x = fruit.getX() * cellSize;
            int y = fruit.getY() * cellSize;

            brush.setFill(Color.rgb(255, 100, 100));  // кораллово-красный
            brush.fillRect(x, y, cellSize - 1, cellSize - 1);
        }
    }
}