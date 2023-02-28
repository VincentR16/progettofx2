package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AddController extends  MenuController implements Initializable
{
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
    private boolean Controllo=true;
    private List<String> list;
    private MainController Main;
    private  URL myurl;
    private ResourceBundle myres;


    @FXML
    void BpickImage(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.gif"));


        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        // apre la cartella dell 'utente e gli permette di scegliere le foto
        //all'interno di showopenadialog si inserisce lo stage corrente

        if (file != null)
        {
            String filePath = file.getPath();
            //viene mostrato il path a schermo
            Pathfield.setText(filePath);
        }

    }

    @FXML
    void aggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws SQLException {


        if(CityField.getText().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERRORE");
                alert.setContentText("Inserire una città");
                alert.showAndWait();
                Controllo=false;

            }else if(DeviceField.getText().equals(""))
                     {
                         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("ERRORE");
                         alert.setContentText("Inserire un device con il quale la foto è stata scattata");
                         alert.showAndWait();
                         Controllo=false;

                     }else  if(Pathfield.getText().equals(""))
                                    {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("ERRORE");
                                        alert.setContentText("SCEGLIERE UNA FOTO");
                                        alert.showAndWait();
                                        Controllo=false;

                                    }else if ( Subjectbox.getSelectionModel().getSelectedItem()==null)
                                            {
                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                alert.setTitle("ERRORE");
                                                alert.setContentText("Scegliere il soggetto della foto");
                                                alert.showAndWait();
                                                Controllo=false;

                                            }


        if(Controllo)
        {

            Main.addPhoto(Pathfield.getText(),DeviceField.getText(),CityField.getText(),Subjectbox.getSelectionModel().getSelectedItem(),list);
            //metodo che aggiunge le foto al db
            Main.getStage().close();

            initialize(myurl,myres);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        myurl=url;
        myres=resourceBundle;
        //servono per poter chimare initialize in un altro metodo

        list=new ArrayList<>();
        //lista che tiene conto di tutti gli utenti presenti nella foto

        Main = MainController.getInstance();

            ResultSet rs= Main.DoQuery("select categoria from soggetto");
            //restituisce la categorie e vengono inserite nel Subjectbox

            try {
                while (rs.next())
                {
                    Subjectbox.getItems().add(rs.getString("categoria"));
                }
                rs.close();
                Main.Closeall();

            }catch (SQLException e){e.printStackTrace();}



        Main.listView(VistaUtente);
            //viene impostata vistautente(listview)


        VistaUtente.setOnMouseClicked(event ->
                //ad ogni click sulle email visualizzate, l'utente della rispettiva email, viene aggiunto come presente nella fotografia
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();
            if (item != null) {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aggiungi Soggetto");
                alert.setHeaderText("Aggiungere "+item+" come utente presente nella tua foto?");
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
