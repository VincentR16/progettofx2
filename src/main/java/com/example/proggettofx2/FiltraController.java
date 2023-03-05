package com.example.proggettofx2;

import com.example.proggettofx2.entita.Fotofiltrate;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class FiltraController extends MenuController implements Initializable
{
    //gestisce la pagina che filtra le foto
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private Label labelprimo;
    @FXML
    private Label labelsec;
    @FXML
    private Label labelterz;
    @FXML
    private ScrollPane pannel;
    @FXML
    private TextField textfiled;



    @FXML
    void Bcerca() throws SQLException, IOException {

        String scelta;

        if(combobox.getSelectionModel().getSelectedItem()==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("IMPORTANTE");
            alert.setContentText("Scegliere la categoria della ricerca");

            alert.show();

        }
        else{

            if (combobox.getSelectionModel().getSelectedItem().equals("Soggetto")){scelta="stesso_soggetto";}else {scelta="stesso_luogo";}
                //scelta viene impostata, come il nome della funzione del db,


            Fotofiltrate fotofiltrate = new Fotofiltrate(scelta,textfiled.getText());
            pannel.setContent(fotofiltrate.setGridpane());
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


        combobox.getItems().add("Luogo");
        combobox.getItems().add("Soggetto");
        combobox.setPromptText("Scegliere qui");

        Fotofiltrate fotofiltrate = new Fotofiltrate();
        try {

            fotofiltrate.top3(labelprimo,labelsec,labelterz);

        } catch (SQLException e) {throw new RuntimeException(e);}


    }
}
