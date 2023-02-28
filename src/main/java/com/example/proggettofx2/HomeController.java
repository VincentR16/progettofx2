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


public class HomeController extends MenuController implements Initializable
{
        // gestisce la home page, ovvero dove si visualizzano le proprie foto

@FXML
public ScrollPane pannel;
private MainController Main;


        @FXML
        void FiltraButton(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
        {
                Main.getStage().close();
                Main.CreateStage("Filtrapage.fxml");
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle)
        {

                Main = MainController.getInstance();

                GridPane gridPane =new GridPane();
                // creo una griglia e ne imposto il gap in altezza e in orizzontale
                gridPane.setHgap(10);
                gridPane.setVgap(10);


                ResultSet rs;

                try
                {
                        rs = Main.GetImage(0);
                        //query gestita dal controller principale che prende tutte le foto dell'utente loggato

                } catch (SQLException e) {throw new RuntimeException(e);}

                try
                {
                        int i=0;
                        int j=0;

                        while (rs.next())
                        {
                               ImageView imageView =Main.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
                               //gli imageview vengono impostati uno alla volta, con le immagini

                                gridPane.add(imageView,j,i);

                                j++;
                                if(j>4){j=0;i++;}
                                //  ciclo per impostare la posizione delle immagini nella griglia
                                // rispetto alle matrici qui si mette prima la colonna e poi la riga


                                imageView.setOnMouseClicked((MouseEvent e) ->
                                        //  semplice listner per poter andare a eliminare le foto ogni qual volta vengano cliccate
                                {
                                        //  alert di conferma
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                                        alert.setTitle("ELIMINA FOTO");
                                        alert.setContentText("VUOI ELIMINARE LA FOTO?");



                                        Optional<ButtonType> result = alert.showAndWait();

                                        if (result.get() == ButtonType.OK)
                                        {
                                                try
                                                {
                                                        PreparedStatement pst= Main.DoPrepared("update fotografia set eliminata=1 where id_foto = ?");

                                                        int value = (int) ((Node)e.getSource()).getUserData();
                                                        pst.setInt(1,value);
                                                        pst.execute();

                                                        pst.close();

                                                        initialize(url, resourceBundle);
                                                        // una sorta di refresh alla pagina ogni qual volta viene eliminata una foto

                                                        Main.Closeall();

                                                } catch (SQLException ex) {throw new RuntimeException(ex);}
                                        }
                                });
                        }

                        rs.close();
                        Main.getCon().close();

                }catch(SQLException | IOException e){throw new RuntimeException(e);}


                pannel.setContent(gridPane);// viene impostata la griglia come contenuto dello scroll pane
        }

}




