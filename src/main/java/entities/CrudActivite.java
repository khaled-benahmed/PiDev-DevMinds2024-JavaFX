/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.SQLException;
import java.util.List;


public interface CrudActivite<Act> {
    
    public void ajouter(Act a);
    public void modifier(Act a);
    public void supprimer(int id) throws SQLException;
    public List<Activite> Show();
    public List<Activite> Search(String t);

}
