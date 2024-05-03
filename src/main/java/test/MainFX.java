package test;

import controllers.BackOffice.ListReclamationsController;
import controllers.BackOffice.ReclamationDetailsController;
import controllers.Router;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Reclamation;
import org.controlsfx.control.Notifications;
import services.ServiceReclamation;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainFX extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader;
        ServiceReclamation serviceReclamation = new ServiceReclamation();
        List<Reclamation> old = serviceReclamation.getAll();
        String role = "Admin";
       //String role = "Client";

        if (role.equals("Admin")) {
            loader = new FXMLLoader(getClass().getResource("/BackOffice/BackOfficeHome.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/FrontOffice/ListReclamations.fxml"));
        }
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setTitle("My application");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (role.equals("Admin")) {
            checkForReclamationEnattente();
            checkforNewReclamarions(primaryStage);
        }

    }
    public void checkForReclamationEnattente() throws SQLException {
        ServiceReclamation serviceReclamation = new ServiceReclamation();
        List<Reclamation> list = serviceReclamation.getAll();
        int tot = list.stream().filter(reclamation -> reclamation.getEtat().equals("En attente")).toList().size();
        if (tot != 0) {
            Notifications.create()
                    .title("Reclamation en attente")
                    .text("You have " + tot + " Reclamation en attente")
                    .onAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ListReclamations.fxml"));
                            Parent root = loader.load();
                            ListReclamationsController listReclamationsController = loader.getController();
                            listReclamationsController.setFilter("En attente");
                            Router.getInstance().showContent(root);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .show();
        }
    }

    public void checkforNewReclamarions(Stage primaryStage) throws SQLException {
        ServiceReclamation serviceReclamation = new ServiceReclamation();
        List<Reclamation> old = serviceReclamation.getAll();
        System.out.println(old);
        if (old.size() != 0) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    System.out.println("timer working");
                    List<Reclamation> news = null;
                    try {
                        news = serviceReclamation.getAll();
                        System.out.println(old);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    for (Reclamation current : news) {

                        if (!old.contains(current)) {
                            System.out.println("new reclamation");
                            Platform.runLater(() -> Notifications.create()
                                    .title("New Reclamations")
                                    .text("You have new Reclamation with id " + current.getId())
                                    .onAction(event -> {
                                        try {
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ReclamationDetails.fxml"));
                                            Parent root = loader.load();
                                            ReclamationDetailsController detailsController = loader.getController();
                                            detailsController.setReclamation(current);
                                            Router.getInstance().showContent(root);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    })
                                    .show());
                            old.add(current);
                        }
                    }


                }

            }, 0, 5000);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
