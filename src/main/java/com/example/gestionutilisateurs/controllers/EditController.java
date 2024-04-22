
package com.example.gestionutilisateurs.controllers;

import com.example.gestionutilisateurs.entities.User;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.example.gestionutilisateurs.services.UserService;


public class EditController implements Initializable {

    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label email;
    @FXML
    private Label tel;
    @FXML
    private Label mdp;
    @FXML
    private Label role;
    @FXML
    private Button delb;
    @FXML
    private Button modb;

    private User user;
    private UserService  us=new UserService();
    @FXML
    private ImageView pdp;
    @FXML
    private ImageView backbtn;
    @FXML
    private ImageView goBackBtn;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (LoginController.UserConnected != null) {
            // Si un utilisateur est connecté, affiche ses informations
            senduser(LoginController.UserConnected);
        } else {
            // Gérer le cas où aucun utilisateur n'est connecté
        }

            if (LoginController.UserConnected.getRole().equals("Admin")){
                backbtn.setVisible(true);
                goBackBtn.setVisible(false);
                delb.setVisible(false);
                modb.setVisible(false);

            }
    }

    void senduser(User u) {
        user = u;
        nom.setText(user.getLastname());
        prenom.setText(user.getFirstname());
        email.setText(user.getEmail());
        tel.setText(Integer.toString(user.getTel()));

        String password = user.getPassword();
        if (password != null) {
            mdp.setText(password.replaceAll(".", "*"));
        } else {
            mdp.setText("");
        }

        role.setText(user.getRole());

        String imagePath = user.getImage();
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            Image image = new Image(imageFile.toURI().toString());
            pdp.setImage(image);
        }
    }


    @FXML
    private void delete(ActionEvent event) throws IOException  {
        try {
            us.supprimer(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);
            alert.setContentText("Compte supprimé!");
            alert.show();
            if(!(LoginController.UserConnected.getRole().equals("Admin"))){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Login");
                stage.setScene(scene);
                stage.show();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/editprofil.fxml"));
        Parent root = loader.load();
        // Récupérer le contrôleur de la vue editprofil.fxml
        EditProfilController editProfilController = loader.getController();
        // Passer l'utilisateur à la méthode initialize
        editProfilController.initialize(user);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Update");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void back(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/MainCotainer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Affiche Users");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void goBackHandler(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/MainCotainer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
