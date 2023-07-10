package de.uni_trier.bibliothek;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class EnrichmentController {

    @FXML
    private Button converterButton;

    @FXML
    private CheckBox checkBoxOne;

    @FXML
    public void checkBoxOne() {
        System.out.println("CheckBoxID = " + checkBoxOne.getId());
        // converterButton.setText("Convert_TEI");
        // checkBoxOne.getId();
    }


    @FXML
    public void changeButtonText() {
        converterButton.setText("Convert_TEI");
        // converterButton.setText("enrich TEI");
    }

    @FXML
    public void initialize() throws IOException
    {
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
