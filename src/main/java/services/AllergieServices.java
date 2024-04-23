package services;

import entities.Allergie;
import entities.Plat;
import interfaces.IService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AllergieServices implements IService<Allergie> {
    @Override
    public void addEntity(Allergie allergie) {
        String requete ="INSERT INTO allergie (plat_id,nom,description) VALUES (?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, allergie.getIdPlat());
            pst.setString(2, allergie.getNom());
            pst.setString(3, allergie.getDescription());
            pst.executeUpdate();
            System.out.println("allergies Added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }}

    @Override
    public void updateEntity(Allergie allergie) {
        String requete = "UPDATE allergie SET plat_id=?, nom=?, description=? WHERE id=?";
        try {
            System.out.println("Executing update query: " + requete);

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1,allergie.getIdPlat());
            pst.setString(2, allergie.getNom());
            pst.setString(3, allergie.getDescription());

            // Assurez-vous d'assigner la valeur de l'ID au bon paramètre dans la requête
            pst.setInt(4, allergie.getId());

            System.out.println("Updating plat with ID " + allergie.getId() + ":");

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Allergie Updated");
            } else {
                System.out.println("Allergie not found or not updated");
            }
        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
        }

    }

    @Override
    public void DeleteEntity(Allergie allergie) {
        String requete = "DELETE FROM allergie WHERE id_s=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, allergie.getId());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Allergie Deleted");
            } else {
                System.out.println("Allergie not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void DeleteEntityWithConfirmation(Allergie allergie) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le projet ?");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce projet ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // L'utilisateur a confirmé la suppression
            DeleteEntity(allergie);
        } else {
            // L'utilisateur a annulé la suppression
            System.out.println("Suppression annulée");
        }
    }

    @Override
    public List<Allergie> getAllData() {
        List<Allergie> data = new ArrayList<>();
        String requete = "SELECT a.*, a.plat_id AS id_plat FROM allergie a";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Allergie r = new Allergie();
                r.setId(rs.getInt("id"));
                r.setNom(rs.getString("nom"));
                r.setDescription(rs.getString("description"));

                // Créer un objet Plat et peupler son identifiant
                Plat plat = new Plat();
                plat.setId_p(rs.getInt("id_plat")); // Utiliser l'ID du plat associé à l'allergie

                // Définir l'objet Plat sur l'objet Allergie
                r.setPlat(plat);

                data.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }
    public List<Allergie> getAllAlergie() {
        List<Allergie> allergies = new ArrayList<>();
        String requete = "SELECT * FROM allergie";

        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                Allergie c = new Allergie();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString("description"));
                allergies.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allergies;
    }

}

