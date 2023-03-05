package com.example.proggettofx2.entita;

import com.example.proggettofx2.MainController;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Fotofiltrate
{
     private List<ImageView> fotofiltrate;
     private ImageView imageView;

     public Fotofiltrate(){}

    public Fotofiltrate(String scelta, String testo) throws SQLException, IOException
    {
        Fotografie foto = Fotografie.getInstance();
        foto.setFotofiltrate(scelta, testo);

        fotofiltrate=foto.getFotofiltrate();

    }

    public GridPane setGridpane()
    {
        GridPane gridPane = new GridPane();// creo una griglia e ne imposto il gap in altezza e in orizzontale
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        int i=0;
        int j=0;

        Iterator<ImageView> it = fotofiltrate.listIterator();

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
        }


        return gridPane;
    }

    public void top3(Label labelprimo,Label labelsec,Label labelterz) throws SQLException
    {
        MainController Main=MainController.getInstance();

        PreparedStatement ps= Main.DoPrepared("select * from top_3_luoghi(?)");

        //query che restituisce i luoghi più immortalati
        ps.setInt(1,Utente.getUtente().getIdutente());

        ResultSet rs= ps.executeQuery();


        if(rs.next()) labelprimo.setText(rs.getString(1)+"  "+rs.getString(2));
        if(rs.next()) labelsec.setText(rs.getString(1)+"  "+rs.getString(2));
        if(rs.next())labelterz.setText(rs.getString(1)+"  "+rs.getString(2));

        //gli if servono nel caso in cui non ci siano meno stringhe

        rs.close();
        ps.close();
        Main.Closeall();
    }





}
