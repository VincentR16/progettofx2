package com.example.proggettofx2.entita;

import com.example.proggettofx2.DAO.FotografieDAO;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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

    List<String> list1= new ArrayList<>();

    list1.add(path);
    list1.add(dispositivo);
    list1.add(citta);
    list1.add(dispositivo);

        FotografieDAO fotodao= new FotografieDAO();
        Fotografie fotografie = Fotografie.getInstance();

        fotografie.AggiungiFoto(path);
        fotodao.update(fotografie,list1,list);

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
