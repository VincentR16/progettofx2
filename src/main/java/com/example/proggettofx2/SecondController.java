package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.*;

public class SecondController {
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
                MainController C  =MainController.getInstance();
            try
            {
                PreparedStatement stmt;
                stmt=C.DoPrepared("call crea_utente(?,?,?,?,?)");

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
                cst.registerOutParameter(1, Types.INTEGER);
                cst.setString(2,EField.getText());

                cst.execute();

                id_utente=cst.getInt(1);


                Utente.getUtente(NomeField.getText(), CognomeField.getText(),NaField.getText(),EField.getText(),PassField.getText(),id_utente);



                C.Closeall();
                C.getStage().close();
                C.CreateStage("HOME_page.fxml");
            }
        }
    }
}










