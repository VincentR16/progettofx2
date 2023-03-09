package com.example.proggettofx2;

import com.example.proggettofx2.DAO.FotografieDAO;


import com.example.proggettofx2.DAO.SoggettiDao;
import com.example.proggettofx2.entita.Fotografie;
import com.example.proggettofx2.entita.SoggettieLuoghi;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
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

    private Fotografie fotografie;
    private FotografieDAO fotografieDAO;
    private SoggettiDao soggettiDao;

    @FXML
    void Bcerca() throws SQLException, IOException {

        String scelta;
        fotografie.resetfiltra();

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



            fotografie.setSceltaricerca(scelta);
            fotografie.setRicerca(textfiled.getText());
            fotografieDAO.search(fotografie);

            pannel.setContent(setGridpane());
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        combobox.getItems().add("Luogo");
        combobox.getItems().add("Soggetto");
        combobox.setPromptText("Scegliere qui");


        try
        {

            fotografie=Fotografie.getInstance();
            fotografieDAO= new FotografieDAO();
            soggettiDao= new SoggettiDao();


            top_3(labelprimo,labelsec,labelterz);

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}


    }




    public GridPane setGridpane()
    {

        GridPane gridPane = new GridPane();// creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        int i=0;
        int j=0;

        for (ImageView view : fotografie.getFotofiltrate())
        {

            gridPane.add(view, j, i);

            j++;

            if (j > 4) {
                j = 0;
                i++;
            }
        }


        return gridPane;
    }

    public void top_3(Label labelprimo,Label labelsec ,Label labelterz) throws SQLException, IOException
    {
        SoggettieLuoghi soggettieLuoghi = new SoggettieLuoghi();
        Iterator<String> it = soggettiDao.search(soggettieLuoghi).listIterator();

        if(it.hasNext()){labelprimo.setText(it.next()+" "+(it.next())); }
        if(it.hasNext()){labelsec.setText(it.next()+" "+(it.next())); }
        if(it.hasNext()){labelterz.setText(it.next()+" "+(it.next())); }
    }
}
