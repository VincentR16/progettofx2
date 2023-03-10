package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.SoggettieLuoghi;
import com.example.proggettofx2.entita.Utente;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoggettiDao implements Dao<SoggettieLuoghi>
{


    @Override
    public void initialize(SoggettieLuoghi soggettieLuoghi) throws SQLException, IOException
    {
        Connection C = new Connection();

        ResultSet rs= C.DoQuery("select categoria from soggetto");
        //restituisce la categorie e vengono inserite nel Subjectbox

        try {
            while (rs.next())
            {
                soggettieLuoghi.getCategorie().add(rs.getString("categoria"));
            }
            rs.close();
            C.Closeall();

        }catch (SQLException e){e.printStackTrace();}

    }

    @Override
    public void insert(SoggettieLuoghi soggettieLuoghi) throws SQLException {

    }


    @Override
    public void delete(SoggettieLuoghi soggettieLuoghi, int value)  {

    }

    @Override
    public List<String> search(SoggettieLuoghi soggettieLuoghi) throws SQLException
    {

        Connection C= new Connection();
        PreparedStatement ps= C.DoPrepared("select * from top_3_luoghi(?)");

        //query che restituisce i luoghi più immortalati
        ps.setInt(1, Utente.getUtente().getIdutente());

        ResultSet rs= ps.executeQuery();


        ArrayList<String> list = new ArrayList<>();

      while (rs.next())
      {
          list.add(rs.getString(1));
          list.add(rs.getString(2));
      }

        rs.close();
        ps.close();
        C.Closeall();

        return list;
    }

    @Override
    public void collection(SoggettieLuoghi soggettieLuoghi)  {

    }

    @Override
    public void update(SoggettieLuoghi soggettieLuoghi, int value) throws SQLException, IOException {

    }
}
