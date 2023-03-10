package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.Fotografie;
import com.example.proggettofx2.entita.Video;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoDao implements Dao<Video>
{

    @Override
    public void initialize(Video video) throws SQLException
    {

        Connection C = new Connection();
        Fotografie foto=Fotografie.getInstance();

        List<String> soggetti= new ArrayList<>();
        List<String> city= new ArrayList<>();
        List<String> dispositivo= new ArrayList<>();


        for (ImageView imageView : foto.getListafoto())
        {

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

        video.setCity(city);
        video.setDispositivo(dispositivo);
        video.setSoggetti(soggetti);

    }

    @Override
    public void insert(Video videoDao) throws SQLException {

    }

    @Override
    public void delete(Video videoDao, int value) {

    }

    @Override
    public List<String> search(Video videoDao)  {
        return null;
    }

    @Override
    public void collection(Video videoDao)  {

    }

    @Override
    public void update(Video videoDao, int value)  {

    }
}
