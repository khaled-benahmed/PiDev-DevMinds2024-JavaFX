/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author khaled
 */
public interface CrudPlanning<Planning> {
    
    public void ajouter(Planning p);
    public void modifier(Planning p);
    public void supprimer(int id) throws SQLException;
    public List<Planning> Show();
    public List<Planning> Search(String t);
}
