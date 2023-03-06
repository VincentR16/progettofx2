package com.example.proggettofx2;

import com.example.proggettofx2.entita.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class MenuController
{
    //gestisce il menu laterale di ogni pagina
    private final MyStage myStage = new MyStage();

    @FXML
    void BAggiungifoto(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException
    {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();


        myStage.CreateStage("Aggiungifotopage.fxml");
        myStage.getStage().setWidth(920);
        myStage.getStage().setHeight(620);
    }

    @FXML
    void BCestino(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        myStage.CreateStage("Trashpage.fxml");
    }

    @FXML
    void BCollezioni(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        myStage.CreateStage("Collezionipage.fxml");
    }

    @FXML
    void BProfile(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        myStage.CreateStage("Profile-page.fxml");
    }

    @FXML
    void Bvideo(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        myStage.CreateStage("Videopage.fxml");
    }


    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        myStage.CreateStage("Firstpage.fxml");
        myStage.getStage().setHeight(450);
        myStage.getStage().setWidth(655);
        myStage.getStage().setResizable(false);

        Utente.getUtente().setdefault();

    }

    @FXML
    void MouseEntered(MouseEvent event)
    {
        javafx.scene.control.Button button=(javafx.scene.control.Button) (event.getSource());
        button.setStyle("-fx-background-color:  #0C1538");
    }

    @FXML
    void MouseExited(MouseEvent event)
    {
        javafx.scene.control.Button button=(Button) (event.getSource());
        button.setStyle("-fx-background-color:  #183669 ");
    }

    @FXML
    void BbackToHome(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException {

        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        myStage.CreateStage("HOME_page.fxml");
    }


}
