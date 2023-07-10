package de.uni_trier.bibliothek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    private static EnrichmentController eController;

    public static void main(String[] args) {
        // System.out.println("Hello World!");
        launch();
        // eController = new EnrichmentController();
        // eController.buttonText();
        // eController.checkBoxOne();
    }

    public void start(Stage stage) throws IOException {
        stage.setTitle("TEI Enrichment");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/javafx_layout/gridpane_enrichment.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 575);
        stage.setScene(scene);
        stage.show();
        eController = fxmlLoader.getController();
        eController.checkBoxOne();
        eController.changeButtonText();

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
