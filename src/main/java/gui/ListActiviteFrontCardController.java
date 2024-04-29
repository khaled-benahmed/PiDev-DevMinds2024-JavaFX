/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import entities.Activite;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class ListActiviteFrontCardController implements Initializable {

    /**
     * Initializes the controller class.
     */


    @FXML
    private ImageView imgAct;

    @FXML
    private Label labelDescriptionAct;

    @FXML
    private Label labelDureeAct;

    @FXML
    private Label labelNomAct;

    @FXML
    private Label prix;

    MyListener myListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    Activite act;

    public void setData(Activite act, MyListener myListener) {
        this.act = act;
        this.myListener = myListener;
        // Check if the image file exists before trying to load it
        String imagePath = "/uploads/" + act.getImage_activite();
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            imgAct.setFitWidth(127);
            imgAct.setFitHeight(111);
            imgAct.setImage(image);
        } else {
            // Handle the case where the image file cannot be found
            System.out.println("Image file not found: " + imagePath);
        }
        labelNomAct.setText(act.getNom_activite());
        labelDureeAct.setText("Duree : " + act.getNom_activite());
        labelDescriptionAct.setText(act.getDescription_activite());
    }


}