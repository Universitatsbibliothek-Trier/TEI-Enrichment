// @author       René Ackels
// Copyright (c) 2024 Universität Trier

// This file is part of TEI-Enrichment.

// TEI-Enrichment is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// TEI-Enrichment is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package de.uni_trier.bibliothek;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.uni_trier.bibliothek.xml.tei.TEIMarshaller;
import de.uni_trier.bibliothek.xml.tei.TEIUnmarshaller;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import jakarta.xml.bind.JAXBException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class EnrichmentController {

    public static File selectedFile;
    public static String fileName;

    @FXML
    private TextArea PathOutput;

    @FXML
    private CheckBox checkBoxXML;

    @FXML
    private CheckBox checkBoxEntities;

    @FXML
    private CheckBox checkBoxLines;

    @FXML
    private CheckBox checkBoxSpecials;

    @FXML
    private Button chosenButton;

    @FXML
    private Button enrichButton;

    @FXML
    private TextArea original_path;

    @FXML
    private Label infoText;

    @FXML
    public void initialize() throws IOException {
        enrichButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Wähle TEI-Datei aus");
                selectedFile = fileChooser.showOpenDialog(null);
                String selectedFileString = selectedFile.toString();
                original_path.setText(selectedFileString);
            }
        });

        chosenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String TEIOriginalFilePath = (EnrichmentController.getSelectedFile()).toString();
                try (InputStream inputStream = new FileInputStream(EnrichmentController.getSelectedFile())) {

                    Boolean anyCheckBoxSelected = false;
                    Reader xmlReader = new InputStreamReader(inputStream);
                    TEI teiFile = TEIUnmarshaller.unmarshal(xmlReader);

                    int lastSlash = TEIOriginalFilePath.lastIndexOf('/');
                    String originalTEIFileName = TEIOriginalFilePath.substring(lastSlash + 1,
                            TEIOriginalFilePath.length());
                    fileName = originalTEIFileName;
                    originalTEIFileName = originalTEIFileName.substring(0, originalTEIFileName.length() - 4);

                    originalTEIFileName = originalTEIFileName + "_enriched.xml";
                    String teiPathName = TEIOriginalFilePath.substring(0, lastSlash + 1);
                    String outputPathStringFile = teiPathName + originalTEIFileName;
                    
                    List<Object> TEIJavaObjectsList = TEIObjectsListCreator.createTEIJavaObects(teiPathName, teiFile);

                    if (checkBoxXML.isSelected()) {           
                        teiFile = XMLidFacsUrlSetter.setIDs(teiFile);
                        anyCheckBoxSelected = true;
                        infoText.setText("IDs für pb- und figure-Elemente eingetragen als \"merian_****_enriched.xml\"");
                        TEIJavaObjectsList.set(0, teiFile);
                    }

                    if (checkBoxLines.isSelected()) {           
                        teiFile = LineCounter.countLines(teiFile);
                        anyCheckBoxSelected = true;
                        infoText.setText("Zeilen gezählt und gespeichert als \"merian_****_enriched.xml\"");
                        TEIJavaObjectsList.set(0, teiFile);
                    }

                    if (checkBoxEntities.isSelected()) {
                        TEIJavaObjectsList = EntityListEnricher.enrichList(TEIJavaObjectsList, fileName);
                        teiFile = (TEI) TEIJavaObjectsList.get(0);
                        EntityListCreator.createEnrichedEntityLists(teiPathName, TEIJavaObjectsList);
                        anyCheckBoxSelected = true;
                        infoText.setText("Angereicherte Entitätslisten gespeichert als \"merian_****_enriched.xml\"");
                    }

                    // write tei
                    String teiXmlString = TEIMarshaller.marshall(teiFile);
                    String teiXmlStringSchema = InsertSchema.insertSchema(teiXmlString);
                    Path outputPath = Paths.get(outputPathStringFile);
                    Files.writeString(outputPath, teiXmlStringSchema, StandardCharsets.UTF_8);
                    PathOutput.setText(outputPathStringFile);
                    if(!anyCheckBoxSelected)
                    {
                        infoText.setText("Nichts getan, bitte wähle eine Datei, eine Option und klicke auf \"Ausführen\"");
                    }                    
                    xmlReader.close();

                } catch (JAXBException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public static File getSelectedFile() {
        return selectedFile;
    }

}
