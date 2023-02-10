package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AddController extends  MenuController implements Initializable
{
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


    @FXML
    void BpickImage(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.gif"));


        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null)
        {
            String filePath = file.getPath();
            Pathfield.setText(filePath);
        }

    }

    @FXML
    void aggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws SQLException, IOException {


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

            Main.getStage().close();
            Main.CreateStage("Aggiungifotopage.fxml");
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        list=new ArrayList<>();

        Main = MainController.getInstance();

            ResultSet rs= Main.DoQuery("select categoria from soggetto");

            try {
                while (rs.next())
                {
                    Subjectbox.getItems().add(rs.getString("categoria"));
                }
                rs.close();
                Main.Closeall();

            }catch (SQLException e){}



        Main.listView(VistaUtente);


        VistaUtente.setOnMouseClicked(event ->
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











// altro metodo piu lungo che non funzionava alla perfezione, poiche si deve aggiungere una variabile che tiene conto se la foto è stata eliminata o meno,
// poiche il change listener ascolta tutti i cambiamentni di una stringa, e per questo quando viene eliminata lo registra come cambiamento e
// fa comparire l'alert 2 volte di seguito eliminando un altra stringa, non trovando piu quella di riferimento;



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
