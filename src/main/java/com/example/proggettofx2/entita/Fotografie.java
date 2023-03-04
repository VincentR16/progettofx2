package com.example.proggettofx2.entita;

import com.example.proggettofx2.MainController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
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
             imageView =this.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
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
            imageView =this.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            listafotoeliminate.add(imageView);
        }
    }

    public List<ImageView> getListafotoeliminate() {
        return listafotoeliminate;
    }



    public ImageView setImageview(byte[] binaryData, int id_foto) throws IOException {
        //metodo per trasfromare le foto(viene spiegato all'interno del readme)

        // la foto sotto forma di bytea viene messa in un array di byte
        InputStream in = new ByteArrayInputStream(binaryData);

        // trasforma i bite in uno stream di dati per poter utilizzarlo come buffered
        BufferedImage Bimage = ImageIO.read(in);


        // viene usato(SwingFXUtils) una libreria esterna (aggiunta tramite file .jar) per poter trasformare una
        // buffered image (sottoclasse d' image in java classico) in una immagine writable
        // sotto classe d' image di javafx.
        // Infatti per quanto possa risultare strano Img(java) NON è COMPATIBILE con IMG(javafx)
        // e quindi di conseguenza non compatibile con le componenti di javafx
        // funziona perche writableimg estende img

        ImageView imageView = new ImageView();

        imageView.setUserData(id_foto);


        imageView.setImage(SwingFXUtils.toFXImage(Bimage, null));

        imageView.setFitHeight(135);
        imageView.setFitWidth(135);

        // viene impostata la grandezza dell'imagine
        imageView.setPickOnBounds(true);


        return imageView;
    }


    public void setCollezione(String S) throws SQLException, IOException {
        ImageView imageView;

        collezione = new ArrayList<>();

        MainController Main=MainController.getInstance();


        PreparedStatement st1 = Main.DoPrepared("select * from collezione_condivisa(?)");
        //recupero foto della collezione

        st1.setString(1,S);
        ResultSet rs= st1.executeQuery();

        while (rs.next())
        {
            imageView=this.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            collezione.add(imageView);
        }
    }

    public List<ImageView> getCollezione() {return collezione;}
}
