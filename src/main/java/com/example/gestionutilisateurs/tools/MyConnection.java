package com.example.gestionutilisateurs.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    private String url="jdbc:mysql://localhost:3306/db_devminds";
    private String login="root";

    private String pwd="";

    private static MyConnection instance;
    Connection cnx;

    public MyConnection(){
        try {
            cnx = DriverManager.getConnection(url,login,pwd);
            System.out.println("Connexion établie..");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnection getInstance(){
        if(instance == null){
            instance = new MyConnection();
        }
        return instance;
    }
}