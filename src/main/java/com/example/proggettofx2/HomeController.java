package com.example.proggettofx2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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


public class HomeController implements Initializable
{



@FXML
public ScrollPane pannel;

private MainController Main;




        @FXML
        void BAggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
        {
                Main.getStage().close();
                Main.CreateStage("Aggiungifotopage.fxml");
                Main.getStage().setWidth(920);
                Main.getStage().setHeight(620);
        }

        @FXML
        void BCestino(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
        {
                Main.getStage().close();
                Main.CreateStage("Trashpage.fxml");
        }

        @FXML
        void BCollezioni(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
        {
                Main.getStage().close();
                Main.CreateStage("Collezionipage.fxml");
        }

        @FXML
        void BProfile(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
        {
                Main.getStage().close();
                Main.CreateStage("Profile-page.fxml");
        }

        @FXML
        void Bvideo(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
        {
                Main.getStage().close();
                Main.CreateStage("Videopage.fxml");
        }


        @FXML
        void Bexit(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
        {
                Main.getStage().close();

                Main.CreateStage("Firstpage.fxml");
                Main.getStage().setHeight(450);
                Main.getStage().setWidth(655);
                Main.getStage().setResizable(false);

                Utente.getUtente().setdefault();

        }

        @FXML
        void MouseEntered(MouseEvent event)
        {
                javafx.scene.control.Button button=(javafx.scene.control.Button) (event.getSource());
                button.setStyle("-fx-background-color:  #0C1538");
        }

        @FXML
        void MouseExited(MouseEvent event)
        {
                javafx.scene.control.Button button=(Button) (event.getSource());
                button.setStyle("-fx-background-color:  #183669 ");
        }

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

                GridPane gridPane =new GridPane();                                                                                                      // creo una griglia e ne imposto il gap in altezza e in orizzontale
                gridPane.setHgap(10);
                gridPane.setVgap(10);


                ResultSet rs;                   //query gestita dal controller principale che prende tutte le foto dell'utente loggato

                try
                {
                        rs = Main.GetImage(0);

                } catch (SQLException e) {throw new RuntimeException(e);}

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


                                imageView.setOnMouseClicked((MouseEvent e) ->                                                                           // creo un semplice listner per poter andare a eliminare le foto ogni qual volta vengano cliccate
                                {                                                                                                                       // per fare cio uso un alert
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

                                                        initialize(url, resourceBundle);                                                                    // una sorta di refresh alla pagina ogni qual volta viene eliminata una foto
                                                        Main.Closeall();

                                                } catch (SQLException ex) {throw new RuntimeException(ex);}
                                        }
                                });
                        }

                        rs.close();
                        Main.getCon().close();

                }catch(SQLException | IOException e){throw new RuntimeException(e);}


                pannel.setContent(gridPane);// imposto la griglia come contenuto dello scroll pane
        }

}




