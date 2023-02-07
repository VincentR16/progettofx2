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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VideoController implements Initializable
{
    boolean controllo=true;
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
        MainController C =MainController.getInstance();

        C.getStage().close();

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
    void Playbutton(@SuppressWarnings("UnusedParameters")MouseEvent event) throws SQLException, IOException {
        if(controllo)
        {
            MainController.getInstance().setVideo(images, dispositivo, city, soggetti);
            controllo=false;
        }
        animationTimer.start();
    }


    @FXML
    void Stopbutton(@SuppressWarnings("UnusedParameters")MouseEvent event) {animationTimer.stop();}

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
    public void initialize(URL url, ResourceBundle resourceBundle) {animazione();}
}
