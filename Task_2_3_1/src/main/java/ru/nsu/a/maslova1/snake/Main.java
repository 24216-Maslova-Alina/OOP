package ru.nsu.a.maslova1.snake;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Главный класс приложения, запускающий игру "Змейка".
 */
public class Main extends Application {

    /**
     * Запускает JavaFX приложение, загружая интерфейс из FXML.
     * @param primaryStage главное окно приложения
     * @throws IOException если FXML файл не найден
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Snake.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Змейка");
        primaryStage.setResizable(false);
        primaryStage.setWidth(950);
        primaryStage.setHeight(800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Точка входа в приложение.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }
}