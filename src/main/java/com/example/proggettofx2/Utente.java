package com.example.proggettofx2;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utente {
    //classe utente

    private static Utente istanza= null;
    private final int idutente;
    private String Nome;
    private String Cognome;
    private String Nazionalita;
    private String Email;
    private String Password;


   private Utente(String N,String C,String Na,String E,String P,int id)
   {
       Nome=N;
       Cognome=C;
       Nazionalita=Na;
       Email=E;
       Password=P;
       idutente=id;
   }


    public static Utente getUtente(String N,String C,String Na,String E,String P,int id)
    {
        if(istanza==null) {istanza=new Utente(N,C,Na,E,P,id);}
        return istanza;
    }

    public  void setdefault() {istanza=null;}

    public void Modifica(String N,String C,String Na,String E,String P)throws SQLException
    {

        MainController Main= MainController.getInstance();
        PreparedStatement pst= Main.DoPrepared("update utente set nome= ?,cognome= ?,email= ?,nazionalità= ?,password= ? where id_utente= ?");
// nel caso di modifica, viene modificato anche il db

        Nome=N;
        Cognome=C;
        Nazionalita=Na;
        Email=E;
        Password=P;


        pst.setString(1,N);
        pst.setString(2,C);
        pst.setString(3,E);
        pst.setString(4,Na);
        pst.setString(5,P);
        pst.setInt(6,idutente);

        pst.execute();
        pst.close();

        Main.Closeall();
    }


    public static  Utente getUtente(){return istanza;}
    public String getNome() {return Nome;}
    public String getCognome() {return Cognome;}
    public String getNazionalita() {return Nazionalita;}
    public String getEmail() {return Email;}
    public String getPassword() {return Password;}
    public int getIdutente(){return idutente;}









}
