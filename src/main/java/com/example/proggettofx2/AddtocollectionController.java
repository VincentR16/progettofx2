package com.example.proggettofx2;

import com.example.proggettofx2.entita.Collezioni;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddtocollectionController extends MenuController implements Initializable
{
    //gestisce l'aggiunta della foto nella collezione
    @FXML
    public ScrollPane pannel;
    public MainController main;


    @FXML
    void Back(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {

        main.getStage().close();
        main.CreateStage("Collezionipage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         main =MainController.getInstance();

        Collezioni collezioni=new Collezioni();

        try {
            pannel.setContent(collezioni.aggiungifoto());

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}

    }
}
