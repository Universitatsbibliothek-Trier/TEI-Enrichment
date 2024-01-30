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
