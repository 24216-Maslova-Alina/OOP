package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.a.maslova1.snake.config.GameConfig;

/**
 * Базовый класс для отрисовки графических элементов игры.
 * Предоставляет общие методы для вычисления динамических размеров и центрирования игрового поля.
 */
public class BaseDraw {

    /**
     * Графический контекст для рисования на холсте.
     */
    protected final GraphicsContext brush;

    /**
     * Создаёт базовый отрисовщик.
     *
     * @param brush графический контекст для рисования
     */
    public BaseDraw(GraphicsContext brush) {
        this.brush = brush;
    }

    /**
     * Вычисляет оптимальный размер одной клетки игрового поля в зависимости от текущего размера
     * холста.
     * Гарантирует, что всё поле поместится на экране, сохраняя при этом клетки квадратными.
     * Если холст ещё не проинициализирован, возвращает размер по умолчанию.
     *
     * @return длина стороны клетки в пикселях
     */
    protected double getCellSize() {
        double width = brush.getCanvas().getWidth();
        double height = brush.getCanvas().getHeight();

        // Если окно еще не прогрузилось, даем размер по умолчанию
        if (width <= 0 || height <= 0) {
            return 25.0;
        }

        return Math.min(width / GameConfig.COLS, height / GameConfig.ROWS);
    }

    /**
     * Вычисляет смещение по оси X, необходимое для отображения игрового поля ровно по центру
     * холста.
     *
     * @return отступ от левого края холста в пикселях
     */
    protected double getOffsetX() {
        double width = brush.getCanvas().getWidth();
        return (width - getCellSize() * GameConfig.COLS) / 2;
    }

    /**
     * Вычисляет смещение по оси Y, необходимое для отображения игрового поля ровно по центру
     * холста.
     *
     * @return отступ от верхнего края холста в пикселях
     */
    protected double getOffsetY() {
        double height = brush.getCanvas().getHeight();
        return (height - getCellSize() * GameConfig.ROWS) / 2;
    }
}