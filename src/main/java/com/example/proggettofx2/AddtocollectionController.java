package com.example.proggettofx2;

import com.example.proggettofx2.DAO.CollezioniDao;
import com.example.proggettofx2.entita.Collezioni;
import com.example.proggettofx2.entita.Fotografie;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddtocollectionController extends MenuController implements Initializable
{
    //gestisce l'aggiunta della foto nella collezione
    @FXML
    public ScrollPane pannel;
    private CollezioniDao collezioniDao;
    private Fotografie fotografie;
    private Collezioni collezioni;



    @FXML
    void Back(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException
    {

        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        MyStage stage1 = new MyStage();
        stage1.CreateStage("Collezionipage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            pannel.setContent(aggiungifoto());

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}

    }




    public GridPane aggiungifoto() throws SQLException, IOException
    {

         collezioniDao=new CollezioniDao();
         fotografie=Fotografie.getInstance();
         collezioni= new Collezioni();

         collezioni.Setlistnotused(fotografie.getNonincollezione());

        GridPane gridPane = new GridPane();                                                                                                      // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int i = 0;
        int j = 0;


        for (ImageView imageView : collezioni.getListnotused())
        {
            gridPane.add(imageView, j, i);

            j++;
            if (j > 4) {
                j = 0;
                i++;
            }

            imageView.setOnMouseClicked((MouseEvent e) ->
                    // listner per poter andare ad aggiungere le foto ogni qual volta vengano cliccate
            {                                                                                                                       // per fare cio uso un alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("AGGIUNGI FOTO");
                alert.setContentText("VUOI AGGIUNGERE LE FOTO ALLA COLLEZIONE?");


                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {


                    try
                    {
                        gridPane.getChildren().remove(this.Onactionadd(e,collezioni));

                    } catch (SQLException | IOException ex) {throw new RuntimeException(ex);}

                    // foto.setNonincollezione(this.getScelta());
                    // foto.setCollezione(this.getScelta());


                    // } catch (SQLException | IOException ex) {
                    //    throw new RuntimeException(ex);


                }
            });
        }

        return gridPane;
    }


    private Node Onactionadd(MouseEvent e, Collezioni collezioni) throws SQLException, IOException {


        //le foto vengono aggiiunte alla collezioneù

        int value = (int) ((Node)e.getSource()).getUserData();
        Node node = (Node) e.getSource();


        collezioniDao.update(collezioni,value);

        fotografie = Fotografie.getInstance();
        fotografie.rimuoviCollezione(value);
        fotografie.AggiungiCollezione(value);


        return node;
    }










}
