package ru.nsu.a.maslova1.snake.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Контроллер окна завершения игры.
 */
public class GameOverController {
    @FXML
    private Label scoreLabel;
    @FXML
    private Label lengthLabel;

    private SnakeController mainController;

    /**
     * Устанавливает ссылку на главный контроллер.
     *
     * @param controller главный контроллер
     */
    public void setMainController (SnakeController controller) {
        this.mainController = controller;
    }

    /**
     * Отображает результаты игры.
     *
     * @param score  счёт
     * @param length длина змейки
     */
    public void setStats (int score, int length) {
        scoreLabel.setText ("Score: " + score);
        lengthLabel.setText ("Length: " + length);
    }

    /**
     * Обрабатывает нажатие кнопки перезапуска игры.
     */
    @FXML
    private void handleRestart () {
        mainController.restartGameFromOverlay ();
    }
}