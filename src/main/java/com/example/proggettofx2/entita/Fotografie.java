package com.example.proggettofx2.entita;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
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
    private ImageView imageView = new ImageView();
    private static Fotografie instanza =null;
    private String scelta;

    private Fotografie()
    {
        listafoto=new ArrayList<>();
        listafotoeliminate= new ArrayList<>();
        collezione= new ArrayList<>();
        nonincollezione = new ArrayList<>();
        fotofiltrate=new ArrayList<>();
    }

    public static Fotografie getInstance() throws SQLException, IOException
    {
        if (instanza == null) {instanza = new Fotografie();}

            return instanza;
    }

    void AggiungiFoto(String path)
    {
         Image image= new Image(path);
         imageView.setImage(image);
         listafoto.add(imageView);
    }
    void AggiungiCollezione(String path)
    {
        Image image= new Image(path);
        imageView.setImage(image);
        collezione.add(imageView);
    }

    public void setScelta(String S){scelta=S;}

    public String getScelta() {return scelta;}

    public List<ImageView> getListafoto() {return listafoto;}
    public List<ImageView> getListafotoeliminate() {
        return listafotoeliminate;
    }
    public List<ImageView> getCollezione() {return collezione;}
    public List<ImageView> getNonincollezione() {return nonincollezione;}




    public void resetfoto(){this.listafoto = new ArrayList<>(); this.listafotoeliminate = new ArrayList<>();}

    public void eliminafoto()
    {
    }


    public ImageView setImageview(byte[] binaryData, int id_foto) throws IOException
    {
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







    public void fotoincollezione(String utente) throws SQLException
    {

        Connection C= new Connection();


        PreparedStatement pst1 = C.DoPrepared("call inserisci_fotografie_in_collezione_condivisa(?,?)");
        //inserimento di tutte le foto
        pst1.setInt(1,Utente.getUtente().getIdutente());
        pst1.setString(2,utente);

        pst1.execute();

        C.Closeall();
        pst1.close();

    }


    public void setFotofiltrate(String scelta, String testo) throws SQLException, IOException {

        Connection C= new Connection();

        fotofiltrate= new ArrayList<>();

        PreparedStatement ps= C.DoPrepared("Select * from "+scelta+"(?,?)");
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
