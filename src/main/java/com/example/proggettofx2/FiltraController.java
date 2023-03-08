package com.example.proggettofx2;

import com.example.proggettofx2.DAO.FotografieDAO;
import com.example.proggettofx2.entita.Fotofiltrate;

import com.example.proggettofx2.entita.Fotografie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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


            Fotografie fotografie = Fotografie.getInstance();

            FotografieDAO fotografieDAO = new FotografieDAO();
            fotografieDAO.search(fotografie,scelta,textfiled.getText());

            Fotofiltrate fotofiltrate = new Fotofiltrate();
            pannel.setContent(fotofiltrate.setGridpane());
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


        combobox.getItems().add("Luogo");
        combobox.getItems().add("Soggetto");
        combobox.setPromptText("Scegliere qui");


        try {

            Fotofiltrate fotofiltrate = new Fotofiltrate();
            fotofiltrate.top3(labelprimo,labelsec,labelterz);

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}


    }




    public GridPane setGridpane()
    {
        GridPane gridPane = new GridPane();// creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        int i=0;
        int j=0;

        for (ImageView view : fotofiltrate) {

            gridPane.add(view, j, i);

            j++;

            if (j > 4) {
                j = 0;
                i++;
            }
        }


        return gridPane;
    }







}
