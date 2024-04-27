package com.example.gestionutilisateurs.controllers;

import com.example.gestionutilisateurs.entities.User;
import com.example.gestionutilisateurs.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class EditProfilController {

    @FXML
    private TextField tLastName;

    @FXML
    private TextField tFirstName;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tTel;
    @FXML
    private ImageView pdp;
    @FXML
    private TextField tUsername; // Nouveau champ pour le nom d'utilisateur

    @FXML
    private TextField tRole; // Nouveau champ pour le rôle

    @FXML
    private Button upbtn;

    private User user;
    private String imageData;
    private UserService userService = new UserService();

    public void initialize(User user) {
        this.user = user;

        // Afficher les informations de l'utilisateur dans les champs texte
        //    ByteArrayInputStream inputStream = new ByteArrayInputStream(user.getImage());
//       Image image = new Image(inputStream);

        File imageFile = new File(user.getImage());
        Image image = new Image(imageFile.toURI().toString());
        pdp.setImage(image);
        tLastName.setText(user.getLastname());
        tFirstName.setText(user.getFirstname());
        tEmail.setText(user.getEmail());
        tTel.setText(Integer.toString(user.getTel()));
        tUsername.setText(user.getUsername());
        tRole.setText(user.getRole());

    }

    @FXML
    void updateProfile(ActionEvent event) throws SQLException {
        // Vérifier si le nom d'utilisateur est déjà enregistré
        String username = tUsername.getText();
        if (userService.existUsername(username) && !username.equals(user.getUsername())) {
            showErrorAlert("Erreur de mise à jour", "Nom d'utilisateur déjà utilisé", "Le nom d'utilisateur est déjà enregistré.");
            return;
        }

        // Vérifier si l'email est déjà enregistré
        String email = tEmail.getText();
        if (userService.existemail(email) && !email.equals(user.getEmail())) {
            showErrorAlert("Erreur de mise à jour", "Email déjà utilisé", "Cet email est déjà enregistré.");
            return;
        }

        // Vérifier si le format de l'email est valide
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showErrorAlert("Erreur de mise à jour", "Format email non valide", "Veuillez saisir un email valide.");
            return;
        }

        // Vérifier si le format du numéro de téléphone est valide
        String tel = tTel.getText();
        if (!tel.matches("\\d{8}")) {
            showErrorAlert("Erreur de mise à jour", "Format téléphone non valide", "Veuillez saisir un numéro de téléphone valide.");
            return;
        }

        // Mettre à jour les informations de l'utilisateur avec les valeurs des champs texte
        user.setLastname(tLastName.getText());
        user.setFirstname(tFirstName.getText());
        user.setEmail(email);
        user.setTel(Integer.parseInt(tel));
        user.setUsername(username);
        user.setRole(tRole.getText());

        // Vérifier si l'image a été modifiée
        if (imageData != null) {
            user.setImage(imageData);
        }

        try {
            // Mettre à jour l'utilisateur dans la base de données
            userService.modifier(user);
            // Afficher un message de succès
            showSuccessAlert("Profil mis à jour", "Le profil a été mis à jour avec succès.");
        } catch (SQLException e) {
            // Afficher un message d'erreur en cas d'échec de la mise à jour
            showErrorAlert("Erreur de mise à jour", "Impossible de mettre à jour le profil", e.getMessage());
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si le numéro de téléphone n'est pas un entier
            showErrorAlert("Erreur de mise à jour", "Numéro de téléphone invalide", "Le numéro de téléphone doit être un entier.");
        }
    }

    @FXML
    private void uploadImgBtn(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            //imageData = Files.readAllBytes(selectedFile.toPath());
            imageData = selectedFile.getPath();
        }


    }

    private void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void goBackHandler(ActionEvent event) {
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
