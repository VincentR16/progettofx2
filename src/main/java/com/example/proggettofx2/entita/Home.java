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
import java.util.List;
import java.util.Optional;

public class Home
{
    private final GridPane gridPane;

    public Home() throws SQLException, IOException
    {
        gridPane =new GridPane();
        this.setGridpane(gridPane);

    }

    public GridPane getGridPane() {return gridPane;}
    private void setGridpane(GridPane gridPane) throws SQLException, IOException {

        int i=0;
        int j=0;

        ImageView imageView;

        Fotografie foto=Fotografie.getInstance();
        List<ImageView> list;

        // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        list=foto.getListafoto();


        for (ImageView view : list) {

            imageView = view;

            gridPane.add(imageView, j, i);

            j++;
            if (j > 4)
            {
                j = 0;
                i++;
            }
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

                        gridPane.getChildren().remove(this.setOnAction(e));

                    } catch (SQLException | IOException ex) {throw new RuntimeException(ex);}
                }
            });
        }

    }

    private Node setOnAction(MouseEvent e) throws SQLException, IOException
    {

        MainController Main=MainController.getInstance();

        PreparedStatement pst= Main.DoPrepared("update fotografia set eliminata=1 where id_foto = ?");

        int value = (int) ((Node)e.getSource()).getUserData();
        Node node = (Node) e.getSource();
        pst.setInt(1,value);
        pst.execute();

        pst.close();

        Fotografie.getInstance().setlistafoto();
        Fotografie.getInstance().setListafotoeliminate();

        // una sorta di refresh alla pagina ogni qual volta viene eliminata una foto


        Main.Closeall();

        return node;
    }
}
