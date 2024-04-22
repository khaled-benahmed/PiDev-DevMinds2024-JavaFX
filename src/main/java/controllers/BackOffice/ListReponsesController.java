package controllers.BackOffice;

import controllers.Router;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.Reclamation;
import models.Reponse;
import services.ServiceReponse;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListReponsesController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Reponse> listViewReponses;
    private ServiceReponse serviceReponse = new ServiceReponse();
    @javafx.fxml.FXML
    private TextArea tfContenu;
    @javafx.fxml.FXML
    private TextField tfIdRec;
    @javafx.fxml.FXML
    private DatePicker tfDate;
    @javafx.fxml.FXML
    private Button BtnUpdate;
    @javafx.fxml.FXML
    private Button BtnDelete;

    private Reclamation rec;
    @javafx.fxml.FXML
    private Button BtnBack;


    public void afficherReponses() throws SQLException {

        listViewReponses.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Reponse reponse, boolean empty) {
                super.updateItem(reponse, empty);

                if (empty || reponse == null) {
                    setText(null);
                } else {
                    // Format the text to display based on your Reclamation object's fields
                    setText(reponse.getContenuResponse());
                }
            }
        });

        List<Reponse> lr = this.serviceReponse.getReponsesByIdRec(rec.getId()); // Call getAll() here
        listViewReponses.getItems().clear(); // Clear existing items before adding new ones
        for (Reponse r : lr) {
            this.listViewReponses.getItems().add(r);
        }
    }

    @javafx.fxml.FXML
    public void OnListClicked(Event event) {
        Reponse reponse = listViewReponses.getSelectionModel().getSelectedItem();
        if (reponse != null) {
            tfContenu.setText(reponse.getContenuResponse());
            tfIdRec.setText(String.valueOf(reponse.getIdReclamation()));
            tfDate.setValue(reponse.getDateReponse());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void OnUpdateClicked(ActionEvent actionEvent) {
        Reponse reponse = listViewReponses.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.WARNING);
        if (reponse != null) {
            if (tfContenu.getText().isEmpty()) {
                a.setTitle("Controle de saisie");
                a.setContentText("contenu vide ! ");
                a.show();
            } else if (tfContenu.getText().length() < 10) {
                a.setTitle("Controle de saisie");
                a.setContentText("contenu trop court ! ");
                a.show();
            } else {
                reponse.setContenuResponse(tfContenu.getText());
                try {
                    this.serviceReponse.update(reponse);
                    afficherReponses();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @javafx.fxml.FXML
    public void OnDeleteClicked(ActionEvent actionEvent) {
        Reponse reponse = listViewReponses.getSelectionModel().getSelectedItem();
        if (reponse != null) {
            try {
                this.serviceReponse.delete(reponse.getId());
                afficherReponses();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setReclamation(Reclamation rec) throws SQLException {

        this.rec = rec;
        afficherReponses();
    }

    @javafx.fxml.FXML
    public void OnBackClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ReclamationDetails.fxml")); // Assuming the path to your FXML file
            Parent root = loader.load();
            ReclamationDetailsController detailsController = loader.getController();
            detailsController.setReclamation(rec); // Pass the reclamation to the details controller
            Router.getInstance().showContent(root);
        } catch (IOException e) {
            e.printStackTrace(); // Handle potential errors
        }
    }
}
