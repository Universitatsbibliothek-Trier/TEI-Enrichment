package de.uni_trier.bibliothek;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnrichmentController {

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
    private TextField pathOriginal;

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
    public void initialize() throws IOException
    {
        enrichButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("OnAction {}  " + event);
        }
    });

        // System.out.println("CheckBoxID = " + checkBoxOne.getId());
        // checkBoxOne();
        // Parent root = FXMLLoader.load(getClass().getResource("/javafx_layout/gridpane_enrichment.fxml"));
        // GridPane gridPane = new GridPane();
        // gridPane.setAlignment(Pos.CENTER);
        // gridPane.setStyle("-fx-background-color: gray");
        // Scene scene = new Scene(root, 900, 575);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // System.out.println("hello again");
    }
    // Scene scene = this
    // Button converterButton = scene.lookup(converter_button);
}
