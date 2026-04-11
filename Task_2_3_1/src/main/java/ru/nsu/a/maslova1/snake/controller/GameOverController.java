package ru.nsu.a.maslova1.snake.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController {
    @FXML private Label scoreLabel;
    @FXML private Label lengthLabel;

    private SnakeController mainController;

    public void setMainController(SnakeController controller) {
        this.mainController = controller;
    }

    public void setStats(int score, int length) {
        scoreLabel.setText("Score: " + score);
        lengthLabel.setText("Length: " + length);
    }

    @FXML
    private void handleRestart() {
        mainController.restartGameFromOverlay();
    }
}