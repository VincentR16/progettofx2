package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.Fotografie;
import com.example.proggettofx2.entita.Utente;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class FotografieDAO implements Dao<Fotografie,String>{

    private Connection Con;
    @Override
    public void initialize(Fotografie fotografie) throws SQLException, IOException
    {
        this.Con=new Connection();

        ResultSet rs = this.getImages(0);

        while (rs.next())
        {
            ImageView imageView =fotografie.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            fotografie.getListafoto().add(imageView);
        }





        ResultSet rs1 = this.getImages(1);

        while (rs1.next())
        {
            ImageView imageView =fotografie.setImageview(rs1.getBytes("val_foto"),rs1.getInt("id_foto"));
            fotografie.getListafotoeliminate().add(imageView);
        }

        Con.Closeall();
    }

    public FotografieDAO(){this.Con=new Connection();}


    @Override
    public void update(Fotografie fotografie, List<String> lista1, List<String> list) throws SQLException
    {
        this.Con=new Connection();

        PreparedStatement pst;
        pst= Con.DoPrepared("call aggiungi_fotografia(pg_read_binary_file(?),?,?,?)");
        //viene aggiunta la foto

        pst.setString(1,lista1.get(0));
        pst.setString(2, lista1.get(1));
        pst.setString(3,lista1.get(2));
        pst.setInt(4, Utente.getUtente().getIdutente());

        pst.execute();
        pst.close();
        Con.Closeall();



        CallableStatement cst= Con.Callprocedure("{?=call recupera_id_foto()}");
        //viene recuperato l'id della foto appena aggiunta
        cst.registerOutParameter(1, Types.INTEGER);

        cst.execute();
        int id_foto = cst.getInt(1);

        cst.close();



        pst= Con.DoPrepared("call inserisci_in_foto_raffigura_soggetto(?,?)");
        pst.setInt(1, id_foto);
        pst.setString(2,lista1.get(3));

        pst.execute();
        pst.close();



        Iterator<String> it= list.listIterator();

        pst=Con.DoPrepared("call inserisci_in_foto_raffigura_utente(?,?)");

        while (it.hasNext())
        {
            Object object=it.next();

            pst.setInt(1, id_foto);
            pst.setString(2,object.toString());

            pst.execute();
        }

        pst.close();
        Con.Closeall();
    }

    @Override
    public void delete(Fotografie fotografie, int value) throws SQLException
    {
        this.Con=new Connection();

        PreparedStatement pst= Con.DoPrepared("update fotografia set eliminata=1 where id_foto = ?");

        pst.setInt(1,value);
        pst.execute();

        pst.close();

        Con.Closeall();

    }

    @Override
    public void search(Fotografie fotografie,String scelta,String testo) throws SQLException, IOException {


        this.Con=new Connection();

        PreparedStatement ps= Con.DoPrepared("Select * from "+scelta+"(?,?)");
        //la funzione nel db restituisce le foto con quei criteri

        ps.setInt(1, Utente.getUtente().getIdutente());
        ps.setString(2,testo);


        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            ImageView imageView=fotografie.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));

            fotografie.getFotofiltrate().add(imageView);
        }


    }

    @Override
    public void collection(Fotografie fotografie) throws SQLException, IOException {

        this.Con=new Connection();

        PreparedStatement st1 = Con.DoPrepared("select * from collezione_condivisa(?)");
        //recupero foto della collezione

        st1.setString(1,fotografie.getScelta());
        ResultSet rs= st1.executeQuery();

        while (rs.next())
        {
            ImageView imageView=fotografie.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            fotografie.getCollezione().add(imageView);
        }


        PreparedStatement ps= Con.DoPrepared("Select * from foto_non_presenti_in_collezione_condivisa(?,?)");
        //prende tutte le foto non presenti nella collezione

        ps.setInt(1, Utente.getUtente().getIdutente());
        ps.setString(2, fotografie.getScelta());


         rs = ps.executeQuery();

        while (rs.next())
        {
           ImageView imageView=fotografie.setImageview(rs.getBytes("val_foto"),rs.getInt("id_foto"));
            fotografie.getNonincollezione().add(imageView);
        }

        Con.Closeall();
    }


    public ResultSet getImages(int value) throws SQLException
   {
       this.Con=new Connection();

       PreparedStatement pst = Con.DoPrepared("select val_foto,id_foto from fotografia where id_utente=? and eliminata=?");

       pst.setInt(1, Utente.getUtente().getIdutente());
       pst.setInt(2, value);


       return pst.executeQuery();
   }


}
