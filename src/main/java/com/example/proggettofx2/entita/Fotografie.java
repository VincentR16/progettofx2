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
    private List<ImageView> fotofiltrate;

    private ImageView imageView;


    private static Fotografie instanza =null;

    private Fotografie() throws SQLException, IOException {
        setlistafoto();
        setListafotoeliminate();
    }

    public static Fotografie getInstance() throws SQLException, IOException
    {
        if (instanza == null) {instanza = new Fotografie();}

            return instanza;
    }


    public void setlistafoto() throws SQLException, IOException {


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

        imageView = new ImageView();

        imageView.setUserData(id_foto);


        imageView.setImage(SwingFXUtils.toFXImage(Bimage, null));

        imageView.setFitHeight(135);
        imageView.setFitWidth(135);

        // viene impostata la grandezza dell'imagine
        imageView.setPickOnBounds(true);


        return imageView;
    }


    public void setCollezione(String S) throws SQLException, IOException {

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


    public void setNonincollezione(String S) throws SQLException, IOException {

        nonincollezione=new ArrayList<>();

        MainController Main = MainController.getInstance();

        //query gestita dal controller principale che prende tutte le foto dell'utente loggato

            PreparedStatement ps= Main.DoPrepared("Select * from foto_non_presenti_in_collezione_condivisa(?,?)");
            //prende tutte le foto non presenti nella collezione

            ps.setInt(1, Utente.getUtente().getIdutente());
            ps.setString(2, S);


           ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            imageView=this.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            nonincollezione.add(imageView);
        }
    }


    public List<ImageView> getNonincollezione() {return nonincollezione;}

    public void fotoincollezione(String utente) throws SQLException
    {

        MainController Main= MainController.getInstance();

        PreparedStatement pst1 = Main.DoPrepared("call inserisci_fotografie_in_collezione_condivisa(?,?)");
        //inserimento di tutte le foto
        pst1.setInt(1,Utente.getUtente().getIdutente());
        pst1.setString(2,utente);

        pst1.execute();

        Main.Closeall();
        pst1.close();

    }


    public void setFotofiltrate(String scelta, String testo) throws SQLException, IOException {

        MainController Main = MainController.getInstance();
        fotofiltrate= new ArrayList<>();

        PreparedStatement ps= Main.DoPrepared("Select * from "+scelta+"(?,?)");
        //la funzione nel db restituisce le foto con quei criteri

        ps.setInt(1, Utente.getUtente().getIdutente());
        ps.setString(2,testo);


        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            imageView=this.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));

            fotofiltrate.add(imageView);
        }
    }

    public List<ImageView> getFotofiltrate() {return fotofiltrate;}
}
