package controles;

import entities.Allergie;
import entities.Plat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.AllergieServices;

public class UpdateAllergie {

    @FXML
    private TextArea destextarea;

    @FXML
    private TextField id_plat;

    @FXML
    private TextField nomtextfield;

    // Variable pour stocker l'instance d'allergie à modifier
    private Allergie allergie;

    // Méthode pour initialiser les champs avec les données de l'allergie à modifier
    public void setAllergie(Allergie allergie) {
        this.allergie = allergie;
        if (allergie != null) {
            // Remplir les champs avec les valeurs actuelles de l'allergie
            nomtextfield.setText(allergie.getNom());
            destextarea.setText(allergie.getDescription());
            id_plat.setText(String.valueOf(allergie.getIdPlat()));
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        // Récupérer les nouvelles valeurs du nom, de la description et de l'identifiant du plat depuis les champs de texte
        String nom = nomtextfield.getText();
        String description = destextarea.getText();
        int idPlat = Integer.parseInt(id_plat.getText());

        // Mettre à jour l'objet Allergie avec les nouvelles valeurs
        allergie.setNom(nom);
        allergie.setDescription(description);
        allergie.setPlat(new Plat(idPlat)); // Assurez-vous que la classe Plat possède un constructeur prenant l'identifiant du plat

        // Mettre à jour l'allergie dans la base de données
        AllergieServices allergieServices = new AllergieServices();
        allergieServices.updateEntity(allergie);

        // Afficher une confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("L'allergie a été modifiée avec succès");
        alert.show();
    }


    @FXML
    void Afficher(ActionEvent event) {

    }

}
