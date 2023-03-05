package com.example.proggettofx2.entita;

import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.List;


public class Add
{
    private String path;
    private String citta;
    private String dispositivo;
    private String soggetto;


    private List<String> list;

    public Add(){}

    public Add(String Pa,String C,String D, String S, List<String> list1)
    {
        path=Pa;
        citta=C;
        dispositivo=D;
        soggetto=S;
        list=list1;
    }

    public void Addphoto() throws SQLException, IOException {

        Connection C = new Connection();

        PreparedStatement pst;
        pst= C.DoPrepared("call aggiungi_fotografia(pg_read_binary_file(?),?,?,?)");
        //viene aggiunta la foto

        pst.setString(1,path);
        pst.setString(2,dispositivo);
        pst.setString(3,citta);
        pst.setInt(4,Utente.getUtente().getIdutente());

        pst.execute();
        pst.close();
        C.Closeall();



        CallableStatement cst= C.Callprocedure("{?=call recupera_id_foto()}");
        //viene recuperato l'id della foto appena aggiunta
        cst.registerOutParameter(1, Types.INTEGER);

        cst.execute();
        int id_foto = cst.getInt(1);

        cst.close();



        pst= C.DoPrepared("call inserisci_in_foto_raffigura_soggetto(?,?)");
        pst.setInt(1, id_foto);
        pst.setString(2,soggetto);

        pst.execute();
        pst.close();



        Iterator<String> it= list.listIterator();

        pst=C.DoPrepared("call inserisci_in_foto_raffigura_utente(?,?)");

        while (it.hasNext())
        {
            Object object=it.next();

            pst.setInt(1, id_foto);
            pst.setString(2,object.toString());

            pst.execute();
        }

        pst.close();
        C.Closeall();

        Fotografie foto= Fotografie.getInstance();
        foto.setlistafoto();
    }

    public void setSubjectbox(ComboBox<String> comboBox)
    {

        Connection C = new Connection();

        ResultSet rs= C.DoQuery("select categoria from soggetto");
        //restituisce la categorie e vengono inserite nel Subjectbox

        try {
            while (rs.next())
            {
                comboBox.getItems().add(rs.getString("categoria"));
            }
            rs.close();
            C.Closeall();

        }catch (SQLException e){e.printStackTrace();}

    }









}
