package com.example.gestionutilisateurs.interfaces;

import java.sql.SQLException;
import java.util.List;
import com.example.gestionutilisateurs.entities.User;

public interface IService <User> {
    void ajouter(User t) throws SQLException;

    void modifier(User t) throws SQLException;

    void supprimer(User t) throws SQLException;

    List<com.example.gestionutilisateurs.entities.User> recuperer() throws SQLException;

    List <User> getAllData()throws SQLException;
}
