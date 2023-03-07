package com.example.proggettofx2.entita;

import com.example.proggettofx2.DAO.FotografieDAO;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.*;
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

        FotografieDAO fotografieDAO = new FotografieDAO();

        Fotografie foto= Fotografie.getInstance();
        foto.setScelta(S);

        fotografieDAO.collection(foto);


        listused=foto.getCollezione();
        listnotused=foto.getNonincollezione();
    }



    public void SetCombo(ComboBox<String> comboBox) throws SQLException
    {
        Connection C= new Connection();

        PreparedStatement pst = C.DoPrepared("select distinct nome from collezione as c, utente_possiede_collezione as u where u.id_utente=? and c.personale=0 and c.id_collezione= u.id_collezione");
        pst.setInt(1, Utente.getUtente().getIdutente());

        ResultSet rs1 = pst.executeQuery();

        while (rs1.next())
        {
            comboBox.getItems().add(rs1.getString("nome"));
        }

        C.Closeall();
        rs1.close();
    }


    public GridPane setAction()
    {

        ImageView imageView;


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int i = 0;
        int j = 0;


        for (ImageView view : listused) {
            imageView = view;

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

                if (result.get() == ButtonType.OK) {

                    try {

                        gridPane.getChildren().remove(this.privatephoto(er));
                        //foto.setCollezione(this.getScelta());


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }


        return gridPane;

    }

    private void Setscelta(String S) throws SQLException, IOException {scelta=S;Fotografie.getInstance().setScelta(S);}

    private String getScelta() {return scelta;}

    private Node privatephoto(MouseEvent er) throws SQLException
    {

        Connection C= new Connection();

        PreparedStatement ps =C.DoPrepared("call rendi_fotografia_privata_o_pubblica(?,?)");
        //viene resa privata la foto

        int value = (int) ((Node) er.getSource()).getUserData();
        Node node = (Node) er.getSource();

        ps.setInt(1, value);
        ps.setString(2, "privata");


        ps.execute();
        ps.close();

        C.Closeall();


        return node;
    }

    private void setID(String S) throws SQLException, IOException
    {
        this.Setscelta(S);

        Connection C= new Connection();

        CallableStatement cs1 = C.Callprocedure("{?= call recupera_id_collezione(?)}");

        // partendo dal nome della collezione, viene recupoerato l id della collezione
        cs1.registerOutParameter(1, Types.INTEGER);

        cs1.setString(2, this.getScelta());

         cs1.execute();

         id_Collezioni=cs1.getInt(1);

         C.Closeall();
    }



    public GridPane aggiungifoto() throws SQLException, IOException {


        Fotografie foto=Fotografie.getInstance();

        listnotused=foto.getNonincollezione();

        GridPane gridPane = new GridPane();                                                                                                      // creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int i = 0;
        int j = 0;


        for (ImageView imageView : listnotused) {
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


                        try {
                            gridPane.getChildren().remove(this.Onactionadd(e));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        // foto.setNonincollezione(this.getScelta());
                       // foto.setCollezione(this.getScelta());


                   // } catch (SQLException | IOException ex) {
                    //    throw new RuntimeException(ex);


                }
            });
        }

        return gridPane;
    }


    private Node Onactionadd(MouseEvent e) throws SQLException
    {

        Connection C= new Connection();


        PreparedStatement  ps1 = C.DoPrepared("call inserisci_fotografia_in_collezione_condivisa(?,?)");
        //le foto vengono aggiiunte alla collezione

        int value = (int) ((Node)e.getSource()).getUserData();
        Node node = (Node) e.getSource();

        ps1.setInt(1,value);
        ps1.setString(2, this.getScelta());

        ps1.execute();
        ps1.close();

        C.Closeall();

        return node;
    }

    public void creaCollezione(String utente,String nomecollezione) throws SQLException
    {
        Connection C= new Connection();



        PreparedStatement pst = C.DoPrepared("call crea_collezione_condivisa(?,?,?)");
        pst.setInt(1, Utente.getUtente().getIdutente());
        pst.setString(2,utente);
        pst.setString(3,nomecollezione);

        pst.execute();

        C.Closeall();
        pst.close();
    }


    public void aggiungiutente()
    {

    }
}
