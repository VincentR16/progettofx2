package com.example.proggettofx2.DAO;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T>
{
    void initialize(T t) throws SQLException, IOException;
    void insert(T t) throws SQLException;
    void delete(T t,int value) throws SQLException;
    List<String> search(T t)throws SQLException,IOException;
    void collection(T t) throws SQLException, IOException;
    void update(T t,int value) throws SQLException, IOException;

}
