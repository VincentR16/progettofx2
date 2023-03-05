package com.example.proggettofx2.entita;

import com.example.proggettofx2.MainController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Trash
{
    private GridPane gridPane;

    public Trash() throws SQLException, IOException
    {
        gridPane= new GridPane();
        this.setGridPane(gridPane);
    }

    public GridPane getGridPane() {return gridPane;}

    public void setGridPane(GridPane gridPane) throws SQLException, IOException {
        int i = 0;
        int j = 0;

        MainController Main = MainController.getInstance();
        ImageView imageView;

        Fotografie foto= Fotografie.getInstance();

        List<ImageView> list = foto.getListafotoeliminate();


        // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Iterator it = list.listIterator();

        while (it.hasNext())
        {

            imageView = (ImageView) it.next();

            gridPane.add(imageView, j, i);

            j++;
            if (j > 4)
            {
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


                    } catch (SQLException | IOException ex) {throw new RuntimeException(ex);}

                }
            });
        }

    }


    private Node setOnAction(MouseEvent e) throws SQLException, IOException
    {
        MainController Main=MainController.getInstance();

        PreparedStatement pst = Main.DoPrepared("delete from fotografia where eliminata=1 and id_foto=?");
        // elimina definitivamente la foto dal db

        int value = (int) ((Node) e.getSource()).getUserData();
        Node node = (Node) e.getSource();
        pst.setInt(1, value);


        System.out.println(Utente.getUtente().getIdutente());
        pst.execute();

        pst.close();

        Fotografie.getInstance().setListafotoeliminate();



        Main.Closeall();
        return node;
    }
}





