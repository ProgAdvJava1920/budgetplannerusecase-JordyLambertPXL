package be.pxl.student.entity;

import java.util.List;

public interface DAO<T, E extends Exception> {

    //Create
    T create(T t) throws E;

    //Read
    List<T> getAll() throws E;
    T getById(int id) throws E;

    //Update
    boolean update(T t) throws E;

    //Delete
    String delete(T t) throws E;

}
