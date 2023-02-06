package com.example.proggettofx2;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VideoController implements Initializable
{
    private final List<Image> images= new ArrayList<>();
    private final List<String>dispositivo= new ArrayList<>();
    private final List<String>city= new ArrayList<>();
    private final List<String>soggetti= new ArrayList<>();
    private AnimationTimer animationTimer;


    @FXML
    private ImageView videoview;
    @FXML
    private Label labeluog;
    @FXML
    private Label labeldisp;
    @FXML
    private Label labesoggetto;

    @FXML
    void BAggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Aggiungifotopage.fxml");
        MainController.getInstance().getStage().setWidth(920);
        MainController.getInstance().getStage().setHeight(620);
    }

    @FXML
    void BCollezioni(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Collezionipage.fxml");
    }


    @FXML
    void BProfile(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Profile-page.fxml");
    }


    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        MainController.getInstance().getStage().close();

        MainController C =MainController.getInstance();

        C.CreateStage("Firstpage.fxml");
        C.getStage().setHeight(450);
        C.getStage().setWidth(655);
        C.getStage().setResizable(false);

        Utente.getUtente().setdefault();
    }

    @FXML
    void BCestino(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Trashpage.fxml");
    }


    @FXML
    void BbackToHome(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("HOME_page.fxml");
    }

    @FXML
    void MouseEntered(MouseEvent event)
    {
        javafx.scene.control.Button button=(javafx.scene.control.Button) (event.getSource());
        button.setStyle("-fx-background-color:  #0C1538");
    }

    @FXML
    void MouseExited(MouseEvent event)
    {
        javafx.scene.control.Button button=(Button) (event.getSource());
        button.setStyle("-fx-background-color:  #183669 ");
    }


    @FXML
    void Playbutton(@SuppressWarnings("UnusedParameters")MouseEvent event)
    {
        animationTimer.start();
    }


    @FXML
    void Stopbutton(@SuppressWarnings("UnusedParameters")MouseEvent event)
    {
        animationTimer.stop();
    }

    public void animazione()
    {
        final long[] inizio = {System.currentTimeMillis()};
        final int[] indice = {0};

        animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                long tempocorrente= System.currentTimeMillis();

                if(tempocorrente- inizio[0] >= 4000)
                {

                    if(indice[0] <images.size())
                    {
                        videoview.setImage(images.get(indice[0]));

                        labeldisp.setText(dispositivo.get(indice[0]));
                        labeluog.setText(city.get(indice[0]));
                        labesoggetto.setText(soggetti.get(indice[0]));

                    }else {indice[0]=-1;}

                    indice[0]++;

                    inizio[0] =tempocorrente;
                }

            }
        };

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

     /*   MainController main =  MainController.getInstance();

        ResultSet rs;

        try {
            rs=main.getInstance().GetImage(0);

            while(rs.next())
            {
                ImageView imageView=main.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
                images.add(imageView.getImage());


                PreparedStatement pst= MainController.getInstance().DoPrepared("select dispositivo,città from fotografia where id_foto=?");
                pst.setInt(1,(int)imageView.getUserData());

                ResultSet rs1 = pst.executeQuery();
                rs1.next();

                dispositivo.add(rs1.getString("dispositivo"));
                city.add(rs1.getString("città"));

                rs1.close();
                pst.close();


                PreparedStatement ps=main.DoPrepared("Select * from recupera_soggetti_foto(?)");
                ps.setInt(1,(int)imageView.getUserData());

                ResultSet rs2=ps.executeQuery();
                rs2.next();

                soggetti.add(rs2.getString(2));

                ps.close();
                rs2.close();
            }

            rs.close();
            main.Closeall();

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}

        videoview.setImage(images.get(0));
        labeldisp.setText(dispositivo.get(0));
        labeluog.setText(city.get(0));
        labesoggetto.setText(soggetti.get(0));

        animazione();
*/
    }
}
