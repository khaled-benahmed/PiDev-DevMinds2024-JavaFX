package controllers.BackOffice;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.Offre;
import services.ServiceOffre;

import javax.swing.text.html.Option;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListOffreController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Offre> ListViewOffres;
    @javafx.fxml.FXML
    private Button BtnSupprimer;
    @javafx.fxml.FXML
    private Button BtnModifier;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private TextArea tfDesc;
    @javafx.fxml.FXML
    private TextField tfTags;
    @javafx.fxml.FXML
    private TextField tfPrix;

    private ServiceOffre serviceOffre = new ServiceOffre();
    private Alert a = new Alert(Alert.AlertType.INFORMATION);
    @javafx.fxml.FXML
    private HBox HboxModif;
    @javafx.fxml.FXML
    private Button BtnAjouter;

    public void afficherOffres() throws SQLException {
        ListViewOffres.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Offre offre, boolean empty) {
                super.updateItem(offre, empty);

                if (empty || offre == null) {
                    setText(null);
                } else {
                    setText(offre.getDesciption() + " - " + offre.getTags() + " - " + offre.getPrix());
                }
            }
        });

        List<Offre> lo = this.serviceOffre.getAll(); // Call getAll() here
        ListViewOffres.getItems().clear(); // Clear existing items before adding new ones
        for (Offre o : lo) {
            this.ListViewOffres.getItems().add(o);
        }
    }

    @javafx.fxml.FXML
    public void OnListClicked(Event event) {
        Offre offre = ListViewOffres.getSelectionModel().getSelectedItem();
        if (offre != null) {
            tfDesc.setText(offre.getDesciption());
            tfTags.setText(offre.getTags());
            tfPrix.setText(String.valueOf(offre.getPrix()));
        }
        BtnAjouter.setVisible(false);
        HboxModif.setVisible(true);
    }

    @javafx.fxml.FXML
    public void OnSupprimerClicked(ActionEvent actionEvent) {
        Offre offre = ListViewOffres.getSelectionModel().getSelectedItem();
        if (offre != null) {
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("voulez vous vraiment supprimer l'offre dont l'id est : " + offre.getId());
            Optional<ButtonType> result = a.showAndWait();

            if (result.get() == ButtonType.OK) {
                try {
                    serviceOffre.delete(offre.getId());
                    afficherOffres();
                    a.setTitle("Information");
                    a.setContentText("Offre supprimée avec succès");
                    a.show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez selectionner une offre");
            a.show();
        }
    }

    @javafx.fxml.FXML
    public void OnModifierClicked(ActionEvent actionEvent) {
        Offre offre = ListViewOffres.getSelectionModel().getSelectedItem();
        if (offre != null) {
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
            } else {
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setContentText("voulez vous vraiment modifier l'offre dont l'id est : " + offre.getId());
                Optional<ButtonType> result = a.showAndWait();

                if (result.get() == ButtonType.OK) {
                    try {
                        offre.setDesciption(tfDesc.getText());
                        offre.setTags(tfTags.getText());
                        offre.setPrix(Double.parseDouble(tfPrix.getText()));
                        serviceOffre.update(offre);
                        afficherOffres();
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setTitle("Information");
                        a.setContentText("Offre modifiée avec succès");
                        a.show();
                        tfDesc.clear();
                        tfTags.clear();
                        tfPrix.clear();
                        HboxModif.setVisible(false);
                        BtnAjouter.setVisible(true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        } else {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez selectionner une offre");
            a.show();
        }
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        tfDesc.clear();
        tfTags.clear();
        tfPrix.clear();
        HboxModif.setVisible(false);
        BtnAjouter.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            afficherOffres();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException {
        boolean trouve = false ;
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
        } else {
            Offre offre = new Offre();
            offre.setDesciption(tfDesc.getText());
            offre.setTags(tfTags.getText());
            offre.setPrix(Double.parseDouble(tfPrix.getText()));
            List<Offre> lo = serviceOffre.getAll();
            for (Offre o : lo )
            {

                if(o.equals(offre))
                {
                    trouve = true ;
                }
            }
            if (!trouve) {
                try {
                    this.serviceOffre.add(offre);
                    afficherOffres();
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setTitle("Information");
                    a.setContentText("Offre ajoutée avec succès");
                    a.show();
                    tfDesc.clear();
                    tfTags.clear();
                    tfPrix.clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Offre existant ! ");
                a.show();
            }
        }
    }
}
