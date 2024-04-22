package tn.esprit.services;

import tn.esprit.interfaces.IAbonnement;
import tn.esprit.interfaces.ICategorie;
import tn.esprit.models.Abonnement;
import tn.esprit.models.Categorie;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tn.esprit.models.Categorie.categorie;

public class ServiceAbonnement implements IAbonnement<Abonnement> {
    private Connection cnx;

    public ServiceAbonnement() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    @Override
    public void Add(Abonnement abonnement) {
        String qry = "INSERT INTO `abonnement`(`nom_a`, `description_a`,`prix_a`,`date_debut_a`,`date_fin_a`,`categorie_abonnement`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, abonnement.getNom_a());
            stm.setString(2, abonnement.getDescription_a());
            stm.setInt(3, abonnement.getPrix_a());
            stm.setDate(4, abonnement.getDate_debut_a());
            stm.setDate(5, abonnement.getDate_fin_a());
            stm.setInt(6, abonnement.getCategorie_abonnement());


            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Abonnement> getAll() {
        return null;
    }

    @Override
    public List<Abonnement> afficher() {
        List<Abonnement> abonnements = new ArrayList<>();
        String sql = "SELECT `id`,`nom_a`, `description_a`,`prix_a`,`date_debut_a`,`date_fin_a`,`categorie_abonnement` FROM `abonnement`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Abonnement abonnement = new Abonnement();
                abonnement.setId(rs.getInt("id"));
                abonnement.setNom_a(rs.getString("nom_a"));
                abonnement.setDescription_a(rs.getString("description_a"));
                abonnement.setPrix_a(rs.getInt("prix_a"));
                abonnement.setDate_debut_a(rs.getDate("date_debut_a"));
                abonnement.setDate_fin_a(rs.getDate("date_fin_a"));
                abonnement.setCategorie_abonnement(rs.getInt("categorie_abonnement"));
                abonnements.add(abonnement);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return abonnements;
    }
    @Override
    public List<Abonnement> TriparNom() {
        List<Abonnement> abonnements = new ArrayList<>();
        String sql = "SELECT `id`,`nom_a`, `description_a`,`prix_a`,`date_debut_a`,`date_fin_a`,`categorie_abonnement` FROM `abonnement` ORDER BY `nom_a`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {

                Abonnement abonnement = new Abonnement();
                abonnement.setId(rs.getInt("id"));
                abonnement.setNom_a(rs.getString("nom_a"));
                abonnement.setDescription_a(rs.getString("description_a"));
                abonnement.setPrix_a(rs.getInt("prix_a"));
                abonnement.setDate_debut_a(rs.getDate("date_debut_a"));
                abonnement.setDate_fin_a(rs.getDate("date_fin_a"));
                abonnement.setCategorie_abonnement(rs.getInt("categorie_abonnement"));
                abonnements.add(abonnement);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return abonnements;
    }

    @Override
    public List<Abonnement> TriparEmail() {
        return null;
    }
    @Override
    public List<Abonnement> Rechreche(String recherche) {
        List<Abonnement> abonnements = new ArrayList<>();
        String sql = "SELECT `id`,`nom_a`, `description_a`,`prix_a`,`date_debut_a`,`date_fin_a`,`categorie_abonnement` FROM `abonnement` WHERE `nom_a` LIKE '%" + recherche + "%' OR `date_debut_a`LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {

                Abonnement abonnement = new Abonnement();
                abonnement.setId(rs.getInt("id"));
                abonnement.setNom_a(rs.getString("nom_a"));
                abonnement.setDescription_a(rs.getString("description_a"));
                abonnement.setPrix_a(rs.getInt("prix_a"));
                abonnement.setDate_debut_a(rs.getDate("date_debut_a"));
                abonnement.setDate_fin_a(rs.getDate("date_fin_a"));
                abonnement.setCategorie_abonnement(rs.getInt("categorie_abonnement"));
                abonnements.add(abonnement);


            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return abonnements;
    }

    @Override
    public void Update(Abonnement abonnement) {

    }

    @Override
    public void Delete(Abonnement abonnement) {
        try {
            String qry = "DELETE FROM `abonnement` WHERE id=?";
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
            String qry = "DELETE FROM `abonnement` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
