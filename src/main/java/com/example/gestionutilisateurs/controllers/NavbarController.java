
package com.example.gestionutilisateurs.controllers;
import com.example.gestionutilisateurs.entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class NavbarController implements Initializable {

    @FXML
    private Text welcomeText;
    @FXML
    private ImageView profileIcon;
    @FXML
    private ImageView panierIcon;
    //private ImageView out;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (LoginController.UserConnected != null) {
            String firstname = LoginController.UserConnected.getFirstname();
            welcomeText.setText("Welcome, " + firstname + "!");
            if (LoginController.UserConnected.getRole().equals("Admin")) {
                profileIcon.setVisible(true);
                panierIcon.setVisible(false);
            }
        } else {
            // Gérer le cas où aucun utilisateur n'est connecté
            welcomeText.setText("Welcome, Guest!");
        }
    }


    @FXML
    private void openProfile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Edit.fxml"));
            Parent root = loader.load();
            EditController controller = loader.getController();
            controller.senduser(LoginController.UserConnected);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void openPanier(MouseEvent event) {

    }

    public void Logout(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Parent root = loader.load();
            //EditController controller = loader.getController();
            //controller.senduser(LoginController.UserConnected);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}



