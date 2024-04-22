package controllers.BackOffice;

import controllers.Router;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BackOfficeHomeController implements Initializable {

    @FXML
    private HBox Content;
    @FXML
    private Button BtnGoOffre;
    @FXML
    private Button BtnGoSponor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Router.getInstance().setContent(Content);
    }

    @FXML
    public void GoOffre(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListOffre.fxml"));
        Parent root = loader.load();
        Router.getInstance().showContent(root);
    }

    @FXML
    public void GoSponsor(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListSponsor.fxml"));
        Router.getInstance().showContent(loader.load());
    }
}
