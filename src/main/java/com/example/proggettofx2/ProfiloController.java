
package com.example.proggettofx2;

import com.example.proggettofx2.DAO.Utentedao;
import com.example.proggettofx2.entita.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ProfiloController extends MenuController implements Initializable {
    //gestisce la pagina del profilo
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
    void Bmodifica(@SuppressWarnings("UnusedParameters")ActionEvent event) throws SQLException
    {
        Utente utente = Utente.getUtente();
        Utentedao utentedao = new Utentedao();

        utente.Modifica(Nome.getText(),Cognome.getText(),Nazionalita.getText(),Email.getText(),Password.getText());

        utentedao.update(utente,1);

        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attenzione");
        alert.setContentText("I tuoi dati sono stati modificati");
        alert.show();
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
