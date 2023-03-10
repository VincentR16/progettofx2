package com.example.proggettofx2.entita;


import javafx.scene.control.ListView;

import java.sql.*;
import java.util.List;

public class Utente {
    //classe utente

    private static Utente istanza= null;
    private int idutente;
    private String Nome;
    private String Cognome;
    private String Nazionalita;
    private String Email;
    private String Password;
    private ResultSet users;
    private Utente(){}


   private void setUtente(String N,String C,String Na,String E,String P)
   {
       Nome=N;
       Cognome=C;
       Nazionalita=Na;
       Email=E;
       Password=P;
   }

    public void setIdutente(int idutente) {this.idutente = idutente;}

    public void setUsers(ResultSet users) {this.users = users;}

    public ResultSet getUsers() {return users;}

    public  static Utente getUtente()
    {
        if(istanza==null) {istanza=new Utente();}
        return istanza;
    }

    public  void setdefault() {istanza=null;}

    public void Modifica(String N,String Co,String Na,String E,String P)
    {
        Nome=N;
        Cognome=Co;
        Nazionalita=Na;
        Email=E;
        Password=P;
    }

    public String getNome() {return Nome;}
    public String getCognome() {return Cognome;}
    public String getNazionalita() {return Nazionalita;}
    public String getEmail() {return Email;}
    public String getPassword() {return Password;}
    public int getIdutente(){return idutente;}


    public void vistautente(ListView<String>VistaUtente,List<String> lista)
    {
        //viene creata una listview con tutte le email di tutti gli utenti presenti nel db
        VistaUtente.getItems().addAll(lista);

    }

    public void Creautente(String n,String c, String e,String na,String P) throws SQLException
    {
        this.setUtente(n,c,na,e,P);
    }

    public void CreaUtente(String N,String C,String E, String Na, String P,int id)
    {
        this.setUtente(N,C,Na,E,P);
        setIdutente(id);
    }
}
