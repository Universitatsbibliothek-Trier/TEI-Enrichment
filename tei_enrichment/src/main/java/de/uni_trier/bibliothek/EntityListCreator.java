// @author       René Ackels
// Copyright (c) 2023 Universität Trier

// This file is part of OCR-To-TEI.

// OCR-To-TEI is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// OCR-To-TEI is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package de.uni_trier.bibliothek;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.uni_trier.bibliothek.xml.events.EventsMarshaller;
import de.uni_trier.bibliothek.xml.listBibl.ListBiblMarshaller;
import de.uni_trier.bibliothek.xml.objects.ObjectsMarshaller;
import de.uni_trier.bibliothek.xml.orgs.OrgsMarshaller;
import de.uni_trier.bibliothek.xml.persons.PersonsMarshaller;
import de.uni_trier.bibliothek.xml.places.PlacesMarshaller;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import jakarta.xml.bind.JAXBException;

public class EntityListCreator {


	public static void createEnrichedEntityLists(String teiPathName, List<Object> teiFiles) throws JAXBException, IOException 
	{
		de.uni_trier.bibliothek.xml.events.model.generated.TEI eventsTEI = (de.uni_trier.bibliothek.xml.events.model.generated.TEI) teiFiles.get(1);
        String eventsOutputPathString = teiPathName + "merian_entities_events_enriched.xml";
        String eventsXmlString = EventsMarshaller.marshall(eventsTEI);
        String eventsXmlStringSchema = InsertSchema.insertSchema(eventsXmlString);
        Path eventsWithPath = Paths.get(eventsOutputPathString);
        Files.writeString(eventsWithPath, eventsXmlStringSchema, StandardCharsets.UTF_8);
        System.out.println("events-liste erzeugt");

        de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI listBiblTEI = (de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI) teiFiles.get(2);
        String listBiblOutputPathString = teiPathName + "merian_entities_listBibl_enriched.xml";
        String listBiblXmlString = ListBiblMarshaller.marshall(listBiblTEI);
        String listBiblXmlStringSchema = InsertSchema.insertSchema(listBiblXmlString);
        Path listBiblWithPath = Paths.get(listBiblOutputPathString);
        Files.writeString(listBiblWithPath, listBiblXmlStringSchema, StandardCharsets.UTF_8);
        System.out.println("listBibl-liste erzeugt");

        de.uni_trier.bibliothek.xml.objects.model.generated.TEI objectsTEI = (de.uni_trier.bibliothek.xml.objects.model.generated.TEI) teiFiles.get(3);
        String objectsOutputPathString = teiPathName + "merian_entities_objects_enriched.xml";
        String objectsXmlString = ObjectsMarshaller.marshall(objectsTEI);
        String objectsXmlStringSchema = InsertSchema.insertSchema(objectsXmlString);
        Path objectsWithPath = Paths.get(objectsOutputPathString);
        Files.writeString(objectsWithPath, objectsXmlStringSchema, StandardCharsets.UTF_8);
        System.out.println("objects-liste erzeugt");

        de.uni_trier.bibliothek.xml.orgs.model.generated.TEI orgsTEI = (de.uni_trier.bibliothek.xml.orgs.model.generated.TEI) teiFiles.get(4);
        String orgsOutputPathString = teiPathName + "merian_entities_orgs_enriched.xml";
        String orgsXmlString = OrgsMarshaller.marshall(orgsTEI);
        String orgsXmlStringSchema = InsertSchema.insertSchema(orgsXmlString);
        Path orgsWithPath = Paths.get(orgsOutputPathString);
        Files.writeString(orgsWithPath, orgsXmlStringSchema, StandardCharsets.UTF_8);
        System.out.println("orgs-liste erzeugt");

        de.uni_trier.bibliothek.xml.persons.model.generated.TEI personsTEI = (de.uni_trier.bibliothek.xml.persons.model.generated.TEI) teiFiles.get(5);
        String personsOutputPathString = teiPathName + "merian_entities_persons_enriched.xml";
        String personsXmlString = PersonsMarshaller.marshall(personsTEI);
        String personsXmlStringSchema = InsertSchema.insertSchema(personsXmlString);
        Path personsWithPath = Paths.get(personsOutputPathString);
        Files.writeString(personsWithPath, personsXmlStringSchema, StandardCharsets.UTF_8);
        System.out.println("persons-liste erzeugt");

        de.uni_trier.bibliothek.xml.places.model.generated.TEI placesTEI = (de.uni_trier.bibliothek.xml.places.model.generated.TEI) teiFiles.get(6);
        String placesOutputPathString = teiPathName + "merian_entities_places_enriched.xml";
        String placesXmlString = PlacesMarshaller.marshall(placesTEI);
        String placesXmlStringSchema = InsertSchema.insertSchema(placesXmlString);
        Path placesWithPath = Paths.get(placesOutputPathString);
        Files.writeString(placesWithPath, placesXmlStringSchema, StandardCharsets.UTF_8);
        System.out.println("places-liste erzeugt");
    

	}

}
