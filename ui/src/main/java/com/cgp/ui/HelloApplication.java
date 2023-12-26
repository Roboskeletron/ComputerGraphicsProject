package com.cgp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader  fxmlLoader = new FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource("hello-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);

        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        var controller = (HelloController) fxmlLoader.getController();

        controller.getAnchorPane().prefHeightProperty().bind(scene.heightProperty());
        controller.getAnchorPane().prefWidthProperty().bind(scene.widthProperty());

        controller.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}