package controllers.FrontOffice;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Reclamation;
import models.Reponse;
import services.ServiceReclamation;
import services.ServiceReponse;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ListReclamationFrontController implements Initializable {
    @javafx.fxml.FXML
    private Label labelTiltle;
    @javafx.fxml.FXML
    private TextField tf_nom;
    @javafx.fxml.FXML
    private Label labelTiltle1;
    @javafx.fxml.FXML
    private TextArea tf_contenu;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    @javafx.fxml.FXML
    private ScrollPane scrollPane;
    @javafx.fxml.FXML
    private HBox HboxModif;
    @javafx.fxml.FXML
    private Button BtbModifier;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private ListView<Reponse> ListViewReponses;

    private ServiceReponse serviceReponse = new ServiceReponse();
    @javafx.fxml.FXML
    private Button BtnAjouter;

    private Reclamation recModif ;


    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException, IOException, InterruptedException {
        Alert a = new Alert(Alert.AlertType.WARNING);
        if (tf_nom.getText().isEmpty() || tf_contenu.getText().isEmpty()) {
            a.setContentText("Veuillez remplir tous les champs");
            a.show();
        } else if (tf_nom.getText().length() < 3 )
        {
            a.setContentText("le nom est trop court !");
            a.show();
        }
        else if (tf_contenu.getText().length() < 10 )
        {
            a.setContentText("le contenu est trop court :");
            a.show();
        }

        else {
            String result = CheckProfanity(tf_contenu.getText());
            Gson gson = new Gson();
            Map<String, Object> data = gson.fromJson(result, Map.class);
            String censoredContent = (String) data.get("censored-content");
            boolean isBad = (boolean) data.get("is-bad");
            List<String> badWordsList = (List<String>) data.get("bad-words-list");
            Double badWordsTotal = (Double) data.get("bad-words-total");
            System.out.println("Censored content: " + censoredContent);
            System.out.println("Is bad: " + isBad);
            System.out.println("Bad words list: " + badWordsList);
            System.out.println("Bad words total: " + badWordsTotal);
            Reclamation r = new Reclamation(tf_nom.getText(), tf_contenu.getText(), LocalDate.now(),1);
            boolean exist = false ;
            List<Reclamation> lr = this.serviceReclamation.getAll();
            for(Reclamation re : lr) {
                if (re.equals(r)) {
                    exist = true;
                }
            }
            if (isBad)
            {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("le contenu contient " + badWordsTotal + " mots inappropriés !");
                a.show();
                return;
            }
            if (!exist) {
                try {
                    serviceReclamation.add(r);
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setContentText("Reclamation ajoutée avec succès");
                    a.show();
                    afficherReclamations();
                    tf_nom.clear();
                    tf_contenu.clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("une reclamation identique existe deja !");
                a.show();
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherReclamations();
        //tf_nom.setDisable(false);
    }

    private void afficherReclamations()
    {
        scrollPane.setContent(null);
        List<Reclamation> lr = new ArrayList<>();
        try {
             lr = serviceReclamation.getReclamationsByIdUser(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        VBox V = new VBox();
        V.setSpacing(20);

        for (Reclamation r : lr) {
            HBox h = new HBox();
            h.setSpacing(30);
            h.setPrefWidth(500);
            h.setPrefHeight(20);
            Label l = new Label(r.getNomUser());
            l.setPrefWidth(300);
            Button b = new Button();
            b.setText("Voir reponses");
            b.setOnMouseClicked(e ->{
                ListViewReponses.setCellFactory(listView -> new ListCell<>() {
                    @Override
                    protected void updateItem(Reponse reponse, boolean empty) {
                        super.updateItem(reponse, empty);

                        if (empty || reponse == null) {
                            setText(null);
                        } else {
                            // Format the text to display based on your Reclamation object's fields
                            setText(reponse.getContenuResponse());
                        }
                    }
                });

                List<Reponse> lrep = null; // Call getAll() here
                try {
                    lrep = this.serviceReponse.getReponsesByIdRec(r.getId());
                    ListViewReponses.getItems().clear(); // Clear existing items before adding new ones
                    if (lrep.size() != 0) {
                        for (Reponse rep : lrep) {
                            this.ListViewReponses.getItems().add(rep);
                        }
                    }
                    else {
                        ListViewReponses.getItems().add(new Reponse( 0 , "Pas de reponse pour cette reclamation !" , LocalDate.now()));
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            });
            l.setOnMouseClicked(e -> {
                this.recModif = r;
                tf_nom.setText(r.getNomUser());
                tf_contenu.setText(r.getTextReclamation());
                HboxModif.setVisible(true);
                BtnAjouter.setVisible(false);
            });
            h.getChildren().addAll(l,b);
            V.getChildren().add(h);
        }
        scrollPane.setContent(V);
    }


    @javafx.fxml.FXML
    public void BtnModifierAction(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        if (tf_nom.getText().isEmpty() || tf_contenu.getText().isEmpty()) {
            a.setContentText("Veuillez remplir tous les champs");
            a.show();
        }
        else if (tf_contenu.getText().length() < 10 )
        {
            a.setContentText("le contenu est trop court :");
            a.show();
        }

        else {


         a.setAlertType(Alert.AlertType.CONFIRMATION);
         a.setContentText("voulez vous vraiment modifier la reclamation dont l'id est : " + recModif.getId());
            Optional<ButtonType> result = a.showAndWait();

            if (result.get() == ButtonType.OK) {
                try {
                    recModif.setTextReclamation(tf_contenu.getText());
                    recModif.setNomUser(tf_nom.getText());
                    serviceReclamation.update(recModif);
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setContentText("Reclamation modifié avec succès");
                    a.show();
                    afficherReclamations();
                    tf_nom.clear();
                    tf_contenu.clear();
                    HboxModif.setVisible(false);
                    BtnAjouter.setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    @javafx.fxml.FXML
    public void BtnAnuulerAction(ActionEvent actionEvent) {
        tf_nom.clear();
        tf_contenu.clear();
        HboxModif.setVisible(false);
        BtnAjouter.setVisible(true);
    }

    public String CheckProfanity(String text) throws IOException, InterruptedException {
        String encodedText = URLEncoder.encode(text, "UTF-8");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://neutrinoapi-bad-word-filter.p.rapidapi.com/bad-word-filter"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "3a411f7ffcmsh7b97d0bc1ea2475p1661f7jsnf518042e562a")
                .header("X-RapidAPI-Host", "neutrinoapi-bad-word-filter.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("content=" + encodedText + "&censor-character=*"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
