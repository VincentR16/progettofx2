package com.example.proggettofx2.entita;


import javafx.scene.control.ListView;

import java.sql.*;
import java.util.ArrayList;

public class Utente {
    //classe utente

    private static Utente istanza= null;
    private int idutente;
    private String Nome;
    private String Cognome;
    private String Nazionalita;
    private String Email;
    private String Password;


    private Utente(){}


   private void setUtente(String N,String C,String Na,String E,String P,int id)
   {
       Nome=N;
       Cognome=C;
       Nazionalita=Na;
       Email=E;
       Password=P;
       idutente=id;
   }


    public  static Utente getUtente()
    {
        if(istanza==null) {istanza=new Utente();}
        return istanza;
    }

    public  void setdefault() {istanza=null;}

    public void Modifica(String N,String Co,String Na,String E,String P)throws SQLException
    {

        Connection C= new Connection();

        PreparedStatement pst= C.DoPrepared("update utente set nome= ?,cognome= ?,email= ?,nazionalità= ?,password= ? where id_utente= ?");
// nel caso di modifica, viene modificato anche il db

        Nome=N;
        Cognome=Co;
        Nazionalita=Na;
        Email=E;
        Password=P;


        pst.setString(1,N);
        pst.setString(2,Co);
        pst.setString(3,E);
        pst.setString(4,Na);
        pst.setString(5,P);
        pst.setInt(6,idutente);

        pst.execute();
        pst.close();

        C.Closeall();
    }



    public String getNome() {return Nome;}
    public String getCognome() {return Cognome;}
    public String getNazionalita() {return Nazionalita;}
    public String getEmail() {return Email;}
    public String getPassword() {return Password;}
    public int getIdutente(){return idutente;}


    public void vistautente(ListView<String> VistaUtente)
    {

        //viene creata una listview con tutte le email di tutti gli utenti presenti nel db

        ArrayList<String> lista = new ArrayList<>();

        Connection C= new Connection();

        ResultSet rs = C.DoQuery("select email from utente");

        try {

            while (rs.next()) {
                lista.add(rs.getString("email"));
            }

            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        VistaUtente.getItems().addAll(lista);

    }

    public void Creautente(String n,String c, String e,String na,String P) throws SQLException
    {
        Connection C  = new Connection();

            PreparedStatement stmt;
            stmt=C.DoPrepared("call crea_utente(?,?,?,?,?)");

            //viene creato un utente all' interno del db

            stmt.setString(1,n);
            stmt.setString(2,c);
            stmt.setString(3,e);
            stmt.setString(4,na);
            stmt.setString(5,P);

            stmt.execute();



            this.setUtente(n,c,na,e,P,this.RecuperaId(e));
    }

    private int RecuperaId(String E) throws SQLException
    {

        Connection C = new Connection();

        CallableStatement cst=C.Callprocedure("{?=call recupera_id_utente(?)}");
        // viene recuperato l'id dell 'utente cche si è appena registrato

        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2,E);

        cst.execute();



       return cst.getInt(1);
    }


    public void CreaUtente(String N,String C,String E, String Na, String P, int id)
    {
        this.setUtente(N,C,Na,E,P,id);
    }







}
