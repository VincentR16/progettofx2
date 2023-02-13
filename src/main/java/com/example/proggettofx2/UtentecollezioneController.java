
package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UtentecollezioneController extends MenuController implements Initializable {

    @FXML
    private ListView<String> VistaUtente;
    private MainController Main;


    @FXML
    void Back(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {

        Main.getStage().close();
        Main.CreateStage("Collezionipage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Main=MainController.getInstance();

        Main.listView(VistaUtente);


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
                    //todo fare query che manda al db
                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }
}
