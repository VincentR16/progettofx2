package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class HelloController  {

    @FXML
    private PasswordField assfield1;

    @FXML
    private TextField txtFIELD;

    @FXML
    void BottonAccedi(ActionEvent event) throws IOException {
        boolean controllo=true;

        if(txtFIELD.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setContentText("INSERIRE EMAIL ,RIPROVARE");
            alert.showAndWait();
            controllo=false;

        }else if(assfield1.getText().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERRORE");
                alert.setContentText("INSERIRE LA PASSWORD, RIPROVARE");
                alert.showAndWait();
                controllo=false;
            }

        if(controllo)
        {
            boolean controllo2=true;

            String E=txtFIELD.getText();
            String P=assfield1.getText();

            MainController C= MainController.getInstance();
            ResultSet rs =C.find_users();

            try {

                while (rs.next())
                {

                    if (E.equals(rs.getString("email")) && P.equals(rs.getString("password")))
                    {
                        controllo2=false;
                        Utente.getUtente(rs.getString("nome"),rs.getString("cognome"),rs.getString("nazionalità"),rs.getString("email"),rs.getString("password"),rs.getInt("id_utente"));

                        C.getStage().close();
                        C.CreateStage("HOME_page.fxml");

                       // Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    }

                }
                rs.close();
                C.Closeall();

                if(controllo2)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERRORE");
                    alert.setContentText("Email o Password errati");
                    alert.showAndWait();
                }


            }catch(SQLException e){throw new RuntimeException(e);}
        }

    }

    @FXML
    void BottonAd(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException {



        TextInputDialog Dialog =new TextInputDialog("AMMINISTRATORE");
        Dialog.setTitle("ACCEDI COME AMMINISTRATORE");
        Dialog.setContentText("INSERISCI LA TUA PASSWORD");

        Optional<String> Pass =  Dialog.showAndWait();



        MainController C= MainController.getInstance();
        ResultSet rs =C.find_Admin();

        try {

           rs.next();


                if (Pass.get().equals(rs.getString("password")))
                {
                    C.getStage().close();
                    C.CreateStage("Adminpage.fxml");

                    C.getStage().setHeight(570);
                    C.getStage().setWidth(600);


                } else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Errore");
                    alert.setContentText("Password Errata");

                    alert.show();
                }

                rs.close();


        }catch (SQLException e){throw  new RuntimeException(e);}


    }

    @FXML
    void BottonR(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController main= MainController.getInstance();

        main.getStage().close();
        main.CreateStage("Second page.fxml");

        main.getStage().setTitle("Welcome");
        main.getStage().setResizable(false);

        main.getStage().setWidth(700);
        main.getStage().setHeight(500);
    }
}
