package com.example.proggettofx2.entita;

import com.example.proggettofx2.MainController;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Fotografie
{
    private List<ImageView> listafoto;
    private List<ImageView> listafotoeliminate;
    private List<ImageView> collezione;
    private List<ImageView> nonincollezione;

    private static Fotografie instanza =null;

    private Fotografie() throws SQLException, IOException {
        setlistafoto();
        setListafotoeliminate();

    }

    public static Fotografie getInstance() throws SQLException, IOException {
        if (instanza == null) {instanza = new Fotografie();}

            return instanza;
    }


    public void setlistafoto() throws SQLException, IOException {

        ImageView imageView;

        listafoto = new ArrayList<>();

        MainController Main=MainController.getInstance();

        ResultSet rs = Main.GetImage(0);

        while (rs.next())
        {
             imageView =Main.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
             listafoto.add(imageView);
        }
    }

    public List<ImageView> getListafoto() {return listafoto;}


    public void setListafotoeliminate()throws SQLException,IOException{

        ImageView imageView;

        listafotoeliminate = new ArrayList<>();

        MainController Main=MainController.getInstance();

        ResultSet rs = Main.GetImage(1);

        while (rs.next())
        {
            imageView =Main.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            listafotoeliminate.add(imageView);
        }
    }

    public List<ImageView> getListafotoeliminate() {
        return listafotoeliminate;
    }
}
