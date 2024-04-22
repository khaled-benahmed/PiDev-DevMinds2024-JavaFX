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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.Abonnement;
import tn.esprit.models.Categorie;
import tn.esprit.services.ServiceAbonnement;
import tn.esprit.services.ServiceCategorie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AbonnementController {
    @FXML
    private GridPane userContainer;
    @FXML
    public TextField nomtf;
    @FXML
    private TextField prixtf;
    @FXML
    private TextField numabonntf;
    @FXML
    private DatePicker datedebtf;
    @FXML
    private DatePicker datefintf;
    @FXML
    private TextField usersearch;
    @FXML
    public TextField desctf;
    @FXML
    private Label uinfolabel;
    private final ServiceAbonnement AbonnementS = new ServiceAbonnement();
    private Connection cnx;

    public void initialize(URL url, ResourceBundle rb) {
        load();
    }
    @FXML
    void AjouterAbb(ActionEvent event) throws SQLException {
        String NOM = nomtf.getText();
        String DESCRIPTION = desctf.getText();
        int PRIX = Integer.parseInt(prixtf.getText());
        Date DATE = Date.valueOf(datedebtf.getValue());
        Date DATEE = Date.valueOf(datefintf.getValue());
        int NUM = Integer.parseInt(numabonntf.getText());



        AbonnementS.Add(new Abonnement(NOM,DESCRIPTION,PRIX,DATE,DATEE,NUM));
        uinfolabel.setText("Ajout Effectue");




    }
    @FXML
    void Deconnection(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage (which effectively closes the scene)
        stage.close();
    }
    @FXML
    void ModifierAbb(ActionEvent event) {

        String NOM = nomtf.getText();
        String DESCRIPTION = desctf.getText();
        int PRIX = Integer.parseInt(prixtf.getText());
        Date DATE = Date.valueOf(datedebtf.getValue());
        Date DATEE = Date.valueOf(datefintf.getValue());
        int NUM = Integer.parseInt(numabonntf.getText());



        AbonnementS.Update(new Abonnement(NOM,DESCRIPTION,PRIX,DATE,DATEE,NUM));
        uinfolabel.setText("Modification Effectue");
    }
    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Abonnement abonnement : AbonnementS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserrController cardC = fxmlLoader.getController();
                cardC.setData(abonnement);
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
            for (Abonnement abonnement : AbonnementS.Rechreche(recherche)){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserrController cardC = fxmlLoader.getController();
                cardC.setData(abonnement);
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
            for (Abonnement abonnement : AbonnementS.TriparNom()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserrController cardC = fxmlLoader.getController();
                cardC.setData(abonnement);
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
        File file = new File(downloadsDir + "abonnements.pdf");
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);

        // Create a document
        Document document = new Document(pdf);

        // Add content to the document
        for (Abonnement abonnement : AbonnementS.afficher()) {
            document.add(new Paragraph("Nom:       " + abonnement.getNom_a()));
            document.add(new Paragraph("description:    " + abonnement.getDescription_a()));
            document.add(new Paragraph("prix:       " + abonnement.getPrix_a()));
            document.add(new Paragraph("date deb:    " + abonnement.getDate_debut_a()));
            document.add(new Paragraph("date fin:       " + abonnement.getDate_fin_a()));
            document.add(new Paragraph("num abonnement:    " + abonnement.getCategorie_abonnement()));
            document.add(new Paragraph("\n")); // Add a blank line between users
        }
        // Close the document
        document.close();

        System.out.println("PDF file generated successfully at: " + file.getAbsolutePath());
    }

}
