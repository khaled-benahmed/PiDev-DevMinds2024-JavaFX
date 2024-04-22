package controllers.BackOffice;

import controllers.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.Reclamation;
import models.Reponse;
import services.ServiceReclamation;
import services.ServiceReponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


public class ReclamationDetailsController {

    private Reclamation reclamation;
    @javafx.fxml.FXML
    private TextArea tfDesc;
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private DatePicker tfDate;
    @javafx.fxml.FXML
    private Button BtnBack;
    @javafx.fxml.FXML
    private Button BtnRespond;
    @javafx.fxml.FXML
    private Button BtnDelete;
    @javafx.fxml.FXML
    private TextArea tfReponse;
    @javafx.fxml.FXML
    private Button BtnSubmit;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    private ServiceReponse serviceReponse = new ServiceReponse();

    private Alert a = new Alert(Alert.AlertType.NONE);
    @javafx.fxml.FXML
    private Button BtnVoirRep;

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        this.tfDate.setValue(reclamation.getDateReclamation());
        this.tfName.setText(reclamation.getNomUser());
        this.tfDesc.setText(reclamation.getTextReclamation());
        this.tfReponse.setVisible(false);
        this.BtnSubmit.setVisible(false);
    }

    @javafx.fxml.FXML
    public void GoBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListReclamations.fxml"));
        //this.BtnBack.getScene().setRoot(loader.load());
        Router.getInstance().showContent(loader.load());
    }

    @javafx.fxml.FXML
    public void Repondre(ActionEvent actionEvent) {
        this.tfReponse.setVisible(true);
        this.BtnSubmit.setVisible(true);
    }

    @javafx.fxml.FXML
    public void OnDeleteClicked(ActionEvent actionEvent) throws IOException {
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("voulez vous vraiment supprimer la reclamation dont l'id est : " + this.reclamation.getId());
        Optional<ButtonType> result = a.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                this.serviceReclamation.delete(this.reclamation.getId());
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setTitle("Success");
                a.setContentText("Reclamation deleted successfully");
                a.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListReclamations.fxml"));
            //this.BtnBack.getScene().setRoot(loader.load());
            Router.getInstance().showContent(loader.load());
        }

    }

    @javafx.fxml.FXML
    public void OnSubmitClicked(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        if (tfReponse.getText().isEmpty()) {
            a.setTitle("controle de saisie");
            a.setContentText("reponse vide ! ");
            a.show();
        } else if (tfReponse.getText().length() < 10) {
            a.setTitle("controle de saisie");
            a.setContentText("reponse trop courte ! ");
            a.show();
        } else {
            Reponse r = new Reponse();
            r.setIdReclamation(this.reclamation.getId());
            r.setContenuResponse(this.tfReponse.getText());
            r.setDateReponse(java.time.LocalDate.now());
            try {
                this.serviceReponse.add(r);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setTitle("Success");
                a.setContentText("Response added successfully");
                a.showAndWait();
                this.tfReponse.setVisible(false);
                this.BtnSubmit.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @javafx.fxml.FXML
    public void GoReponses(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListReponses.fxml"));
        Parent root = loader.load();
        ListReponsesController listReponsesController = loader.getController();
        System.out.println(this.reclamation);
        listReponsesController.setReclamation(this.reclamation); // Pass the reclamation to the details controller
        Router.getInstance().showContent(root);

    }
}
