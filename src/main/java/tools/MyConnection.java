package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private final String url="jdbc:mysql://localhost:3306/pidev24";
    private final String login="root";
    private final String mdp="";
    private Connection cnx;
    public static MyConnection instance;

    public Connection getCnx() {
        return cnx;
    }

    private MyConnection() {
        try {
            cnx= DriverManager.getConnection(url,login,mdp);
            System.out.println("Connexion établie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static MyConnection getInstance() {
        if (instance==null){
            instance= new MyConnection();
        }
        return instance;
    }
}
