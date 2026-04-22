package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;

/**
 * Отрисовка стен на игровом поле.
 */
public class Walls extends BaseDraw {

    public Walls (GraphicsContext brush) {
        super (brush);
    }

    /**
     * Рисует все стены из конфигурации с учетом центрирования.
     */
    public void drawWalls () {
        double size = getCellSize ();
        double offX = getOffsetX (); // Получаем отступ
        double offY = getOffsetY (); // Получаем отступ

        for (var wall : GameConfig.WALLS) {
            double x = offX + wall.getPointX () * size;
            double y = offY + wall.getPointY () * size;

            brush.setFill (Color.rgb (70, 130, 120));
            brush.fillRect (x, y, size, size);

            brush.setStroke (Color.rgb (50, 90, 85));
            brush.setLineWidth (1);
            brush.strokeRect (x + 0.5, y + 0.5, size - 1, size - 1);
        }
    }
}