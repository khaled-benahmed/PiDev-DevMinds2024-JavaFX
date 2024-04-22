/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Planning;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import services.PlanningService;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class ListPlanningFrontController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherPlanningLundi();
        AfficherPlanningMardi();
        AfficherPlanningMercredi();
        AfficherPlanningJeudi();
        AfficherPlanningVendredi();
        AfficherPlanningSamedi();
        AfficherPlanningDimanche();
    }  
    
    @FXML
    private TableColumn<Planning, Integer> Lundi;

    @FXML
    private TableColumn<Planning, Integer> Mardi;
    
    @FXML
    private TableColumn<Planning, Integer> Dimanche;

    @FXML
    private TableColumn<Planning, Integer> Jeudi;

    @FXML
    private TableColumn<Planning, Integer> Mercredi;

    @FXML
    private TableColumn<Planning, Integer> Samedi;

    @FXML
    private TableColumn<Planning, Integer> Vendredi;
    
    @FXML
    private TableColumn<Planning,Integer> HeurePlanning;

    @FXML
    private TableView<Planning> tablePlanningLundi;

    @FXML
    private TableView<Planning> tablePlanningMardi;
    
    @FXML
    private TableView<Planning> tablePlanningDimanche;

    @FXML
    private TableView<Planning> tablePlanningJeudi;

    @FXML
    private TableView<Planning> tablePlanningMercredi;

    @FXML
    private TableView<Planning> tablePlanningSamedi;

    @FXML
    private TableView<Planning> tablePlanningVendredi;
    
    @FXML
    private Button btnConsulterCours;

    @FXML
    private Button btnConsulterActivite;
    
    @FXML
    private AnchorPane listPlanningPane;
    
    
    @FXML
    void go_ListCours(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/FXML/listCoursFront.fxml"));
        listPlanningPane.getChildren().removeAll();
        listPlanningPane.getChildren().setAll(fxml);
    }

    @FXML
    void go_ListActivite(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/FXML/listActiviteFront.fxml"));
        listPlanningPane.getChildren().removeAll();
        listPlanningPane.getChildren().setAll(fxml);
    }
    

    
    
    
    ObservableList<Planning> dataLundi = FXCollections.observableArrayList();
    ObservableList<Planning> dataMardi = FXCollections.observableArrayList();
    ObservableList<Planning> dataMercredi = FXCollections.observableArrayList();
    ObservableList<Planning> dataJeudi = FXCollections.observableArrayList();
    ObservableList<Planning> dataVendredi = FXCollections.observableArrayList();
    ObservableList<Planning> dataSamedi = FXCollections.observableArrayList();
    ObservableList<Planning> dataDimanche = FXCollections.observableArrayList();
    
    
    public void AfficherPlanningLundi()
    {
        PlanningService ps = new PlanningService();
        
        
        ps.ShowLundi().stream().forEach((c) -> {
            dataLundi.add(c);
        });
        Lundi.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        HeurePlanning.setCellValueFactory(new PropertyValueFactory<>("heure_planning"));
        tablePlanningLundi.setItems(dataLundi);
        
    }
    
    public void AfficherPlanningMardi()
    {
        PlanningService ps = new PlanningService();
        
        
        ps.ShowMardi().stream().forEach((c) -> {
            dataMardi.add(c);
        });
        Mardi.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        tablePlanningMardi.setItems(dataMardi);
        
    }
    
    public void AfficherPlanningMercredi()
    {
        PlanningService ps = new PlanningService();
        
        
        ps.ShowMercredi().stream().forEach((c) -> {
            dataMercredi.add(c);
        });
        Mercredi.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        tablePlanningMercredi.setItems(dataMercredi);
        
    }
    
    public void AfficherPlanningJeudi()
    {
        PlanningService ps = new PlanningService();
        
        
        ps.ShowJeudi().stream().forEach((c) -> {
            dataJeudi.add(c);
        });
        Jeudi.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        tablePlanningJeudi.setItems(dataJeudi);
        
    }
    
    public void AfficherPlanningVendredi()
    {
        PlanningService ps = new PlanningService();
        
        
        ps.ShowVendredi().stream().forEach((c) -> {
            dataVendredi.add(c);
        });
        Vendredi.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        tablePlanningVendredi.setItems(dataVendredi);
        
    }
    
    public void AfficherPlanningSamedi()
    {
        PlanningService ps = new PlanningService();
        
        
        ps.ShowSamedi().stream().forEach((c) -> {
            dataSamedi.add(c);
        });
        Samedi.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        tablePlanningSamedi.setItems(dataSamedi);
        
    }
    
    public void AfficherPlanningDimanche()
    {
        PlanningService ps = new PlanningService();
        
        
        ps.ShowDimanche().stream().forEach((c) -> {
            dataDimanche.add(c);
        });
        Dimanche.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        tablePlanningDimanche.setItems(dataDimanche);
        
    }
    
}
