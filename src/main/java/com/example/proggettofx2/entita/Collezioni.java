package com.example.proggettofx2.entita;
import javafx.scene.image.ImageView;


import java.io.IOException;
import java.sql.*;
import java.util.List;


public class Collezioni
{
    private List<ImageView> listused;
    private List<ImageView> listnotused;
    private List<String>nomicollezione;
    private  int id_Collezioni;




    public Collezioni() throws SQLException, IOException
    {
        Fotografie foto= Fotografie.getInstance();

        listused=foto.getCollezione();
        listnotused=foto.getNonincollezione();
    }


    public void setNomi(List<String>nomi)
    {
        nomicollezione=nomi;
    }
    public List<String> getNomicollezione() {return nomicollezione;}
    public List<ImageView> getListused() {return listused;}
    public List<ImageView> getListnotused() {return listnotused;}
    public void setID(int S) {id_Collezioni=S;}
    public void Setlistnotused(List<ImageView> list){listnotused=list;}
    public void aggiungiutente(){}

}
