package controles;
        import entities.Allergie;
        import entities.Plat;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.SnapshotResult;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.layout.HBox;
        import javafx.stage.Stage;
        import javafx.util.Callback;
        import services.PlatServices;

        import java.io.IOException;
        import java.util.Optional;

public class AfficherPlat {

    @FXML
    private TableColumn<Plat, String> plat_calories;
    @FXML
    private TableColumn<Plat, String> plat_allergie;
    @FXML
    private TableColumn<Plat, Integer> plat_id;

    @FXML
    private TableColumn<Plat, String> plat_image;

    @FXML
    private TableColumn<Plat, SnapshotResult> plat_nom;
    @FXML
    private TableView<Plat> list_plat;
    @FXML
    private TableColumn<Plat, Void> plat_actions;
    private PlatServices platServices = new PlatServices();

    @FXML
    private TableColumn<Plat, String> plat_prix;

    public void initialize() {
        ObservableList<Plat> list = FXCollections.observableList(platServices.getAllData());
        // Création des colonnes
        plat_id.setCellValueFactory(new PropertyValueFactory<>("id_p"));
        plat_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        plat_calories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        plat_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        plat_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        plat_allergie.setCellValueFactory(new PropertyValueFactory<>("allergie"));
        plat_actions.setCellFactory(createActionsCellFactory());
        list_plat.setItems(list);
    }

    private Callback<TableColumn<Plat, Void>, TableCell<Plat, Void>> createActionsCellFactory() {
        return new Callback<TableColumn<Plat, Void>, TableCell<Plat, Void>>() {
            @Override
            public TableCell<Plat, Void> call(final TableColumn<Plat, Void> param) {
                return new TableCell<Plat, Void>() {
                    private final Button btnUpdate = new Button("Update");
                    private final Button btnDelete = new Button("Delete");

                    {
                        btnUpdate.setOnAction(event -> handleUpdate());
                        btnDelete.setOnAction(event -> handleDelete());
                        // You can add an event handler for btnRate if needed
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Plat currentPlat = getTableView().getItems().get(getIndex());
                            if (currentPlat != null) {
                               HBox buttonsBox = new HBox(btnUpdate, btnDelete);
                                setGraphic(buttonsBox);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }

            private void handleUpdate() {
                Plat selectedPlat = list_plat.getSelectionModel().getSelectedItem();
                if (selectedPlat != null) {
                    // Load the update scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdatePlat.fxml"));
                    Parent updateScene;
                    try {
                        updateScene = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    // Get the controller for the update scene
                    UpdatePlat updateController = loader.getController();
                    updateController.setPlat(selectedPlat);

                    // Create a new stage for the update scene
                    Stage updateStage = new Stage();
                    updateStage.setTitle("Update Plat");
                    updateStage.setScene(new Scene(updateScene));

                    // Show the update stage
                    updateStage.showAndWait();

                    // After the update scene is closed, refresh the table view
                    refreshList();
                } else {
                    // No item selected, show an information alert
                    showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un plat à mettre à jour.");
                }
            }

            private void handleDelete() {
                Plat selectedPlat = list_plat.getSelectionModel().getSelectedItem();
                if (selectedPlat != null) {
                    // Show confirmation dialog
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Confirmation de suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer ce plat?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // User confirmed, delete the selected item
                        platServices.deleteEntity(selectedPlat);
                        refreshList(); // Refresh the TableView
                    }
                } else {
                    // No item selected, show an information alert
                    showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un plat à supprimer.");
                }
            }

            private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
                Alert alert = new Alert(alertType);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(content);
                alert.showAndWait();
            }

            public void refreshList() {
                ObservableList<Plat> updatedList = FXCollections.observableList(platServices.getAllData());
                list_plat.setItems(updatedList);
            }
        };
    }
}