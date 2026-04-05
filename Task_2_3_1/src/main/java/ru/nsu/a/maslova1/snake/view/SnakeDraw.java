package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

public class SnakeDraw {
    private final int cellSize = GameConfig.CELL_SIZE;
    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    private final GraphicsContext brush;

    public SnakeDraw(GraphicsContext brush) {
        this.brush = brush;
    }

    public void clearField() {
        // Игровое поле - очень светлый мята (отличается от фона окна)
        brush.setFill(Color.rgb(225, 245, 240));  // #E1F5F0
        brush.fillRect(0, 0, cols * cellSize, rows * cellSize);
    }

    public void drawSnake(ArrayList<Point> snake) {
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            int x = p.getX() * cellSize;
            int y = p.getY() * cellSize;

            // Голова - темнее, тело - светлее
            if (i == 0) {
                brush.setFill(Color.rgb(70, 120, 50));  // темно-оливковый для головы
            } else {
                brush.setFill(Color.rgb(100, 160, 75));  // мягкий оливково-зеленый для тела
            }
            brush.fillRect(x, y, cellSize - 1, cellSize - 1);
        }
    }
}