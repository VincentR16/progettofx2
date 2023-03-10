package com.example.proggettofx2;

import com.example.proggettofx2.DAO.FotografieDAO;
import com.example.proggettofx2.DAO.SoggettiDao;
import com.example.proggettofx2.DAO.Utentedao;
import com.example.proggettofx2.entita.Fotografie;
import com.example.proggettofx2.entita.SoggettieLuoghi;
import com.example.proggettofx2.entita.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AddController extends  MenuController implements Initializable {
    //gestisce la pagine dell aggiunta delle foto
    @FXML
    private ListView<String> VistaUtente;
    @FXML
    private TextField CityField;
    @FXML
    private TextField DeviceField;
    @FXML
    private TextField Pathfield;
    @FXML
    private ComboBox<String> Subjectbox;
    private boolean Controllo = true;
    private List<String> list;


    private Fotografie fotografie;
    private FotografieDAO fotografieDAO;
    private SoggettieLuoghi soggettieLuoghi;
    private SoggettiDao soggettiDao;


    @FXML
    void BpickImage(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.gif"));


        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        // apre la cartella dell 'utente e gli permette di scegliere le foto
        //all'interno di showopenadialog si inserisce lo stage corrente

        if (file != null) {
            String filePath = file.getPath();
            //viene mostrato il path a schermo
            Pathfield.setText(filePath);
        }
    }


    @FXML
    void aggiungifoto(@SuppressWarnings("UnusedParameters") ActionEvent event) throws SQLException, IOException {


        if (CityField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setContentText("Inserire una città");
            alert.showAndWait();
            Controllo = false;

        } else if (DeviceField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setContentText("Inserire un device con il quale la foto è stata scattata");
            alert.showAndWait();
            Controllo = false;

        } else if (Pathfield.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setContentText("SCEGLIERE UNA FOTO");
            alert.showAndWait();
            Controllo = false;

        } else if (Subjectbox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setContentText("Scegliere il soggetto della foto");
            alert.showAndWait();
            Controllo = false;

        }


        if (Controllo)
        {

            this.Addphoto(Pathfield.getText(), CityField.getText(),Subjectbox.getSelectionModel().getSelectedItem(), DeviceField.getText(), list);
            //metodo che aggiunge le foto al db


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            MyStage myStage1 = new MyStage();
            myStage1.CreateStage("Aggiungifotopage.fxml");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        //servono per poter chimare initialize in un altro metodo

        list = new ArrayList<>();
        //lista che tiene conto di tutti gli utenti presenti nella foto

        soggettieLuoghi = new SoggettieLuoghi();
        soggettiDao = new SoggettiDao();
        fotografieDAO = new FotografieDAO();


        try
        {
            fotografie = Fotografie.getInstance();



            soggettiDao.initialize(soggettieLuoghi);

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}


        soggettieLuoghi.setBox(Subjectbox);

        Utentedao utentedao = new Utentedao();
        Utente utente = Utente.getUtente();

        try
        {

            utente.vistautente(VistaUtente,utentedao.search(utente));

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}


        //viene impostata vistautente(listview)


        VistaUtente.setOnMouseClicked(event ->
                //ad ogni click sulle email visualizzate, l'utente della rispettiva email, viene aggiunto come presente nella fotografia
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();
            if (item != null) {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aggiungi Soggetto");
                alert.setHeaderText("Aggiungere " + item + " come utente presente nella tua foto?");
                alert.setContentText(item);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    list.add(item);
                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }


    public void Addphoto(String path, String citta,String soggetto, String dispositivo, List<String> list) throws SQLException, IOException
    {
        List<String> list1 = new ArrayList<>();


        list1.add(path);
        list1.add(dispositivo);
        list1.add(citta);
        list1.add(dispositivo);
        list1.add(soggetto);

        fotografie.setInformazioni(list1);
        fotografie.setUtentiscelti(list);

        fotografie.AggiungiFoto(path);
        fotografieDAO.insert(fotografie);
        fotografieDAO.initialize(fotografie);
    }

}
















// altro metodo piu lungo che non funzionava alla perfezione, poiche si deve aggiungere una variabile che tiene conto se la foto è stata eliminata o meno.
// non funzionava perchè il change listener ascolta tutti i cambiamentni di una stringa, quando viene eliminata lo registra come cambiamento e
// fa comparire l'alert 2 volte(quando veniva cliccata e quando veniva eliminata) di seguito al secondo alert veniva eliminata un altra stringa, non trovando piu quella di riferimento;



       /* VistaUtente.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old, String nuovo) {



                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("AGGIUNGI UTENTE ");
                alert.setContentText("VUOI AGGIUNGERE QUESTO UTENTE ALLA TUA FOTO? \n Dopo che compare il secondo alert premere X");


                Optional<ButtonType> result=alert.showAndWait();

                if (result.get() == ButtonType.OK ) {

                    int i=0;
                    while(!(nuovo.equals(VistaUtente.getItems().get(i))))
                    {i++;}

                    VistaUtente.getItems().remove(i);
                    System.out.println(i);

                    Selected.add(nuovo);
                    MainController.getInstance().SetList(Selected);
                    /
                }
            }
        });



    }

       */
