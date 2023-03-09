package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.Utente;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Utentedao implements Dao<Utente>
{
    @Override
    public void initialize(Utente utente) throws SQLException, IOException
    {
        Connection C  = new Connection();

        PreparedStatement stmt;
        stmt=C.DoPrepared("call crea_utente(?,?,?,?,?)");

        //viene creato un utente all' interno del db

        stmt.setString(1, utente.getNome());
        stmt.setString(2, utente.getCognome());
        stmt.setString(3, utente.getEmail());
        stmt.setString(4, utente.getNazionalita());
        stmt.setString(5, utente.getPassword());

        stmt.execute();
    }


    @Override
    public void insert(Utente utente) throws SQLException
    {
        Connection C = new Connection();

        CallableStatement cst=C.Callprocedure("{?=call recupera_id_utente(?)}");

        // viene recuperato l'id dell 'utente cche si è appena registrato

        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2, utente.getEmail());

        cst.execute();
        utente.setIdutente(cst.getInt(1));
    }

    @Override
    public void delete(Utente utente, int value)
    {}

    @Override
    public List<String> search(Utente utente) throws SQLException, IOException
    {
        ArrayList<String> lista = new ArrayList<>();

        Connection C= new Connection();

        ResultSet rs = C.DoQuery("select email from utente");


            while (rs.next())
            {
                lista.add(rs.getString("email"));
            }

            rs.close();

        return lista;
    }

    @Override
    public void collection(Utente utente)
    {
        Connection C = new Connection();
        utente.setUsers(C.DoQuery("Select * from utente"));
    }

    @Override
    public void update(Utente utente, int value) throws SQLException
    {
        Connection C= new Connection();

        PreparedStatement pst= C.DoPrepared("update utente set nome= ?,cognome= ?,email= ?,nazionalità= ?,password= ? where id_utente= ?");
        // nel caso di modifica, viene modificato anche il db


        pst.setString(1, utente.getNome());
        pst.setString(2, utente.getCognome());
        pst.setString(3, utente.getEmail());
        pst.setString(4, utente.getNazionalita());
        pst.setString(5, utente.getPassword());
        pst.setInt(6,utente.getIdutente());

        pst.execute();
        pst.close();

        C.Closeall();

    }
}
