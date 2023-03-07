package com.example.proggettofx2;

import com.example.proggettofx2.DAO.CollezioniDao;
import com.example.proggettofx2.entita.Collezioni;
import com.example.proggettofx2.entita.Fotografie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;


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



    @FXML
    void BnewCollection(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        MyStage myStage = new MyStage();
        myStage.CreateStage("Creacollezionepage.fxml");
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
            Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            MyStage myStage= new MyStage();
            myStage.CreateStage("Add2Collectionpage.fxml");
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
            Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            MyStage myStage = new MyStage();
            myStage.CreateStage("UtenteCollezione.fxml");
        }
    }


   @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
   {
       Collezioni collezioni= null;
       Fotografie fotografie= null;

       try {
           fotografie = Fotografie.getInstance();

       } catch (SQLException | IOException e) {throw new RuntimeException(e);}



        combobox.setPromptText("Scegli la libreria");

       try {

           collezioni.SetCombo(combobox);

       } catch (SQLException e) {throw new RuntimeException(e);}

       Fotografie finalFotografie = fotografie;
       combobox.setOnAction((ActionEvent er)->
            {
                Collezioni collection;

                try {
                     finalFotografie.setScelta(combobox.getSelectionModel().getSelectedItem());

                     CollezioniDao collezioniDao= new CollezioniDao();
                     collezioniDao.initialize(collezioni);


                     collection = new Collezioni();

                } catch (SQLException | IOException e) {throw new RuntimeException(e);}
                // collezioni.Setscelta(combobox.getSelectionModel().getSelectedItem());

                pannel.setContent(collection.setAction());                                                                             // imposto la griglia come contenuto dello scroll pane

            });
   }

}
