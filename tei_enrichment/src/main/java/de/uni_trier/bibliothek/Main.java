package de.uni_trier.bibliothek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private static Scene scene;
    private static EnrichmentController eController;

    public static void main(String[] args) throws Exception {
        launch();
    }

    public void start(Stage stage) throws IOException {
        stage.setTitle("TEI Enrichment");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/javafx_layout/gridpane_enrichment.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 900, 575);
        stage.setScene(scene);
        stage.show();
        eController = fxmlLoader.getController();
    }

}
