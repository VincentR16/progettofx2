package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddtocollectionController extends MenuController implements Initializable
{
    @FXML
    public ScrollPane pannel;
    private MainController Main;


    @FXML
    void Back(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException {

        Main.getStage().close();
        Main.CreateStage("Collezionipage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Main = MainController.getInstance();

        PreparedStatement ps;
        ResultSet rs;                                                                                                                     //query gestita dal controller principale che prende tutte le foto dell'utente loggato

        try {
            ps= Main.DoPrepared("Select * from foto_non_presenti_in_collezione_condivisa(?,?)");
            ps.setInt(1,Utente.getUtente().getIdutente());
            ps.setString(2, Main.getScelta());

            rs = ps.executeQuery();

        } catch (SQLException e) {throw new RuntimeException(e);}


        GridPane gridPane = new GridPane();                                                                                                      // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        try {
            int i = 0;
            int j = 0;

            while (rs.next())
            {
                ImageView imageView = Main.setImageview(rs.getBytes("val_foto"), rs.getInt("id_foto"));

                gridPane.add(imageView, j, i);

                j++;                                                                                                                    // rispetto alle matrici qui si mette prima la colonna e poi la riga
                if (j > 4) {j = 0;i++;}


                imageView.setOnMouseClicked((MouseEvent e) ->                                                                           // creo un semplice listner per poter andare a eliminare le foto ogni qual volta vengano cliccate
                {                                                                                                                       // per fare cio uso un alert
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("AGGIUNGI FOTO");
                    alert.setContentText("VUOI AGGIUNGERE LE FOTO ALLA COLLEZIONE?");


                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK)
                    {
                        PreparedStatement ps1;

                        try {
                            ps1 = Main.DoPrepared("call inserisci_fotografia_in_collezione_condivisa(?,?)");

                            int value = (int) ((Node)e.getSource()).getUserData();
                            ps1.setInt(1,value);
                            ps1.setString(2, Main.getScelta());

                            ps1.execute();
                            ps1.close();

                            initialize(url, resourceBundle);

                        } catch (SQLException ex) {throw new RuntimeException(ex);}

                    }
                });
            }
                ps.close();
                rs.close();
                Main.getCon().close();

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}


        pannel.setContent(gridPane);

    }
}
