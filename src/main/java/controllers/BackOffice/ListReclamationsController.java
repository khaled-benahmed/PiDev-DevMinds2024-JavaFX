package controllers.BackOffice;


import controllers.Router;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.Reclamation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import services.ServiceReclamation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import java.awt.Color;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class ListReclamationsController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Reclamation> ListViewReclamations;
    private ServiceReclamation serviceReclamation ;
    @javafx.fxml.FXML
    private TextField tfRecherche;
    @javafx.fxml.FXML
    private ComboBox<String> ComboBoxFilter;
    @javafx.fxml.FXML
    private Button BtnStats;

    @javafx.fxml.FXML
    public void OnMouseClicked(Event event) {
        Reclamation reclamation = ListViewReclamations.getSelectionModel().getSelectedItem();
        if (reclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ReclamationDetails.fxml")); // Assuming the path to your FXML file
                Parent root = loader.load();
                ReclamationDetailsController detailsController = loader.getController();
                detailsController.setReclamation(reclamation); // Pass the reclamation to the details controller
                Router.getInstance().showContent(root);
            } catch (IOException e) {
                e.printStackTrace(); // Handle potential errors
            }
        }
    }

    public void afficherReclamations() throws SQLException {

        ListViewReclamations.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);

                if (empty || reclamation == null) {
                    setText(null);
                } else {
                    // Format the text to display based on your Reclamation object's fields
                    setText(reclamation.getNomUser() + " - " + reclamation.getTextReclamation() + " - " + reclamation.getEtat());
                }
            }
        });

        List<Reclamation> lr = this.serviceReclamation.getAll(); // Call getAll() here
        ListViewReclamations.getItems().clear(); // Clear existing items before adding new ones
        for (Reclamation r : lr) {
            this.ListViewReclamations.getItems().add(r);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboBoxFilter.getItems().addAll("Tous" , "En attente", "Traitee");
        ComboBoxFilter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           updateItems();
        });
        this.tfRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            updateItems();
        });
        this.serviceReclamation = new ServiceReclamation();
        try {
            afficherReclamations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateItems() {

        List<Reclamation> allItems = null;
        String selectedEtat = ComboBoxFilter.getSelectionModel().getSelectedItem();
        String searchText = tfRecherche.getText();
        try {
            allItems = serviceReclamation.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(searchText != null && !searchText.isEmpty()) {
            // filter by nom user and text reclamation
            allItems = allItems.stream().filter(item -> item.getNomUser().contains(searchText) || item.getTextReclamation().contains(searchText)).collect(Collectors.toList());
        }
        if(selectedEtat == null || selectedEtat.equals("Tous")) {
            ListViewReclamations.getItems().setAll(allItems);
            return;
        }
        List<Reclamation> filteredItems = filterItems(selectedEtat, allItems);
        ListViewReclamations.getItems().setAll(filteredItems);
    }

    private List<Reclamation> filterItems(String filter, List<Reclamation> items) {
        if (filter == null || filter.isEmpty()) {
            return items;
        }
        return items.stream().filter(item -> item.getEtat().equals(filter)).collect(Collectors.toList());
    }

    @javafx.fxml.FXML
    public void OnRechercheTextChanged(Event event) {
    }

    public void setFilter(String filter) {
        ComboBoxFilter.getSelectionModel().select(filter);
    }


    public ChartPanel createChart(List<Reclamation> reclamations) {
        // Group reclamations by date (assuming 'date' field is a java.util.Date)
        Map<Date, Integer> dailyReclamations = new HashMap<>();
        for (Reclamation reclamation : reclamations) {
            // converte LocalDate to Date
            LocalDate ld = reclamation.getDateReclamation();
            java.util.Date date = java.sql.Date.valueOf(ld);
            dailyReclamations.put(date, dailyReclamations.getOrDefault(date, 0) + 1);
        }

        // Create a time series for daily reclamations
        TimeSeries series = new TimeSeries("Reclamations");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Map.Entry<java.util.Date, Integer> entry : dailyReclamations.entrySet()) {
            series.add(new org.jfree.data.time.Day(entry.getKey()), entry.getValue());
        }

        // Create a dataset and chart
        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Total Reclamations per Day",
                "Date", "Reclamations", dataset,
                true, true, false);

        // Customize chart appearance (optional)
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(dateFormat);

        // Return the chart panel to be added to your JavaFX application
        return new ChartPanel(chart);
    }

    @javafx.fxml.FXML
    public void ShowStats(ActionEvent actionEvent) {

    }


}
