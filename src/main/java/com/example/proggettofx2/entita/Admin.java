package com.example.proggettofx2.entita;

import javafx.scene.control.Label;

import java.sql.*;

public class Admin
{
    private String Password;

    public Admin() throws SQLException {this.setPassword();}
    public Admin(Label labelfoto,Label labelutenti) throws SQLException
    {
        this.setLabel(labelfoto,labelutenti);
        this.setPassword();
    }

    private void setPassword() throws SQLException
    {
        Connection C= new Connection();

        Password=C.find_Admin().getString("password");
    }

    public String getPassword() {return Password;}

    public void setLabel(Label labelfoto,Label labelutenti) throws SQLException
    {
        Connection C= new Connection();

        ResultSet rs = C.DoQuery("select * from numero_totale_fotografie_e_utenti");

        rs.next();

        labelfoto.setText(""+rs.getInt("totale_foto"));
        labelutenti.setText(""+rs.getInt("totale_utenti"));
    }


    public void deleteUtente(String item) throws SQLException
    {
        Connection C= new Connection();

        CallableStatement cst = C.Callprocedure("{?=call recupera_id_utente(?)}");
        //parrtendo dall'email si recupera l'id dell'utente

        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2, item);

        cst.execute();

        PreparedStatement pst = C.Callprocedure("call elimina_utente(?)");
        //eliminazioe utente
        pst.setInt(1, cst.getInt(1));

        pst.execute();

        C.Closeall();
    }
}
