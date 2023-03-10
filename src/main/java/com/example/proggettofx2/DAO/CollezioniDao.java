package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Collezioni;
import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.Fotografie;
import com.example.proggettofx2.entita.Utente;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollezioniDao implements Dao<Collezioni>
{
    @Override
    public void initialize(Collezioni collezioni) throws SQLException, IOException
    {
        Fotografie fotografie = Fotografie.getInstance();

        Connection C =new Connection();

        PreparedStatement st1 = C.DoPrepared("select * from collezione_condivisa(?)");
        //recupero foto della collezione

        st1.setString(1,fotografie.getScelta());
        ResultSet rs= st1.executeQuery();

        while (rs.next())
        {
            ImageView imageView=fotografie.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            fotografie.getCollezione().add(imageView);
        }


        PreparedStatement ps= C.DoPrepared("Select * from foto_non_presenti_in_collezione_condivisa(?,?)");
        //prende tutte le foto non presenti nella collezione

        ps.setInt(1, Utente.getUtente().getIdutente());
        ps.setString(2, fotografie.getScelta());


        rs = ps.executeQuery();

        while (rs.next())
        {
            ImageView imageView=fotografie.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            fotografie.getNonincollezione().add(imageView);
        }

        collezioni.Setlistnotused(fotografie.getNonincollezione());
        collezioni.setListused(fotografie.getCollezione());


        C.Closeall();

    }


    @Override
    public void insert(Collezioni collezioni) throws SQLException
    {
        Connection C= new Connection();


        PreparedStatement pst1 = C.DoPrepared("call inserisci_fotografie_in_collezione_condivisa(?,?)");
        //inserimento di tutte le foto
        pst1.setInt(1,Utente.getUtente().getIdutente());
        pst1.setString(2, collezioni.getNuovoutente());

        pst1.execute();

        C.Closeall();
        pst1.close();
    }

    @Override
    public void delete(Collezioni collezioni, int value) throws SQLException
    {

        Connection C= new Connection();

        PreparedStatement ps =C.DoPrepared("call rendi_fotografia_privata_o_pubblica(?,?)");
        //viene resa privata la foto

        ps.setInt(1, value);
        ps.setString(2, "privata");


        ps.execute();
        ps.close();

        C.Closeall();
    }

    @Override
    public List<String> search(Collezioni collezioni) throws SQLException
    {

        Connection C= new Connection();

        PreparedStatement pst = C.DoPrepared("select distinct nome from collezione as c, utente_possiede_collezione as u where u.id_utente=? and c.personale=0 and c.id_collezione= u.id_collezione");
        pst.setInt(1, Utente.getUtente().getIdutente());

        ResultSet rs1 = pst.executeQuery();

        List<String>nomecollezione= new ArrayList<>();

        while (rs1.next())
        {

            nomecollezione.add(rs1.getString("nome"));
        }

        C.Closeall();
        rs1.close();

        return nomecollezione;
    }

    @Override
    public void collection(Collezioni collezioni) throws SQLException
    {

        Connection C= new Connection();


        PreparedStatement pst = C.DoPrepared("call crea_collezione_condivisa(?,?,?)");
        pst.setInt(1, Utente.getUtente().getIdutente());

        pst.setString(2, collezioni.getNuovoutente());
        pst.setString(3, collezioni.getNomeCollezione());

        pst.execute();

        C.Closeall();
        pst.close();

    }

    @Override
    public void update(Collezioni collezioni, int value) throws SQLException, IOException {

        Connection C= new Connection();

        Fotografie fotografie = Fotografie.getInstance();

        PreparedStatement  ps1 = C.DoPrepared("call inserisci_fotografia_in_collezione_condivisa(?,?)");

        ps1.setInt(1,value);
        ps1.setString(2,fotografie .getScelta());

        ps1.execute();
        ps1.close();

        C.Closeall();

    }


    public  void Setid(Collezioni collezioni) throws SQLException
    {
        Fotografie fotografie = Fotografie.getInstance();

        Connection C= new Connection();

        CallableStatement cs1 = C.Callprocedure("{?= call recupera_id_collezione(?)}");

        // partendo dal nome della collezione, viene recupoerato l id della collezione
        cs1.registerOutParameter(1, Types.INTEGER);
        cs1.setString(2, fotografie.getScelta());

        cs1.execute();

        collezioni.setID(cs1.getInt(1));

        C.Closeall();
    }

}
