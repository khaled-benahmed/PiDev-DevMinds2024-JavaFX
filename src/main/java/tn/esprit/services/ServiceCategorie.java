package tn.esprit.services;

import tn.esprit.interfaces.ICategorie;
import tn.esprit.models.Categorie;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategorie implements ICategorie<Categorie> {
    private Connection cnx;

    public ServiceCategorie() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Categorie categorie) {
        String qry = "INSERT INTO `categorie`(`nom_categorie`, `description_c`) VALUES (?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, categorie.getNom_categorie());
            stm.setString(2, categorie.getdescription_c());


            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Categorie> getAll() {
        return null;
    }

    @Override
    public List<Categorie> afficher() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT `id`, `nom_categorie`, `description_c` FROM `categorie`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id"));
                categorie.setNom_categorie(rs.getString("nom_categorie"));
                categorie.setdescription_c(rs.getString("description_c"));
                categories.add(categorie);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }

    @Override
    public List<Categorie> TriparNom() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT `id`, `nom_categorie`, `description_c` FROM `categorie` ORDER BY `nom_categorie`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id"));
                categorie.setNom_categorie(rs.getString("nom_categorie"));
                categorie.setdescription_c(rs.getString("description_c"));


                categories.add(categorie);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }

    @Override
    public List<Categorie> TriparEmail() {
        return null;
    }

    @Override
    public List<Categorie> Rechreche(String recherche) {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT `id`, `nom_categorie`, `description_c` FROM `categorie` WHERE `nom_categorie` LIKE '%" + recherche + "%' OR `description_c`LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id"));
                categorie.setNom_categorie(rs.getString("nom_categorie"));
                categorie.setdescription_c(rs.getString("description_c"));

                categories.add(categorie);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }

    @Override
    public void Update(Categorie categorie) {
        try {
            String qry = "UPDATE `categorie` SET `id`=?,`nom_categorie`=?,`description_c`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, categorie.getId());
            stm.setString(2, categorie.getNom_categorie());
            stm.setString(3, categorie.getdescription_c());

            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Delete(Categorie categorie) {
        try {
            String qry = "DELETE FROM `categorie` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, categorie.getId());
            smt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteByID(int id) {
        try {
            String qry = "DELETE FROM `categorie` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

