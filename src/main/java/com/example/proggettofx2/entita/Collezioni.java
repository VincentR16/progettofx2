package com.example.proggettofx2.entita;
import javafx.scene.image.ImageView;


import java.io.IOException;
import java.sql.*;
import java.util.List;


public class Collezioni
{
    private List<ImageView> listused;
    private List<ImageView> listnotused;
    private static List<String>nomicollezione;
    private  int id_Collezioni;
    private String nomeCollezione;
    private String nuovoutente;




    public Collezioni() throws SQLException, IOException
    {}

    public void setNuovoutente(String nuovoutente) {this.nuovoutente = nuovoutente;}

    public String getNuovoutente() {return nuovoutente;}

    public void setNomeCollezione(String nomeCollezione) {this.nomeCollezione = nomeCollezione;}
    public String getNomeCollezione() {return nomeCollezione;}

    public void setNomi(List<String>nomi)
    {
        nomicollezione=nomi;
    }
    public  List<String> getNomicollezione() {return nomicollezione;}
    public List<ImageView> getListused() {return listused;}
    public List<ImageView> getListnotused() {return listnotused;}
    public void setID(int S) {id_Collezioni=S;}
    public void Setlistnotused(List<ImageView> list){listnotused=list;}
    public void setListused(List<ImageView> listused) {this.listused = listused;}

    public void aggiungiutente(){}

}
