package de.uni_trier.bibliothek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.Color;
import java.io.IOException;

/**
 * Hello world!
 */
public class Main extends Application {
    private static Scene scene;

    public static void main(String[] args) {
        // System.out.println("Hello World!");
        launch();
    }

    public void start(Stage stage) throws IOException {
        stage.setTitle("Oh hi Mark");

        // Parent root = FXMLLoader.load(getClass().getResource("/main_window.fxml"));
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: gray");

        Scene scene = new Scene(gridPane, 900, 575);

        // scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    // static void setRoot(String fxml) throws IOException {
    // scene.setRoot(loadFXML(fxml));
    // }

    // private static Parent loadFXML(String fxml) throws IOException {
    // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml +
    // ".fxml"));
    // return fxmlLoader.load();
    // }
}
