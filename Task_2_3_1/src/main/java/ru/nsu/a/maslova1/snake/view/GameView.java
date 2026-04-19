package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.a.maslova1.snake.model.GameState;
import ru.nsu.a.maslova1.snake.model.Observer;

/**
 * Класс представления, отвечающий за визуализацию игрового процесса на холсте.
 */
public class GameView implements Observer {
    private final SnakeDraw snakeDraw;
    private final AppleDraw appleDraw;
    private final Walls walls;

    /**
     * Инициализирует компоненты отрисовки с использованием предоставленного графического контекста.
     *
     * @param brush объект GraphicsContext для рисования на Canvas.
     */
    public GameView(GraphicsContext brush) {
        this.snakeDraw = new SnakeDraw(brush);
        this.appleDraw = new AppleDraw(brush);
        this.walls = new Walls(brush);
    }

    /**
     * Очищает игровое поле и перерисовывает все игровые объекты на основе полученного состояния.
     *
     * @param state актуальный снимок состояния игры от модели.
     */
    @Override
    public void notify(GameState state) {
        snakeDraw.clearField();
        snakeDraw.drawSnake(state.getSnake());
        walls.drawWalls();
        appleDraw.drawApple(state.getApples(), state.getGoldApple());
    }
}