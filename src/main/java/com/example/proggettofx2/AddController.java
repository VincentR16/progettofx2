package com.example.proggettofx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AddController implements Initializable
{
    @FXML
    private ListView<String> VistaUtente;

    @FXML
    private TextField CityField;

    @FXML
    private TextField DeviceField;

    @FXML
    private TextField Pathfield;

    @FXML
    private ComboBox<String> Subjectbox;

    @FXML
    void BpickImage(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.gif"));


        File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null)
        {
            String filePath = file.getPath();
            Pathfield.setText(filePath);
        }

    }


    @FXML
    void Baggiungifoto(@SuppressWarnings("UnusedParameters")ActionEvent event) throws SQLException, IOException {

        boolean Controllo=true;

        List<String> list= MainController.getInstance().getList();

        if(CityField.getText().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERRORE");
                alert.setContentText("Inserire una città");
                alert.showAndWait();
                Controllo=false;

            }else if(DeviceField.getText().equals(""))
                     {
                         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                         alert.setTitle("ERRORE");
                         alert.setContentText("Inserire un device con il quale la foto è stata scattata");
                         alert.showAndWait();
                         Controllo=false;

                     }else  if(Pathfield.getText().equals(""))
                                    {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("ERRORE");
                                        alert.setContentText("SCEGLIERE UNA FOTO");
                                        alert.showAndWait();
                                        Controllo=false;

                                    }else if ( Subjectbox.getSelectionModel().getSelectedItem()==null)
                                            {
                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                alert.setTitle("ERRORE");
                                                alert.setContentText("Scegliere il soggetto della foto");
                                                alert.showAndWait();
                                                Controllo=false;

                                            }


        if(Controllo)
        {
            MainController C = MainController.getInstance();

            PreparedStatement pst;
            pst= C.DoPrepared("call aggiungi_fotografia(pg_read_binary_file(?),?,?,?)");


            pst.setString(1,Pathfield.getText());
            pst.setString(2,DeviceField.getText());
            pst.setString(3,CityField.getText());
            pst.setInt(4,Utente.getUtente().getIdutente());

            pst.execute();
            pst.close();
            C.CloseCon();



            CallableStatement cst= C.Callprocedure("{?=call recupera_id_foto()}");
            cst.registerOutParameter(1, Types.INTEGER);

            cst.execute();
            int id_foto = cst.getInt(1);

            cst.close();

            pst=MainController.getInstance().DoPrepared("call inserisci_in_foto_raffigura_soggetto(?,?)");
            pst.setInt(1, id_foto);
            pst.setString(2,Subjectbox.getSelectionModel().getSelectedItem());

            pst.execute();

            pst.close();


            Iterator<String> it= list.listIterator();


            pst=MainController.getInstance().DoPrepared("call inserisci_in_foto_raffigura_utente(?,?)");

            while (it.hasNext())
            {
                Object object=it.next();

                pst.setInt(1, id_foto);
                pst.setString(2,object.toString());

                pst.execute();
            }

            pst.close();
            C.Closeall();




           MainController.getInstance().getStage().close();
           MainController.getInstance().CreateStage("Aggiungifotopage.fxml");

           Alert alert=new Alert (Alert.AlertType.CONFIRMATION);
           alert.setTitle("FOTO Aggiunta");
           alert.setContentText("Premi torna indietro per visualizzarla");
        }
    }





    @FXML
    void BbackToHome(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("HOME_page.fxml");
    }

    @FXML
    void Bcestino(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Trashpage.fxml");
    }

    @FXML
    void Bcollezioni(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Collezionipage.fxml");
    }

    @FXML
    void Bexit(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();

        MainController C = MainController.getInstance();

        C.CreateStage("Firstpage.fxml");
        C.getStage().setHeight(450);
        C.getStage().setWidth(655);
        C.getStage().setResizable(false);

        Utente.getUtente().setdefault();
    }

    @FXML
    void BProfile(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Profile-page.fxml");
    }

    @FXML
    void Bvideo(@SuppressWarnings("UnusedParameters")ActionEvent event)throws IOException
    {
        MainController.getInstance().getStage().close();
        MainController.getInstance().CreateStage("Videopage.fxml");
    }

    @FXML
    void MouseEntered(MouseEvent event)
    {
        javafx.scene.control.Button button = (javafx.scene.control.Button) (event.getSource());
        button.setStyle("-fx-background-color:  #0C1538");
    }

    @FXML
    void MouseExited(MouseEvent event)
    {
        javafx.scene.control.Button button = (Button) (event.getSource());
        button.setStyle("-fx-background-color:  #183669 ");
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

       MainController main = MainController.getInstance();

            ResultSet rs= main.DoQuery("select categoria from soggetto");

            try {
                while (rs.next())
                {
                    Subjectbox.getItems().add(rs.getString("categoria"));
                }
                rs.close();
                main.Closeall();

            }catch (SQLException e){}


        main.listView(VistaUtente);

        VistaUtente.setOnMouseClicked(event ->
        {
            String item = VistaUtente.getSelectionModel().getSelectedItem();
            if (item != null) {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Aggiungi Soggetto");
                alert.setHeaderText("Aggiungere "+item+" come utente presente nella tua foto?");
                alert.setContentText(item);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    main.SetList(item);
                    VistaUtente.getItems().remove(item);
                }
            }
        });

    }
}











// altro metodo piu lungo che non funzionava alla perfezione, poiche si deve aggiungere una variabile che tiene conto se la foto è stata eliminata o meno,
// poiche il change listener ascolta tutti i cambiamentni di una stringa, e per questo quando viene eliminata lo registra come cambiamento e
// fa comparire l'alert 2 volte di seguito eliminando un altra stringa, non trovando piu quella di riferimento;



       /* VistaUtente.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old, String nuovo) {



                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("AGGIUNGI UTENTE ");
                alert.setContentText("VUOI AGGIUNGERE QUESTO UTENTE ALLA TUA FOTO? \n Dopo che compare il secondo alert premere X");


                Optional<ButtonType> result=alert.showAndWait();

                if (result.get() == ButtonType.OK ) {

                    int i=0;
                    while(!(nuovo.equals(VistaUtente.getItems().get(i))))
                    {i++;}

                    VistaUtente.getItems().remove(i);
                    System.out.println(i);

                    Selected.add(nuovo);
                    MainController.getInstance().SetList(Selected);
                    /
                }
            }
        });



    }

       */
