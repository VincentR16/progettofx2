
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


public class ProfiloController extends MenuController implements Initializable
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
    void Bmodifica(@SuppressWarnings("UnusedParameters")ActionEvent event)throws SQLException
    {
        Utente.getUtente().Modifica(Nome.getText(),Cognome.getText(),Nazionalita.getText(),Email.getText(),Password.getText());
        // todo non va bene fare tanti if e controllare solo i campi usati!
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
