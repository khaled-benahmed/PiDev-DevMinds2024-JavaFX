package controllers.BackOffice;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import models.Sponsor;
import services.ServiceSponsor;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListSponsorController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Sponsor> ListSponsors;
    @javafx.fxml.FXML
    private Button BtnSupprimer;
    @javafx.fxml.FXML
    private TextField tfNom;
    @javafx.fxml.FXML
    private TextField tfDonnation;
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Button BtnModifier;
    @javafx.fxml.FXML
    private Button BtnInsererImage;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private Button BtnGoPromo;

    private ServiceSponsor serviceSponsor = new ServiceSponsor();
    private Alert a = new Alert(Alert.AlertType.NONE);

    private String ImagePath;
    @javafx.fxml.FXML
    private HBox HboxModif;
    @javafx.fxml.FXML
    private Button BtnAjouter;

    public void afficherSponsors() throws SQLException {
        ListSponsors.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Sponsor sponsor, boolean empty) {
                super.updateItem(sponsor, empty);

                if (empty || sponsor == null) {
                    setText(null);
                } else {
                    setText(sponsor.getNomSponsor() + " - " + sponsor.getDonnation());
                }
            }
        });

        List<Sponsor> ls = this.serviceSponsor.getAll();
        ListSponsors.getItems().clear();
        for (Sponsor s : ls) {
            this.ListSponsors.getItems().add(s);
        }
        System.out.println("here");
    }

    @javafx.fxml.FXML
    public void OnListClicked(Event event) {
        Sponsor sponsor = ListSponsors.getSelectionModel().getSelectedItem();
        if (sponsor != null) {
            tfNom.setText(sponsor.getNomSponsor());
            tfDonnation.setText(String.valueOf(sponsor.getDonnation()));
            try {
                Image image = new Image(sponsor.getImage());
                imageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
            HboxModif.setVisible(true);
            BtnAjouter.setVisible(false);
            BtnGoPromo.setVisible(true);
        }
    }

    @javafx.fxml.FXML
    public void OnSupprimerClicked(ActionEvent actionEvent) {
        Sponsor sponsor = ListSponsors.getSelectionModel().getSelectedItem();
        if (sponsor != null) {
            try {
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setContentText("voulez vous supprimer le sponsor dont l'id est : " + sponsor.getId());
                Optional<ButtonType> result = a.showAndWait();

                if (result.get() == ButtonType.OK) {
                    serviceSponsor.delete(sponsor.getId());
                    afficherSponsors();
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setTitle("Succès");
                    a.setContentText("Sponsor supprimé avec succès");
                    a.show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @javafx.fxml.FXML
    public void OnModifierClicked(ActionEvent actionEvent) {
        Sponsor sponsor = ListSponsors.getSelectionModel().getSelectedItem();
        if (sponsor != null) {
            // check empty fields
            if (tfNom.getText().isEmpty() || tfDonnation.getText().isEmpty()) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setTitle("WARNING");
                a.setContentText("Veuillez remplir tous les champs");
                a.show();
                return;
            }
            // check donation is a valid double using if
            else if (!tfDonnation.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setTitle("WARNING");
                a.setContentText("Donnation doit être un nombre");
                a.show();
                return;
            }
            // check nom is long enough
            else if (tfNom.getText().length() < 3) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setTitle("WARNING");
                a.setContentText("Le nom doit contenir au moins 3 caractères");
                a.show();

            } else {
                try {
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setContentText("voulez vous modifier le sponsor dont l'id est : " + sponsor.getId());
                    Optional<ButtonType> result = a.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        sponsor.setNomSponsor(tfNom.getText());
                        sponsor.setDonnation(Double.parseDouble(tfDonnation.getText()));
                        if (ImagePath != null ) {
                            System.out.println("image path not empty");
                            sponsor.setImage(ImagePath);
                        }
                        System.out.println("image path empty");
                        serviceSponsor.update(sponsor);
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setTitle("Succès");
                        a.setContentText("Sponsor modifié avec succès");
                        a.show();

                    }
                    afficherSponsors();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez selectionner un sponsor");
            a.show();
        }
    }

    @javafx.fxml.FXML
    public void OnInsererClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/Images/"));
        fileChooser.setTitle("Select Image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            ImagePath = selectedFile.toURI().toString();
            Image image = new Image(ImagePath);
            imageView.setImage(image);
        }

    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        ListSponsors.getSelectionModel().clearSelection();
        tfNom.clear();
        tfDonnation.clear();
        imageView.setImage(null);
        ImagePath = "";
        HboxModif.setVisible(false);
        BtnAjouter.setVisible(true);
        BtnGoPromo.setVisible(false);
    }

    @javafx.fxml.FXML
    public void GoProm(ActionEvent actionEvent) {
        // get selected sponsor
        Sponsor sponsor = ListSponsors.getSelectionModel().getSelectedItem();
        if (sponsor != null) {
            try {
                // load the promo page
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/BackOffice/ListPromo.fxml"));
                javafx.scene.Parent root = loader.load();
                ListPromoController promoController = loader.getController();
                promoController.setSponsor(sponsor);
                controllers.Router.getInstance().showContent(root);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez selectionner un sponsor");
            a.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            afficherSponsors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException {
        boolean trouve = false ;
        // check empty fields
        if (tfNom.getText().isEmpty() || tfDonnation.getText().isEmpty() || ImagePath == null) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez remplir tous les champs");
            a.show();

        }
        // check donation is a valid double using condition with regular expression
        else if (!tfDonnation.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Donnation doit être un nombre");
            a.show();

        }


        // check nom is long enough
        else if (tfNom.getText().length() < 3) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Le nom doit contenir au moins 3 caractères");
            a.show();

        }
        // check imagePath is selected
        else if (ImagePath.isEmpty()) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez selectionner une image");
            a.show();

        } else {


            Sponsor sponsor = new Sponsor();
            sponsor.setNomSponsor(tfNom.getText());
            sponsor.setDonnation(Double.parseDouble(tfDonnation.getText()));
            sponsor.setImage(ImagePath);

            List<Sponsor> ls = serviceSponsor.getAll();
            for (Sponsor s : ls )
            {
                if(s.equals(sponsor))
                {
                    trouve = true ;
                }
            }
            if(!trouve) {
                try {
                    this.serviceSponsor.add(sponsor);
                    afficherSponsors();
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setTitle("Succès");
                    a.setContentText("Sponsor ajouté avec succès");
                    a.show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("sponsor existant ! ");
                a.show();
            }
        }
    }
}
