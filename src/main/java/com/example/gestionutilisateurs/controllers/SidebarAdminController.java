
package com.example.gestionutilisateurs.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class SidebarAdminController implements Initializable {

    private MainContainer mcc;
    @FXML
    private Button homeBtn;

    @FXML
    private Button reclamationsBtn;

    @FXML
    private Button utilisateursBtn;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    @FXML
    private void homePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/LandingPageAdmin.fxml"));
            Parent root = loader.load();
            //-------MCCSaver.mcc.setContent(root);
            //---------LandingAdminController controller = loader.getController();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void reclamationPage(ActionEvent event) throws IOException {


    }

    @FXML
    private void utilisateursPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Users.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SidebarAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
