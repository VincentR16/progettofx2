package com.example.proggettofx2;

import com.example.proggettofx2.DAO.Utentedao;
import com.example.proggettofx2.entita.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SecondController {
    //gestisce la registrazione dell'utente
    @FXML
    private TextField NomeField;

    @FXML
    private TextField CognomeField;

    @FXML
    private TextField NaField;

    @FXML
    private TextField EField;
    @FXML
    private TextField PassField;




    @FXML
    void Clickcrea(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException {

         boolean controllo=true;
         // controlla l'inserimento di tutti i campi


        Utente utente = Utente.getUtente();
        Utentedao utentedao = new Utentedao();


        if(NomeField.getText().equals(""))
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setContentText("INSERIRE IL NOME");
            alert.showAndWait();
            controllo=false;

        } else if(CognomeField.getText().equals(""))
            {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERRORE");
                alert.setContentText("INSERIRE IL COGNOME");
                alert.showAndWait();
                controllo=false;


            } else if (NaField.getText().equals(""))
               {
                   Alert alert=new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("ERRORE");
                   alert.setContentText("INSERIRE LA NAZIONALITA'");
                   alert.showAndWait();
                   controllo=false;


               } else if (EField.getText().equals(""))
                  {
                       Alert alert=new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("ERRORE");
                       alert.setContentText("INSERIRE LA TUA E-MAIL");
                       alert.showAndWait();
                      controllo=false;

                  } else if (PassField.getText().equals(""))
                     {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("ERRORE");
                            alert.setContentText("INSERIRE LA PASSWORD");
                            alert.showAndWait();
                            controllo = false;
                     }



        if(controllo)
        {

            try
            {
               utente.Creautente(NomeField.getText(),CognomeField.getText(),EField.getText(),NaField.getText(),PassField.getText());
               utentedao.initialize(utente);

               utentedao.insert(utente);

            }
            catch (SQLException e)
            {
                // se avviene ciò significa che l' email scelta è gia presente all' interno del db
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERRORE");
                alert.setContentText("CONTROLLARE CHE TUTTI I CAMPI SIANO INSERITI, OPPURE CAMBIARE EMAIL \n POTREBBE ESSERE GIA UTILIZZATA.");
                alert.showAndWait();

                throw new RuntimeException(e);
            }

            catch (RuntimeException e)
            {
                controllo=false;
            }


                if(controllo)
                {
                    Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();

                    MyStage myStage = new MyStage();
                    myStage.CreateStage("HOME_page.fxml");
            }
        }
    }
}










