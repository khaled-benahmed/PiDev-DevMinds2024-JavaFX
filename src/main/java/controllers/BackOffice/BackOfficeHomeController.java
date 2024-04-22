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
    private Button BtnGoRec;
    @FXML
    private HBox Content;

    @FXML
    public void GoRec(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListReclamations.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ListReclamations.fxml"));

        // this.BtnGoRec.getScene().setRoot(loader.load());
        Router.getInstance().showContent(loader.load());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Router.getInstance().setContent(Content);
    }
}
