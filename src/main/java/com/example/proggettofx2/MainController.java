package com.example.proggettofx2;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainController {

    private final List<String>  list=new ArrayList<String>();
    private static MainController istanza = null;
    private  Stage stage;
    private Connection con=null;
    private Statement st=null;
    private CallableStatement stmt=null;
    private PreparedStatement pst=null;
    private String scelta;


    private MainController() {}
    public Connection getCon() {return con;}
    public void setScelta(String s){scelta=s;}
    public String getScelta() {return scelta;}

    public  Stage getStage(){return stage;}

    public  void Closeall() throws SQLException {this.CloseCon();this.Closest();this.Closeprepared();this.CloseCallable();}
    public void SetList(String string) {list.add(string);}
    public List<String> getList(){return list;}


    public Stage CreateStage(String S) throws IOException
    {

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

    public static MainController getInstance()
    {
        if(istanza==null) {istanza= new  MainController();}
        return istanza;
    }

   public Connection getConnention()
    {
        try {
            Class.forName("org.postgresql.Driver");

            String url="jdbc:postgresql://localhost:5432/GALLERIA_PROGGETTO";
            Connection con=DriverManager.getConnection(url,"postgres","biscotti");

            //System.out.println("CONNESSIONE RIUSCITA CON SUCCESSO");
            return  con;

        } catch (ClassNotFoundException e)
       {
           System.out.println("DB NOT FOUND");
           throw new RuntimeException(e);

       }catch (SQLException e)
        {
           System.out.println("\n\n CONNESSIONE FALLITA");
           throw  new RuntimeException(e);

        }

    }

    public ResultSet DoQuery(String S)
    {
        con= this.getConnention();

        try {

            st= con.createStatement();
            ResultSet rs= st.executeQuery(S);

            return rs;

        }catch (SQLException e){throw new RuntimeException(e);}

    }

    public PreparedStatement DoPrepared(String S)throws SQLException
    {
        con=this.getConnention();
        pst=con.prepareStatement(S);

        return pst;

    }

    public CallableStatement Callprocedure(String S) throws SQLException
    {
        MainController C = MainController.getInstance();

        con=C.getConnention();

        return stmt=con.prepareCall(S);
    }

    public void Closest()throws SQLException
    {
        if(st!=null)
        {
            st.close();
        }
    }

    public void Closeprepared()throws SQLException
    {
        if(pst!=null)
        {
            pst.close();
        }
    }

    public void CloseCallable()throws SQLException
    {
        if(stmt!=null)
        {
            stmt.close();
        }
    }
    public void CloseCon()throws SQLException
    {
        if(con!=null)
        {
            con.close();
        }
    }

    public ResultSet find_users()
    {
        MainController C =MainController.getInstance();
        return   C.DoQuery("Select * from utente");
    }

    public  ResultSet find_Admin()
    {
        MainController C =MainController.getInstance();
        return   C.DoQuery("Select password from amministratore");
    }

    public ResultSet GetImage(int value)throws SQLException
    {
        PreparedStatement pst= this.DoPrepared("select val_foto,id_foto from fotografia where id_utente=? and eliminata=?");

        pst.setInt(1,Utente.getUtente().getIdutente());
        pst.setInt(2,value);


        return  pst.executeQuery();
    }

    public ImageView setImageview(byte [] binaryData,int id_foto) throws IOException
    {
                                                                                                                                //metto la foto in un array di byte


        InputStream in = new ByteArrayInputStream(binaryData);                                                                  // trasformo i bite in uno stream di dati per poter utilizzarlo come buffered
        BufferedImage Bimage = ImageIO.read(in);

                                                                                                                                // uso(SwingFXUtils) una  libreria esterna (aggiunta tramite file .jar) per poter trasformare una
        ImageView imageView= new ImageView();                                                                                   // buffered image (sottoclasse di image in java classico) in una immagine writable
        imageView.setUserData(id_foto);                                                                                         // sotto classe di image di javafx.
                                                                                                                                      // infatti per quanto possa risultare strano Img(java) NON è COMPATIBILE con IMG(javafx)
                                                                                                                                      // e quindi di conseguenza non compatibile con le componenti di javafx


        imageView.setImage( SwingFXUtils.toFXImage(Bimage,null));                                                         // funziona perche writableimg estende img

        imageView.setFitHeight(135);                                                                                                 // imposto la grandezza dell'imagine
        imageView.setFitWidth(135);
        imageView.setPickOnBounds(true);


          return imageView;
    }

    public ListView<String> listView(ListView<String> VistaUtente)
    {

        ArrayList<String> lista = new ArrayList<>();

        MainController C =MainController.getInstance();
        ResultSet rs=C.DoQuery("select email from utente");

        try {

            while (rs.next())
            {
                lista.add(rs.getString("email"));
            }

            rs.close();

        }catch (SQLException e){throw  new RuntimeException(e);}


        VistaUtente.getItems().addAll(lista);


       return VistaUtente;
    }
}

























//Image img = MainController.convertToFxImage(Bimage); altro  metodo al posto della libreria esterna (preso da un atlro utente)



/*public Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    // ALTRA POSSIBILITA
    // CONVERTE UNA BUFFEREDIMAGES IN UNA WRITABLE  IMAGE CHE è UNA SOTTOCLASSE DI IMAGE, NON L'HO FATTO IO, HO PREFERITO USARE
    // UNA LIBRERIA ESTERNA CHIAMATA JAVAX.SWING CHE METTE IN COLLEGAMENTO LE CLASSI SWING E LE CLASSI JAVAFX
    // TUTTO CIO MI SERVIVA POICHE IMAGE DI JAVA NON E COMPATIBILE CON IMAGE DI JAVAFX.
    // ED BUFFERED IMAGE è UNA SOTTOCLASSE DI JAVA QUINDI NON COMPATIBILE CON LE CLASSI DI JAVAFX.*/
//INVECE WRITABLEIMAGE è UNA SOTTOCLASSE DI IMAGE IN JAVAFX.