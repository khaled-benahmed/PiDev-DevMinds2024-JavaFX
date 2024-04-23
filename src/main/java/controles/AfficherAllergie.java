package controles;

import entities.Allergie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.AllergieServices;

import java.io.IOException;
import java.util.Optional;

public class AfficherAllergie {

    @FXML
    private TableColumn<Allergie, Integer> Id_allergie;
    private Scene scene;

    @FXML
    private TableColumn<Allergie, Integer> Idp_allergie;

    @FXML
    private TableColumn<Allergie,Void> actions;

    @FXML
    private TableColumn<Allergie, String> des_allergie;

    @FXML
    private TableColumn<Allergie, String> nom_allergie;

    @FXML
    private TableView<Allergie> listAllergie;
    private AllergieServices allergieServices = new AllergieServices();
    public AfficherAllergie() {
        this.allergieServices = new AllergieServices();  // Initialize projetServices
    }
    public void initialize() {
        ObservableList<Allergie> list = FXCollections.observableList(allergieServices.getAllData());

    // Définir comment les données de chaque colonne doivent être récupérées à partir de l'objet Allergie
        Id_allergie.setCellValueFactory(new PropertyValueFactory<>("id"));
        Idp_allergie.setCellValueFactory(new PropertyValueFactory<>("idPlat"));
        des_allergie.setCellValueFactory(new PropertyValueFactory<>("description"));
        nom_allergie.setCellValueFactory(new PropertyValueFactory<>("nom"));
        actions.setCellFactory(createActionsCellFactory());
        listAllergie.setItems(list);
}
    private Callback<TableColumn<Allergie, Void>, TableCell<Allergie, Void>> createActionsCellFactory() {
        return new Callback<TableColumn<Allergie, Void>, TableCell<Allergie, Void>>() {
            @Override
            public TableCell<Allergie, Void> call(final TableColumn<Allergie, Void> param) {
                return new TableCell<Allergie, Void>() {
                    private final Button btnUpdate = new Button("Update");
                    private final Button btnDelete = new Button("Delete");

                    {
                        btnUpdate.setOnAction(event -> handleUpdate());
                        btnDelete.setOnAction(event -> handleDelete());
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Vous pouvez personnaliser l'affichage des boutons en fonction des conditions si nécessaire
                            setGraphic(new HBox(btnUpdate, btnDelete));
                        }
                    }
                };
            }
        };
    }
    public void refreshList() {
        ObservableList<Allergie> updatedList = FXCollections.observableList(allergieServices.getAllData());
        listAllergie.setItems(updatedList);
    }
    @FXML
    void handleUpdate() {
        Allergie selectedProjet = listAllergie.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            // Load the update scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateAllergie.fxml"));
            Parent updateScene;
            try {
                updateScene = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Get the controller for the update scene
            UpdateAllergie updateController = loader.getController();
            updateController.setAllergie(selectedProjet);

            // Create a new stage for the update scene
            Stage updateStage = new Stage();
            updateStage.setTitle("Update Projet");
            updateStage.setScene(new Scene(updateScene));

            // Show the update stage
            updateStage.showAndWait();

            // After the update scene is closed, refresh the table view
            refreshList();
        } else {
            // No item selected, show an information alert
            showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un projet à mettre à jour.");
        }
    }

    @FXML
    void handleDelete() {
        Allergie selectedProjet = listAllergie.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            // Show confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce projet?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed, delete the selected item
                allergieServices.DeleteEntityWithConfirmation(selectedProjet);
                refreshList(); // Refresh the TableView
            }
        } else {
            // No item selected, show an information alert
            showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un projet à supprimer.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void AjouterAllergie(ActionEvent actionEvent) {

            try {
                // Charger la vue AfficherPlat.fxml dans une nouvelle fenêtre
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAllergie.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);

                // Récupérer la fenêtre actuelle à partir de l'actionEvent
                Node source = (Node) actionEvent.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();

                // Afficher la nouvelle scène dans une nouvelle fenêtre
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

