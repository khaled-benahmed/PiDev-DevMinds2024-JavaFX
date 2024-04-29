/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Activite;
import java.io.IOException;
import java.net.URL;
import java.io.File;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.FileOutputStream;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import services.ActiviteService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;


import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;

public class ListActiviteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane listActivitePane;
    
    @FXML
    void open_addActivite(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/FXML/ajoutActivite.fxml"));
        listActivitePane.getChildren().removeAll();
        listActivitePane.getChildren().setAll(fxml);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherActivites();
    }  
    
    
    /*TableView Activité*/
    @FXML
    private TableColumn<Activite, String> DescriptionActCell;
    @FXML
    private TableColumn<Activite, String> DiffActCell;
    @FXML
    private TableColumn<Activite, String> DureeActCell;
    @FXML
    private TableColumn<Activite, String> ImageActCell;
    @FXML
    private TableColumn<Activite, String> NomActCell;
    @FXML
    private TableColumn<Activite, Void> actCell;
    @FXML
    private TableView<Activite> tableActivite;
    
    
    @FXML
    private Button btnDeleteAct;
    @FXML
    private TextField txtSearchAct;
    @FXML
    private ComboBox<String> comboBoxTriAct;
    
    
    ObservableList<Activite> data = FXCollections.observableArrayList();
    
    
    public void AfficherActivites()
    {
        ActiviteService as = new ActiviteService();
        as.Show().stream().forEach((p) -> {
            data.add(p);
        });
        NomActCell.setCellValueFactory(new PropertyValueFactory<>("nom_activite"));
        NomActCell.setCellFactory(TextFieldTableCell.forTableColumn());
        NomActCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Activite, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Activite, String> event) {
                Activite a = event.getRowValue();
                a.setNom_activite(event.getNewValue());
                ActiviteService as = new ActiviteService();
                as.modifier(a);
            }
        });
        DureeActCell.setCellValueFactory(new PropertyValueFactory<>("duree_activite"));
        DureeActCell.setCellFactory(TextFieldTableCell.forTableColumn());
        DureeActCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Activite, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Activite, String> event) {
                // Vérification si la nouvelle valeur est un nombre
                String nouvelleValeur = event.getNewValue();
                String regexNombre = "^\\d+(\\.\\d+)?$";
                if (!nouvelleValeur.matches(regexNombre)) {
                    // Si la nouvelle valeur n'est pas un nombre, afficher une alerte
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Valeur invalide");
                    alert.setHeaderText("Modification de la durée de l'activité");
                    alert.setContentText("La durée de l'activité doit être un nombre.");
                    alert.showAndWait();
                } else {
                    // Si la nouvelle valeur est un nombre, procéder à la mise à jour
                    Activite a = event.getRowValue();
                    a.setDuree_activite(nouvelleValeur);
                    ActiviteService as = new ActiviteService();
                    as.modifier(a);
                }
            }
        });

        DiffActCell.setCellValueFactory(new PropertyValueFactory<>("difficulte_activite"));
        DiffActCell.setCellFactory(TextFieldTableCell.forTableColumn());
        DiffActCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Activite, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Activite, String> event) {
                Activite a = event.getRowValue();
                a.setDifficulte_activite(event.getNewValue());
                ActiviteService as = new ActiviteService();
                as.modifier(a);
            }
        });
        ImageActCell.setCellValueFactory(new PropertyValueFactory<>("image_activite"));
        ImageActCell.setCellFactory(TextFieldTableCell.forTableColumn());
        ImageActCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Activite, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Activite, String> event) {
                Activite a = event.getRowValue();
                a.setImage_activite(event.getNewValue());
                ActiviteService as = new ActiviteService();
                as.modifier(a);
            }
        });
        DescriptionActCell.setCellValueFactory(new PropertyValueFactory<>("description_activite"));
        DescriptionActCell.setCellFactory(TextFieldTableCell.forTableColumn());
        DescriptionActCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Activite, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Activite, String> event) {
                Activite a = event.getRowValue();
                a.setDescription_activite(event.getNewValue());
                ActiviteService as = new ActiviteService();
                as.modifier(a);
            }
        });
        actCell.setCellFactory(column->{
            return new TableCell<Activite,Void>(){
                private final Button showBtn = new Button("Show");
                {
                    showBtn.setStyle("-fx-background-color: #720000; -fx-text-fill: #fff;");
                    showBtn.setOnAction(event->{
                        Activite act = getTableView().getItems().get(getIndex());
                        Parent fxml;
                        try {
                            FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/showActivite.fxml"));
                            Parent root=loader.load();
                            ShowActiviteController controller = loader.getController();
                            controller.initData(act.getId());
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException ex) {
                            System.out.println("here modifications risque !!!");
                        }
                    });
                }
                
                @Override
                protected void updateItem(Void item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        setGraphic(null);
                    }else{
                        setGraphic(showBtn);
                    }
                }
            };
        });
        tableActivite.setItems(data);
        comboBoxTriAct.getItems().addAll("Trier Selon",  "Nom", "Difficulte" , "Description");
        try {
            searchActivite();
        } catch (SQLException ex) {
            Logger.getLogger(ListActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void TrieNom() {
        ActiviteService as = new ActiviteService();
        List<Activite> a = as.triNomActivite();
        NomActCell.setCellValueFactory(new PropertyValueFactory<>("nom_activite"));
        DureeActCell.setCellValueFactory(new PropertyValueFactory<>("duree_activite"));
        DiffActCell.setCellValueFactory(new PropertyValueFactory<>("difficulte_activite"));
        ImageActCell.setCellValueFactory(new PropertyValueFactory<>("image_activite"));
        DescriptionActCell.setCellValueFactory(new PropertyValueFactory<>("description_activite"));
        

        tableActivite.setItems(FXCollections.observableList(a));

    }

    
    private void TrieDiff() {
        ActiviteService as = new ActiviteService();
        List<Activite> a = as.triDiffActivite();
        NomActCell.setCellValueFactory(new PropertyValueFactory<>("nom_activite"));
        DureeActCell.setCellValueFactory(new PropertyValueFactory<>("duree_activite"));
        DiffActCell.setCellValueFactory(new PropertyValueFactory<>("difficulte_activite"));
        ImageActCell.setCellValueFactory(new PropertyValueFactory<>("image_activite"));
        DescriptionActCell.setCellValueFactory(new PropertyValueFactory<>("description_activite"));
        

        tableActivite.setItems(FXCollections.observableList(a));

    }
    
    private void TrieDescription() {
        ActiviteService as = new ActiviteService();
        List<Activite> a = as.triDescriptionActivite();
        NomActCell.setCellValueFactory(new PropertyValueFactory<>("nom_activite"));
        DureeActCell.setCellValueFactory(new PropertyValueFactory<>("duree_activite"));
        DiffActCell.setCellValueFactory(new PropertyValueFactory<>("difficulte_activite"));
        ImageActCell.setCellValueFactory(new PropertyValueFactory<>("image_activite"));
        DescriptionActCell.setCellValueFactory(new PropertyValueFactory<>("description_activite"));
        

        tableActivite.setItems(FXCollections.observableList(a));

    }
    
    @FXML
    private void TriChoice(ActionEvent event) throws IOException {
        if (comboBoxTriAct.getValue().equals("Nom")) {
            TrieNom();
        } else if (comboBoxTriAct.getValue().equals("Difficulte")) {
            TrieDiff();
        } else if (comboBoxTriAct.getValue().equals("Description")) {
            TrieDescription();
        }

    }
    
    
    
    @FXML
    private void supprimerActivite(ActionEvent event) throws SQLException {
        ActiviteService as = new ActiviteService();
        
        if (tableActivite.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez sélectionner l'activité à supprimer");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette activité ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de l'activité sélectionnée dans la vue de la table
            int activite_id = tableActivite.getSelectionModel().getSelectedItem().getId();

            // Supprimer l'activité de la base de données
            as.supprimer(activite_id);
            // Rafraîchir la liste de données
            data.clear();
            AfficherActivites();
            // Rafraîchir la vue de la table
            tableActivite.refresh();
        }
    }
    
    
    
    public ActiviteService as = new ActiviteService();
    
    public void searchActivite() throws SQLException {    
        FilteredList<Activite> filteredData = new FilteredList<>(FXCollections.observableArrayList(as.Show()), p -> true);
        txtSearchAct.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(act -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String nom = String.valueOf(act.getNom_activite());
                String duree = String.valueOf(act.getDuree_activite());
                String diff = String.valueOf(act.getDifficulte_activite());
                String lowerCaseFilter = newValue.toLowerCase();

                if (nom.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (duree.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (diff.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Activite> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableActivite.comparatorProperty());
        tableActivite.setItems(sortedData);
    }
    
    
    

    
    
    @FXML
    void genererPDF(MouseEvent event) {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des produits
            ActiviteService as = new ActiviteService();
            List<Activite> activiteList = as.Show();

            try {
                // Créer le document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Créer une instance de l'image
                /*Image image = Image.getInstance(System.getProperty("user.dir") + "/src/images/LogoGymBlack.png");

                // Positionner l'image en haut à gauche
                image.setAbsolutePosition(5, document.getPageSize().getHeight() - 120);

                // Modifier la taille de l'image
                image.scaleAbsolute(152, 100);

                // Ajouter l'image au document
                document.add(image);*/

                // Créer une police personnalisée pour la date
                Font fontDate = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                BaseColor color = new BaseColor(114, 0, 0); // Rouge: 114, Vert: 0, Bleu: 0
                fontDate.setColor(color); // Définir la couleur de la police

                // Créer un paragraphe avec le lieu
                Paragraph tunis = new Paragraph("Ariana", fontDate);
                tunis.setIndentationLeft(455); // Définir la position horizontale
                tunis.setSpacingBefore(-30); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(tunis);

                // Obtenir la date d'aujourd'hui
                LocalDate today = LocalDate.now();

                // Créer un paragraphe avec la date
                Paragraph date = new Paragraph(today.toString(), fontDate);

                date.setIndentationLeft(437); // Définir la position horizontale
                date.setSpacingBefore(1); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(date);

                // Créer une police personnalisée
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD);
                BaseColor titleColor = new BaseColor(114, 0, 0); //
                font.setColor(titleColor);

                // Ajouter le contenu au document
                Paragraph title = new Paragraph("Liste des activites", font);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingBefore(50); // Ajouter une marge avant le titre pour l'éloigner de l'image
                title.setSpacingAfter(20);
                document.add(title);

                PdfPTable table = new PdfPTable(5); // 5 colonnes pour les 5 attributs des activités
                table.setWidthPercentage(100);
                table.setSpacingBefore(30f);
                table.setSpacingAfter(30f);

                // Ajouter les en-têtes de colonnes
                Font hrFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
                BaseColor hrColor = new BaseColor(255, 255, 255); //
                hrFont.setColor(hrColor);

                PdfPCell cell1 = new PdfPCell(new Paragraph("ID", hrFont));
                BaseColor bgColor = new BaseColor(114, 0, 0);
                cell1.setBackgroundColor(bgColor);
                cell1.setBorderColor(titleColor);
                cell1.setPaddingTop(20);
                cell1.setPaddingBottom(20);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell2 = new PdfPCell(new Paragraph("Nom", hrFont));
                cell2.setBackgroundColor(bgColor);
                cell2.setBorderColor(titleColor);
                cell2.setPaddingTop(20);
                cell2.setPaddingBottom(20);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell3 = new PdfPCell(new Paragraph("Duree", hrFont));
                cell3.setBackgroundColor(bgColor);
                cell3.setBorderColor(titleColor);
                cell3.setPaddingTop(20);
                cell3.setPaddingBottom(20);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell4 = new PdfPCell(new Paragraph("Difficulté", hrFont));
                cell4.setBackgroundColor(bgColor);
                cell4.setBorderColor(titleColor);
                cell4.setPaddingTop(20);
                cell4.setPaddingBottom(20);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);

                Font hdFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
                BaseColor hdColor = new BaseColor(255, 255, 255); //
                hrFont.setColor(hdColor);
                // Ajouter les données des produits
                for (Activite act : activiteList) {
                    PdfPCell cellR1 = new PdfPCell(new Paragraph(String.valueOf(act.getId()), hdFont));
                    cellR1.setBorderColor(titleColor);
                    cellR1.setPaddingTop(10);
                    cellR1.setPaddingBottom(10);
                    cellR1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR1);

                    PdfPCell cellR2 = new PdfPCell(new Paragraph(act.getNom_activite(), hdFont));
                    cellR2.setBorderColor(titleColor);
                    cellR2.setPaddingTop(10);
                    cellR2.setPaddingBottom(10);
                    cellR2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR2);

                    PdfPCell cellR3 = new PdfPCell(new Paragraph(String.valueOf(act.getDuree_activite()), hdFont));
                    cellR3.setBorderColor(titleColor);
                    cellR3.setPaddingTop(10);
                    cellR3.setPaddingBottom(10);
                    cellR3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR3);


                    PdfPCell cellR5 = new PdfPCell(
                            new Paragraph(String.valueOf(act.getDifficulte_activite()), hdFont));
                    cellR5.setBorderColor(titleColor);
                    cellR5.setPaddingTop(10);
                    cellR5.setPaddingBottom(10);
                    cellR5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR5);

                }
                table.setSpacingBefore(20);
                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    
    }
    
}
