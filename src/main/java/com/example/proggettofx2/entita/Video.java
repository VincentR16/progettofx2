package com.example.proggettofx2.entita;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Video
{
    private final List<Image> images= new ArrayList<>();
    private final List<String>dispositivo= new ArrayList<>();
    private final List<String>city= new ArrayList<>();
    private final List<String>soggetti= new ArrayList<>();

    public Video() throws SQLException, IOException {
        this.setVideo();
    }

    public void setVideo() throws SQLException, IOException {
        //imposta una lista d' images, di dispositivi(string),di citta(string),di soggetti(string)che poi verranno utlizzate all'internon del video

        Connection C= new Connection();

                Fotografie foto=Fotografie.getInstance();

        for (ImageView imageView : foto.getListafoto()) {
            images.add(imageView.getImage());

            PreparedStatement pst = C.DoPrepared("select dispositivo,città from fotografia where id_foto=?");

            //vegnono recuperati tutti i dispositivi e tutte le citta
            pst.setInt(1, (int) imageView.getUserData());

            ResultSet rs1 = pst.executeQuery();
            rs1.next();

            dispositivo.add(rs1.getString("dispositivo"));
            city.add(rs1.getString("città"));

            rs1.close();
            pst.close();


            PreparedStatement ps = C.DoPrepared("Select * from recupera_soggetti_foto(?)");
            //vegono recuperati tutti i soggetti di una foto
            ps.setInt(1, (int) imageView.getUserData());

            ResultSet rs2 = ps.executeQuery();
            rs2.next();

            soggetti.add(rs2.getString(2));

            ps.close();
            rs2.close();
        }

        C.Closeall();
    }


    public AnimationTimer animazione(ImageView videoview, Label labeldisp,Label labeluog,Label labesoggetto)
    {
        final long[] inizio = {System.currentTimeMillis()};
        final int[] indice = {0};

        return new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                long tempocorrente= System.currentTimeMillis();

                if(tempocorrente- inizio[0] >= 4000)
                {

                    if(indice[0] <images.size())
                    {
                        videoview.setImage(images.get(indice[0]));

                        labeldisp.setText(dispositivo.get(indice[0]));
                        labeluog.setText(city.get(indice[0]));
                        labesoggetto.setText(soggetti.get(indice[0]));

                    }else {indice[0]=-1;}

                    indice[0]++;

                    inizio[0] =tempocorrente;
                }
            }
        };
    }


}
