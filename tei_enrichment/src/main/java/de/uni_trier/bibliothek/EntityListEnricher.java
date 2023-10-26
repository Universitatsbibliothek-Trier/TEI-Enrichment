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

import de.uni_trier.bibliothek.xml.persons.model.generated.Death;
import de.uni_trier.bibliothek.xml.persons.model.generated.ListPerson;
import de.uni_trier.bibliothek.xml.persons.model.generated.Person;
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

	public static List<Object> enrichList(List<Object> originalObjectTEIList)
	{		
		objectTEIList = originalObjectTEIList;
		createJavaObjects();
		Text originText = originalTEI.getText();
		Front front = originText.getFront();
		List<Object> pbOrDivOrTitlePage = front.getPbOrDivOrTitlePage();

		de.uni_trier.bibliothek.xml.persons.model.generated.Text personsText = teiPersons.getText();
		de.uni_trier.bibliothek.xml.persons.model.generated.Body personsBody = personsText.getBody();
		de.uni_trier.bibliothek.xml.persons.model.generated.Div personsDiv = personsBody.getDiv();
		ListPerson listPerson = personsDiv.getListPerson();
		List<Person> personList =  listPerson.getPerson();
		Person person0 = personList.get(0);
		List<JAXBElement<?>> personElements = person0.getPersNameOrNoteOrBirth();
		// for (JAXBElement<?> somePersonElement : personElements)
		for (JAXBElement<?> somePersonElement : personElements)
		{
			if(somePersonElement.getValue() instanceof String)
			{
				System.out.println("person element ist: " + somePersonElement.getValue());
			}
			if(somePersonElement.getValue() instanceof Death)
			{
				
				Death jaxbDeath = (Death)somePersonElement.getValue();
				System.out.println("person Death ist: " + jaxbDeath.getWhen());
			}
			
		}
		JAXBElement<String> persNameJAXB;	
		persNameJAXB = personsTEIObjectFactory.createPersonPersName("persname2");
		// JAXBElement<String> personName = "Peter";
		personElements.add(persNameJAXB);

		return objectTEIList;
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

	// public List<Object> createTEIJavaObjectsList()
	// {
	// 	objectTEIList.add(originalTEI);
	// 	objectTEIList.add(teiEvents);
	// 	objectTEIList.add(teiListBibl);
	// 	objectTEIList.add(teiObjects);
	// 	objectTEIList.add(teiOrgs);
	// 	objectTEIList.add(teiPersons);
	// 	objectTEIList.add(teiPlaces);
	// 	return objectTEIList;		
	// }

}
