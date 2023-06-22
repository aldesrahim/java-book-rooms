/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aldes
 */
public class Database {
    private static final String DB_HOST = "mysql";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "book_rooms";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    
    private static Database instance;
    private Connection connection;
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        
        return instance;
    }

    public void connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        connection = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }

    public Connection getConnection() {
        return connection;
    }
}
