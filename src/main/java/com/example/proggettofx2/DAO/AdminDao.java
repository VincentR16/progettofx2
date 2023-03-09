package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Admin;
import com.example.proggettofx2.entita.Connection;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class AdminDao implements Dao<Admin>
{
    @Override
    public void initialize(Admin admin) throws SQLException, IOException
    {
        Connection C= new Connection();

        ResultSet rs=C.DoQuery("Select password from amministratore");
        rs.next();

        admin.setPassword(rs.getString("password"));
    }

    @Override
    public void insert(Admin admin) throws SQLException
    {
        Connection C= new Connection();

        CallableStatement cst = C.Callprocedure("{?=call recupera_id_utente(?)}");

        //parrtendo dall'email si recupera l'id dell'utente

        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2, admin.getSelectedusers());

        cst.execute();

        delete(admin,cst.getInt(1));
    }

    @Override
    public void delete(Admin admin,int value) throws SQLException
    {
        Connection C= new Connection();


        PreparedStatement pst = C.Callprocedure("call elimina_utente(?)");
        //eliminazioe utente
        pst.setInt(1, value);

        pst.execute();

        C.Closeall();

    }

    @Override
    public List<String> search(Admin admin) throws SQLException
    {
        Connection C= new Connection();

        ResultSet rs = C.DoQuery("select * from numero_totale_fotografie_e_utenti");

        rs.next();

        admin.getLabelfoto().setText(""+rs.getInt("totale_foto"));
        admin.getLabelutenti().setText(""+rs.getInt("totale_utenti"));

        return null;
    }

    @Override
    public void collection(Admin admin)  {}

    @Override
    public void update(Admin admin,int v) {}
}
