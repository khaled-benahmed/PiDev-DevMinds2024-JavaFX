package com.example.gestionutilisateurs.controllers;

import com.example.gestionutilisateurs.entities.AESCrypt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import com.example.gestionutilisateurs.services.UserService;


import java.net.URL;
import java.util.ResourceBundle;


public class ChangementMdpController implements Initializable {

    @FXML
    private Button valider;
    @FXML
    private PasswordField mdp1;
    @FXML
    private PasswordField mdp2;
    String email2;
    public AESCrypt CryptVar;
    public String key = "ThisIsASecretKey";
    UserService us= new UserService();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void updateMdp(String email){
        email2=email;

    }

    @FXML
    private void Update_password(ActionEvent event) throws Exception {
        String newPassword = mdp1.getText();
        String confirmPassword = mdp2.getText();

        if (newPassword.equals(confirmPassword)) {
            if (isPasswordComplex(newPassword)) {
                us.ModifMDP(email2, CryptVar.encrypt(newPassword, key));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mot de passe");
                alert.setHeaderText(null);
                alert.setContentText("Votre mot de passe a été changé avec succès.");
                alert.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Login");
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Mot de passe");
                alert.setHeaderText(null);
                alert.setContentText("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("Les mots de passe ne sont pas identiques.");
            alert.show();
        }
    }
    private boolean isPasswordComplex(String password) {
        // Vérifier si le mot de passe a au moins 8 caractères
        if (password.length() < 8) {
            return false;
        }

        // Vérifier s'il contient au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

}