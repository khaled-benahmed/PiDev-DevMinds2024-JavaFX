/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author khaled
 */

public class DashboardController implements Initializable{

    
    /*Containers to View*/
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane viewPages;
    
    
    /*Close+Minus Buttons*/
    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinus;
    
    
    /*NavBar Buttons*/

    @FXML
    private Button btnGestionPlanning;

    
    
    /*Close App*/
    @FXML
    public void close(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("EnergyBox | CrossFit Center");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous quitter ?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {
                System.exit(0);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /*Minimize App*/
    @FXML
    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);   
    }
    
    
    /*Scroll NavBar Buttons*/
    @FXML
    public void switchForm(ActionEvent event) throws IOException{
        if(event.getSource()== btnGestionPlanning) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/FXML/gestionPlanning.fxml"));
            viewPages.getChildren().removeAll();
            viewPages.getChildren().setAll(fxml);
        }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TO DO
    }
    
    
}
