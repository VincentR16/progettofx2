package com.example.proggettofx2;

import com.example.proggettofx2.DAO.CollezioniDao;
import com.example.proggettofx2.entita.Collezioni;
import com.example.proggettofx2.entita.Fotografie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class CollezioniController extends MenuController implements Initializable
{
    //gestisce lo stage delle collezioni
    @FXML
    public ScrollPane pannel;
    @FXML
    private ComboBox<String> combobox;

    private Collezioni collezioni;
    private Fotografie fotografie;
    private CollezioniDao collezioniDao;



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
       try {
            collezioni= new Collezioni();
            fotografie = Fotografie.getInstance();
           collezioniDao= new CollezioniDao();

       } catch (SQLException | IOException e) {throw new RuntimeException(e);}



        combobox.setPromptText("Scegli la libreria");


       try
       {

           collezioni.setNomi(collezioniDao.search(collezioni));
           SetCombo(combobox,collezioni);

       } catch (SQLException | IOException e) {throw new RuntimeException(e);}


       combobox.setOnAction((ActionEvent er)->
            {


                try {


                    fotografie.setScelta(combobox.getSelectionModel().getSelectedItem());
                    collezioniDao.initialize(collezioni); //todo trovare modo per passare fotografia come parametro
                    pannel.setContent(setAction());

                // imposto la griglia come contenuto dello scroll pane
                } catch (SQLException | IOException e) {throw new RuntimeException(e);}

            });
   }


    public void SetCombo(ComboBox<String> comboBox,Collezioni collezioni) throws SQLException, IOException
    {
        for (String s : collezioni.getNomicollezione()) {
            comboBox.getItems().add(s);
        }
    }



    public GridPane setAction() throws SQLException, IOException
    {

        ImageView imageView;



        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int i = 0;
        int j = 0;


        for (ImageView view : collezioni.getListused()) {
            imageView = view;

            gridPane.add(imageView, j, i);

            j++;
            if (j > 4) {
                j = 0;
                i++;
            }


            imageView.setOnMouseClicked((MouseEvent er) ->

                    //  semplice listner per poter rendere private le foto ogni qual volta vengano cliccate
            {                                                                                                                       // per fare cio uso un alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("FOTO");
                alert.setContentText("VUOI RENDERE PRIVATA LA FOTO?");


                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                    try {

                        gridPane.getChildren().remove(this.privatephoto(er));
                        //foto.setCollezione(this.getScelta());


                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }


        return gridPane;

    }

    private Node privatephoto(MouseEvent er) throws SQLException, IOException
    {

        int value = (int) ((Node) er.getSource()).getUserData();
        Node node = (Node) er.getSource();

        collezioniDao.delete(collezioni,value);

        fotografie.rimuoviCollezione(value);

        return node;
    }






}
