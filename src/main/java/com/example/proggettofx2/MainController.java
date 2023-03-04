package com.example.proggettofx2;

import com.example.proggettofx2.entita.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;

// todo si potrebbe renderla non statica, e fare che ogni volta che si crea un oggetto venga impostata la connessione nella variabile con,
// todo gli stage vengono gestiti in altro modo tipo andando a creare un altra classe statica, oppure facendo come i nodi e non usare nessuna
// todo classe per gestire gli stage.
// todo questa diventerebbe una classe connessione.
// todo andare poi a scorporare tutte le query e andare a creare un metodo per ogni query che gestisce la connessione e che restituisce solo cio che è
// todo realmente utile

public class MainController {

    //gestisce il collegamento col db, calcoli ed elaborazione dati, e trasmissione dati tra i diversi controller

    private static MainController istanza = null;
    private Stage stage;
    private Connection con = null;
    private Statement st = null;
    private CallableStatement stmt = null;
    private PreparedStatement pst = null;
    private String scelta;


    private MainController() {}

    public Connection getCon() {
        return con;
    }

    public void setScelta(String s) {
        scelta = s;
    }

    public String getScelta() {
        return scelta;
    }

    public Stage getStage() {
        return stage;
    }

    public void Closeall() throws SQLException
    {
        //chiude tutte le connessioni col db
        this.CloseCon();
        this.Closest();
        this.Closeprepared();
        this.CloseCallable();
    }


    public Stage CreateStage(String S) throws IOException {
        //crea un nuovo stage partendo dal file fxml
        //riceve come input il nome del file, presente nella cartella resources/com.examples..

        Parent root = FXMLLoader.load(getClass().getResource(S));
        stage = new Stage();

        stage.setScene(new Scene(root, 500, 500));
        stage.setTitle("Project Gallery");
        stage.setResizable(false);


        stage.setWidth(920);
        stage.setHeight(620);
        stage.show();

        return stage;
    }

    public static MainController getInstance() {
        //crea o restituisce istanza maincontroller
        if (istanza == null) {
            istanza = new MainController();
        }
        return istanza;
    }

    public Connection getConnention() {

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

    public ResultSet DoQuery(String S) {
        //svolge la query rappresentata dalla stringa S
        con = this.getConnention();

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





















