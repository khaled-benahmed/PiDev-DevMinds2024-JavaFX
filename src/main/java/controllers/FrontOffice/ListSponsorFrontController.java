package controllers.FrontOffice;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Sponsor;
import services.ServiceSponsor;

import java.io.IOException;
import java.net.URL;
import java.security.Provider;
import java.util.List;
import java.util.ResourceBundle;

public class ListSponsorFrontController implements Initializable {
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private ScrollPane scrollpane;

    private ServiceSponsor serviceSponsor = new ServiceSponsor();

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/FrontOfficeHome.fxml"));
        this.BtnRetour.getScene().setRoot(loader.load());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherSponsors();
    }

    public void afficherSponsors() {
        try {
            scrollpane.setContent(null);
            List<Sponsor> sponsors = serviceSponsor.getAll();
            // create a card for each sponsor and add the cards 3 by 3 in  to a VBoxex then add the VBoxex to a Hbox and add the HBox to the the scrollpane
            VBox V = new VBox();
            V.setSpacing(30);
            for (int i = 0; i < sponsors.size(); i += 3) {
                HBox hBox = new HBox();
                hBox.setSpacing(30);
                for (int j = i; j < i + 3 && j < sponsors.size(); j++) {
                    hBox.getChildren().add(createSponsorCard(sponsors.get(j)));
                }
                V.getChildren().add(hBox);
            }
            scrollpane.setContent(V);
            scrollpane.setFitToWidth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VBox createSponsorCard(Sponsor sponsor) {
        VBox vBox = new VBox();
        vBox.getStyleClass().add("sponsor-card");
        vBox.setSpacing(10);
        Image image = new Image(sponsor.getImage(), 230, 230, true, true); // Adjust width, height, preserve ratio, smooth scaling
        ImageView imageView = new ImageView(image);
        imageView.getStyleClass().add("image-view");
        vBox.getChildren().add(imageView);
        Label NomSponsor = new Label("Nom: " + sponsor.getNomSponsor());
        NomSponsor.getStyleClass().add("sponsor-name");
        vBox.getChildren().add(NomSponsor);
        Label Donnation = new Label("Donnation: " + sponsor.getDonnation().toString());
        Donnation.getStyleClass().add("donation-amount");
        vBox.getChildren().add(Donnation);

        return vBox;
    }
}
