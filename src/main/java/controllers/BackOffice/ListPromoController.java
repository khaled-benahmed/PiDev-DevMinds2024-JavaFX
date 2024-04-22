package controllers.BackOffice;

import controllers.Router;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.Promotion;
import models.Sponsor;
import services.ServicePromotion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ListPromoController {
    @javafx.fxml.FXML
    private ListView<Promotion> ListPromo;
    @javafx.fxml.FXML
    private Button BtnSupp;
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private TextField tfCode;
    @javafx.fxml.FXML
    private TextField tfReduction;
    @javafx.fxml.FXML
    private DatePicker tfDate;
    @javafx.fxml.FXML
    private HBox HboxModif;
    @javafx.fxml.FXML
    private Button btnModif;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private Button BtnAjouter;

    private Sponsor sponsor;

    private ServicePromotion servicePromotion = new ServicePromotion();

    private Alert at = new Alert(Alert.AlertType.NONE);


    public void afficherPromotion() throws SQLException {
        ListPromo.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Promotion promotion, boolean empty) {
                super.updateItem(promotion, empty);

                if (empty || promotion == null) {
                    setText(null);
                } else {
                    setText(promotion.getCodePromotion() + " - " + promotion.getReductionPromotion() + " - " + promotion.getDateExpiration());
                }
            }
        });

        List<Promotion> lp = this.servicePromotion.getByIdSponsor(sponsor.getId());
        ListPromo.getItems().clear();
        for (Promotion p : lp) {
            this.ListPromo.getItems().add(p);
        }
    }

    public void setSponsor(Sponsor sponsor) throws SQLException {
        this.sponsor = sponsor;
        afficherPromotion();

    }

    @javafx.fxml.FXML
    public void OnListPtomoClicked(Event event) {
        Promotion promotion = ListPromo.getSelectionModel().getSelectedItem();
        if (promotion != null) {
            tfCode.setText(promotion.getCodePromotion());
            tfReduction.setText(String.valueOf(promotion.getReductionPromotion()));
            tfDate.setValue(promotion.getDateExpiration());
        }
        HboxModif.setVisible(true);
        BtnAjouter.setVisible(false);
    }

    @javafx.fxml.FXML
    public void OnSuppClicked(ActionEvent actionEvent) {
        Promotion promotion = ListPromo.getSelectionModel().getSelectedItem();
        if (promotion != null) {
            try {
                at.setAlertType(Alert.AlertType.CONFIRMATION);
                at.setContentText("voulez vous vraiment supprimer la promotion dont l'id est : " + promotion.getId());
                Optional<ButtonType> result = at.showAndWait();

                if (result.get() == ButtonType.OK) {
                    servicePromotion.delete(promotion.getId());
                    afficherPromotion();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListSponsor.fxml"));
        Router.getInstance().showContent(loader.load());
    }

    @javafx.fxml.FXML
    public void OnModifClicked(ActionEvent actionEvent) {
        Promotion promotion = ListPromo.getSelectionModel().getSelectedItem();
        if (promotion != null) {
            if (tfCode.getText().isEmpty() || tfReduction.getText().isEmpty() || tfDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.show();
            }
            // check that tfReduction contains only numbers using regular expression
            else if (!tfReduction.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Veuillez saisir un nombre valide");
                alert.show();
            }
            // check date superior to date now by 2 days
            else if (tfDate.getValue().isBefore(java.time.LocalDate.now().plusDays(2))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("La date doit être supérieure de 2 jours à la date actuelle");
                alert.show();
            } else {
                promotion.setCodePromotion(tfCode.getText());
                promotion.setReductionPromotion(Double.parseDouble(tfReduction.getText()));
                promotion.setDateExpiration(tfDate.getValue());
                try {
                    at.setAlertType(Alert.AlertType.CONFIRMATION);
                    at.setContentText("voulez vous vraiment modifier la promotion dont l'id est : " + promotion.getId());
                    Optional<ButtonType> result = at.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        this.servicePromotion.update(promotion);
                        afficherPromotion();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        tfCode.clear();
        tfReduction.clear();
        tfDate.setValue(null);
        HboxModif.setVisible(false);
        BtnAjouter.setVisible(true);
    }

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException {
        boolean trouve = false ;
        // check empty fields
        if (tfCode.getText().isEmpty() || tfReduction.getText().isEmpty() || tfDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();

        }
        // check that tfReduction contains only numbers using regular expression
        else if (!tfReduction.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Veuillez saisir un nombre valide");
            alert.show();
        }
        // check date superior to date now by 2 days
        else if (tfDate.getValue().isBefore(java.time.LocalDate.now().plusDays(2))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("La date doit être supérieure de 2 jours à la date actuelle");
            alert.show();
        } else {
            Promotion promotion = new Promotion();
            promotion.setCodePromotion(tfCode.getText());
            promotion.setReductionPromotion(Double.parseDouble(tfReduction.getText()));
            promotion.setDateExpiration(tfDate.getValue());
            promotion.setSponsorId(sponsor.getId());
            List<Promotion> lp = servicePromotion.getAll();
            for (Promotion p : lp )
            {
                if (p.equals(promotion))
                {
                    trouve = true ;
                }
            }

            if (!trouve) {
                try {
                    this.servicePromotion.add(promotion);
                    afficherPromotion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Promotion existe ! ");
                alert.show();
            }
        }
    }
}
