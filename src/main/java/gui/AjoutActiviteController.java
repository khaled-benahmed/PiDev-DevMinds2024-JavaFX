/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Activite;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import services.ActiviteService;
import utils.MyDB;


public class AjoutActiviteController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    private AnchorPane addActivitePane;


    @FXML
    void return_ListActivite()throws IOException{ 
        Parent fxml= FXMLLoader.load(getClass().getResource("/FXML/listActivite.fxml"));
        addActivitePane.getChildren().removeAll();
        addActivitePane.getChildren().setAll(fxml);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options = FXCollections.observableArrayList(
        "Facile","Moyenne","Difficile");
        textDiffAct.setItems(options);
    }    
    
    
    /*Formulaire AjoutActivité*/
    @FXML
    private TextArea textDescriptionAct;

    @FXML
    private ComboBox<String> textDiffAct;
    
    @FXML
    private TextField textDureeAct;
    @FXML
    private TextField textNomAct;
    
    @FXML
    private ImageView imgActiviteInput;
    
    
    @FXML
    private Button btnImportAct;
    @FXML
    private Button btnAddAct;
    @FXML
    private Button btnClearAct;
    
    
    private File selectedImageFile;
    private String imageName = null ;
    
    
    @FXML
    void ajouterImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imgActiviteInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imgActiviteInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src/main/resources/", "uploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        }
    }
    
    @FXML
    private void AjoutActivite(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddAct){
            if (textDescriptionAct.getText().isEmpty() || textDiffAct.getItems().isEmpty() || textDureeAct.getText().isEmpty() || textNomAct.getText().isEmpty() || imageName==null)
            {    
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre activité.");
                Optional<ButtonType> option = alert.showAndWait();
                
            } else {    
                ajouterActivite();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre activité a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();
                
                clearFieldsActivite();
            }
        }
        if(event.getSource() == btnClearAct){
            clearFieldsActivite();
        }
    }
    
    
    @FXML
    private void clearFieldsActivite() {
        textDescriptionAct.clear();
        textDureeAct.clear();
        textNomAct.clear();
        imgActiviteInput.setImage(null);
    }
    
    
    private void ajouterActivite() {
        
         // From Formulaire
        String nomAct = textNomAct.getText();
        String dureeAct = textDureeAct.getText();
        String diffAct = textDiffAct.getValue();
        String imgAct = imageName;
        String descpAct = textDescriptionAct.getText();
       
        
        MyDB db = MyDB.getInstance();
        Activite act = new Activite(
                nomAct, dureeAct, diffAct, imgAct, descpAct);
        ActiviteService as = new ActiviteService();
        as.ajouter(act);
    }
    
}
