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
import tn.esprit.models.Categorie;
import tn.esprit.services.ServiceCategorie;

import java.io.IOException;

public class CardUserController {
    @FXML
    private Label carddesc;
    @FXML
    private Label cardname;
    @FXML
    private Pane Card;
    @FXML
    private Label cardspecialite;
    private final ServiceCategorie CategorieS = new ServiceCategorie();

    int uid;
    String unom, udesc;

    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};


    public void setData(Categorie categorie) {

        cardname.setText(categorie.getNom_categorie());
        carddesc.setText(categorie.getdescription_c());



        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");


        uid = categorie.getId();
        unom = categorie.getNom_categorie();
        udesc = categorie.getdescription_c();

    }

    public void suppuser(ActionEvent actionEvent) throws IOException {
        CategorieS.DeleteByID(uid);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionCat.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        CategorieController AUC = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    public void modifuser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionCat.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            CategorieController AUC = loader.getController();

            AUC.nomtf.setText(unom);
            AUC.desctf.setText(udesc);


            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
