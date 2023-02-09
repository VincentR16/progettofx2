
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
    private MainController Main;




    @FXML
    void BAggiungifoto(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        Main.getStage().close();
        Main.CreateStage("Aggiungifotopage.fxml");
        Main.getStage().setWidth(920);
        Main.getStage().setHeight(620);
    }


    @FXML
    void BProfile(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        Main.getStage().close();
        Main.CreateStage("Profile-page.fxml");
    }


    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {

        Main.getStage().close();

        Main.CreateStage("Firstpage.fxml");
        Main.getStage().setHeight(450);
        Main.getStage().setWidth(655);
        Main.getStage().setResizable(false);

        Utente.getUtente().setdefault();

    }

    @FXML
    void Bvideo(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        Main.getStage().close();
        Main.CreateStage("Videopage.fxml");
    }

    @FXML
    void BCestino(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        Main.getStage().close();
        Main.CreateStage("Trashpage.fxml");
    }


    @FXML
    void BbackToHome(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {
        Main.getStage().close();
        Main.CreateStage("HOME_page.fxml");
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
