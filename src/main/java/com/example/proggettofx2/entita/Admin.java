package com.example.proggettofx2.entita;

import com.example.proggettofx2.MainController;
import javafx.scene.control.Label;

import java.sql.*;

public class Admin
{
    private String Password;
    public Admin() throws SQLException {
        this.setPassword();
    }

    private void setPassword() throws SQLException
    {
        MainController main= MainController.getInstance();

        Password=main.find_Admin().getString("password");
    }

    public String getPassword() {return Password;}

    public void setLabel(Label labelfoto,Label labelutenti) throws SQLException
    {
        MainController main =MainController.getInstance();

        ResultSet rs = main.DoQuery("select * from numero_totale_fotografie_e_utenti");

        rs.next();

        labelfoto.setText(""+rs.getInt("totale_foto"));
        labelutenti.setText(""+rs.getInt("totale_utenti"));
    }


    public void deleteUtente(String item) throws SQLException
    {
        MainController main = MainController.getInstance();
        CallableStatement cst = main.Callprocedure("{?=call recupera_id_utente(?)}");
        //parrtendo dall'email si recupera l'id dell'utente

        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2, item);

        cst.execute();

        PreparedStatement pst = main.Callprocedure("call elimina_utente(?)");
        //eliminazioe utente
        pst.setInt(1, cst.getInt(1));

        pst.execute();

        main.Closeall();
    }
}
