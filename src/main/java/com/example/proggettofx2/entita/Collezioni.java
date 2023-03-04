package com.example.proggettofx2.entita;

import com.example.proggettofx2.MainController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Collezioni
{

    private static String scelta ;
    private List<ImageView> listused;
    private List<ImageView> listnotused;
    private static int id_Collezioni;


    public  Collezioni(){}

    public Collezioni(String S) throws SQLException, IOException
    {
        this.setID(S);
        listused=Fotografie.getInstance().getCollezione();
    }



    public void SetCombo(ComboBox<String> comboBox) throws SQLException
    {
        MainController main = MainController.getInstance();

        PreparedStatement pst = main.DoPrepared("select distinct nome from collezione as c, utente_possiede_collezione as u where u.id_utente=? and c.personale=0 and c.id_collezione= u.id_collezione");
        pst.setInt(1, Utente.getUtente().getIdutente());

        ResultSet rs1 = pst.executeQuery();

        while (rs1.next())
        {
            comboBox.getItems().add(rs1.getString("nome"));
        }

        main.Closeall();
        rs1.close();
    }


    public GridPane setAction()
    {


        ImageView imageView;
        MainController main= MainController.getInstance();


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int i = 0;
        int j = 0;

        try {

            Fotografie foto= Fotografie.getInstance();
            foto.setCollezione(this.getScelta());


            Iterator it = listused.listIterator();

            while(it.hasNext())
            {
                imageView = (ImageView) it.next();

                gridPane.add(imageView, j, i);

                j++;
                if (j > 4) {
                    j = 0;
                    i++;
                }


                imageView.setOnMouseClicked((MouseEvent er) ->

                        //  semplice listner per poter rendere private le foto ogni qual volta vengano cliccate
                {                                                                                                                       // per fare cio uso un alert
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("FOTO");
                    alert.setContentText("VUOI RENDERE PRIVATA LA FOTO?");


                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK)
                    {

                        try {

                            gridPane.getChildren().remove(this.privatephoto(er));
                            foto.setCollezione(this.getScelta());

                            main.Closeall();


                        } catch (SQLException | IOException ex) {throw new RuntimeException(ex);}
                    }
                });

            }

            main.getCon().close();

        } catch (SQLException | IOException ex) {throw new RuntimeException(ex);
        }

        return gridPane;

    }

    private void Setscelta(String S) {scelta=S;}

    private String getScelta() {return scelta;}

    private Node privatephoto(MouseEvent er) throws SQLException
    {

        MainController main=MainController.getInstance();

        PreparedStatement ps =main.DoPrepared("call rendi_fotografia_privata_o_pubblica(?,?)");
        //viene resa privata la foto

        int value = (int) ((Node) er.getSource()).getUserData();
        Node node = (Node) er.getSource();

        ps.setInt(1, value);
        ps.setString(2, "privata");

        System.out.println(value);

        ps.execute();
        ps.close();

        return node;
    }

    private void setID(String S) throws SQLException
    {
        this.Setscelta(S);

        MainController main = MainController.getInstance();

        CallableStatement cs1 = main.Callprocedure("{?= call recupera_id_collezione(?)}");

        // partendo dal nome della collezione, viene recupoerato l id della collezione
        cs1.registerOutParameter(1, Types.INTEGER);

        cs1.setString(2, this.getScelta());

         cs1.execute();

         id_Collezioni=(int)cs1.getInt(1);
    }


}
