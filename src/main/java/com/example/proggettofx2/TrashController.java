package com.example.proggettofx2;


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

public class TrashController extends MenuController implements Initializable
{
    //gestisce la pagina del cestino
    @FXML
    public ScrollPane pannel;
    private MainController Main;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Main = MainController.getInstance();

        ResultSet rs;                                                                                                                     //query gestita dal controller principale che prende tutte le foto dell'utente loggato

        try {
            rs = Main.GetImage(1);
            // il valore è 1 poiche cerca tutte le foto eliminate, 0 invece tutte le foto non eliminate

        } catch (SQLException e) {throw new RuntimeException(e);}

        GridPane gridPane =new GridPane();                                                                                                      // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        try
        {
            int i=0;
            int j=0;

            while (rs.next())
            {

                ImageView imageView =Main.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
                gridPane.add(imageView,j,i);
                                                                                                                                        // faccio un semplice ciclo per impostare la posizione delle immagevie nella griglia
                j++;                                                                                                                    // rispetto alle matrici qui si mette prima la colonna e poi la riga
                if(j>4){j=0;i++;}


                imageView.setOnMouseClicked((MouseEvent e) ->
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("ELIMINA FOTO");
                    alert.setContentText("VUOI DEFINITIVAMENTE ELIMINARE LA FOTO?");


                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK)
                    {
                        try {
                            PreparedStatement pst=Main.DoPrepared("call elimina_fotografia(?,?)");
                            // elimina definitivamente la foto dal db

                            int value = (int) ((Node)e.getSource()).getUserData();

                            pst.setInt(1,value);
                            pst.setInt(2,Utente.getUtente().getIdutente());

                            pst.execute();
                            pst.close();
                            Main.Closeall();

                        } catch (SQLException ex) {throw new RuntimeException(ex);}


                        initialize(url, resourceBundle);                                                                    // una sorta di refresh alla pagina ogni qual volta viene eliminata una foto
                    }
                });
            }
        }catch(SQLException | IOException e){throw new RuntimeException(e);}


        try
        {
            rs.close();

        } catch (SQLException e) {throw new RuntimeException(e);}

        pannel.setContent(gridPane);                                                                             // imposto la griglia come contenuto dello scroll pane


        try
        {
            Main.getCon().close();

        } catch (SQLException e) {throw new RuntimeException(e);}


    }

}

