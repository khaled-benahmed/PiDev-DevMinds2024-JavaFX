package controllers.FrontOffice;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class FrontOfficeHomeController {
    @javafx.fxml.FXML
    private Button BtnGoOffres;
    @javafx.fxml.FXML
    private Button BtnGoAjoutOffre;
    @javafx.fxml.FXML
    private Button BtnGoPromo;
    @javafx.fxml.FXML
    private Button BtnGoSponsors;
    @javafx.fxml.FXML
    private Button BtnAjoutPromo;

    @javafx.fxml.FXML
    public void GoOffres(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ListOffreFront.fxml"));
        this.BtnGoOffres.getScene().setRoot(loader.load());
    }

    @javafx.fxml.FXML
    public void GoAjouterOffre(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterOffre.fxml"));
        this.BtnGoAjoutOffre.getScene().setRoot(loader.load());
    }

    @javafx.fxml.FXML
    public void GoPromo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ListPromoFront.fxml"));
        this.BtnGoPromo.getScene().setRoot(loader.load());
    }

    @javafx.fxml.FXML
    public void GoSponsors(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ListSponsorFront.fxml"));
        this.BtnGoSponsors.getScene().setRoot(loader.load());
    }

    @javafx.fxml.FXML
    public void GoAjouterSponsor(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterSponsor.fxml"));
        this.BtnAjoutPromo.getScene().setRoot(loader.load());
    }
}
