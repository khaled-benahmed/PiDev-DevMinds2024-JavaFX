package controllers.FrontOffice;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Offre;
import services.ServiceOffre;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterOffreController {
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private TextArea tfDesc;
    @javafx.fxml.FXML
    private TextField tfTags;
    @javafx.fxml.FXML
    private TextField tfPrix;

    private ServiceOffre serviceOffre = new ServiceOffre();
    private Alert a = new Alert(Alert.AlertType.INFORMATION);

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException {

        boolean test = false ;

        //check fileds like the update
        if (tfDesc.getText().isEmpty() || tfTags.getText().isEmpty() || tfPrix.getText().isEmpty()) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez remplir tous les champs");
            a.show();

        }
        // check that tfPrix contains only numbers using regular expression
        else if (!tfPrix.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Le prix doit être un nombre");
            a.show();

        }
        // check minimal length of description
        else if (tfDesc.getText().length() < 10) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("La description doit contenir au moins 10 caractères");
            a.show();

        }
        // check maximal length of description
        else if (tfDesc.getText().length() > 255) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("La description doit contenir au maximum 255 caractères");
            a.show();

        }
        // check minimal length of tags
        else if (tfTags.getText().length() < 3) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Les tags doivent contenir au moins 3 caractères");
            a.show();
        }
        // check maximal length of tags
        else if (tfTags.getText().length() > 255) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Les tags doivent contenir au maximum 255 caractères");
            a.show();
        }
        else {
            List<Offre> lo = serviceOffre.getAll();
            Offre offre = new Offre();
            offre.setDesciption(tfDesc.getText());
            offre.setTags(tfTags.getText());
            offre.setPrix(Double.parseDouble(tfPrix.getText()));

            for (Offre o : lo)
            {
                if (o.equals(offre)) {
                    test = true;
                    break;
                }
            }
            if (!test) {
                try {
                    this.serviceOffre.add(offre);
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setTitle("Information");
                    a.setContentText("Offre ajoutée avec succès");
                    a.show();
                    tfDesc.clear();
                    tfTags.clear();
                    tfPrix.clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ListOffreFront.fxml"));
                    this.BtnRetour.getScene().setRoot(loader.load());

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("un offre identique existe ! ");
                a.show();
            }
        }
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/FrontOfficeHome.fxml"));
        this.BtnRetour.getScene().setRoot(loader.load());
    }
}
