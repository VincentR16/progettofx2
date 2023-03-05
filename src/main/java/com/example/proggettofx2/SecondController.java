package com.example.proggettofx2;

import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.MyStage;
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
    void Clickcrea(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException, SQLException {

         boolean controllo=true;
         // controlla l'inserimento di tutti i campi


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
                com.example.proggettofx2.entita.Connection C  = new Connection();
            try
            {
                PreparedStatement stmt;
                stmt=C.DoPrepared("call crea_utente(?,?,?,?,?)");

                //viene creato un utente all' interno del db

                stmt.setString(1,NomeField.getText());
                stmt.setString(2,CognomeField.getText());
                stmt.setString(3,EField.getText());
                stmt.setString(4,NaField.getText());
                stmt.setString(5,PassField.getText());

                stmt.execute();

                C.Closeall();
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
                int id_utente;

                CallableStatement cst=C.Callprocedure("{?=call recupera_id_utente(?)}");
                // viene recuperato l'id dell 'utente cche si è appena registrato

                cst.registerOutParameter(1, Types.INTEGER);
                cst.setString(2,EField.getText());

                cst.execute();

                id_utente=cst.getInt(1);


                Utente.getUtente(NomeField.getText(), CognomeField.getText(),NaField.getText(),EField.getText(),PassField.getText(),id_utente);
                //viene creato un oggetto utente, che è un singleton poichè come in ogni app, si accede solo un utente alla volta per ogni dispositivo


                C.Closeall();

                    Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();

                    MyStage myStage = new MyStage();
                    myStage.CreateStage("HOME_page.fxml");
            }
        }
    }
}










