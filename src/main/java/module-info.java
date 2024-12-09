module com.example.vehicle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.vehicle to javafx.fxml;
    exports com.example.vehicle;
    exports com.example.vehicle.controller;
    opens com.example.vehicle.controller to javafx.fxml;

    exports com.example.vehicle.model;
    opens com.example.vehicle.model to javafx.fxml;
}
