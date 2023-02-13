package com.example.proggettofx2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable
{

    @FXML
    private Label Labelutenti;
    @FXML
    private ListView<String> VistaUtente;
    @FXML
    private Label labelfoto;
    private MainController Main;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Main =MainController.getInstance();

        Main.listView(VistaUtente);

        ResultSet rs = Main.DoQuery("select * from numero_totale_fotografie_e_utenti");

        try {
            rs.next();

            labelfoto.setText(""+rs.getInt("totale_foto"));
            Labelutenti.setText(""+rs.getInt("totale_utenti"));



        } catch (SQLException e) {throw new RuntimeException(e);}




        VistaUtente.setOnMouseClicked(event ->
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();
            if (item != null) {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Elimina utente");
                alert.setHeaderText("Eliminare " + item + " dal database");
                alert.setContentText(item);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {


                    try
                    {
                        CallableStatement cst = Main.Callprocedure("{?=call recupera_id_utente(?)}");
                        cst.registerOutParameter(1, Types.INTEGER);
                        cst.setString(2,item);

                        cst.execute();

                        PreparedStatement pst = Main.Callprocedure("call elimina_utente(?)");
                        pst.setInt(1,cst.getInt(1));

                        pst.execute();

                        Main.Closeall();

                    } catch (SQLException e) {throw new RuntimeException(e);}

                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }
}
