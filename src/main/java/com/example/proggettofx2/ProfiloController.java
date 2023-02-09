
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
    private MainController Main;


    @FXML
    void Baggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Aggiungifotopage.fxml");
        Main.getStage().setWidth(920);
        Main.getStage().setHeight(630);
    }

    @FXML
    void Bcestino(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Trashpage.fxml");
    }

    @FXML
    void Bcollezioni(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Collezionipage.fxml");
    }

    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();

        Main.CreateStage("Firstpage.fxml");
        Main.getStage().setHeight(450);
        Main.getStage().setWidth(655);
        Main.getStage().setResizable(false);

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
        Main.getStage().close();
        Main.CreateStage("Videopage.fxml");
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
        Main.getStage().close();
        Main.CreateStage("HOME_page.fxml");
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        Main=MainController.getInstance();

        Nome.setText(Utente.getUtente().getNome());
        Cognome.setText(Utente.getUtente().getCognome());
        Nazionalita.setText(Utente.getUtente().getNazionalita());
        Email.setText(Utente.getUtente().getEmail());
        Password.setText(Utente.getUtente().getPassword());
    }
}
