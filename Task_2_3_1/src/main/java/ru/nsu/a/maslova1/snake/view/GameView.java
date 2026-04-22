package ru.nsu.a.maslova1.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import ru.nsu.a.maslova1.snake.model.GameState;
import ru.nsu.a.maslova1.snake.model.Observer;

/**
 * Класс представления, отвечающий за визуализацию игрового процесса на холсте.
 */
public class GameView implements Observer {
    private final SnakeDraw snakeDraw;
    private final AppleDraw appleDraw;
    private final Walls walls;

    private final Label scoreLabel;
    private final Label lengthLabel;

    /**
     * Инициализирует компоненты отрисовки с использованием предоставленного графического контекста.
     *
     * @param brush объект GraphicsContext для рисования на Canvas.
     */
    public GameView (GraphicsContext brush, Label scoreLabel, Label lengthLabel) {
        this.snakeDraw = new SnakeDraw (brush);
        this.appleDraw = new AppleDraw (brush);
        this.walls = new Walls (brush);
        this.scoreLabel = scoreLabel;
        this.lengthLabel = lengthLabel;
    }

    /**
     * Очищает игровое поле и перерисовывает все игровые объекты на основе полученного состояния.
     *
     * @param state актуальный снимок состояния игры от модели.
     */
    @Override
    public void notify (GameState state) {
        snakeDraw.clearField ();
        snakeDraw.drawSnake (state.getSnake ());
        walls.drawWalls ();
        appleDraw.drawApple (state.getApples (), state.getGoldApple ());

        scoreLabel.setText (String.valueOf (state.getScore ()));
        lengthLabel.setText (String.valueOf (state.getLength ()));
    }
}