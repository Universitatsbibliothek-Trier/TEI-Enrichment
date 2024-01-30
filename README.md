Copyright (c) 2024 Universität Trier\
Permission is hereby granted, free of charge, to any person obtaining
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

## Anreichern einer verschlagworteten TEI-Datei mit Zusatzinformationen in Form von Entitätslisten, Zeilennummerierung und dem Einfügen von xml:ids für Kupferstiche, Seiten und Artikel

Mit diesem Java-Programm ist es über eine grafische Benutzeroberfläche möglich eine TEI-Datei auszuwählen, die bereits mit Elementen der Form z.B. ```<name ref="https://d-nb.info/gnd/4229460-5">Schlagwort</name>``` verschlagwortet wurde. Je nach Auswahl der Optionen der grafischen Benutzeroberfläche werden daraufhin Entitätenlisten erstellt, Zeilen automatisch nummeriert und/oder `xml:id`s für Artikel, Stiche und Seiten eingetragen. 

## Ausführung

Voraussetzungen:\
Voraussetzungen für das Ausführen des Programmes sind ein installiertes Java JDK und Apache Maven.\
Nötige Angaben:\
Um das Programm ausführen zu können, muss eine TEI-konforme Datei gewählt werden. Um die Entitätslisten zu erstellen müssen sich im selben Ordner wie die gewählte TEI-Datei die `merian_entitites_*.xml`-Listen befinden.

Für Ubuntu:\
Da die JavaFX-Applikation von Maven zusammengebaut wird, sieht der Befehl zum Starten über die Bash wie folgt aus:\
`mvn clean javafx:run`\
Dieser Befehl muss vom Ordner aus gestartet werden, in dem sich die `pom.xml`-Datei von Maven befindet.
Alternativ dazu kann das Bash-Skript `startEnrichment.sh` ausgeführt werden, dass die grafische Benutzeroberfläche startet.

Für Windows:\
Zur Ausführung des Programmes kann die Datei `tei_enrichment.bat` ausgeführt werden. Dadurch wird die grafische Benutzeroberfläche gestartet.\

Die Grafische Benutzeroberfläche selbst besteht aus einem Button..
anpassen der IDs
Namen der entitätslisten können angepasst werden


dateien checken
lizenen checken

## Ordnerstruktur

**tei_enrichment**  
Enthält die `pom.xml` für Maven und eine `.bat`-Datei zum Ausführen des Programmes unter Windows.

**tei_enrichment/source/main/resources/schemes**  
Enthält Unterordner für `.xsd`-Dateien.

**tei_enrichment/source/main/resources/schemes/ListSchemes**  
Enthält `.xsd`-Dateien für XML-Dateien, die die Entitätslisten repräsentieren.

**tei_enrichment/source/main/resources/schemes/modifiedTEI**  
Enthält `.xsd`-Dateien für XML-Dateien, die eine modifizierte Version einer TEI repräsentieren.

**tei_enrichment/source/main/resources/schemes/officialTEI**  
Enthält `.xsd`-Dateien für XML-Dateien, die die offizielle TEI repräsentieren.

**tei_enrichment/source/main/resources/entityLists**  
Enthält leere und TEI-konforme Entitätslisten in Form von `.xml`-Dateien, die sich im selben Ordner wie die TEI-Datei befinden müssen. Nach der Auswahl der Option `Entitätslisten erstellen` und der anschließenden Ausführung des Programmes entstehen hier die angereichterten Entitätslisten, was durch die zusätzliche Endung `*_enriched.xml` zu erkennen ist.

**tei_enrichment/source/main/resources/javafx_layout**  
Enthält eine `.fxml`-Datei, die die grafische Benutzeroberfläche für die JavaFX Applikation vorgibt.

**tei_enrichment/source/main/java/de/uni_trier/bibliothek**  
Enthält die `Main`-Klasse des Java-Projektes, den `CountryRegionCodes`-Generator, den `EnrichmentController`, die `InsertSchema`-Klasse, den `LineCounter`, den `TEIObjectsListCreator`, den `XMLidFacsUrlSetter` und die Klassen zum Erstellen der Entitätenlisten: den `EntityListCreator`, den `EntityListEnricher` und den `EntityListWriter`. 

**tei_enrichment/source/main/java/de/uni_trier/bibliothek/xml**  
Enthält eine Klasse um XML-Dateien gegen ein Schema zu validieren, eine Klasse um aus einer XML-Datei Java-Objekte zu erzeugen und umgekehrt.

**tei_enrichment/source/main/java/de/uni_trier/bibliothek/xml/**  
Hier befinden sich sieben Unterordner mit jeweils automatisch generierten Java-Klassen, und zwei Java-Klassen um aus XML-Dateien Java-Objekte zu erzeugen und umgekehrt.

**tei_enrichment/source/main/java/de/uni_trier/bibliothek/xml/*/model/generated**  
Enthält automatisch generierte Java-Klassen.

**tei_enrichment/scripts**  
Enthält Skripte zum Generieren von Java-Klassen aus `.xsd`-Dateien. In diesem Fall von allen sechs Entitätenlisten und der TEI.
Enthält außerdem ein Skript zum Starten des Programmes unter Ubuntu.

## Dependencies

Die Dependencies werden beim Starten des Projektes von Maven automatisch geladen und sind in der `pom.xml`-Datei zu finden.
