/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Activite;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import services.ActiviteService;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class ListActiviteFrontController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private ImageView background;

    @FXML
    private Button btnConsulterCours;

    @FXML
    private Button btnConsulterPlanning;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox;

    @FXML
    private Pagination pag;

    @FXML
    private HBox vbox;
    
    
    
    private MyListener myListener;
    
    @FXML
    private AnchorPane listActiviteFront;
    
    
    @FXML
    void go_ListCours()throws IOException{ 
        Parent fxml= FXMLLoader.load(getClass().getResource("/FXML/listCoursFront.fxml"));
        listActiviteFront.getChildren().removeAll();
        listActiviteFront.getChildren().setAll(fxml);
    }
    
    @FXML
    void go_ListPlanning()throws IOException{ 
        Parent fxml= FXMLLoader.load(getClass().getResource("/FXML/listPlanningFront.fxml"));
        listActiviteFront.getChildren().removeAll();
        listActiviteFront.getChildren().setAll(fxml);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ActiviteService as = new ActiviteService();
            List<Activite> act = as.Show();
            pag.setPageCount((int) Math.ceil(act.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre d'articles à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, act.size()); i++) {
                    
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/FXML/listActiviteFrontCard.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        ListActiviteFrontCardController itemController = fxmlLoader.getController();
                        itemController.setData(act.get(i), myListener);
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(ListActiviteFrontController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}
