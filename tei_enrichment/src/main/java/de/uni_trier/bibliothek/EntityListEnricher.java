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

import java.util.ArrayList;
import java.util.List;

import de.uni_trier.bibliothek.xml.events.model.generated.Event;
import de.uni_trier.bibliothek.xml.events.model.generated.ListEvent;
import de.uni_trier.bibliothek.xml.listBibl.model.generated.Bibl;
import de.uni_trier.bibliothek.xml.listBibl.model.generated.ListBibl;
import de.uni_trier.bibliothek.xml.objects.model.generated.ListObject;
import de.uni_trier.bibliothek.xml.orgs.model.generated.ListOrg;
import de.uni_trier.bibliothek.xml.orgs.model.generated.Org;
import de.uni_trier.bibliothek.xml.persons.model.generated.Death;
import de.uni_trier.bibliothek.xml.persons.model.generated.ListPerson;
import de.uni_trier.bibliothek.xml.persons.model.generated.Person;
import de.uni_trier.bibliothek.xml.places.model.generated.ListPlace;
import de.uni_trier.bibliothek.xml.places.model.generated.Place;
import de.uni_trier.bibliothek.xml.tei.model.generated.Front;
import de.uni_trier.bibliothek.xml.tei.model.generated.Lb;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import de.uni_trier.bibliothek.xml.tei.model.generated.Text;
import jakarta.xml.bind.JAXBElement;

public class EntityListEnricher {

	public static TEI originalTEI;
	public static de.uni_trier.bibliothek.xml.events.model.generated.TEI teiEvents;
	public static de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI teiListBibl;
	public static de.uni_trier.bibliothek.xml.objects.model.generated.TEI teiObjects;
	public static de.uni_trier.bibliothek.xml.orgs.model.generated.TEI teiOrgs;
	public static de.uni_trier.bibliothek.xml.persons.model.generated.TEI teiPersons;
	public static de.uni_trier.bibliothek.xml.places.model.generated.TEI teiPlaces;
	public static List<Object> objectTEIList;
	public static de.uni_trier.bibliothek.xml.persons.model.generated.ObjectFactory personsTEIObjectFactory = new de.uni_trier.bibliothek.xml.persons.model.generated.ObjectFactory();
	public static de.uni_trier.bibliothek.xml.events.model.generated.ObjectFactory eventsTEIObjectFactory = new de.uni_trier.bibliothek.xml.events.model.generated.ObjectFactory();
	public static de.uni_trier.bibliothek.xml.listBibl.model.generated.ObjectFactory listBiblTEIObjectFactory = new de.uni_trier.bibliothek.xml.listBibl.model.generated.ObjectFactory();
	public static de.uni_trier.bibliothek.xml.objects.model.generated.ObjectFactory objectsTEIObjectFactory = new de.uni_trier.bibliothek.xml.objects.model.generated.ObjectFactory();
	public static de.uni_trier.bibliothek.xml.places.model.generated.ObjectFactory placesTEIObjectFactory = new de.uni_trier.bibliothek.xml.places.model.generated.ObjectFactory();
	public static List<Person> listPersonList;
	public static List<Bibl> listBiblList;
	public static List<Event> eventList;
	public static List<de.uni_trier.bibliothek.xml.objects.model.generated.Object> objectList;
	public static List<Org> orgsList;
	public static List<Place> placesList;

	public static List<Object> enrichList(List<Object> originalObjectTEIList)
	{		
		objectTEIList = originalObjectTEIList;
		createJavaObjects();
		Text originText = originalTEI.getText();
		Front front = originText.getFront();
		List<Object> pbOrDivOrTitlePage = front.getPbOrDivOrTitlePage();

		addPersonEntity();


		JAXBElement<String> persNameJAXB;	
		persNameJAXB = personsTEIObjectFactory.createPersonPersName("persname2");
		

		return objectTEIList;
	}




	public static void createLists()
	{

		de.uni_trier.bibliothek.xml.persons.model.generated.Text personsText = teiPersons.getText();
		de.uni_trier.bibliothek.xml.persons.model.generated.Body personsBody = personsText.getBody();
		de.uni_trier.bibliothek.xml.persons.model.generated.Div personsDiv = personsBody.getDiv();
		ListPerson listPerson = personsDiv.getListPerson();
		listPersonList =  listPerson.getPerson();

		de.uni_trier.bibliothek.xml.listBibl.model.generated.Text listBiblText = teiListBibl.getText();
		de.uni_trier.bibliothek.xml.listBibl.model.generated.Body listBiblBody = listBiblText.getBody();
		de.uni_trier.bibliothek.xml.listBibl.model.generated.Div listBiblDiv = listBiblBody.getDiv();
		ListBibl listBibl = listBiblDiv.getListBibl();
		listBiblList =  listBibl.getBibl();

		de.uni_trier.bibliothek.xml.events.model.generated.Text eventsBiblText = teiEvents.getText();
		de.uni_trier.bibliothek.xml.events.model.generated.Body eventsBody = eventsBiblText.getBody();
		de.uni_trier.bibliothek.xml.events.model.generated.Div eventsDiv = eventsBody.getDiv();
		ListEvent listEvent = eventsDiv.getListEvent();
		eventList =  listEvent.getEvent();

		de.uni_trier.bibliothek.xml.objects.model.generated.Text objectsText = teiObjects.getText();
		de.uni_trier.bibliothek.xml.objects.model.generated.Body objecBody = objectsText.getBody();
		de.uni_trier.bibliothek.xml.objects.model.generated.Div objectsDiv = objecBody.getDiv();
		ListObject listObject = objectsDiv.getListObject();
		objectList =  listObject.getObject();

		de.uni_trier.bibliothek.xml.orgs.model.generated.Text orgsText = teiOrgs.getText();
		de.uni_trier.bibliothek.xml.orgs.model.generated.Body orgsBody = orgsText.getBody();
		de.uni_trier.bibliothek.xml.orgs.model.generated.Div orgsDiv = orgsBody.getDiv();
		ListOrg listOrgs = orgsDiv.getListOrg();
		orgsList =  listOrgs.getOrg();

		de.uni_trier.bibliothek.xml.places.model.generated.Text placesText = teiPlaces.getText();
		de.uni_trier.bibliothek.xml.places.model.generated.Body placesBody = placesText.getBody();
		de.uni_trier.bibliothek.xml.places.model.generated.Div placesDiv = placesBody.getDiv();
		ListPlace listPlaces = placesDiv.getListPlace();
		placesList =  listPlaces.getPlace();
	}





	public static void addPersonEntity()
	{

	}



	public static void createJavaObjects()
	{
		originalTEI = (TEI) objectTEIList.get(0);
		teiEvents = (de.uni_trier.bibliothek.xml.events.model.generated.TEI) objectTEIList.get(1);
		teiListBibl = (de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI) objectTEIList.get(2);
		teiObjects = (de.uni_trier.bibliothek.xml.objects.model.generated.TEI) objectTEIList.get(3);
		teiOrgs = (de.uni_trier.bibliothek.xml.orgs.model.generated.TEI) objectTEIList.get(4);
		teiPersons = (de.uni_trier.bibliothek.xml.persons.model.generated.TEI) objectTEIList.get(5);
		teiPlaces = (de.uni_trier.bibliothek.xml.places.model.generated.TEI) objectTEIList.get(6);
	}

}
