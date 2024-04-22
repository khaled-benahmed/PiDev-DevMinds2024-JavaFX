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
import models.Promotion;
import models.Sponsor;
import services.ServicePromotion;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ListPromoFrontController implements Initializable {
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private ScrollPane scrollPane;

    private ServicePromotion servicePromotion = new ServicePromotion();


    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/FrontOfficeHome.fxml"));
        this.BtnRetour.getScene().setRoot(loader.load());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherPromo();

    }

    public void afficherPromo() {
        try {
            scrollPane.setContent(null);
            List<Promotion> promos = servicePromotion.getAll();
            // create a card for each sponsor and add the cards 3 by 3 in  to a VBoxex then add the VBoxex to a Hbox and add the HBox to the the scrollpane
            VBox V = new VBox();
            V.setSpacing(30);
            for (int i = 0; i < promos.size(); i += 3) {
                HBox hBox = new HBox();
                hBox.setSpacing(30);
                for (int j = i; j < i + 3 && j < promos.size(); j++) {
                    hBox.getChildren().add(createPromoCard(promos.get(j)));
                }
                V.getChildren().add(hBox);
            }
            scrollPane.setContent(V);
            scrollPane.setFitToWidth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VBox createPromoCard(Promotion promo) {
        VBox vBox = new VBox();
        vBox.setPrefWidth(280);
        vBox.getStyleClass().add("promotion-card");  // New class name
        vBox.setSpacing(10);

        // Code and reduction labels
        Label codeLabel = new Label("Code: " + promo.getCodePromotion());
        codeLabel.getStyleClass().add("promo-code");  // New class name
        vBox.getChildren().add(codeLabel);

        Label reductionLabel = new Label("Discount: " + promo.getReductionPromotion().toString() + "%"); // Label text adjusted
        reductionLabel.getStyleClass().add("discount-value");  // New class name
        vBox.getChildren().add(reductionLabel);

        // Expiration date label (formatted)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy"); // Customize format as needed
        Label expirationDateLabel = new Label("Expires: " + formatter.format(promo.getDateExpiration()));
        expirationDateLabel.getStyleClass().add("expiration-date");  // New class name
        vBox.getChildren().add(expirationDateLabel);

        return vBox;
    }
}
