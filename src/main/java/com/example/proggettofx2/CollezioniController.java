package com.example.proggettofx2;

import com.example.proggettofx2.entita.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class CollezioniController extends MenuController implements Initializable
{
    //gestisce lo stage delle collezioni
    @FXML
    public ScrollPane pannel;

    @FXML
    private ComboBox<String> combobox;

    private MainController Main;


    @FXML
    void BnewCollection(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        Main.getStage().close();
        Main.CreateStage("Creacollezionepage.fxml");
    }
    @FXML
    void BaddCollection(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        if(combobox.getSelectionModel().getSelectedItem()==null)
        {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Scegliere una collezione");
            alert.setTitle("IMPORTANTE");

            alert.show();
        }else
        {
            Main.getStage().close();
            Main.CreateStage("Add2Collectionpage.fxml");
        }
    }

    @FXML
    void BaddusersCollection(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        if(combobox.getSelectionModel().getSelectedItem()==null)
        {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Scegliere una collezione");
            alert.setTitle("IMPORTANTE");

            alert.show();
        }else
        {
            Main.getStage().close();
            Main.CreateStage("UtenteCollezione.fxml");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        combobox.setPromptText("Scegli la libreria");

        Main = MainController.getInstance();

        //vengono recuperate le foto della collezione dell'utente, ogni qual volta combobox cambia
        try {

            PreparedStatement pst = Main.DoPrepared("select distinct nome from collezione as c, utente_possiede_collezione as u where u.id_utente=? and c.personale=0 and c.id_collezione= u.id_collezione");
            pst.setInt(1, Utente.getUtente().getIdutente());

            ResultSet rs1 = pst.executeQuery();

            while (rs1.next())
            {
                combobox.getItems().add(rs1.getString("nome"));
            }

            Main.Closeall();
            rs1.close();

            combobox.setOnAction((ActionEvent er)->
            {
                Main.setScelta(combobox.getSelectionModel().getSelectedItem());

                GridPane gridPane;
                try {

                    CallableStatement cs1 = Main.Callprocedure("{?= call recupera_id_collezione(?)}");
                    // partendo dal nome della collezione, viene recupoerato l id della collezione
                    cs1.registerOutParameter(1, Types.INTEGER);

                    cs1.setString(2, combobox.getSelectionModel().getSelectedItem());

                    cs1.execute();


                    PreparedStatement st1 = Main.DoPrepared("select * from collezione_condivisa(?)");
                    //recupero foto della collezione

                    st1.setString(1,combobox.getSelectionModel().getSelectedItem());
                    ResultSet rs= st1.executeQuery();



                    gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);


                    int i = 0;
                    int j = 0;

                    while (rs.next()) {
                        ImageView imageView = Main.setImageview(rs.getBytes("val_foto"), rs.getInt("id_foto"));

                        gridPane.add(imageView, j, i);
                        // faccio un semplice ciclo per impostare la posizione delle immagevie nella griglia
                        j++;
                        if (j > 4) {
                            j = 0;
                            i++;
                        }


                        imageView.setOnMouseClicked((MouseEvent e) ->
                                //  semplice listner per poter rendere private le foto ogni qual volta vengano cliccate
                        {                                                                                                                       // per fare cio uso un alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                            alert.setTitle("FOTO");
                            alert.setContentText("VUOI RENDERE PRIVATA LA FOTO?");


                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {

                                try {
                                    PreparedStatement ps = Main.DoPrepared("call rendi_fotografia_privata_o_pubblica(?,?)");
                                    //viene resa privata la foto

                                    int value = (int) ((Node) e.getSource()).getUserData();
                                    Node node =(Node) e.getSource();

                                    ps.setInt(1, value);
                                    ps.setString(2,"privata");

                                    gridPane.getChildren().remove(node);

                                    ps.execute();

                                    ps.close();
                                    Main.Closeall();


                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                    }

                    rs.close();
                    Main.getCon().close();

                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }

                pannel.setContent(gridPane);                                                                             // imposto la griglia come contenuto dello scroll pane

            });



        } catch (SQLException e) {throw new RuntimeException(e);}

    }
}
