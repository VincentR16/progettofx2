package com.example.proggettofx2.entita;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Fotofiltrate
{
     private List<ImageView> fotofiltrate;


    public Fotofiltrate() throws SQLException, IOException
    {
        Fotografie foto = Fotografie.getInstance();

        fotofiltrate=foto.getFotofiltrate();
    }

    public GridPane setGridpane()
    {
        GridPane gridPane = new GridPane();// creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        int i=0;
        int j=0;

        for (ImageView view : fotofiltrate) {

            gridPane.add(view, j, i);

            j++;

            if (j > 4) {
                j = 0;
                i++;
            }
        }


        return gridPane;
    }

    public void top3(Label labelprimo,Label labelsec,Label labelterz) throws SQLException
    {
        Connection C= new Connection();


        PreparedStatement ps= C.DoPrepared("select * from top_3_luoghi(?)");

        //query che restituisce i luoghi più immortalati
        ps.setInt(1,Utente.getUtente().getIdutente());

        ResultSet rs= ps.executeQuery();


        if(rs.next()) labelprimo.setText(rs.getString(1)+"  "+rs.getString(2));
        if(rs.next()) labelsec.setText(rs.getString(1)+"  "+rs.getString(2));
        if(rs.next())labelterz.setText(rs.getString(1)+"  "+rs.getString(2));

        //gli if servono nel caso in cui non ci siano meno stringhe

        rs.close();
        ps.close();
        C.Closeall();
    }





}
