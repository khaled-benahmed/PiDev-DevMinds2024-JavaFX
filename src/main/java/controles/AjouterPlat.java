package controles;

import entities.Allergie;
import entities.Plat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.AllergieServices;
import services.PlatServices;

import java.io.File;
import java.io.IOException;

public class AjouterPlat {

    @FXML
    private ChoiceBox<String> CalorieBox;
    @FXML
    private ComboBox<Allergie> AllergieBox;

    @FXML
    private TextField NomTextfield;

    @FXML
    private ChoiceBox<String> PrixBox;

    @FXML
    private ImageView imageview;

    // Déclaration d'une variable pour stocker le chemin de l'image sélectionnée
    private String imagePath;

    @FXML
    void initialize() {
        // Initialisation de la ChoiceBox des calories
        CalorieBox.getItems().addAll("1-200", "200-800", "800-1500", "1500-3000");

        // Initialisation de la ChoiceBox des prix
        PrixBox.getItems().addAll("10-30", "40-60", "70-90", "100-120");

        // Charger la liste des allergies dans la ComboBox
        loadAllergies();
    }

    private void loadAllergies() {
        AllergieServices allergieServices = new AllergieServices();
        ObservableList<Allergie> allergieList = FXCollections.observableList(allergieServices.getAllAlergie());
        AllergieBox.setItems(allergieList);

        // Définir une méthode de rendu personnalisée pour n'afficher que le nom de l'allergie dans la ComboBox
        AllergieBox.setCellFactory(param -> new ListCell<Allergie>() {
            @Override
            protected void updateItem(Allergie item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });
    }

    @FXML
    void Ajouter(ActionEvent event) {
        String nom = NomTextfield.getText();
        String caloriesSelct = CalorieBox.getValue();
        String prixSelect = PrixBox.getValue();

        // Récupérer l'objet Allergie sélectionnée dans la ComboBox
        Allergie allergieSelectionnee = AllergieBox.getValue();

        // Vérifier si une allergie a été sélectionnée
        if (allergieSelectionnee != null) {
            // Récupérer l'ID de l'allergie sélectionnée
            int allergieId = allergieSelectionnee.getId();

            // Créer une instance de Plat avec les données récupérées
            Plat p = new Plat(nom, caloriesSelct, prixSelect, imagePath, allergieSelectionnee.getNom());

            // Ajouter le plat en base de données
            PlatServices ps = new PlatServices();
            ps.addEntity(p);

            // Afficher une confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le plat a été ajouté avec succès");
            alert.show();
        } else {
            // Si aucune allergie n'a été sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner une allergie");
            alert.show();
        }
    }


    @FXML
    void InsereImage(ActionEvent event) {
        // Ouvrir un dialogue de sélection de fichier pour choisir une image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Obtenir la scène actuelle pour afficher le dialogue de sélection de fichier
        Stage stage = (Stage) imageview.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Si un fichier est sélectionné, charger l'image dans l'ImageView et enregistrer le chemin de l'image
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imageview.setImage(image);
        }
    }

    @FXML
    void Afficher(ActionEvent event) {
        try {
            // Charger la vue AfficherPlat.fxml dans une nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlat.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) NomTextfield.getScene().getWindow();

            // Afficher la nouvelle scène dans une nouvelle fenêtre
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Allergie(ActionEvent event) {
        try {
            // Charger la vue AfficherPlat.fxml dans une nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAllergie.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) NomTextfield.getScene().getWindow();

            // Afficher la nouvelle scène dans une nouvelle fenêtre
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void AfficherA(ActionEvent actionEvent) {
        try {
            // Charger la vue AfficherPlat.fxml dans une nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAllergie.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) NomTextfield.getScene().getWindow();

            // Afficher la nouvelle scène dans une nouvelle fenêtre
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
