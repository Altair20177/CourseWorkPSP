package com.company;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException{
        Connection dbConnection;
        String connection = "jdbc:mysql://localhost:3306/kursach";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connection,"root","12345");
        return dbConnection;
    }
}
