package com.java.medicall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Conectare {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.2.2:3306/policlinica?allowPublicKeyRetrieval=true&useSSL=false";


    static final String USER = "root";
    static final String PASS = "CCV_980510";

    public static Connection conect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://db4free.net:3306/policlinicadb?allowPublicKeyRetrieval=true&useSSL=false";
        String username = "carmendb";
        String password = "CCV_980510";
        // Class.forName(driver);

        Connection conn = DriverManager.getConnection(url, "carmendb", PASS);
        System.out.print("se conecteaza...");

        return conn;
    }

    public static void inserare(String s) throws SQLException, ClassNotFoundException {
        Conectare.conect().createStatement().executeUpdate(s);
    }

    public static ResultSet selectare(String s) throws SQLException, ClassNotFoundException {
        System.out.print("selecteaza");
        return Conectare.conect().createStatement().executeQuery(s);

    }
}

