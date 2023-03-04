package com.example.proggettofx2;

import com.example.proggettofx2.entita.Collezioni;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CollezioniController extends MenuController implements Initializable
{
    //gestisce lo stage delle collezioni
    @FXML
    public ScrollPane pannel;

    @FXML
    private ComboBox<String> combobox;

    private MainController Main;


    @FXML
    void BnewCollection(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Creacollezionepage.fxml");
    }
    @FXML
    void BaddCollection(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        if(combobox.getSelectionModel().getSelectedItem()==null)
        {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Scegliere una collezione");
            alert.setTitle("IMPORTANTE");

            alert.show();
        }else
        {
            Main.getStage().close();
            Main.CreateStage("Add2Collectionpage.fxml");
        }
    }

    @FXML
    void BaddusersCollection(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        if(combobox.getSelectionModel().getSelectedItem()==null)
        {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Scegliere una collezione");
            alert.setTitle("IMPORTANTE");

            alert.show();
        }else
        {
            Main.getStage().close();
            Main.CreateStage("UtenteCollezione.fxml");
        }
    }


   @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
   {
       Collezioni collezioni= new Collezioni();

       Main=MainController.getInstance();

        combobox.setPromptText("Scegli la libreria");

       try {

           collezioni.SetCombo(combobox);

       } catch (SQLException e) {throw new RuntimeException(e);}

       combobox.setOnAction((ActionEvent er)->
            {
                collezioni.Setscelta(combobox.getSelectionModel().getSelectedItem());

                pannel.setContent(collezioni.setAction());                                                                             // imposto la griglia come contenuto dello scroll pane

            });
   }

}
