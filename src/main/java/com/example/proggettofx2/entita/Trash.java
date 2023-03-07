package com.example.proggettofx2.entita;

import com.example.proggettofx2.DAO.FotografieDAO;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Trash
{
    private final GridPane gridPane;

    public Trash() throws SQLException, IOException
    {
        gridPane= new GridPane();
        this.setGridPane(gridPane);
    }

    public GridPane getGridPane() {return gridPane;}

    public void setGridPane(GridPane gridPane) throws SQLException, IOException {
        int i = 0;
        int j = 0;


        ImageView imageView;

        Fotografie foto= Fotografie.getInstance();

        List<ImageView> list = foto.getListafotoeliminate();


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

    }


    private Node setOnAction(MouseEvent e) throws SQLException, IOException
    {
        FotografieDAO fotografieDAO = new FotografieDAO();
        Fotografie fotografie = Fotografie.getInstance();


        int value = (int) ((Node) e.getSource()).getUserData();
        Node node = (Node) e.getSource();

        fotografieDAO.delete(fotografie,value);

        fotografie.deletecestino(value);

        return node;
    }
}





