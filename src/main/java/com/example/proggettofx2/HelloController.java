package com.example.proggettofx2;

import com.example.proggettofx2.DAO.AdminDao;
import com.example.proggettofx2.DAO.FotografieDAO;
import com.example.proggettofx2.DAO.Utentedao;
import com.example.proggettofx2.entita.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class HelloController
{
    //gestisce accesso utente

    @FXML
    private PasswordField assfield1;

    @FXML
    private TextField txtFIELD;

    @FXML
    void BottonAccedi(ActionEvent event) throws IOException {

        boolean controllo=true;
        // serve a verificare che tutti sono stati inseriti

        if(txtFIELD.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setContentText("INSERIRE EMAIL ,RIPROVARE");
            alert.showAndWait();
            controllo=false;

        }else if(assfield1.getText().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERRORE");
                alert.setContentText("INSERIRE LA PASSWORD, RIPROVARE");
                alert.showAndWait();
                controllo=false;
            }

        if(controllo)
        {
            boolean controllo2=true;

            String E=txtFIELD.getText();
            String P=assfield1.getText();


            Utentedao utentedao = new Utentedao();
            Utente utente = Utente.getUtente();
            Fotografie fotografie = Fotografie.getInstance();
            FotografieDAO fotografieDAO = new FotografieDAO();


            utentedao.collection(utente);
            ResultSet rs =utente.getUsers();

            //find_users prende tutte le email e tutte le password dal database

            try {

                while (rs.next())
                {

                    if (E.equals(rs.getString("email")) && P.equals(rs.getString("password")))
                    {
                        controllo2=false;
                        // controlla se esiste almeno un utente



                        Utente.getUtente().CreaUtente(rs.getString("nome"),rs.getString("cognome"),rs.getString("nazionalità"),rs.getString("email"),rs.getString("password"),rs.getInt("id_utente"));


                        fotografieDAO.initialize(fotografie);


                        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.close();

                        //lo stage utilizzato in questo momento viene chiuso

                        MyStage myStage = new MyStage();
                        myStage.CreateStage("HOME_page.fxml");
                        // viene creato un altro stage

                    }
                }
                rs.close();


                if(controllo2)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERRORE");
                    alert.setContentText("Email o Password errati");
                    alert.showAndWait();
                }


            }catch(SQLException e){throw new RuntimeException(e);}
        }
    }

    @FXML
    void BottonAd(@SuppressWarnings("UnusedParameters")ActionEvent event) throws SQLException, IOException
    {

        TextInputDialog Dialog =new TextInputDialog("AMMINISTRATORE");
        Dialog.setTitle("ACCEDI COME AMMINISTRATORE");
        Dialog.setContentText("INSERISCI LA TUA PASSWORD");

        Optional<String> Pass =  Dialog.showAndWait();
        // variabile string che si riferisce alla password inserita dall'utente all' interno dell' alert


        AdminDao Admindao= new AdminDao();
        Admin admin = new Admin();

        Admindao.initialize(admin);

        try {

                if (Pass.get().equals(admin.getPassword()))
                //find_admin resituisce la password dell admin, presa dal db
                {
                    Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();


                    MyStage myStage = new MyStage();

                    myStage.CreateStage("Adminpage.fxml");

                    myStage.getStage().setHeight(570);
                    myStage.getStage().setWidth(600);


                } else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Errore");
                    alert.setContentText("Password Errata");

                    alert.show();
                }

        } catch (IOException e) {throw new RuntimeException(e);}
    }


    @FXML
    void BottonR(@SuppressWarnings("UnusedParameters")ActionEvent event) throws IOException
    {

        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        MyStage myStage = new MyStage();

        myStage.CreateStage("Second page.fxml");

        myStage.getStage().setTitle("Welcome");
        myStage.getStage().setWidth(700);
        myStage.getStage().setHeight(500);
    }
}
