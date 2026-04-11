package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;

public class Walls {
    private final int cellSize = GameConfig.CELL_SIZE;
    private final GraphicsContext brush;

    public Walls(GraphicsContext brush) {
        this.brush = brush;
    }

    public void drawWalls() {
        for (var wall : GameConfig.WALLS) {
            int x = wall.getX() * cellSize;
            int y = wall.getY() * cellSize;

            brush.setFill(Color.rgb(70, 130, 120));
            brush.fillRect(x, y, cellSize, cellSize);

            brush.setStroke(Color.rgb(50, 90, 85)); // Тот же цвет, но темнее
            brush.setLineWidth(1);
            brush.strokeRect(x + 0.5, y + 0.5, cellSize - 1, cellSize - 1);
        }
    }
}