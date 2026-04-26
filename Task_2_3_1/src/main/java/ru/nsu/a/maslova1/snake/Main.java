package ru.nsu.a.maslova1.snake;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.a.maslova1.snake.controller.SnakeController;
import ru.nsu.a.maslova1.snake.model.GameManager;
import ru.nsu.a.maslova1.snake.view.GameView;

/**
 * Точка входа в приложение, отвечающая за сборку компонентов MVC и запуск интерфейса.
 */
public class Main extends Application {

    /**
     * Конфигурирует сцену, инициализирует модель, представление и контроллер, устанавливая связи
     * между ними.
     *
     * @param primaryStage основной контейнер JavaFX для отображения окна приложения.
     *
     * @throws IOException если файл разметки FXML не найден или поврежден.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Snake.fxml"));

        // 1. ОБЯЗАТЕЛЬНО СНАЧАЛА вызываем load() - здесь читается FXML и создается контроллер
        Scene scene = new Scene(loader.load());

        // 2. Теперь контроллер уже существует, забираем его!
        SnakeController controller = loader.getController();

        GameManager model = new GameManager();

        GameView view = new GameView(
                controller.getGraphicsContext(),
                controller.getScoreLabel(),
                controller.getLengthLabel()
        );

        model.addObserver(view);
        controller.setModel(model);

        primaryStage.setTitle("Змейка");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Запускает стандартный цикл выполнения JavaFX приложения.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        launch(args);
    }
}