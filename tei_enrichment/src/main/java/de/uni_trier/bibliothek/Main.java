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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.uni_trier.bibliothek.xml.tei.TEIMarshaller;
import de.uni_trier.bibliothek.xml.tei.TEIUnmarshaller;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;

/**
 * Hello world!
 */
public class Main extends Application {
    private static Scene scene;
    private static EnrichmentController eController;

    public static void main(String[] args) throws Exception {
        // System.out.println("Hello World!");

        // String examplePath = "/home/ackels/Dokumente/test_enrichment/tei_enrichment/tei_enrichment/src/main/resources/originTEI/backup/merian_all_elements_example_after_original_3.xml";
        
        
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
