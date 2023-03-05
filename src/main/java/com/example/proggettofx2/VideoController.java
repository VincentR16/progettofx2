package com.example.proggettofx2;

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


    @FXML
    void Playbutton(@SuppressWarnings("UnusedParameters")MouseEvent event) {
        animationTimer.start();
    }

    @FXML
    void Stopbutton(@SuppressWarnings("UnusedParameters")MouseEvent event) {animationTimer.stop();}


    public void animazione() throws SQLException, IOException {

        Video video= new Video();
        animationTimer = video.animazione(videoview,labeldisp,labeluog,labesoggetto);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            animazione();

        } catch (SQLException | IOException e) {throw new RuntimeException(e);}
    }
}
