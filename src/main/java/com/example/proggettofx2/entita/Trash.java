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

    public Trash() throws SQLException, IOException {

        int i = 0;
        int j = 0;

        MainController Main = MainController.getInstance();
        ImageView imageView;

        Fotografie foto= Fotografie.getInstance();

        List<ImageView> list = foto.getListafotoeliminate();


        gridPane = new GridPane();                                                                                                      // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Iterator it = list.listIterator();

        while (it.hasNext()) {
            imageView = (ImageView) it.next();

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
                        PreparedStatement pst = Main.DoPrepared("delete from fotografia where eliminata=1 and id_foto=?");
                        // elimina definitivamente la foto dal db

                        int value = (int) ((Node) e.getSource()).getUserData();
                        pst.setInt(1, value);


                        System.out.println(Utente.getUtente().getIdutente());
                        pst.execute();

                        pst.close();

                        foto.setListafotoeliminate();

                        Main.Closeall();

                    } catch (SQLException ex) {throw new RuntimeException(ex);} catch (IOException ex) {throw new RuntimeException(ex);}


                    Main.getStage().close();

                    try {

                        Main.CreateStage("Trashpage.fxml");                     // una sorta di refresh alla pagina ogni qual volta viene eliminata una foto

                    } catch (IOException ex) {throw new RuntimeException(ex);}

                }
            });
        }
    }







    public GridPane getGridPane() {return gridPane;}

}
