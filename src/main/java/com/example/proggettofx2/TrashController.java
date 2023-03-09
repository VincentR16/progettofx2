package com.example.proggettofx2;


import com.example.proggettofx2.DAO.FotografieDAO;
import com.example.proggettofx2.entita.Fotografie;
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
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TrashController extends MenuController implements Initializable
{
    //gestisce la pagina del cestino
    @FXML
    public ScrollPane pannel;
    private Fotografie fotografie;
    private  FotografieDAO fotografieDAO;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try {
             fotografie = Fotografie.getInstance();
             fotografieDAO = new FotografieDAO();

            pannel.setContent(setGridPane());


        } catch (SQLException | IOException e) {throw new RuntimeException(e);}
    }

    public GridPane setGridPane() throws SQLException, IOException
    {
        int i = 0;
        int j = 0;


        ImageView imageView;
        GridPane gridPane = new GridPane();


        List<ImageView> list = fotografie.getListafotoeliminate();


        // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (ImageView view : list) {

            imageView = view;

            gridPane.add(imageView, j, i);

            j++;
            if (j > 4) {
                j = 0;
                i++;
            }


            imageView.setOnMouseClicked((MouseEvent e) ->
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("ELIMINA FOTO");
                alert.setContentText("VUOI DEFINITIVAMENTE ELIMINARE LA FOTO?");


                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                    try {
                        gridPane.getChildren().remove(this.setOnAction(e));


                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
        }

        return gridPane;
    }



    private Node setOnAction(MouseEvent e) throws SQLException, IOException
    {

        int value = (int) ((Node) e.getSource()).getUserData();
        Node node = (Node) e.getSource();

        fotografieDAO.delete(fotografie,value);

        fotografie.deletecestino(value);

        return node;
    }








}

