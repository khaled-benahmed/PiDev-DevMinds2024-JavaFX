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
        if(mdp1.getText().equals(mdp2.getText())){
            us.ModifMDP(email2, CryptVar.encrypt(mdp1.getText(),key));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("votre mot de passe a été changé avec succés");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }

        else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("champ vide ou les mot de passe ne sont pas identiques");
            alert.show();
        }
    }



}