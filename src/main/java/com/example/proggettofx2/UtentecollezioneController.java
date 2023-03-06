
package com.example.proggettofx2;

import com.example.proggettofx2.entita.Collezioni;
import com.example.proggettofx2.entita.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UtentecollezioneController extends MenuController implements Initializable {
    //gestisce lo stage per poter aggiungere altri utenti alla collezione

    @FXML
    private ListView<String> VistaUtente;



    @FXML
    void Back(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {

        MyStage myStage= new MyStage();

        myStage.getStage().close();
        myStage.CreateStage("Collezionipage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Utente.getUtente().vistautente(VistaUtente);


        VistaUtente.setOnMouseClicked(event ->
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();
            if (item != null) {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aggiungi utente");
                alert.setHeaderText("Aggiungere " + item + " come utente nella tua collezione?");
                alert.setContentText(item);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    Collezioni collezioni= new Collezioni();
                    collezioni.aggiungiutente();

                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }
}
