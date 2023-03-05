package com.example.proggettofx2;


import com.example.proggettofx2.entita.Trash;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TrashController extends MenuController implements Initializable
{
    //gestisce la pagina del cestino
    @FXML
    public ScrollPane pannel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Trash trash;
        try {

            trash = new Trash();

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}

        pannel.setContent(trash.getGridPane());
    }

}

