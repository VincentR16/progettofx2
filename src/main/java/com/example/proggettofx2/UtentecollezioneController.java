
package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UtentecollezioneController implements Initializable {


    @FXML
    private ListView<String> VistaUtente;

    @FXML
    void BAggiungifoto(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Aggiungifotopage.fxml");
        MainController.getInstance().getStage().setWidth(920);
        MainController.getInstance().getStage().setHeight(620);
    }


    @FXML
    void BProfile(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Profile-page.fxml");
    }


    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();

        MainController C = MainController.getInstance();

        C.CreateStage("Firstpage.fxml");
        C.getStage().setHeight(450);
        C.getStage().setWidth(655);
        C.getStage().setResizable(false);

        Utente.getUtente().setdefault();

    }

    @FXML
    void Bvideo(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Videopage.fxml");
    }

    @FXML
    void BCestino(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Trashpage.fxml");
    }


    @FXML
    void BbackToHome(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("HOME_page.fxml");
    }

    @FXML
    void MouseEntered(MouseEvent event) {
        javafx.scene.control.Button button = (javafx.scene.control.Button) (event.getSource());
        button.setStyle("-fx-background-color:  #0C1538");
    }

    @FXML
    void MouseExited(MouseEvent event) {
        javafx.scene.control.Button button = (Button) (event.getSource());
        button.setStyle("-fx-background-color:  #183669 ");
    }

    @FXML
    void Back(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {

        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Collezionipage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainController.getInstance().listView(VistaUtente);


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
