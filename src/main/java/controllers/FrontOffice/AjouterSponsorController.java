package controllers.FrontOffice;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Sponsor;
import services.ServiceSponsor;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterSponsorController {
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private TextField tfNom;
    @javafx.fxml.FXML
    private TextField tfDonnation;
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Button BtnInsererImage;

    private ServiceSponsor serviceSponsor = new ServiceSponsor();
    private Alert a = new Alert(Alert.AlertType.NONE);

    private String ImagePath = "";

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException {
        boolean test = false ;
        // check empty fields
        if (tfNom.getText().isEmpty() || tfDonnation.getText().isEmpty() || ImagePath == null) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez remplir tous les champs");
            a.show();

        }
        // check donation is a valid double using condition with regular expression
        else if (!tfDonnation.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Donnation doit être un nombre");
            a.show();

        }


        // check nom is long enough
        else if (tfNom.getText().length() < 3) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Le nom doit contenir au moins 3 caractères");
            a.show();

        }
        // check imagePath is selected
        else if (ImagePath.isEmpty()) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setTitle("WARNING");
            a.setContentText("Veuillez selectionner une image");
            a.show();

        } else {


            Sponsor sponsor = new Sponsor();
            sponsor.setNomSponsor(tfNom.getText());
            sponsor.setDonnation(Double.parseDouble(tfDonnation.getText()));
            sponsor.setImage(ImagePath);
            List<Sponsor> ls = serviceSponsor.getAll();

            for (Sponsor s : ls)
            {
                if(s.equals(sponsor))
                {
                    test = true ;
                    break ;
                }
            }
            if (!test) {
                try {
                    this.serviceSponsor.add(sponsor);
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setTitle("Succès");
                    a.setContentText("Sponsor ajouté avec succès");
                    a.show();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ListSponsorFront.fxml"));
                    this.BtnRetour.getScene().setRoot(loader.load());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Sponsor existe ! ");
                a.show();
            }
        }

    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/FrontOfficeHome.fxml"));
        this.BtnRetour.getScene().setRoot(loader.load());

    }

    @javafx.fxml.FXML
    public void OnInsererClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/Images/"));
        fileChooser.setTitle("Select Image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            ImagePath = selectedFile.toURI().toString();
            Image image = new Image(ImagePath);
            imageView.setImage(image);
        }
    }
}
