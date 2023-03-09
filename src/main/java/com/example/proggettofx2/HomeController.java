package com.example.proggettofx2;


import com.example.proggettofx2.DAO.FotografieDAO;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class HomeController extends MenuController implements Initializable
{
        // gestisce la home page, ovvero dove si visualizzano le proprie foto

@FXML
public ScrollPane pannel;


        @FXML
        void FiltraButton(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
        {
                Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                MyStage myStage = new MyStage();
                myStage.CreateStage("Filtrapage.fxml");
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle)
        {

                try {
                        pannel.setContent(setGridpane());

                } catch (SQLException | IOException e) {throw new RuntimeException(e);}

                // viene impostata la griglia come contenuto dello scroll pane
        }




        private GridPane setGridpane() throws SQLException, IOException {

                int i=0;
                int j=0;

                ImageView imageView;

                Fotografie foto=Fotografie.getInstance();
                List<ImageView> list;

                // creo una griglia e ne imposto il gap in altezza e in orizzontale
                GridPane gridPane= new GridPane();
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

                return  gridPane;
        }

        private Node setOnAction(MouseEvent e) throws SQLException, IOException
        {
                int value = (int) ((Node)e.getSource()).getUserData();
                Node node = (Node) e.getSource();

                Fotografie fotografie = Fotografie.getInstance();

                FotografieDAO fotografieDAO = new FotografieDAO();

                fotografieDAO.update(fotografie,value);
                fotografie.eliminafoto(value);


                return node;
        }
}




