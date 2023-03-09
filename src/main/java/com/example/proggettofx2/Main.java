package com.example.proggettofx2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {

        MyStage myStage = new MyStage();

        myStage.CreateStage("Firstpage.fxml");
        myStage.getStage().setHeight(450);
        myStage.getStage().setWidth(655);
        myStage.getStage().setResizable(false);

    }

    public static void main(String[] args)
    {
        launch();
    }
}