package ru.nsu.a.maslova1.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }
}