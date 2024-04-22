package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Abonnement;
import tn.esprit.services.ServiceAbonnement;

import java.io.IOException;
import java.time.LocalDate;

public class CardUserrController {
    @FXML
    private Label cardprix;
    @FXML
    private Label carddesc;
    @FXML
    private Label cardname;
    @FXML
    private Label carddatedeb;
    @FXML
    private Pane Card;
    @FXML
    private Label carddatefin;
    private final ServiceAbonnement AbonnementS = new ServiceAbonnement();

    int uid;

    String unom, udesc;
    double uprix;
    LocalDate udatedeb, udatefin;

    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};


    public void setData(Abonnement abonnement) {

        cardname.setText(abonnement.getNom_a());
        carddesc.setText(abonnement.getDescription_a());
        cardprix.setText(String.valueOf(abonnement.getPrix_a()));
        carddatedeb.setText(abonnement.getDate_debut_a().toString());
        carddatefin.setText(abonnement.getDate_fin_a().toString());



        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");


        uid = abonnement.getId();
        unom = abonnement.getNom_a();
        udesc = abonnement.getDescription_a();
        uprix = abonnement.getPrix_a();
        udatedeb = abonnement.getDate_debut_a().toLocalDate(); // Assuming getDate_debut_a() returns a java.util.Date
        udatefin = abonnement.getDate_fin_a().toLocalDate(); // Assuming getDate_fin_a() returns a java.util.Date
    }
    public void modifuser(ActionEvent actionEvent) {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAbon.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            AbonnementController AUC = loader.getController();





            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void suppuser(ActionEvent actionEvent) throws IOException {
        AbonnementS.DeleteByID(uid);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAbon.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        AbonnementController AUC = loader.getController();
        stage.setScene(scene);
        stage.show();
    }
}
