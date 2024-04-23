package services;

import entities.Plat;
import tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlatServices {

    public void addEntity(Plat plat) {
        String requete = "INSERT INTO plat (nom, calories, prix, image, allergie) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, plat.getNom());
            pst.setString(2, plat.getCalories());
            pst.setString(3, plat.getPrix());
            pst.setString(4, plat.getImage());
            pst.setString(5, plat.getAllergie());
            pst.executeUpdate();
            System.out.println("Plat Added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEntity(Plat plat) {
        String requete = "UPDATE plat SET nom=?, calories=?, prix=?, image=?, allergie=? WHERE id_p=?";
        try {
            System.out.println("Executing update query: " + requete);

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, plat.getNom());
            pst.setString(2, plat.getCalories());
            pst.setString(3, plat.getPrix());
            pst.setString(4, plat.getImage());
            pst.setString(5, plat.getAllergie());
            pst.setInt(6, plat.getId_p());

            System.out.println("Updating plat with ID " + plat.getId_p() + ":");

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Plat Updated");
            } else {
                System.out.println("Plat not found or not updated");
            }
        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
        }
    }

    public void deleteEntity(Plat plat) {
        String requete = "DELETE FROM plat WHERE id_p=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, plat.getId_p());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Plat Deleted");
            } else {
                System.out.println("Plat not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Plat> getAllData() {
        List<Plat> plats = new ArrayList<>();
        String requete = "SELECT * FROM plat";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Plat plat = new Plat();
                plat.setId_p(rs.getInt(1));
                plat.setNom(rs.getString("nom"));
                plat.setCalories(rs.getString("calories"));
                plat.setPrix(rs.getString("prix"));
                plat.setImage(rs.getString("image"));
                plat.setAllergie(rs.getString("allergie"));
                plats.add(plat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return plats;
    }
}
