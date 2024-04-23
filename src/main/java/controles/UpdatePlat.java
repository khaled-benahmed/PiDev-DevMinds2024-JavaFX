package controles;

import entities.Plat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.PlatServices;

import java.io.File;

public class UpdatePlat {

    @FXML
    private ChoiceBox<String> CalorieBox;

    @FXML
    private TextField NomTextfield;

    @FXML
    private ChoiceBox<String> PrixBox;

    @FXML
    private Label imagelabel;

    @FXML
    private ImageView imageview;

    private Plat plat;
    private PlatServices platServices = new PlatServices();
    private AfficherPlat list_plat;

    // Set the Plat object to update
    public void setPlat(Plat plat) {
        this.plat = plat;
        // Initialize fields with current values
        NomTextfield.setText(plat.getNom());
        CalorieBox.setValue(plat.getCalories());
        PrixBox.setValue(plat.getPrix());
        // Set other fields as needed
        // Load the image and set it in the ImageView
       // Image image = new Image(plat.getImage());
       // imageview.setImage(image);
    }

    @FXML
    void initialize() {
        // Initialization of the ChoiceBox for calories
        CalorieBox.getItems().addAll("100-250", "300-450", "450-550", "600-750");

        // Initialization of the ChoiceBox for prices
        PrixBox.getItems().addAll("10-30", "40-60", "70-90", "100-120");
    }

    @FXML
    void InsereImage(ActionEvent event) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");

        // Set the file chooser to only allow image files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file chooser and get the selected file
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Convert the file to URI string
            String imagePath = selectedFile.toURI().toString();

            // Load the image and set it in the ImageView
            Image image = new Image(imagePath);
            imageview.setImage(image);
        }
    }

    @FXML
    void Modifier(ActionEvent event) {
        if (plat != null) {
            // Mise à jour de l'objet plat avec les nouvelles valeurs des champs
            plat.setNom(NomTextfield.getText());
            plat.setCalories(CalorieBox.getValue());
            plat.setPrix(PrixBox.getValue());
            // Vous pouvez mettre à jour d'autres champs selon vos besoins

            // Appel du service PlatServices pour mettre à jour le plat dans la base de données
            platServices.updateEntity(plat);

            // Afficher un message de réussite ou gérer comme nécessaire
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le plat a été modifié avec succès.");
            alert.showAndWait();

            // Fermer la fenêtre de modification (si elle est dans une fenêtre modale)
            // Vous pouvez ajouter ici le code pour fermer la fenêtre ou rediriger vers une autre vue
        } else {
            // Gérer le cas où aucun plat n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun plat sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un plat à modifier.");
            alert.showAndWait();
        }
    }

}
