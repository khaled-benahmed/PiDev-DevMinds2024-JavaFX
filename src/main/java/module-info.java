module com.example.gestionutilisateurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.gestionutilisateurs to javafx.fxml;
    exports com.example.gestionutilisateurs;
    exports com.example.gestionutilisateurs.entities;
    opens com.example.gestionutilisateurs.entities to javafx.fxml;
    exports com.example.gestionutilisateurs.tools;
    opens com.example.gestionutilisateurs.tools to javafx.fxml;
    exports com.example.gestionutilisateurs.controllers;
    opens com.example.gestionutilisateurs.controllers to javafx.fxml;
}