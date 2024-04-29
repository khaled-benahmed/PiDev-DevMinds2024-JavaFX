/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Planning;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import services.PlanningService;


public class ListPlanningController implements Initializable {

    public Button bntAddPlanning;
    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane listPlanningPane;
    
    @FXML
    void open_addPlanning(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/FXML/ajoutPlanning.fxml"));
        listPlanningPane.getChildren().removeAll();
        listPlanningPane.getChildren().setAll(fxml);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherPlanning();
    }    
    
    
    /*TableView Planning*/
    @FXML
    private TableColumn<Planning, Integer> ActivitePlanningCell;
    @FXML
    private TableColumn<Planning, Date> DatePlanningCell;
    @FXML
    private TableColumn<Planning, String> JourPlanningCell;
    @FXML
    private TableColumn<Planning, Integer> HeurePlanningCell;
    @FXML
    private TableView<Planning> tablePlanning;
    @FXML
    public TableColumn<Planning, Integer> HeureFinPlanningCell;




    @FXML
    private Button btnDeletePlanning;
    @FXML
    private TextField txtSearchPlanning;
    @FXML
    private ComboBox<String> comboBoxTriPlanning;
    
    ObservableList<Planning> dataPlanning = FXCollections.observableArrayList();
    
    
    public void AfficherPlanning()
    {
        PlanningService ps = new PlanningService();
        ps.Show().stream().forEach((c) -> {
            dataPlanning.add(c);
        });

        ActivitePlanningCell.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        ActivitePlanningCell.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ActivitePlanningCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Planning, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Planning, Integer> event) {
                Planning p = event.getRowValue();
                PlanningService ps = new PlanningService();
                ps.modifier(p);
            }
        });
        DatePlanningCell.setCellValueFactory(new PropertyValueFactory<>("date_planning"));
        DatePlanningCell.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            @Override
            public String toString(Date object) {
                return dateFormat.format(object);
            }

            @Override
            public Date fromString(String string) {
                try {
                    // Parse the string into a Date object using the defined format
                    return dateFormat.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // If the string can't be parsed, return null
                    return null;
                }
            }
        }));
        DatePlanningCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Planning, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Planning, Date> event) {
                Planning p = event.getRowValue();
                Date dateSaisie = event.getNewValue();
                // Obtenir la date actuelle sans l'heure
                Date dateActuelle = new Date();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dateActuelle = sdf.parse(sdf.format(dateActuelle));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Comparer si la date saisie dépasse la date actuelle
                if (dateSaisie.after(dateActuelle)) {
                    // Si la date saisie est dans le futur, afficher une alerte et ne pas mettre à jour
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erreur de Validation");
                    alert.setHeaderText(null);
                    alert.setContentText("La date ne doit pas dépasser la date actuelle.");
                    alert.showAndWait();

                    // Rafraîchir la table pour annuler la modification visuelle
                    tablePlanning.refresh();
                } else {
                    // Si la date saisie est valide, mettre à jour la date dans l'objet Planning
                    p.setDate_planning(dateSaisie);
                    PlanningService ps = new PlanningService();
                    ps.modifier(p);
                }
            }
        });

        JourPlanningCell.setCellValueFactory(new PropertyValueFactory<>("jour_planning"));
        JourPlanningCell.setCellFactory(TextFieldTableCell.forTableColumn());
        JourPlanningCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Planning, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Planning, String> event) {
                Planning p = event.getRowValue();
                String jourSaisi = event.getNewValue().trim();

                // Ensemble des jours valides
                Set<String> joursValides = new HashSet<>(Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"));

                // Vérifier si le jour saisi est valide
                if (!joursValides.contains(jourSaisi)) {
                    // Si le jour saisi n'est pas valide, afficher une alerte et ne pas mettre à jour
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erreur de Validation");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez entrer un jour valide (Lundi, Mardi, Mercredi, etc.).");
                    alert.showAndWait();

                    // Rafraîchir la table pour annuler la modification visuelle
                    tablePlanning.refresh();
                } else {
                    // Si le jour saisi est valide, mettre à jour le jour dans l'objet Planning
                    p.setJour_planning(jourSaisi);
                    PlanningService ps = new PlanningService();
                    ps.modifier(p);
                }
            }
        });

        HeurePlanningCell.setCellValueFactory(new PropertyValueFactory<>("heure_planning"));
        HeurePlanningCell.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        HeurePlanningCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Planning, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Planning, Integer> event) {
                Planning p = event.getRowValue();
                p.setHeure_planning(event.getNewValue());
                PlanningService ps = new PlanningService();
                ps.modifier(p);
            }
        });
        HeureFinPlanningCell.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
        HeureFinPlanningCell.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        HeureFinPlanningCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Planning, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Planning, Integer> event) {
                Planning p = event.getRowValue();
                // Récupérer la nouvelle valeur de heure_fin saisie par l'utilisateur
                int nouvelleHeureFin = event.getNewValue();
                // Vérifier si la nouvelle heure de fin est supérieure à l'heure de début
                if (nouvelleHeureFin <= p.getHeure_planning()) {
                    // Si l'heure de fin est inférieure ou égale à l'heure de début, afficher une alerte et ne pas mettre à jour
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erreur de Validation");
                    alert.setHeaderText(null);
                    alert.setContentText("L'heure de fin doit être supérieure à l'heure de début.");
                    alert.showAndWait();
                } else {
                    // Si la validation est passée, mettre à jour l'heure de fin et procéder à la modification
                    p.setHeure_fin(nouvelleHeureFin);
                    PlanningService ps = new PlanningService();
                    ps.modifier(p);
                }
            }
        });

        tablePlanning.setItems(dataPlanning);
        comboBoxTriPlanning.getItems().addAll("Trier Selon",  "Jour", "Date");
        try {
            searchPlanning();
        } catch (SQLException ex) {
            Logger.getLogger(ListActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimerPlanning(ActionEvent event) throws SQLException {
        PlanningService ps = new PlanningService();
        
        if (tablePlanning.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez sélectionner le planning à supprimer");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce planning ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID du planning sélectionnée dans la vue de la table
            int id = tablePlanning.getSelectionModel().getSelectedItem().getId();

            // Supprimer le planning de la base de données
            ps.supprimer(id);
            // Rafraîchir la liste de données
            dataPlanning.clear();
            AfficherPlanning();
            // Rafraîchir la vue de la table
            tablePlanning.refresh();
        }
    }
    
    
    private void TriJourPlanning() {
        PlanningService cs = new PlanningService();
        List<Planning> a = cs.triJourPlanning();
        ActivitePlanningCell.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        DatePlanningCell.setCellValueFactory(new PropertyValueFactory<>("date_planning"));
        JourPlanningCell.setCellValueFactory(new PropertyValueFactory<>("jour_planning"));
        HeurePlanningCell.setCellValueFactory(new PropertyValueFactory<>("heure_planning"));
        HeureFinPlanningCell.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));


        tablePlanning.setItems(FXCollections.observableList(a));

    }
    
    
    private void TriDatePlanning() {
        PlanningService cs = new PlanningService();
        List<Planning> a = cs.triDatePlanning();
        ActivitePlanningCell.setCellValueFactory(new PropertyValueFactory<>("activite_id"));
        DatePlanningCell.setCellValueFactory(new PropertyValueFactory<>("date_planning"));
        JourPlanningCell.setCellValueFactory(new PropertyValueFactory<>("jour_planning"));
        HeurePlanningCell.setCellValueFactory(new PropertyValueFactory<>("heure_planning"));
        HeureFinPlanningCell.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));


        tablePlanning.setItems(FXCollections.observableList(a));
    }
    
    
    @FXML
    private void TriChoice(ActionEvent event) throws IOException {
        if (comboBoxTriPlanning.getValue().equals("Jour")) {
            TriJourPlanning();
        } else if (comboBoxTriPlanning.getValue().equals("Date")) {
            TriDatePlanning();
        } 

    }

    
    public PlanningService ps = new PlanningService();
    
    public void searchPlanning() throws SQLException {    
        FilteredList<Planning> filteredData = new FilteredList<>(FXCollections.observableArrayList(ps.Show()), p -> true);
        txtSearchPlanning.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(planning -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String date = String.valueOf(planning.getDate_planning());
                String jour = String.valueOf(planning.getJour_planning());
                String heure = String.valueOf(planning.getHeure_planning());
                String lowerCaseFilter = newValue.toLowerCase();

                if (date.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (jour.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (heure.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Planning> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablePlanning.comparatorProperty());
        tablePlanning.setItems(sortedData);
    }
    
}
