
package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ProfiloController implements Initializable
{
    @FXML
    private TextField Cognome;

    @FXML
    private TextField Email;

    @FXML
    private TextField Nazionalita;

    @FXML
    private TextField Nome;

    @FXML
    private TextField Password;


    @FXML
    void Baggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Aggiungifotopage.fxml");
        MainController.getInstance().getStage().setWidth(920);
        MainController.getInstance().getStage().setHeight(630);
    }

    @FXML
    void Bcestino(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Trashpage.fxml");
    }

    @FXML
    void Bcollezioni(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Collezionipage.fxml");
    }

    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();

        MainController C =MainController.getInstance();

        C.CreateStage("Firstpage.fxml");
        C.getStage().setHeight(450);
        C.getStage().setWidth(655);
        C.getStage().setResizable(false);

        Utente.getUtente().setdefault();
    }

    @FXML
    void Bmodifica(@SuppressWarnings("UnusedParameters")ActionEvent event)throws SQLException
    {
        Utente.getUtente().Modifica(Nome.getText(),Cognome.getText(),Nazionalita.getText(),Email.getText(),Password.getText());
        // todo non va bene fare tanti if e controllare solo i campi usati!
    }

    @FXML
    void Bvideo(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Videopage.fxml");
    }


    @FXML
    void MouseEntered(MouseEvent event)
    {
        Button button=(Button) (event.getSource());
        button.setStyle("-fx-background-color:  #0C1538");
    }

    @FXML
    void MouseExited(MouseEvent event)
    {
        Button button=(Button) (event.getSource());
        button.setStyle("-fx-background-color:  #183669 ");
    }

    @FXML
    void BbackToHome(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("HOME_page.fxml");
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Nome.setText(Utente.getUtente().getNome());
        Cognome.setText(Utente.getUtente().getCognome());
        Nazionalita.setText(Utente.getUtente().getNazionalita());
        Email.setText(Utente.getUtente().getEmail());
        Password.setText(Utente.getUtente().getPassword());
    }
}
