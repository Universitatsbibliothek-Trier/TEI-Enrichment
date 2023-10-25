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
import java.util.ArrayList;
import java.util.List;

import de.uni_trier.bibliothek.xml.Marshaller;
import de.uni_trier.bibliothek.xml.events.EventsMarshaller;
import de.uni_trier.bibliothek.xml.events.EventsUnmarshaller;
import de.uni_trier.bibliothek.xml.listBibl.ListBiblUnmarshaller;
import de.uni_trier.bibliothek.xml.objects.ObjectsUnmarshaller;
import de.uni_trier.bibliothek.xml.orgs.OrgsUnmarshaller;
import de.uni_trier.bibliothek.xml.persons.PersonsUnmarshaller;
import de.uni_trier.bibliothek.xml.places.PlacesUnmarshaller;
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
    private TextArea PathOutput;

    @FXML
    private CheckBox checkBoxDOIs;

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


                    

                    int lastSlash = TEIOriginalFilePath.lastIndexOf('/');
                    String originalTEIFileName = TEIOriginalFilePath.substring(lastSlash + 1,
                            TEIOriginalFilePath.length());

                    System.out.println("Filename from originalTEIFileName:" + originalTEIFileName);
                    originalTEIFileName = originalTEIFileName.substring(0, originalTEIFileName.length() - 4);
                    System.out.println("Filename from TEI:" + originalTEIFileName);
                    originalTEIFileName = originalTEIFileName + "_enriched.xml";
                    String teiPathName = TEIOriginalFilePath.substring(0, lastSlash + 1);
                    String outputPathStringFile = teiPathName + originalTEIFileName;

                    

                    List<Object> entityList = listUnmarshaller(teiPathName, teiFile);

                    if (checkBoxEntities.isSelected()) {
                        System.out.println("checkbox selected!");
                        entityList = EntityListEnricher.enrichList(entityList);
                    }
                    //verwurschteln, dass tei erzeugte objekt  von entitylistenricher beachtet wird
                    String teiXmlString = TEIMarshaller.marshall(teiFile);
                    String teiXmlStringSchema = InsertSchema.insertSchema(teiXmlString);

                    // write tei
                    Path outputPath = Paths.get(outputPathStringFile);
                    Files.writeString(outputPath, teiXmlStringSchema, StandardCharsets.UTF_8);
                    PathOutput.setText(outputPathStringFile);
                    // InputStream inputStream = new FileInputStream
                    // TEI teiFile = TEIUnmarshaller.unmarshal(xmlReader);
                    // String teiXmlString = EventsMarshaller.marshall(teiFile);

                    // Marshaller<de.uni_trier.bibliothek.xml.events.model.generated.TEI>
                    // eventsMarshaller = new
                    // Marshaller<>(de.uni_trier.bibliothek.xml.events.model.generated.TEI.class);

                    listCreateFiles(teiPathName, entityList);


                    xmlReader.close();

                    

                    // do stuff with TEI

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

    public List<Object> listUnmarshaller(String teiPathName, TEI TEIFile) throws FileNotFoundException, JAXBException {
        List<Object> entityList = new ArrayList<Object>();
        entityList.add(TEIFile);
        String eventsWithPathString = teiPathName + "merian_entities_events.xml";
        InputStream inputStreamEvents = new FileInputStream(eventsWithPathString);
        Reader eventsXMLReader = new InputStreamReader(inputStreamEvents);
        de.uni_trier.bibliothek.xml.events.model.generated.TEI teiEventsFile = EventsUnmarshaller
                .unmarshal(eventsXMLReader);
        entityList.add(teiEventsFile);

        String listBiblWithPathString = teiPathName + "merian_entities_listBibl.xml";
        InputStream inputStreamListBibl = new FileInputStream(listBiblWithPathString);
        Reader listBiblXMLReader = new InputStreamReader(inputStreamListBibl);
        de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI teiListBiblFile = ListBiblUnmarshaller
                .unmarshal(listBiblXMLReader);
        entityList.add(teiListBiblFile);

        String objectsWithPathString = teiPathName + "merian_entities_objects.xml";
        InputStream inputStreamObjects = new FileInputStream(objectsWithPathString);
        Reader objectsXMLReader = new InputStreamReader(inputStreamObjects);
        de.uni_trier.bibliothek.xml.objects.model.generated.TEI teiObjectsFile = ObjectsUnmarshaller
                .unmarshal(objectsXMLReader);
        entityList.add(teiObjectsFile);

        String orgsWithPathString = teiPathName + "merian_orgs_events.xml";
        InputStream inputStreamOrgs = new FileInputStream(orgsWithPathString);
        Reader orgsXMLReader = new InputStreamReader(inputStreamOrgs);
        de.uni_trier.bibliothek.xml.orgs.model.generated.TEI teiOrgsFile = OrgsUnmarshaller
                .unmarshal(orgsXMLReader);
        entityList.add(teiOrgsFile);

        String personsWithPathString = teiPathName + "merian_entities_persons.xml";
        InputStream inputStreamPersons = new FileInputStream(personsWithPathString);
        Reader personsXMLReader = new InputStreamReader(inputStreamPersons);
        de.uni_trier.bibliothek.xml.persons.model.generated.TEI teiPersonsFile = PersonsUnmarshaller
                .unmarshal(personsXMLReader);
        entityList.add(teiPersonsFile);

        String placesWithPathString = teiPathName + "merian_entities_events.xml";
        InputStream inputStreamPlaces = new FileInputStream(placesWithPathString);
        Reader placesXMLReader = new InputStreamReader(inputStreamPlaces);
        de.uni_trier.bibliothek.xml.places.model.generated.TEI teiPlacesFile = PlacesUnmarshaller
                .unmarshal(placesXMLReader);
        entityList.add(teiPlacesFile);

        return entityList;
    }

    public void listCreateFiles(String teiPathName, List<Object> teiFiles) throws JAXBException, IOException {
        // String eventsWithPathString = teiPathName + "merian_entities_events.xml";
        // InputStream inputStreamEvents = new FileInputStream(eventsWithPathString);
        // Reader eventsXMLReader = new InputStreamReader(inputStreamEvents);
        // de.uni_trier.bibliothek.xml.events.model.generated.TEI teiEventsFile = EventsUnmarshaller
                // .unmarshal(eventsXMLReader);
        // List<Object> entityList = new ArrayList<Object>();
        de.uni_trier.bibliothek.xml.events.model.generated.TEI eventsTEI = (de.uni_trier.bibliothek.xml.events.model.generated.TEI) teiFiles.get(1);
        String eventsOutputPathString = teiPathName + "merian_entities_events_enriched.xml";

        String eventsXmlString = EventsMarshaller.marshall(eventsTEI);
        String eventsXmlStringSchema = InsertSchema.insertSchema(eventsXmlString);
        Path eventsWithPath = Paths.get(eventsOutputPathString);
        Files.writeString(eventsWithPath, eventsXmlStringSchema, StandardCharsets.UTF_8);
        System.out.println("events-liste erzeugt");
    }
    // Sc
    // Scene scene = this
    // Button converterButton = scene.lookup(converter_button);
}
