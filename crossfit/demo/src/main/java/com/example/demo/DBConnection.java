package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection con;
    private static DBConnection instance = null;

    private DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crossfit", "root", "root");
            System.out.println("Connected to the database.");
            System.out.println(con);
        } catch (ClassNotFoundException ex) {
            System.err.println("Could not find the database driver: " + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("Failed to connect to the database: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static DBConnection getInstance(){
        if(instance==null){
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return con;
    }

}
