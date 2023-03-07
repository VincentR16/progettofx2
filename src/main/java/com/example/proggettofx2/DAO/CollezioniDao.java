package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Collezioni;
import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.Fotografie;
import com.example.proggettofx2.entita.Utente;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CollezioniDao implements Dao<Collezioni,String>
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


        C.Closeall();

    }

    @Override
    public void insert(Fotografie fotografie, List<String> lista1, List<String> list) throws SQLException {



    }
    @Override
    public void insert(String s) throws SQLException
    {
        Connection C= new Connection();


        PreparedStatement pst1 = C.DoPrepared("call inserisci_fotografie_in_collezione_condivisa(?,?)");
        //inserimento di tutte le foto
        pst1.setInt(1,Utente.getUtente().getIdutente());
        pst1.setString(2,s);

        pst1.execute();

        C.Closeall();
        pst1.close();

    }

    @Override
    public void delete(Collezioni collezioni, int value) throws SQLException {

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
    public void search(Collezioni collezioni, String s, String testo) throws SQLException, IOException {

    }

    @Override
    public void collection(Collezioni collezioni) throws SQLException, IOException {

    }

    @Override
    public void update(Collezioni collezioni, int value) throws SQLException {

    }
}
