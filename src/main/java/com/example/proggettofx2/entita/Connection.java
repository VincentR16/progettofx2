package com.example.proggettofx2.entita;

import java.sql.*;




// todo andare poi a scorporare tutte le query e andare a creare un metodo per ogni query che gestisce la connessione e che restituisce solo cio che è
// todo realmente utile

public class Connection {

    //gestisce il collegamento col db,

    private java.sql.Connection con;
    private Statement st = null;
    private CallableStatement stmt = null;
    private PreparedStatement pst = null;

    public Connection() {con=this.getConnention();}


    public void Closeall() throws SQLException
    {
        //chiude tutte le connessioni col db
        this.CloseCon();
        this.Closest();
        this.Closeprepared();
        this.CloseCallable();
    }



    public java.sql.Connection getConnention() {

        //staabilisce connessione col db
        //si sarebbe potuto creare un ulteriore pagina che prima dell'apertura dell'applicazione, chiede
        //il nome del db, username e password di postgres

        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/GALLERIA_PROGGETTO";
            return DriverManager.getConnection(url, "postgres", "biscotti");

            //System.out.println("CONNESSIONE RIUSCITA CON SUCCESSO");

        } catch (ClassNotFoundException e) {
            System.out.println("DB NOT FOUND");
            throw new RuntimeException(e);

        } catch (SQLException e) {
            System.out.println("\n\n CONNESSIONE FALLITA");
            throw new RuntimeException(e);

        }

    }

    public ResultSet DoQuery(String S)
    {
        //svolge la query rappresentata dalla stringa S

        try {
            st = con.createStatement();
            return st.executeQuery(S);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement DoPrepared(String S) throws SQLException {
        //ritorna una statmentprepared, che vine impostata dalla stringa S
        con = this.getConnention();
        pst = con.prepareStatement(S);

        return pst;
    }

    public CallableStatement Callprocedure(String S) throws SQLException {
        //ritorna uno statement che viene impostato dalla stringa S

        con = this.getConnention();

        return stmt = con.prepareCall(S);
    }

    public void Closest() throws SQLException {
        if (st != null) {
            st.close();
        }
    }

    public void Closeprepared() throws SQLException {
        if (pst != null) {
            pst.close();
        }
    }

    public void CloseCallable() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    public void CloseCon() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public ResultSet find_users() {
        //query per ricevere tutte le informazione di tutti gli utenti
        return this.DoQuery("Select * from utente");
    }

    public ResultSet find_Admin() {
        //query per ricervere la pass dell admin
        return this.DoQuery("Select password from amministratore");
    }

    public ResultSet GetImage(int value) throws SQLException {

        //query per cercare tutte le foto di un utente

        PreparedStatement pst = this.DoPrepared("select val_foto,id_foto from fotografia where id_utente=? and eliminata=?");

        pst.setInt(1, Utente.getUtente().getIdutente());
        pst.setInt(2, value);


        return pst.executeQuery();
    }
}





















