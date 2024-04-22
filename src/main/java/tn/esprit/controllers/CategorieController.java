
    package tn.esprit.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.Categorie;
import tn.esprit.services.ServiceCategorie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

    public class CategorieController {
        @FXML
        private GridPane userContainer;
        @FXML
        public TextField nomtf;
        @FXML
        private TextField usersearch;
        @FXML
        public TextField desctf;
        @FXML
        private Label uinfolabel;
        private final ServiceCategorie CategorieS = new ServiceCategorie();
        private Connection cnx;

        public void initialize(URL url, ResourceBundle rb) {
            load();
        }

        @FXML
        void AjouterCat(ActionEvent event) throws SQLException {
            String NOM = nomtf.getText();
            String DESCRIPTION = desctf.getText();





            CategorieS.Add(new Categorie(NOM, DESCRIPTION));
            uinfolabel.setText("Ajout Effectue");




        }
        @FXML
        void Deconnection(ActionEvent event) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Close the stage (which effectively closes the scene)
            stage.close();
        }
        @FXML
        void ModifierCat(ActionEvent event) {

            String NOM = nomtf.getText();
            String DESCRIPTION = desctf.getText();



            CategorieS.Update(new Categorie(NOM,DESCRIPTION));
            uinfolabel.setText("Modification Effectue");
        }
        public void load() {
            int column = 0;
            int row = 1;
            try {
                for (Categorie categorie : CategorieS.afficher()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                    Pane userBox = fxmlLoader.load();
                    CardUserController cardC = fxmlLoader.getController();
                    cardC.setData(categorie);
                    if (column == 3) {
                        column = 0;
                        ++row;
                    }
                    userContainer.add(userBox, column++, row);
                    GridPane.setMargin(userBox, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @FXML
        void RechercheNom(ActionEvent event) {
            int column = 0;
            int row = 1;
            String recherche = usersearch.getText();
            try {
                userContainer.getChildren().clear();
                for (Categorie categorie : CategorieS.Rechreche(recherche)){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                    Pane userBox = fxmlLoader.load();
                    CardUserController cardC = fxmlLoader.getController();
                    cardC.setData(categorie);
                    if (column == 3) {
                        column = 0;
                        ++row;
                    }
                    userContainer.add(userBox, column++, row);
                    GridPane.setMargin(userBox, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @FXML
        void refresh(ActionEvent event) {
            load();
        }
        @FXML
        void TriNom(ActionEvent event) {
            int column = 0;
            int row = 1;
            try {
                for (Categorie categorie : CategorieS.TriparNom()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                    Pane userBox = fxmlLoader.load();
                    CardUserController cardC = fxmlLoader.getController();
                    cardC.setData(categorie);
                    if (column == 3) {
                        column = 0;
                        ++row;
                    }
                    userContainer.add(userBox, column++, row);
                    GridPane.setMargin(userBox, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @FXML
        void extract(ActionEvent event) {
            try {
                generatePDF();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        private void generatePDF() throws FileNotFoundException {
            // Get the path to the Downloads directory
            String downloadsDir = System.getProperty("user.home") + "/Downloads/";

            // Create a PDF file in the Downloads directory
            File file = new File(downloadsDir + "categories.pdf");
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);

            // Create a document
            Document document = new Document(pdf);

            // Add content to the document
            for (Categorie categorie : CategorieS.afficher()) {
                document.add(new Paragraph("Nom:       " + categorie.getNom_categorie()));
                document.add(new Paragraph("description:    " + categorie.getdescription_c()));
                document.add(new Paragraph("\n")); // Add a blank line between users
            }
            // Close the document
            document.close();

            System.out.println("PDF file generated successfully at: " + file.getAbsolutePath());
        }
    }







