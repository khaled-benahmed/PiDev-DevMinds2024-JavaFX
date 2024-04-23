package controles;
import entities.Allergie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.AllergieServices;
import javafx.scene.control.Alert;

import java.io.IOException;

public class AjouterAllergie {
    @FXML
    private TextField id_plat;

    @FXML
    private TextArea destextarea;

    @FXML
    private TextField nomtextfield;

    @FXML
    void Ajouter(ActionEvent event) {
        // Récupérer l'identifiant du plat à partir du champ de texte id_plat
        String idPlatText = id_plat.getText();

        // Vérifier que l'identifiant du plat est un entier valide
        int idPlat = -1; // Valeur par défaut
        try {
            idPlat = Integer.parseInt(idPlatText);
        } catch (NumberFormatException e) {
            // Afficher une erreur si l'identifiant du plat n'est pas un nombre valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("L'identifiant du plat doit être un nombre entier");
            alert.show();
            return; // Sortir de la méthode si l'identifiant du plat n'est pas valide
        }

        // Récupérer le nom et la description de l'allergie à partir des champs de texte
        String nom = nomtextfield.getText();
        String description = destextarea.getText();

        // Vérifier que les champs ne sont pas vides
        if (!nom.isEmpty() && !description.isEmpty()) {
            // Créer une nouvelle instance d'Allergie avec les données récupérées
            Allergie nouvelleAllergie = new Allergie(idPlat, nom, description);

            // Ajouter l'allergie en base de données
            AllergieServices allergieServices = new AllergieServices();
            allergieServices.addEntity(nouvelleAllergie);

            // Afficher une confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'allergie a été ajoutée avec succès");
            alert.show();
        } else {
            // Afficher un message d'erreur si l'un des champs est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        }
    }


        @FXML
        void Afficher(ActionEvent event) {
            try {
                // Charger la vue AfficherPlat.fxml dans une nouvelle fenêtre
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAllergie.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);
                Stage currentStage = (Stage) nomtextfield.getScene().getWindow();

                // Afficher la nouvelle scène dans une nouvelle fenêtre
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

