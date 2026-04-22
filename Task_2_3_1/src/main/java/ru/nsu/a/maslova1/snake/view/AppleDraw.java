package ru.nsu.a.maslova1.snake.view;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.model.Point;

/**
 * Отрисовка яблок на игровом поле.
 */
public class AppleDraw extends BaseDraw {

    /**
     * Создаёт отрисовщик яблок.
     *
     * @param brush контекст для рисования
     */
    public AppleDraw (GraphicsContext brush) {
        super (brush);
    }

    /**
     * Рисует обычные и золотое яблоко на поле.
     *
     * @param apples список обычных яблок
     * @param golden золотое яблоко (может быть null)
     */
    public void drawApple (ArrayList<Point> apples, Point golden) {
        double size = getCellSize ();
        double offX = getOffsetX ();
        double offY = getOffsetY ();

        for (Point fruit : apples) {
            double x = offX + fruit.getPointX () * size;
            double y = offY + fruit.getPointY () * size;

            brush.setFill (Color.MAROON);
            brush.fillOval (x, y, size, size);

            brush.setFill (Color.rgb (255, 100, 100));
            brush.fillOval (x + 2, y + 2, size - 4, size - 4);

            brush.setFill (Color.rgb (255, 200, 200));
            brush.fillOval (x + 5, y + 5, size / 4.0, size / 4.0);
        }

        if (golden != null) {
            double x = offX + golden.getPointX () * size;
            double y = offY + golden.getPointY () * size;

            brush.setFill (Color.DARKGOLDENROD);
            brush.fillOval (x, y, size, size);

            brush.setFill (Color.GOLD);
            brush.fillOval (x + 2, y + 2, size - 4, size - 4);

            brush.setFill (Color.WHITE);
            brush.fillOval (x + 5, y + 5, size / 4.0, size / 4.0);
        }
    }
}