package com.example.proggettofx2;

import com.example.proggettofx2.entita.Utente;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

        ImageView imageView = new ImageView();

        imageView.setUserData(id_foto);


        imageView.setImage(SwingFXUtils.toFXImage(Bimage, null));

        imageView.setFitHeight(135);
        imageView.setFitWidth(135);

        // viene impostata la grandezza dell'imagine
        imageView.setPickOnBounds(true);


        return imageView;
    }

    public void listView(ListView<String> VistaUtente) {

        //viene creata una listview con tutte le email di tutti gli utenti presenti nel db

        ArrayList<String> lista = new ArrayList<>();

        MainController C = MainController.getInstance();
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


    void setVideo(List<Image> images, List<String> dispositivo, List<String> city, List<String> soggetti) throws SQLException, IOException {
       //imposta una lista d' images, di dispositivi(string),di citta(string),di soggetti(string)che poi verranno utlizzate all'internon del video

        MainController main = MainController.getInstance();

        ResultSet rs;


        rs = main.GetImage(0);
        //vengono recuperate tutte le foto di un utente

        while (rs.next()) {
            ImageView imageView = main.setImageview(rs.getBytes("val_foto"), rs.getInt("id_foto"));
            images.add(imageView.getImage());

            //le foto vengono aggiunte a una lista d' image
            // le foto provengono da main.setimageview, che restituisce imageview, quindi viene usato il metodo getimages



            PreparedStatement pst = MainController.getInstance().DoPrepared("select dispositivo,città from fotografia where id_foto=?");
            //vegnono recuperati tutti i dispositivi e tutte le citta
            pst.setInt(1, (int) imageView.getUserData());

            ResultSet rs1 = pst.executeQuery();
            rs1.next();

            dispositivo.add(rs1.getString("dispositivo"));
            city.add(rs1.getString("città"));

            rs1.close();
            pst.close();


            PreparedStatement ps = main.DoPrepared("Select * from recupera_soggetti_foto(?)");
            //vegono recuperati tutti i soggetti di una foto
            ps.setInt(1, (int) imageView.getUserData());

            ResultSet rs2 = ps.executeQuery();
            rs2.next();

            soggetti.add(rs2.getString(2));

            ps.close();
            rs2.close();
        }

        rs.close();
        main.Closeall();

    }


    void addPhoto(String path,String device,String city,String subject,List<String>list)throws SQLException
    {
        //metodo utilizzato per aggiungere la foto all utente

        MainController C = MainController.getInstance();

        PreparedStatement pst;
        pst= C.DoPrepared("call aggiungi_fotografia(pg_read_binary_file(?),?,?,?)");
        //viene aggiunta la foto

        pst.setString(1,path);
        pst.setString(2,device);
        pst.setString(3,city);
        pst.setInt(4,Utente.getUtente().getIdutente());

        pst.execute();
        pst.close();
        C.Closeall();



        CallableStatement cst= C.Callprocedure("{?=call recupera_id_foto()}");
        //viene recuperato l'id della foto appena aggiunta
        cst.registerOutParameter(1, Types.INTEGER);

        cst.execute();
        int id_foto = cst.getInt(1);

        cst.close();



        pst=MainController.getInstance().DoPrepared("call inserisci_in_foto_raffigura_soggetto(?,?)");
        pst.setInt(1, id_foto);
        pst.setString(2,subject);

        pst.execute();
        pst.close();



        Iterator<String> it= list.listIterator();

        pst=C.DoPrepared("call inserisci_in_foto_raffigura_utente(?,?)");

        while (it.hasNext())
        {
            Object object=it.next();

            pst.setInt(1, id_foto);
            pst.setString(2,object.toString());

            pst.execute();
        }

        pst.close();
        C.Closeall();

    }
}





















