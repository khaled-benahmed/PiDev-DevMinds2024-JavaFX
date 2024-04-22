module com.example.pidevdevminds2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires org.json;
    requires itextpdf;
    requires twilio;


    opens gui to javafx.fxml;
    exports gui to javafx.graphics;

    opens entities to javafx.base;
    //opens com.example.pidevdevminds2024 to javafx.fxml;
    //exports com.example.pidevdevminds2024;
}