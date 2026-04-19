package ru.nsu.a.maslova1.snake.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ru.nsu.a.maslova1.snake.model.Directions;
import ru.nsu.a.maslova1.snake.model.GameManager;
import ru.nsu.a.maslova1.snake.model.GameState;
import ru.nsu.a.maslova1.snake.model.Observer;

/**
 * Основной контроллер игры "Змейка".
 */
public class SnakeController implements Observer {

    @FXML private Canvas gameCanvas;
    @FXML private Label lengthLabel;
    @FXML private Label scoreLabel;
    @FXML private Label bestLabel;
    @FXML private Button startButton;
    @FXML private Button pauseButton;

    @FXML private VBox gameOverOverlay;
    @FXML private GameOverController gameOverOverlayController;
    @FXML private StackPane gamePane;

    private GameManager gameManager;
    private Timeline timeline;
    private boolean wasGameRunning = false;
    private boolean keysInstalled = false;

    /**
     * Инициализирует контроллер.
     */
    @FXML
    public void initialize() {
        if (gameOverOverlayController != null) {
            gameOverOverlayController.setMainController(this);
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            if (gameManager != null) {
                gameManager.makeStep();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        startButton.setOnAction(e -> restartGameFromOverlay());
        pauseButton.setOnAction(e -> togglePause());

        //Canvas всегда будет такого же размера, как StackPane
        gameCanvas.widthProperty().bind(gamePane.widthProperty());
        gameCanvas.heightProperty().bind(gamePane.heightProperty());

        setupKeysHandling();
    }

    /**
     * Устанавливает модель игры и регистрирует контроллер как наблюдателя.
     *
     * @param model экземпляр GameManager для управления игровой логикой.
     */
    public void setModel(GameManager model) {
        this.gameManager = model;
        this.gameManager.addObserver(this);
    }

    /**
     * Возвращает графический контекст холста для выполнения отрисовки.
     *
     * @return GraphicsContext2D используемого холста.
     */
    public GraphicsContext getGraphicsContext() {
        return gameCanvas.getGraphicsContext2D();
    }

    /**
     * Переключает состояние паузы в игре.
     */
    private void togglePause() {
        if (gameManager == null || !gameManager.isGameRunning()) {
            return;
        }

        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
        } else {
            timeline.play();
        }
        updatePauseButtonText();
    }

    /**
     * Запускает новую игру, сбрасывает состояние модели и активирует таймер.
     */
    public void restartGameFromOverlay() {
        gameOverOverlay.setVisible(false);
        gameManager.startGame();
        timeline.play();
        wasGameRunning = true;
        bestLabel.setText(String.valueOf(gameManager.getBestScore()));
        pauseButton.setText("Пауза");
    }

    /**
     * Вызывается при обновлении состояния модели.
     * Обновляет текстовые метки интерфейса и обрабатывает ситуацию завершения игры.
     *
     * @param state текущее состояние игры.
     */
    @Override
    public void notify(GameState state) {
        lengthLabel.setText(String.valueOf(state.getLength()));
        scoreLabel.setText(String.valueOf(state.getScore()));

        if (state.isGameOver() && wasGameRunning) {
            handleGameOver();
        }
    }

    /**
     * Останавливает игровой процесс и отображает окно завершения игры.
     */
    private void handleGameOver() {
        timeline.stop();
        wasGameRunning = false;
        bestLabel.setText(String.valueOf(gameManager.getBestScore()));
        pauseButton.setText("Пауза");

        if (gameOverOverlayController != null) {
            gameOverOverlayController.setStats(gameManager.getScore(), gameManager.getLength());
        }
        gameOverOverlay.setVisible(true);
    }

    /**
     * Изменяет текст на кнопке паузы в зависимости от состояния таймера.
     */
    private void updatePauseButtonText() {
        if (pauseButton.getText().equals("Пауза")) {
            pauseButton.setText("Продолжить");
        } else {
            pauseButton.setText("Пауза");
        }
    }

    /**
     * Настраивает глобальный фильтр нажатий клавиш после прикрепления сцены.
     */
    private void setupKeysHandling() {
        gameCanvas.sceneProperty().addListener((obs,
                                                oldScene, newScene) -> {
            if (newScene != null && !keysInstalled) {
                keysInstalled = true;
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeys);
            }
        });
    }

    /**
     * Обрабатывает нажатия клавиш и передает команды управления в модель.
     *
     * @param event событие нажатия клавиши.
     */
    private void handleKeys(KeyEvent event) {
        if (gameManager == null) {
            return;
        }

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