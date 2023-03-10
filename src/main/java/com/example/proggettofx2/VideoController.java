package com.example.proggettofx2;

import com.example.proggettofx2.DAO.VideoDao;
import com.example.proggettofx2.entita.Video;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VideoController extends MenuController implements Initializable
{
    // gestisce lo stage del video

    private AnimationTimer animationTimer;
    @FXML
    private ImageView videoview;
    @FXML
    private Label labeluog;
    @FXML
    private Label labeldisp;
    @FXML
    private Label labesoggetto;
    private Video video;


    @FXML
    void Playbutton(@SuppressWarnings("UnusedParameters")MouseEvent event) {
        animationTimer.start();
    }

    @FXML
    void Stopbutton(@SuppressWarnings("UnusedParameters")MouseEvent event) {animationTimer.stop();}


    public void animazione()
    {
        animationTimer = animazione(videoview,labeldisp,labeluog,labesoggetto);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        video= new Video();
        VideoDao videoDao = new VideoDao();

        try
        {
            videoDao.initialize(video);

        } catch (SQLException e) {throw new RuntimeException(e);}

        animazione();
    }





    public AnimationTimer animazione(ImageView videoview, Label labeldisp,Label labeluog,Label labesoggetto)
    {
        final long[] inizio = {System.currentTimeMillis()};
        final int[] indice = {0};

        return new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                long tempocorrente= System.currentTimeMillis();

                if(tempocorrente- inizio[0] >= 4000)
                {

                    if(indice[0] <video.getImages().size())
                    {
                        videoview.setImage(video.getImages().get(indice[0]));

                        labeldisp.setText(video.getDispositivo().get(indice[0]));
                        labeluog.setText(video.getCity().get(indice[0]));
                        labesoggetto.setText(video.getSoggetti().get(indice[0]));

                    }else {indice[0]=-1;}

                    indice[0]++;

                    inizio[0] =tempocorrente;
                }
            }
        };
    }















}
