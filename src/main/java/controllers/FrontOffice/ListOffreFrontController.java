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
import models.Offre;
import models.Sponsor;
import services.ServiceOffre;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ListOffreFrontController implements Initializable {
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private ScrollPane scrollPane;

    private ServiceOffre serviceOffre = new ServiceOffre();


    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/FrontOfficeHome.fxml"));
        this.BtnRetour.getScene().setRoot(loader.load());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherOffres();

    }

    public void afficherOffres() {
        try {
            scrollPane.setContent(null);
            List<Offre> offres = serviceOffre.getAll();
            // create a card for each sponsor and add the cards 3 by 3 in  to a VBoxex then add the VBoxex to a Hbox and add the HBox to the the scrollpane
            VBox V = new VBox();
            V.setSpacing(30);
            for (int i = 0; i < offres.size(); i += 3) {
                HBox hBox = new HBox();
                hBox.setSpacing(30);
                for (int j = i; j < i + 3 && j < offres.size(); j++) {
                    hBox.getChildren().add(createOffreCard(offres.get(j)));
                }
                V.getChildren().add(hBox);
            }
            scrollPane.setContent(V);
            scrollPane.setFitToWidth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VBox createOffreCard(Offre offre) {
        VBox vBox = new VBox();
        vBox.setPrefWidth(280);
        vBox.getStyleClass().add("offre-card");  // New class name
        vBox.setSpacing(10);

        // Label for description (can be wrapped in HBox for longer descriptions)
        Label descriptionLabel = new Label(offre.getDesciption());
        descriptionLabel.getStyleClass().add("offre-description");  // New class name
        vBox.getChildren().add(descriptionLabel);

        // Label for tags
        Label tagsLabel = new Label("Tags: " + offre.getTags());
        tagsLabel.getStyleClass().add("offre-tags");  // New class name
        vBox.getChildren().add(tagsLabel);

        // Label for price with currency symbol (adjust formatting as needed)
        NumberFormat priceFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        Label priceLabel = new Label("Price: " + priceFormatter.format(offre.getPrix()));
        priceLabel.getStyleClass().add("offre-price");  // New class name
        vBox.getChildren().add(priceLabel);

        return vBox;
    }
}
