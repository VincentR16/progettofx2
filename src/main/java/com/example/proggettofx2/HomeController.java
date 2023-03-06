package com.example.proggettofx2;


import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.Home;
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


public class HomeController extends MenuController implements Initializable
{
        // gestisce la home page, ovvero dove si visualizzano le proprie foto

@FXML
public ScrollPane pannel;
private Connection Main;


        @FXML
        void FiltraButton(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
        {
                Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                MyStage myStage = new MyStage();
                myStage.CreateStage("Filtrapage.fxml");
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle)
        {
                Home home;

                try {
                      home = new Home();
                } catch (SQLException | IOException e) {throw new RuntimeException(e);}

                pannel.setContent(home.getGridPane());// viene impostata la griglia come contenuto dello scroll pane
        }
}




