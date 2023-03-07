package com.example.proggettofx2;

import com.example.proggettofx2.entita.Collezioni;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddtocollectionController extends MenuController implements Initializable
{
    //gestisce l'aggiunta della foto nella collezione
    @FXML
    public ScrollPane pannel;



    @FXML
    void Back(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {

        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        MyStage stage1 = new MyStage();
        stage1.CreateStage("Collezionipage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


        Collezioni collezioni= null;
        try {

            collezioni = new Collezioni();

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}

        try {
            pannel.setContent(collezioni.aggiungifoto());

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}

    }
}
