package be.pxl.student.entity;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T, E extends Exception> {

    //Create
    T create(T t) throws E, SQLException;

    //Read
    List<T> getAll() throws E;
    T getById(int id) throws E;

    //Update
    Account update(T t) throws E;

    //Delete
    void delete(T t) throws E;

}
