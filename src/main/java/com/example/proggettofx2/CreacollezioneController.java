package com.example.proggettofx2;

import com.example.proggettofx2.entita.Collezioni;
import com.example.proggettofx2.entita.Fotografie;
import com.example.proggettofx2.entita.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreacollezioneController extends MenuController implements Initializable
{
    //gestisce la creazione di nuove collezioni

    private boolean controllo= true;
    private String utente;
    private MainController Main;
    @FXML
    private TextField label;
    @FXML
    private ListView<String> VistaUtente;



    @FXML
    void BnewCollection(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException, SQLException {

        if(utente == null)
        {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("IMPORTANTE");
            alert.setContentText("Scegliere un utente");
        }
        else {

            String nomecollezione=label.getText();

            if(nomecollezione==null)
            {
                Alert alert =new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("IMPORTANTE");
                alert.setContentText("Scegliere il nome della collezione");
            }
             else{

                Collezioni collection = new Collezioni();
                collection.creaCollezione(utente,nomecollezione);



                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Aggiungi foto");
                alert.setContentText("VUOI AGGIUNGERE TUTTE LE TUE FOTO E QUELLE DI " + utente + " ALLA COLLEZIONE CREATA? ");
                //se l'utente sceglie si, allora  tutte le foto sue e quelle dell'utente scelto, vengono aggiunte alla collezione


                Optional<ButtonType> result = alert.showAndWait();

                if(result.get()==ButtonType.OK)
                {
                    Fotografie foto = Fotografie.getInstance();
                    foto.fotoincollezione(utente);
                }


                Main.getStage().close();
                Main.CreateStage("Collezionipage.fxml");
            }
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        Main=MainController.getInstance();

        Utente.getUtente().vistautente(VistaUtente);

        VistaUtente.setOnMouseClicked(event ->
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();

            //nella creazione di una collezione si puo scegliere solo un utente(item)
            if (item != null && controllo)
            {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aggiungi utente");
                alert.setHeaderText("Aggiungere "+item+" come utente nella tua collezione?");
                alert.setContentText(item);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    controllo=false;
                    utente=item;
                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }
}