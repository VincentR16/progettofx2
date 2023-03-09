package com.example.proggettofx2;

import com.example.proggettofx2.DAO.AdminDao;
import com.example.proggettofx2.DAO.Utentedao;
import com.example.proggettofx2.entita.Admin;
import com.example.proggettofx2.entita.Utente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable
{
    //gestisce lo stage dell 'admin

    @FXML
    private Label Labelutenti;
    @FXML
    private ListView<String> VistaUtente;
    @FXML
    private Label labelfoto;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


         Admin admin = new Admin(labelfoto,Labelutenti);
         AdminDao adminDao = new AdminDao();

        try
        {
            adminDao.search(admin);}



        catch (SQLException e) {throw new RuntimeException(e);}



        Utentedao utentedao = new Utentedao();
        Utente utente = Utente.getUtente();

        try
        {

            utente.vistautente(VistaUtente,utentedao.search(utente));

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}
        //imposta vistautente(listview)


        VistaUtente.setOnMouseClicked(event ->

                //ad ogni click viene eliminato l' utente dal db
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();
            if (item != null) {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Elimina utente");
                alert.setHeaderText("Eliminare " + item + " dal database");
                alert.setContentText(item);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    //viene eliminato l'utente


                    try
                    {
                        admin.setSelectedusers(item);
                        adminDao.insert(admin);

                    } catch (SQLException e) {throw new RuntimeException(e);}


                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }
}
