package com.example.proggettofx2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        MainController C =MainController.getInstance();

        C.getInstance().CreateStage("Firstpage.fxml");
        C.getStage().setHeight(450);
        C.getStage().setWidth(655);
        C.getStage().setResizable(false);

    }

    public static void main(String[] args) {

        launch();
    }
}