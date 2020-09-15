package com.codecool.hsdecktracker.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    T getById(Long id) throws ElementNotFoundException,SQLException, ClassNotFoundException;

    boolean insert(T t) throws ElementNotFoundException,ClassNotFoundException, SQLException;

    boolean update(T t) throws ElementNotFoundException,ClassNotFoundException, SQLException;

    boolean delete(Long id) throws ElementNotFoundException,ClassNotFoundException;

    List<T> getAll() throws  ElementNotFoundException, SQLException, ClassNotFoundException;
}
