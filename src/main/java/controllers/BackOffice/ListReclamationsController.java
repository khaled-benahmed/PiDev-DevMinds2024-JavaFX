package controllers.BackOffice;


import controllers.Router;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import models.Reclamation;
import services.ServiceReclamation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListReclamationsController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Reclamation> ListViewReclamations;
    private ServiceReclamation serviceReclamation ;

    @javafx.fxml.FXML
    public void OnMouseClicked(Event event) {
        Reclamation reclamation = ListViewReclamations.getSelectionModel().getSelectedItem();
        if (reclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ReclamationDetails.fxml")); // Assuming the path to your FXML file
                Parent root = loader.load();
                ReclamationDetailsController detailsController = loader.getController();
                detailsController.setReclamation(reclamation); // Pass the reclamation to the details controller
                Router.getInstance().showContent(root);
            } catch (IOException e) {
                e.printStackTrace(); // Handle potential errors
            }
        }
    }

    public void afficherReclamations() throws SQLException {

        ListViewReclamations.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);

                if (empty || reclamation == null) {
                    setText(null);
                } else {
                    // Format the text to display based on your Reclamation object's fields
                    setText(reclamation.getNomUser() + " - " + reclamation.getTextReclamation());
                }
            }
        });

        List<Reclamation> lr = this.serviceReclamation.getAll(); // Call getAll() here
        ListViewReclamations.getItems().clear(); // Clear existing items before adding new ones
        for (Reclamation r : lr) {
            this.ListViewReclamations.getItems().add(r);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.serviceReclamation = new ServiceReclamation();
        try {
            afficherReclamations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
