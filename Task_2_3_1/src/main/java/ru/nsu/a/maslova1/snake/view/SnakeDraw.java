package ru.nsu.a.maslova1.snake.view;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Point;

/**
 * Отрисовка змейки и игрового поля.
 */
public class SnakeDraw {
    private final int size = GameConfig.CELL_SIZE;
    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    private final GraphicsContext brush;

    /**
     * Создаёт отрисовщик змейки.
     * @param brush контекст для рисования
     */
    public SnakeDraw(GraphicsContext brush) {
        this.brush = brush;
    }

    /**
     * Очищает игровое поле, заливая фоновым цветом.
     */
    public void clearField() {
        brush.setFill(Color.rgb(225, 245, 240));
        brush.fillRect(0, 0, cols * size, rows * size);
    }

    /**
     * Рисует змейку: голову с глазами и сегменты тела.
     * @param snake список точек змейки
     */
    public void drawSnake(ArrayList<Point> snake) {
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            int x = p.getPointX() * size;
            int y = p.getPointY() * size;

            if (i == 0) {
                brush.setFill(Color.web("#388E3C"));
                brush.fillRoundRect(x - 1, y - 1, size + 2, size + 2, 12, 12);

                brush.setFill(Color.BLACK);
                double eyeSize = size * 0.2;
                double eyeY = y + size * 0.3;

                brush.fillOval(x + size * 0.25, eyeY, eyeSize, eyeSize);
                brush.fillOval(x + size * 0.55, eyeY, eyeSize, eyeSize);
            } else {
                brush.setFill(Color.web("#66BB6A"));
                brush.fillRoundRect(x + 1, y + 1, size - 2, size - 2, 10, 10);
            }
        }
    }
}