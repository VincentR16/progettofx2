package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreacollezioneController implements Initializable
{

    private boolean controllo= true;
    private String utente;

    private MainController Main;
    @FXML
    private TextField label;
    @FXML
    private ListView<String> VistaUtente;


    @FXML
    void BAggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Aggiungifotopage.fxml");
        Main.getStage().setWidth(920);
        Main.getStage().setHeight(620);
    }


    @FXML
    void BProfile(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Profile-page.fxml");
    }


    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        Main.getStage().close();

        Main.CreateStage("Firstpage.fxml");
        Main.getStage().setHeight(450);
        Main.getStage().setWidth(655);
        Main.getStage().setResizable(false);

        Utente.getUtente().setdefault();

    }

    @FXML
    void Bvideo(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Videopage.fxml");
    }
    @FXML
    void BCestino(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Trashpage.fxml");
    }


    @FXML
    void BbackToHome(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("HOME_page.fxml");
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
    void BnewCollection(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException, SQLException {

        if(utente == null)
        {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("IMPORTANTE");
            alert.setContentText("Scegliere un utente");
        }else{

            String nomecollezione=label.getText();

            if(nomecollezione==null)
            {
                Alert alert =new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("IMPORTANTE");
                alert.setContentText("Scegliere il nome della collezione");
            }else{

                Main = MainController.getInstance();


                PreparedStatement pst = Main.DoPrepared("call crea_collezione_condivisa(?,?,?)");
                pst.setInt(1,Utente.getUtente().getIdutente());
                pst.setString(2,utente);
                pst.setString(3,nomecollezione);

                pst.execute();

                Main.Closeall();
                pst.close();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Aggiungi foto");
                alert.setContentText("VUOI AGGIUNGERE TUTTE LE TUE FOTO E QUELLE DI " + utente + " ALLA COLLEZIONE CREATA? ");


                Optional<ButtonType> result = alert.showAndWait();

                if(result.get()==ButtonType.OK)
                {
                    PreparedStatement pst1 = Main.DoPrepared("call inserisci_fotografie_in_collezione_condivisa(?,?)");
                    pst1.setInt(1,Utente.getUtente().getIdutente());
                    pst1.setString(2,utente);

                    pst1.execute();

                    Main.Closeall();
                    pst1.close();
                }


                Main.getStage().close();
                Main.CreateStage("Collezionipage.fxml");
            }
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        Main=MainController.getInstance();

        Main.listView(VistaUtente);


        VistaUtente.setOnMouseClicked(event ->
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();
            if (item != null && controllo)
            {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aggiungi utente");
                alert.setHeaderText("Aggiungere "+item+" come utente nella tua collezione?");
                alert.setContentText(item);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    controllo=false;
                    utente=item;
                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }
}