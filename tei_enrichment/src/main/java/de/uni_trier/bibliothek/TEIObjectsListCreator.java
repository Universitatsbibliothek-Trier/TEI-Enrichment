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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import de.uni_trier.bibliothek.xml.events.EventsUnmarshaller;
import de.uni_trier.bibliothek.xml.listBibl.ListBiblUnmarshaller;
import de.uni_trier.bibliothek.xml.objects.ObjectsUnmarshaller;
import de.uni_trier.bibliothek.xml.orgs.OrgsUnmarshaller;
import de.uni_trier.bibliothek.xml.persons.PersonsUnmarshaller;
import de.uni_trier.bibliothek.xml.places.PlacesUnmarshaller;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import jakarta.xml.bind.JAXBException;

public class TEIObjectsListCreator {


	public static List<Object> createTEIJavaObects(String teiPathName, TEI TEIFile) throws FileNotFoundException, JAXBException
	{
		
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

        String orgsWithPathString = teiPathName + "merian_entities_orgs.xml";
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

        String placesWithPathString = teiPathName + "merian_entities_places.xml";
        InputStream inputStreamPlaces = new FileInputStream(placesWithPathString);
        Reader placesXMLReader = new InputStreamReader(inputStreamPlaces);
        de.uni_trier.bibliothek.xml.places.model.generated.TEI teiPlacesFile = PlacesUnmarshaller
                .unmarshal(placesXMLReader);
        entityList.add(teiPlacesFile);

        return entityList;
	}

}
