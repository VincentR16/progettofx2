package com.example.proggettofx2;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

    private final List<String> list = new ArrayList<>();
    private static MainController istanza = null;
    private Stage stage;
    private Connection con = null;
    private Statement st = null;
    private CallableStatement stmt = null;
    private PreparedStatement pst = null;
    private String scelta;


    private MainController() {
    }

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

    public void Closeall() throws SQLException {
        this.CloseCon();
        this.Closest();
        this.Closeprepared();
        this.CloseCallable();
    }

    public void SetList(String string) {
        list.add(string);
    }

    public List<String> getList() {
        return list;
    }


    public Stage CreateStage(String S) throws IOException {
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
        if (istanza == null) {
            istanza = new MainController();
        }
        return istanza;
    }

    public Connection getConnention() {
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
        con = this.getConnention();

        try {

            st = con.createStatement();
            return st.executeQuery(S);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public PreparedStatement DoPrepared(String S) throws SQLException {
        con = this.getConnention();
        pst = con.prepareStatement(S);

        return pst;

    }

    public CallableStatement Callprocedure(String S) throws SQLException {
        MainController C = MainController.getInstance();

        con = C.getConnention();

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
        MainController C = MainController.getInstance();
        return C.DoQuery("Select * from utente");
    }

    public ResultSet find_Admin() {
        MainController C = MainController.getInstance();
        return C.DoQuery("Select password from amministratore");
    }

    public ResultSet GetImage(int value) throws SQLException {
        PreparedStatement pst = this.DoPrepared("select val_foto,id_foto from fotografia where id_utente=? and eliminata=?");

        pst.setInt(1, Utente.getUtente().getIdutente());
        pst.setInt(2, value);


        return pst.executeQuery();
    }

    public ImageView setImageview(byte[] binaryData, int id_foto) throws IOException {
        //metto la foto in un array di byte
        InputStream in = new ByteArrayInputStream(binaryData);                                                                  // trasformo i bite in uno stream di dati per poter utilizzarlo come buffered
        BufferedImage Bimage = ImageIO.read(in);

        // uso(SwingFXUtils) una  libreria esterna (aggiunta tramite file .jar) per poter trasformare una
        ImageView imageView = new ImageView();                                                                                   // buffered image (sottoclasse di image in java classico) in una immagine writable
        imageView.setUserData(id_foto);                                                                                         // sotto classe di image di javafx.
        // infatti per quanto possa risultare strano Img(java) NON è COMPATIBILE con IMG(javafx)
        // e quindi di conseguenza non compatibile con le componenti di javafx
        imageView.setImage(SwingFXUtils.toFXImage(Bimage, null));                                                         // funziona perche writableimg estende img

        imageView.setFitHeight(135);                                                                                                 // imposto la grandezza dell'imagine
        imageView.setFitWidth(135);
        imageView.setPickOnBounds(true);


        return imageView;
    }

    public void listView(ListView<String> VistaUtente) {

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
        MainController main = MainController.getInstance();

        ResultSet rs;


        rs = main.getInstance().GetImage(0);

        while (rs.next()) {
            ImageView imageView = main.setImageview(rs.getBytes("val_foto"), rs.getInt("id_foto"));
            images.add(imageView.getImage());


            PreparedStatement pst = MainController.getInstance().DoPrepared("select dispositivo,città from fotografia where id_foto=?");
            pst.setInt(1, (int) imageView.getUserData());

            ResultSet rs1 = pst.executeQuery();
            rs1.next();

            dispositivo.add(rs1.getString("dispositivo"));
            city.add(rs1.getString("città"));

            rs1.close();
            pst.close();


            PreparedStatement ps = main.DoPrepared("Select * from recupera_soggetti_foto(?)");
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

        MainController C = MainController.getInstance();

        PreparedStatement pst;
        pst= C.DoPrepared("call aggiungi_fotografia(pg_read_binary_file(?),?,?,?)");


        pst.setString(1,path);
        pst.setString(2,device);
        pst.setString(3,city);
        pst.setInt(4,Utente.getUtente().getIdutente());

        pst.execute();
        pst.close();
        C.Closeall();



        CallableStatement cst= C.Callprocedure("{?=call recupera_id_foto()}");
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





















