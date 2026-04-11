package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

public class SnakeDraw {
    private final int size = GameConfig.CELL_SIZE;
    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    private final GraphicsContext brush;

    public SnakeDraw(GraphicsContext brush) {
        this.brush = brush;
    }

    public void clearField() {
        // Игровое поле - очень светлый мята (отличается от фона окна)
        brush.setFill(Color.rgb(225, 245, 240));  // #E1F5F0
        brush.fillRect(0, 0, cols * size, rows * size);
    }

    public void drawSnake(ArrayList<Point> snake) {
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            int x = p.getX() * size;
            int y = p.getY() * size;

            if (i == 0) {
                brush.setFill(Color.web("#388E3C"));
                brush.fillRoundRect(x - 1, y - 1, size + 2, size + 2, 12, 12);

                brush.setFill(Color.BLACK);
                double eyeSize = size * 0.2; // Размер зрачка
                double eyeY = y + size * 0.3; // Высота глаз

                brush.fillOval(x + size * 0.25, eyeY, eyeSize, eyeSize);
                brush.fillOval(x + size * 0.55, eyeY, eyeSize, eyeSize);
            } else {
                brush.setFill(Color.web("#66BB6A"));
                brush.fillRoundRect(x + 1, y + 1, size - 2, size - 2, 10, 10);
            }
        }
    }
}