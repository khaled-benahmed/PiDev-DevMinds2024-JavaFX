
package com.example.gestionutilisateurs.controllers;

import com.example.gestionutilisateurs.entities.AESCrypt;
import com.example.gestionutilisateurs.entities.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.gestionutilisateurs.services.UserService;


public class InscriptionUserController implements Initializable {

    @FXML
    private ChoiceBox<String> choices;
    @FXML
    private TextField nomtf;
    @FXML
    private TextField prenomtf;
    @FXML
    private TextField emailtf;
    @FXML
    private TextField teltf;
    @FXML
    private PasswordField mdptf;
    @FXML
    private TextField usernametf;
    @FXML
    private ChoiceBox<String> usernameSuggestions;

    @FXML
    private Button ajouter;

    UserService us= new UserService();
    @FXML
    private Button annuler;
    @FXML
    private Button uploadImgBtn;

    private String imageData;
    @FXML
    private Text iscriL;
    @FXML
    private Button ret;

    public AESCrypt CryptVar;
    public String key = "ThisIsASecretKey";

    private List<String> suggestions = new ArrayList<>();
    private void onUsernameClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choices.getItems().add("Coach");
        choices.getItems().add("Nutritionist");
        choices.getItems().add("simple utilisateur");
        choices.getSelectionModel().select("Coach");
        generateUsernameSuggestions();

    }


    @FXML
    private void ajouter(ActionEvent event) throws SQLException {

        String nom = nomtf.getText();
        String prenom = prenomtf.getText();
        String email = emailtf.getText();
        String tel = teltf.getText();
        String mdp = mdptf.getText();
        String username = usernametf.getText();
        String role = choices.getValue();
        String imageData = this.imageData;

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tel.isEmpty() || mdp.isEmpty() || username.isEmpty() || role == null || imageData == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return;
        }

        if (us.existUsername(username)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Ce nom d'utilisateur est déjà enregistré!");
            alert.show();
            return;
        }
        if (!isPasswordComplex(mdptf.getText())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit être complexe!");
            alert.show();
            return;
        }

        else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("format email non valide!");
            alert.show();  }

        else if  (us.existemail(email)==true){



            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Cet email est déjà enregistré!");
            alert.show();
        }

        else if (!tel.matches("\\d{8}")){

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("format tel non valide!");
            alert.show();

        }

        else  {
            try {

                User p = new User();
                String encrypted = CryptVar.encrypt(mdptf.getText(), key);
                p.setLastname(nomtf.getText());
                p.setFirstname(prenomtf.getText());
                p.setUsername(usernametf.getText());
                p.setTel(Integer.parseInt(teltf.getText()));
                p.setRole(choices.getValue());
                p.setEmail(emailtf.getText());
                p.setPassword(encrypted);
                p.setImage(imageData);
                us.ajouter(p);

                // Récupérer le numéro de téléphone de TelField
                String tel1 = teltf.getText();

                // Appeler la méthode SMSSender de SmsSender avec le numéro de téléphone récupéré
                //SMSsender.sendSMS(tel1, "Bienvenue ! Votre inscription est réussie.");


                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("utilisateur ajouté!");
                alert.show();
                // Rediriger vers la page de login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            catch (SQLException ex) {
                System.out.println("error" + ex.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    private void annuler(ActionEvent event) {


        nomtf.setText("");
        prenomtf.setText("");
        emailtf.setText("");
        teltf.setText("");
        mdptf.setText("");
        usernametf.setText("");
    }

    @FXML
    private void onUploadButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            //imageData = Files.readAllBytes(selectedFile.toPath());
            imageData=selectedFile.getPath();
        }

    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
//       if (LoginController.UserConnected.getRole().equals("Admin")){
//
//       FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheUser.fxml"));
//            Parent root = loader.load();
//
//
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setTitle("Affiche Users");
//        stage.setScene(scene);
//        stage.show();
//
//       }
//
//       else if(LoginController.UserConnected==null){
//
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        Parent root = loader.load();
//
//
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
//
//       }

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

    @FXML
    private void onNomOrPrenomChange(KeyEvent event) {
        generateUsernameSuggestions();
        updateUsernameSuggestions();
    }
    private void generateUsernameSuggestions() {
        String nom = nomtf.getText().toLowerCase();
        String prenom = prenomtf.getText().toLowerCase();

        suggestions.clear(); // Effacer les suggestions précédentes
        suggestions.add(nom + "_" + prenom);
        suggestions.add(prenom + "_" + nom);
        suggestions.add(nom + "." + prenom);
        suggestions.add(prenom + "." + nom);
        suggestions.add(nom + prenom);
        suggestions.add(prenom + nom);
    }

    private void updateUsernameSuggestions() {
        usernameSuggestions.getItems().clear();
        usernameSuggestions.getItems().addAll(suggestions);
    }


    @FXML
    private void onUsernameSuggestionSelected(ActionEvent event) {
        String selectedUsername = usernameSuggestions.getSelectionModel().getSelectedItem();
        usernametf.setText(selectedUsername);
    }



}
