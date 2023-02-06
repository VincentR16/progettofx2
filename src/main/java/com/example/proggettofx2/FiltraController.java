package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class FiltraController implements Initializable
{
    @FXML
    private ComboBox<String> combobox;

    @FXML
    private Label labelprimo;

    @FXML
    private Label labelsec;

    @FXML
    private Label labelterz;

    @FXML
    private ScrollPane pannel;

    @FXML
    private TextField textfiled;

    @FXML
    void BAggiungifoto(@SuppressWarnings("UnusedParameters") ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Aggiungifotopage.fxml");
        MainController.getInstance().getStage().setWidth(920);
        MainController.getInstance().getStage().setHeight(620);
    }

    @FXML
    void BCestino(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Trashpage.fxml");
    }

    @FXML
    void BCollezioni(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Collezionipage.fxml");
    }

    @FXML
    void BProfile(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Profile-page.fxml");
    }

    @FXML
    void Bvideo(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Videopage.fxml");
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
    void BbackToHome(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("HOME_page.fxml");
    }

    @FXML
    void Bcerca(ActionEvent event) throws SQLException {

        String scelta;

        if(combobox.getSelectionModel().getSelectedItem()==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("IMPORTANTE");
            alert.setContentText("Scegliere la categoria della ricerca");

            alert.show();
        }else{

            if (combobox.getSelectionModel().getSelectedItem()=="Soggetto"){scelta="stesso_soggetto";}else {scelta="stesso_luogo";}

            MainController main= MainController.getInstance();

                PreparedStatement ps= MainController.getInstance().DoPrepared("Select * from "+scelta+"(?,?)");
                ps.setInt(1,Utente.getUtente().getIdutente());
                ps.setString(2,textfiled.getText());

                ResultSet rs = ps.executeQuery();

            GridPane gridPane =new GridPane();                                                                                                      // creo una griglia e ne imposto il gap in altezza e in orizzontale
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            try
            {
                int i=0;
                int j=0;

                while (rs.next())
                {
                    ImageView imageView =main.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));

                    gridPane.add(imageView,j,i);
                    j++;                                                                                                                    // rispetto alle matrici qui si mette prima la colonna e poi la riga
                    if(j>4){j=0;i++;}
                }

                rs.close();
                main.getCon().close();

            }catch(SQLException | IOException e){throw new RuntimeException(e);}


            pannel.setContent(gridPane);

        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        combobox.getItems().add("Luogo");
        combobox.getItems().add("Soggetto");


        MainController main = MainController.getInstance();

        try {
            PreparedStatement ps= main.DoPrepared("select * from top_3_luoghi(?)");
            ps.setInt(1,Utente.getUtente().getIdutente());

            ResultSet rs= ps.executeQuery();


            if(rs.next()) labelprimo.setText(rs.getString(1)+"  "+rs.getString(2));
            if(rs.next()) labelsec.setText(rs.getString(1)+"  "+rs.getString(2));
            if(rs.next())labelterz.setText(rs.getString(1)+"  "+rs.getString(2));

            rs.close();
            ps.close();
            main.Closeall();

        } catch (SQLException e) {throw new RuntimeException(e);}


    }
}
