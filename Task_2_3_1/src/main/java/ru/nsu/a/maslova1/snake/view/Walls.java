package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.a.maslova1.snake.config.GameConfig;

/**
 * Отрисовка стен на игровом поле.
 */
public class Walls {
    private final int cellSize = GameConfig.CELL_SIZE;
    private final GraphicsContext brush;

    /**
     * Создаёт отрисовщик стен.
     * @param brush контекст для рисования
     */
    public Walls(GraphicsContext brush) {
        this.brush = brush;
    }

    /**
     * Рисует все стены из конфигурации.
     */
    public void drawWalls() {
        for (var wall : GameConfig.WALLS) {
            int x = wall.getPointX() * cellSize;
            int y = wall.getPointY() * cellSize;

            brush.setFill(Color.rgb(70, 130, 120));
            brush.fillRect(x, y, cellSize, cellSize);

            brush.setStroke(Color.rgb(50, 90, 85));
            brush.setLineWidth(1);
            brush.strokeRect(x + 0.5, y + 0.5, cellSize - 1, cellSize - 1);
        }
    }
}