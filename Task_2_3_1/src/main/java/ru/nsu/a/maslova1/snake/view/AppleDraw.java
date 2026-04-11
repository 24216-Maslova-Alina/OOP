package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;
import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

public class AppleDraw {
    private final int size = GameConfig.CELL_SIZE;
    private final GraphicsContext brush;

    public AppleDraw(GraphicsContext brush) {
        this.brush = brush;
    }

    public void drawApple(ArrayList<Point> apples, Point golden) {
        for (Point fruit : apples) {
            int x = fruit.getX() * size;
            int y = fruit.getY() * size;

            brush.setFill(Color.MAROON);
            brush.fillOval(x, y, size, size);

            brush.setFill(Color.rgb(255, 100, 100));
            brush.fillOval(x + 2, y + 2, size - 4, size - 4);

            brush.setFill(Color.rgb(255, 200, 200));
            brush.fillOval(x + 5, y + 5, size / 4.0, size / 4.0);
        }

        if (golden != null) {
            int x = golden.getX() * size;
            int y = golden.getY() * size;

            brush.setFill(Color.DARKGOLDENROD);
            brush.fillOval(x, y, size, size);

            brush.setFill(Color.GOLD);
            brush.fillOval(x + 2, y + 2, size - 4, size - 4);

            brush.setFill(Color.WHITE);
            brush.fillOval(x + 5, y + 5, size / 4.0, size / 4.0);
        }
    }
}