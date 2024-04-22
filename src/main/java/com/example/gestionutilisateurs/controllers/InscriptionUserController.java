
package com.example.gestionutilisateurs.controllers;

import com.example.gestionutilisateurs.entities.AESCrypt;
//import com.example.gestionutilisateurs.entities.SMSsender;
import com.example.gestionutilisateurs.entities.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.gestionutilisateurs.entities.AESCrypt;
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



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choices.getItems().add("Coach");
        choices.getItems().add("Nutritionist");
        choices.getItems().add("simple utilisateur");
        choices.getSelectionModel().select("Coach");

    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {

        String email=emailtf.getText();
        String tel= teltf.getText();

        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
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

}
