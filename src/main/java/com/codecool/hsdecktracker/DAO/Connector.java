package com.codecool.hsdecktracker.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {
    java.sql.Connection connection;

    private final String user = "mzmwrjyqafeyex";
    private final String password = "9f354d130bc65584699de319e86e44ec4bd8febcec6525a0c0577781ab12d44a";
    private static final String CONNECTION_STRING = "jdbc:postgresql://ec2-54-75-225-52.eu-west-1.compute.amazonaws.com:5432/d4mstjv279ghi3";

    public Connection Connect() {
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
