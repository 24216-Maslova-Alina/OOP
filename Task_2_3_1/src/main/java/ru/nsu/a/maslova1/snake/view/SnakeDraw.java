package ru.nsu.a.maslova1.snake.view;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Point;

/**
 * Отрисовка змейки и игрового поля.
 */
public class SnakeDraw extends BaseDraw {
    private final int cols = GameConfig.COLS;
    private final int rows = GameConfig.ROWS;

    public SnakeDraw(GraphicsContext brush) {
        super(brush);
    }

    /**
     * Очищает игровое поле, заливая фоновым цветом на весь экран, и рисует рамку по центру.
     */
    public void clearField() {
        brush.setFill(Color.rgb(225, 245, 240));
        brush.fillRect(0, 0, brush.getCanvas().getWidth(), brush.getCanvas().getHeight());

        double size = getCellSize();
        double offX = getOffsetX();
        double offY = getOffsetY();

        brush.setStroke(Color.rgb(70, 130, 120));
        brush.setLineWidth(3);
        brush.strokeRect(offX, offY, cols * size, rows * size);
    }

    /**
     * Рисует змейку с учетом центрирования.
     */
    public void drawSnake(ArrayList<Point> snake) {
        double size = getCellSize();
        double offX = getOffsetX(); // Получаем отступ X
        double offY = getOffsetY(); // Получаем отступ Y

        for(int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);

            double x = offX + p.getPointX() * size;
            double y = offY + p.getPointY() * size;

            if(i == 0) {
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