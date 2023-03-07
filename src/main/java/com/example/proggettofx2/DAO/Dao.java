package com.example.proggettofx2.DAO;

import com.example.proggettofx2.entita.Connection;
import com.example.proggettofx2.entita.Fotografie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T,S>
{
     Connection Con = null;
     void initialize(T t) throws SQLException, IOException;
    void insert(Fotografie fotografie, List<String> lista1, List<String> list) throws SQLException;

    void insert(String s) throws SQLException;

    void delete(T t, int value) throws SQLException;
    void search(T t,String s, String testo)throws SQLException,IOException;
    void collection(T t) throws SQLException, IOException;

    void update(T t, int value) throws SQLException;

}
