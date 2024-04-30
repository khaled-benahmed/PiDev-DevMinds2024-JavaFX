package com.example.gestionutilisateurs.controllers;

import com.example.gestionutilisateurs.entities.User;
import com.example.gestionutilisateurs.entities.AESCrypt;
import com.example.gestionutilisateurs.tools.MyConnection;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField tname;
    public TextField tpass;
    public Button btnCon;
    public Button btncancel;

    @FXML
    private ImageView eyeIcon;

    private boolean passwordVisible = false;

    public static User UserConnected;

    public AESCrypt CryptVar;
    public String key = "ThisIsASecretKey";

    @FXML
    private Button icibt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eyeIcon.setOnMouseClicked(event -> togglePasswordVisibility());
        btnCon.setOnAction(actionEvent -> {
            try {
                login();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void login() throws SQLException, IOException, Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = MyConnection.getInstance().getCnx();

        st = con.prepareStatement("SELECT * FROM user WHERE USERNAME = ?");
        st.setString(1, tname.getText());
        rs = st.executeQuery();

        if (rs.next()) {
            String decryptedPassword = AESCrypt.decrypt(rs.getString("PASSWORD"), key); // Call decrypt statically
            boolean isBlocked = rs.getBoolean("is_blocked");
            Date blockedUntil = rs.getObject("is_blocked_until", Date.class);
            String blockReason = rs.getString("block_reason");

            if (isBlocked && (new Date()).before(blockedUntil)) {
                // L'utilisateur est bloqué
                showAlert("Votre compte est bloqué jusqu'au " + blockedUntil + " pour la raison suivante : " + blockReason);
            } else if (decryptedPassword.equals(tpass.getText())) {
                UserConnected = new User();
                UserConnected.setUsername(rs.getString("USERNAME"));
                UserConnected.setRole(rs.getString("ROLES"));
                UserConnected.setFirstname(rs.getString("FIRSTNAME"));
                UserConnected.setLastname(rs.getString("LASTNAME"));
                UserConnected.setEmail(rs.getString("EMAIL"));
                UserConnected.setTel(rs.getInt("TEL"));
                UserConnected.setImage(rs.getString("IMAGE"));
                UserConnected.setId(rs.getInt("ID"));
                UserConnected.setIs_blocked(isBlocked);
                UserConnected.setIs_blocked_until(blockedUntil);
                UserConnected.setBlock_reason(blockReason);

                FXMLLoader loader;
                if (UserConnected.getRole().equals("Admin")) {
                    loader = new FXMLLoader(getClass().getResource("/Fxml/MainCotainer.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/Fxml/MainCotainer.fxml"));
                }
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) btnCon.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert("Incorrect password");
            }
        } else {
            showAlert("User not found");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.show();
    }

    @FXML
    void retourInscri(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/InscriptionUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.show();
    }
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Si le mot de passe est visible, changez le champ de mot de passe en PasswordField
            tpass.setManaged(true);
            tpass.setVisible(true);
            eyeIcon.setImage(new Image(getClass().getResourceAsStream("/Fxml/eye-closed.jpg"))); // Remplacez "eye-closed.png" par le chemin de votre icône d'œil fermé
        } else {
            // Si le mot de passe est masqué, changez le champ de mot de passe en TextField
            tpass.setManaged(false);
            tpass.setVisible(false);
            eyeIcon.setImage(new Image(getClass().getResourceAsStream("/Fxml/eye.jpg"))); // Remplacez "eye-open.png" par le chemin de votre icône d'œil ouvert
        }
        passwordVisible = !passwordVisible;
    }

    @FXML
    private void mdp_ob(MouseEvent event) {
    }

    @FXML
    private void passwrd(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/email_check.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("changement de mot de passe");
        stage.setScene(scene);
        stage.show();


    }



}
