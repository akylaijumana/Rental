package com.example.vehicle;

import com.example.vehicle.database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file and set up the scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("com/example/vehicle/hello-view.fxml")); // Adjust the FXML path
        Scene scene = new Scene(fxmlLoader.load());

        // Set the title and scene for the stage
        stage.setTitle("Vehicle Rental System");
        stage.setScene(scene);
        stage.show();

        // Connect to the database
        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
