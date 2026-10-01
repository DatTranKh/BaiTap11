package com.bookstore.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext_24110198 {
    private static final String SERVER_NAME = "localhost";
    private static final String PORT = "1433";
    private static final String DB_NAME = "KTGK";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT 
                    + ";databaseName=" + DB_NAME 
                    + ";encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(url, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}