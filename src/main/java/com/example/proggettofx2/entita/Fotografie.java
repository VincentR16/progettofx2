package com.example.proggettofx2.entita;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
    private List<String> informazioni;
    private List<String> utentiscelti;
    private String ricerca;
    private String sceltaricerca;
    private  Iterator<ImageView> it;



    private Fotografie()
    {
        listafoto=new ArrayList<>();
        listafotoeliminate= new ArrayList<>();
        collezione= new ArrayList<>();
        nonincollezione = new ArrayList<>();
        fotofiltrate=new ArrayList<>();
    }

    public static Fotografie getInstance()
    {
        if (instanza == null) {instanza = new Fotografie();}

            return instanza;
    }

    public void reset()
    {
        listafoto=new ArrayList<>();
        listafotoeliminate= new ArrayList<>();
    }

    public void resetfiltra(){fotofiltrate= new ArrayList<>();}

    public void setScelta(String S)
    {
        collezione= new ArrayList<>();
        nonincollezione = new ArrayList<>();
        scelta=S;
    }

    public void setRicerca(String ricerca) {this.ricerca = ricerca;}
    public void setSceltaricerca(String sceltaricerca) {this.sceltaricerca = sceltaricerca;}
    public String getRicerca() {return ricerca;}
    public String getSceltaricerca() {return sceltaricerca;}
    public void setInformazioni(List<String> informazioni) {this.informazioni = informazioni;}
    public List<String> getInformazioni() {return informazioni;}
    public void setUtentiscelti(List<String> utentiscelti) {this.utentiscelti = utentiscelti;}
    public List<String> getUtentiscelti() {return utentiscelti;}
    public String getScelta() {return scelta;}
    public List<ImageView> getListafoto() {return listafoto;}
    public List<ImageView> getListafotoeliminate() {return listafotoeliminate;}
    public List<ImageView> getCollezione() {return collezione;}
    public List<ImageView> getNonincollezione() {return nonincollezione;}
    public List<ImageView> getFotofiltrate() {return fotofiltrate;}


    public void AggiungiFoto(String path)
    {
         Image image= new Image(path);
         imageView.setImage(image);
         listafoto.add(imageView);
    }

   public void AggiungiCollezione(int id)
    {
        it = nonincollezione.listIterator();

         while (it.hasNext())
         {
             imageView = it.next();
             int value= (int) imageView.getUserData();

            if(value==id){collezione.add(imageView);it.remove();}
         }
    }

   public void rimuoviCollezione(int id)
    {
       it = collezione.listIterator();

        while (it.hasNext())
        {
            imageView = it.next();
            int value= (int) imageView.getUserData();

            if(value==id){nonincollezione.add(imageView);it.remove();}
        }
    }


    public void eliminafoto(int id)
    {
        it = listafoto.listIterator();

        while (it.hasNext())
        {
            imageView =  it.next();
            int value= (int) imageView.getUserData();

            if(value==id){listafotoeliminate.add(imageView);it.remove();}
        }
    }

    public void deletecestino(int id)
    {
        it = listafotoeliminate.listIterator();

        while (it.hasNext())
        {
            imageView =  it.next();
            int value= (int) imageView.getUserData();

            if(value==id){it.remove();}
        }

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

}
