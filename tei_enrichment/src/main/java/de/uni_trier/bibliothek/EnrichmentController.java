package de.uni_trier.bibliothek;

import java.io.File;
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
import jakarta.xml.bind.JAXBException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EnrichmentController {

    public static File selectedFile;

    @FXML
    private TextField PathOutput;

    @FXML
    private CheckBox checkBoxCategory;

    @FXML
    private CheckBox checkBoxFederals;

    @FXML
    private CheckBox checkBoxGeo;

    @FXML
    private CheckBox checkBoxLines;
 
    @FXML
    private CheckBox checkBoxSpecials;

    @FXML
    private CheckBox checkBoxVariants;

    @FXML
    private Button enrichButton;

    @FXML
    private TextArea original_path;

    @FXML
    private Button chosenButton;

    @FXML
    public void checkBoxOne() {
        System.out.println("checkBoxCategory = " + checkBoxCategory.getId());
        // converterButton.setText("Convert_TEI");
        // checkBoxOne.getId();
    }

    @FXML
    public void changeButtonText() {
        enrichButton.setText("Convert_TEI");
        // converterButton.setText("enrich TEI");
    }

    @FXML
    public void initialize() throws IOException {
        enrichButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // enrichButton.setText("Button clicked");
                // Stage stage = new Stage();
                // stage.show();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                selectedFile = fileChooser.showOpenDialog(null);
                String selectedFileString = selectedFile.toString();
                original_path.setText(selectedFileString);
                // System.out.println("OnAction {} " + event);
            }
        });

        chosenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String TEIOriginalFilePath = (EnrichmentController.getSelectedFile()).toString();
                try (InputStream inputStream = new FileInputStream(EnrichmentController.getSelectedFile())) {
                    Reader xmlReader = new InputStreamReader(inputStream);
                    TEI teiFile = TEIUnmarshaller.unmarshal(xmlReader);
                    xmlReader.close();
                    teiFile.getVersion();
                    System.out.println("unmarshalled " + teiFile.getVersion());

                    String teiXmlString = TEIMarshaller.marshall(teiFile);
                    String teiXmlStringSchema = InsertSchema.insertSchema(teiXmlString);
                    // System.out.println(teiXmlStringSchema);

                    System.out.println(TEIOriginalFilePath);

                    int lastSlash = TEIOriginalFilePath.lastIndexOf('/');
                    String originalTEIFileName = TEIOriginalFilePath.substring(lastSlash + 1, TEIOriginalFilePath.length());

                    System.out.println("Filename from originalTEIFileName:" + originalTEIFileName);
                    originalTEIFileName = originalTEIFileName.substring(0, originalTEIFileName.length() - 4);
                    System.out.println("Filename from TEI:" + originalTEIFileName);
                    originalTEIFileName = originalTEIFileName + "_enriched.xml";
                    String teiPathName = TEIOriginalFilePath.substring(0, lastSlash + 1);
                    String outputPathStringFile = teiPathName + originalTEIFileName;

                    Path outputPath = Paths.get(outputPathStringFile);
                    Files.writeString(outputPath, teiXmlStringSchema, StandardCharsets.UTF_8);
                    PathOutput.setText(outputPathStringFile);
                } catch (JAXBException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // System.out.println("Selected File: " + getSelectedFile());
            }
        });

        // System.out.println("CheckBoxID = " + checkBoxOne.getId());
        // checkBoxOne();
        // Parent root =
        // FXMLLoader.load(getClass().getResource("/javafx_layout/gridpane_enrichment.fxml"));
        // GridPane gridPane = new GridPane();
        // gridPane.setAlignment(Pos.CENTER);
        // gridPane.setStyle("-fx-background-color: gray");
        // Scene scene = new Scene(root, 900, 575);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // System.out.println("hello again");
    }

    public static File getSelectedFile() {
        return selectedFile;
    }
    // Scene scene = this
    // Button converterButton = scene.lookup(converter_button);
}
