package ru.nsu.a.maslova1.snake.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ru.nsu.a.maslova1.snake.model.Directions;

public class SnakeController {

    @FXML private Canvas gameCanvas;
    @FXML private Label lengthLabel;
    @FXML private Label scoreLabel;
    @FXML private Label bestLabel;
    @FXML private Button startButton;
    @FXML private Button pauseButton;

    @FXML private VBox gameOverOverlay;
    @FXML private GameOverController gameOverOverlayController;

    private GraphicsContext brush;
    private GameManager gameManager;
    private boolean wasGameRunning = false;
    private boolean keysInstalled = false;

    @FXML
    public void initialize() {
        brush = gameCanvas.getGraphicsContext2D();
        gameManager = new GameManager(brush);

        // Связываем контроллер окна Game Over с главным контроллером
        if (gameOverOverlayController != null) {
            gameOverOverlayController.setMainController(this);
        }

        startButton.setOnAction(e -> {
            restartGameFromOverlay();
        });

        pauseButton.setOnAction(e -> {
            if (gameManager.isGameRunning()) {
                gameManager.togglePause();
                updatePauseButtonText();
            }
        });

        setupKeysHandling();

        Timeline uiUpdater = new Timeline(
                new KeyFrame(Duration.millis(100), e -> {
                    lengthLabel.setText(String.valueOf(gameManager.getLength()));
                    scoreLabel.setText(String.valueOf(gameManager.getScore()));

                    if (wasGameRunning && !gameManager.isGameRunning()) {
                        handleGameOver();
                    }
                })
        );
        uiUpdater.setCycleCount(Timeline.INDEFINITE);
        uiUpdater.play();
    }

    private void handleGameOver() {
        wasGameRunning = false;
        bestLabel.setText(String.valueOf(gameManager.getBestScore()));
        pauseButton.setText("Пауза");

        if (gameOverOverlayController != null) {
            gameOverOverlayController.setStats(gameManager.getScore(), gameManager.getLength());
        }
        gameOverOverlay.setVisible(true);
    }

    public void restartGameFromOverlay() {
        gameOverOverlay.setVisible(false);
        gameManager.startGame();
        wasGameRunning = true;
        bestLabel.setText(String.valueOf(gameManager.getBestScore()));
        pauseButton.setText("Пауза");
    }

    private void updatePauseButtonText() {
        if (pauseButton.getText().equals("Пауза")) {
            pauseButton.setText("Продолжить");
        } else {
            pauseButton.setText("Пауза");
        }
    }

    private void setupKeysHandling() {
        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null && !keysInstalled) {
                keysInstalled = true;
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeys);
            }
        });
    }

    private void handleKeys(KeyEvent event) {
        Directions dir = switch (event.getCode()) {
            case UP -> Directions.UP;
            case DOWN -> Directions.DOWN;
            case LEFT -> Directions.LEFT;
            case RIGHT -> Directions.RIGHT;
            default -> null;
        };

        if (dir != null) {
            gameManager.setDirection(dir);
        }
    }
}