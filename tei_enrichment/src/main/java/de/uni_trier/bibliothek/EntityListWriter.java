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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.json.JSONArray;
import org.json.JSONObject;

import de.uni_trier.bibliothek.xml.events.model.generated.Desc;
import de.uni_trier.bibliothek.xml.events.model.generated.Event;
import de.uni_trier.bibliothek.xml.events.model.generated.EventDate;
import de.uni_trier.bibliothek.xml.events.model.generated.ListEvent;
import de.uni_trier.bibliothek.xml.listBibl.model.generated.Bibl;
import de.uni_trier.bibliothek.xml.listBibl.model.generated.ListBibl;
import de.uni_trier.bibliothek.xml.objects.model.generated.ListObject;
import de.uni_trier.bibliothek.xml.objects.model.generated.ObjectIdentifier;
import de.uni_trier.bibliothek.xml.orgs.model.generated.ListOrg;
import de.uni_trier.bibliothek.xml.orgs.model.generated.Org;
import de.uni_trier.bibliothek.xml.persons.model.generated.Birth;
import de.uni_trier.bibliothek.xml.persons.model.generated.Death;
import de.uni_trier.bibliothek.xml.persons.model.generated.Link;
import de.uni_trier.bibliothek.xml.persons.model.generated.ListPerson;
import de.uni_trier.bibliothek.xml.persons.model.generated.PersName;
import de.uni_trier.bibliothek.xml.persons.model.generated.Person;
import de.uni_trier.bibliothek.xml.places.model.generated.ListPlace;
import de.uni_trier.bibliothek.xml.places.model.generated.Location;
import de.uni_trier.bibliothek.xml.places.model.generated.Note;
import de.uni_trier.bibliothek.xml.places.model.generated.Place;
import de.uni_trier.bibliothek.xml.places.model.generated.PlaceIdno;
import de.uni_trier.bibliothek.xml.tei.model.generated.Add;
import de.uni_trier.bibliothek.xml.tei.model.generated.Back;
import de.uni_trier.bibliothek.xml.tei.model.generated.Body;
import de.uni_trier.bibliothek.xml.tei.model.generated.Choice;
import de.uni_trier.bibliothek.xml.tei.model.generated.Del;
import de.uni_trier.bibliothek.xml.tei.model.generated.DivFront;
import de.uni_trier.bibliothek.xml.tei.model.generated.DocImprint;
import de.uni_trier.bibliothek.xml.tei.model.generated.DocTitle;
import de.uni_trier.bibliothek.xml.tei.model.generated.FileDesc;
import de.uni_trier.bibliothek.xml.tei.model.generated.Foreign;
import de.uni_trier.bibliothek.xml.tei.model.generated.Front;
import de.uni_trier.bibliothek.xml.tei.model.generated.Fw;
import de.uni_trier.bibliothek.xml.tei.model.generated.GroupBody;
import de.uni_trier.bibliothek.xml.tei.model.generated.GroupText;
import de.uni_trier.bibliothek.xml.tei.model.generated.Head;
import de.uni_trier.bibliothek.xml.tei.model.generated.InnerGroup;
import de.uni_trier.bibliothek.xml.tei.model.generated.Item;
import de.uni_trier.bibliothek.xml.tei.model.generated.Lb;
import de.uni_trier.bibliothek.xml.tei.model.generated.LbEtc;
import de.uni_trier.bibliothek.xml.tei.model.generated.NameGND;
import de.uni_trier.bibliothek.xml.tei.model.generated.OuterGroup;
import de.uni_trier.bibliothek.xml.tei.model.generated.PFront;
import de.uni_trier.bibliothek.xml.tei.model.generated.Pb;
import de.uni_trier.bibliothek.xml.tei.model.generated.PbFront;
import de.uni_trier.bibliothek.xml.tei.model.generated.Row;
import de.uni_trier.bibliothek.xml.tei.model.generated.SourceGND;
import de.uni_trier.bibliothek.xml.tei.model.generated.Subst;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import de.uni_trier.bibliothek.xml.tei.model.generated.Table;
import de.uni_trier.bibliothek.xml.tei.model.generated.TeiHeader;
import de.uni_trier.bibliothek.xml.tei.model.generated.Text;
import de.uni_trier.bibliothek.xml.tei.model.generated.TitlePage;
import de.uni_trier.bibliothek.xml.tei.model.generated.TitlePart;
import de.uni_trier.bibliothek.xml.tei.model.generated.TitleStmt;
import de.uni_trier.bibliothek.xml.tei.model.generated.TitleStmtValue;
import jakarta.xml.bind.JAXBElement;

public class EntityListWriter {

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
	public static de.uni_trier.bibliothek.xml.orgs.model.generated.ObjectFactory orgsTEIObjectFactory = new de.uni_trier.bibliothek.xml.orgs.model.generated.ObjectFactory();
	public static List<Person> listPersonList;
	public static ListBibl listBibl;
	public static List<Event> eventList;
	public static List<de.uni_trier.bibliothek.xml.objects.model.generated.Object> objectList;
	public static List<Org> orgsList;
	public static List<Place> placesList;
	public static String reference;
	public static String fileName;

	public static void initiate(List<Object> originalObjectTEIList, String bandFileName)
	{
		fileName = bandFileName;
		objectTEIList = originalObjectTEIList;
		createJavaObjects();

		TeiHeader teiHeader = originalTEI.getTeiHeader();
		FileDesc fileDesc = teiHeader.getFileDesc();
		TitleStmt titleStmt = fileDesc.getTitleStmt();
		TitleStmtValue title = titleStmt.getTitle();
		reference = title.getRef();

		createLists();

	}

	public static void createLists() {

		de.uni_trier.bibliothek.xml.persons.model.generated.Text personsText = teiPersons.getText();
		de.uni_trier.bibliothek.xml.persons.model.generated.Body personsBody = personsText.getBody();
		de.uni_trier.bibliothek.xml.persons.model.generated.Div personsDiv = personsBody.getDiv();
		ListPerson listPerson = personsDiv.getListPerson();
		listPersonList = listPerson.getPerson();

		de.uni_trier.bibliothek.xml.listBibl.model.generated.Text listBiblText = teiListBibl.getText();
		de.uni_trier.bibliothek.xml.listBibl.model.generated.Body listBiblBody = listBiblText.getBody();
		de.uni_trier.bibliothek.xml.listBibl.model.generated.Div listBiblDiv = listBiblBody.getDiv();
		listBibl = listBiblDiv.getListBibl();

		de.uni_trier.bibliothek.xml.events.model.generated.Text eventsBiblText = teiEvents.getText();
		de.uni_trier.bibliothek.xml.events.model.generated.Body eventsBody = eventsBiblText.getBody();
		de.uni_trier.bibliothek.xml.events.model.generated.Div eventsDiv = eventsBody.getDiv();
		ListEvent listEvent = eventsDiv.getListEvent();
		eventList = listEvent.getEvent();

		de.uni_trier.bibliothek.xml.objects.model.generated.Text objectsText = teiObjects.getText();
		de.uni_trier.bibliothek.xml.objects.model.generated.Body objecBody = objectsText.getBody();
		de.uni_trier.bibliothek.xml.objects.model.generated.Div objectsDiv = objecBody.getDiv();
		ListObject listObject = objectsDiv.getListObject();
		objectList = listObject.getObject();

		de.uni_trier.bibliothek.xml.orgs.model.generated.Text orgsText = teiOrgs.getText();
		de.uni_trier.bibliothek.xml.orgs.model.generated.Body orgsBody = orgsText.getBody();
		de.uni_trier.bibliothek.xml.orgs.model.generated.Div orgsDiv = orgsBody.getDiv();
		ListOrg listOrgs = orgsDiv.getListOrg();
		orgsList = listOrgs.getOrg();

		de.uni_trier.bibliothek.xml.places.model.generated.Text placesText = teiPlaces.getText();
		de.uni_trier.bibliothek.xml.places.model.generated.Body placesBody = placesText.getBody();
		de.uni_trier.bibliothek.xml.places.model.generated.Div placesDiv = placesBody.getDiv();
		ListPlace listPlaces = placesDiv.getListPlace();
		placesList = listPlaces.getPlace();
	}

	public static void createJavaObjects() {
		originalTEI = (TEI) objectTEIList.get(0);
		teiEvents = (de.uni_trier.bibliothek.xml.events.model.generated.TEI) objectTEIList.get(1);
		teiListBibl = (de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI) objectTEIList.get(2);
		teiObjects = (de.uni_trier.bibliothek.xml.objects.model.generated.TEI) objectTEIList.get(3);
		teiOrgs = (de.uni_trier.bibliothek.xml.orgs.model.generated.TEI) objectTEIList.get(4);
		teiPersons = (de.uni_trier.bibliothek.xml.persons.model.generated.TEI) objectTEIList.get(5);
		teiPlaces = (de.uni_trier.bibliothek.xml.places.model.generated.TEI) objectTEIList.get(6);
	}




	//LISTBIBL ENTITY
	//LISTBIBL ENTITY
	//LISTBIBL ENTITY
	//LISTBIBL ENTITY
	public static void writeListBiblEntity(JSONObject jsonObject, String preferredName, List<String> typeTermslist,
			DivFront divFrontElement) {
		Bibl bibl = new Bibl();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
		bibl.getTitleOrNoteOrLink().add(preferredNameStringOriginal);
		if(jsonObject.has("variantName"))
		{
			JSONArray variantNameArray = jsonObject.getJSONArray("variantName");
			for(int i = 0; i < variantNameArray.length(); i++)
			{
				bibl.getTitleOrNoteOrLink().add(variantNameArray.get(i));
			}
			
		}
		ArrayList<Bibl> arrayListBibl = new ArrayList<Bibl>(listBibl.getBibl());
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (Bibl listBiblElement : arrayListBibl) {
			Map<QName, String> attributeName = listBiblElement.getOtherAttributes();
			String xmlID = attributeName.get(qname);
				if (xmlID!=null) {
					if (xmlID.equals("listBibl_" + preferredName)) {
						bibl = listBiblElement;
						alreadyHasTitle = true;
						List<Object> titleOrNoteOrLinkList = listBiblElement.getTitleOrNoteOrLink();
						for (Object titleOrNoteOrLinkElement : titleOrNoteOrLinkList) {
							if (titleOrNoteOrLinkElement instanceof de.uni_trier.bibliothek.xml.listBibl.model.generated.Link) {
								de.uni_trier.bibliothek.xml.listBibl.model.generated.Link linkElement = (de.uni_trier.bibliothek.xml.listBibl.model.generated.Link)titleOrNoteOrLinkElement;
								if ((linkElement.getTarget()).equals(fileName + reference)) {
									System.out.println("alreadyhaslink: " + alreadyHasLink);
									alreadyHasLink = true;
								}
							}
						}
					}
				}
			
		}
		if (!alreadyHasTitle) {

			Map<QName, String> attributesMap = bibl.getOtherAttributes();
			attributesMap.put(qname, "listBibl_" + preferredName);

			if (jsonObject.has("biographicalOrHistoricalInformation")) {
				JSONArray detailedInformationArray = jsonObject.getJSONArray("biographicalOrHistoricalInformation");
				String detailedInformationString = detailedInformationArray.getString(0);
				// String jsonObjectDetailedInformationTermString = jsonObjectDetailedInformation.getString("label");
				de.uni_trier.bibliothek.xml.listBibl.model.generated.Note note = listBiblTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(detailedInformationString);
				bibl.getTitleOrNoteOrLink().add(note);
			}
			else if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");
				de.uni_trier.bibliothek.xml.listBibl.model.generated.Note note = listBiblTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(jsonObjectBroaderTermString);
				bibl.getTitleOrNoteOrLink().add(note);
			}
			else if (jsonObject.has("definition")) {
				JSONArray definitionArray = jsonObject.getJSONArray("definition");
				// JSONObject jsonObjectBroaderTerm = definitionArray.getString(0);
				String definitionString = definitionArray.getString(0);
				de.uni_trier.bibliothek.xml.listBibl.model.generated.Note note = listBiblTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(definitionString);
				bibl.getTitleOrNoteOrLink().add(note);
			}
			de.uni_trier.bibliothek.xml.listBibl.model.generated.Note categoriesNote = listBiblTEIObjectFactory
					.createNote();
			categoriesNote.setType("categories");
			de.uni_trier.bibliothek.xml.listBibl.model.generated.List biblListSuperList = new de.uni_trier.bibliothek.xml.listBibl.model.generated.List();

			de.uni_trier.bibliothek.xml.listBibl.model.generated.List biblListSubList = new de.uni_trier.bibliothek.xml.listBibl.model.generated.List();

			boolean hasSupercategory = false;
			boolean hasSubcategory = false;

			biblListSuperList.setType("supercategory");
			biblListSubList.setType("subcategory");

			if (typeTermslist.contains("AuthorityResource")) {
				typeTermslist.remove("AuthorityResource");
			}
			List<String> typeTermsListCopy = new ArrayList<String>(typeTermslist); 
			if (!typeTermsListCopy.isEmpty()) {

				for (String termString : typeTermsListCopy) {
					switch (termString) {
						case "Person":
							typeTermslist.remove("Person");
							biblListSuperList.getItem().add("Person");
							hasSupercategory = true;
							break;
						case "Work":
							typeTermslist.remove("Work");
							biblListSuperList.getItem().add("Work");
							hasSupercategory = true;
							break;
						case "Family":
							typeTermslist.remove("Family");
							biblListSuperList.getItem().add("Family");
							hasSupercategory = true;
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							biblListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							biblListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							biblListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							biblListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					hasSubcategory = true;
					biblListSubList.getItem().add(subcategory);
				}

				if (hasSupercategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.listBibl.model.generated.List> biblNoteList = listBiblTEIObjectFactory
							.createNoteList(biblListSuperList);
					categoriesNote.getContent().add(biblNoteList);
				}

				if (hasSubcategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.listBibl.model.generated.List> biblNoteList = listBiblTEIObjectFactory
							.createNoteList(biblListSubList);
					categoriesNote.getContent().add(biblNoteList);
				}
				bibl.getTitleOrNoteOrLink().add(categoriesNote);
			}

			de.uni_trier.bibliothek.xml.listBibl.model.generated.BiblIdno biblIdno = listBiblTEIObjectFactory
					.createBiblIdno();
			if (jsonObject.has("id")) {
				String dnbURL = jsonObject.getString("id");
				biblIdno.setContent(dnbURL);
				biblIdno.setType("URI");
				biblIdno.setSubtype("GND");
				bibl.getTitleOrNoteOrLink().add(biblIdno);
			}

			if (jsonObject.has("sameAs")) {
				JSONArray sameAsArray = jsonObject.getJSONArray("sameAs");
				for (int i = 0; i < sameAsArray.length(); i++) {
					JSONObject idCollectionObject = sameAsArray.getJSONObject(i);
					JSONObject jsonObjectCollection = idCollectionObject.getJSONObject("collection");
					String collectionName = jsonObjectCollection.getString("name");
					if(jsonObjectCollection.has("name"))
					{
						if (collectionName.equals("Wikidata")) {
							de.uni_trier.bibliothek.xml.listBibl.model.generated.BiblIdno biblIdnoWiki = listBiblTEIObjectFactory
								.createBiblIdno();
							biblIdnoWiki.setContent(jsonObjectCollection.getString("id"));
							biblIdnoWiki.setType("URI");
							biblIdnoWiki.setSubtype("WIKIDATA");
							bibl.getTitleOrNoteOrLink().add(biblIdnoWiki);
						}
					}

				}

			}
			listBibl.getBibl().add(bibl);
		} 
		if (divFrontElement.getType() != null) {
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredName);
			de.uni_trier.bibliothek.xml.listBibl.model.generated.Link linkArtikel = listBiblTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredName);
			bibl.getTitleOrNoteOrLink().add(linkArtikel);
		}
		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.listBibl.model.generated.Link link = listBiblTEIObjectFactory.createLink();
			link.setTarget(fileName + reference);
			bibl.getTitleOrNoteOrLink().add(link);
		}
	}





	//PERSON ENTITY
	//PERSON ENTITY
	//PERSON ENTITY
	//PERSON ENTITY
	public static void writePersonEntity(JSONObject jsonObject, String preferredName, List<String> typeTermslist,
			DivFront divFrontElement) {
		Person person = new Person();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");

		if(preferredNameStringOriginal.contains("Familie") || preferredNameStringOriginal.contains("Adelsgeschlecht")){
			PersName personName = new PersName();
			personName.setSurname(preferredNameStringOriginal);
			person.getPersNameOrNoteOrBirth().add(personName);
		}

		if(jsonObject.has("titleOfNobility"))
		{
			JSONArray titleOfNobilityArray = jsonObject.getJSONArray("titleOfNobility");
			JSONObject titleOfNobilityObject = titleOfNobilityArray.getJSONObject(0);
			if(titleOfNobilityObject.has("label"))
			{
				person.setRole(titleOfNobilityObject.getString("label"));
			}
		}

		if(jsonObject.has("preferredNameEntityForThePerson"))
		{
			PersName personName = new PersName();
			JSONObject preferredNameEntityForThePerson = jsonObject.getJSONObject("preferredNameEntityForThePerson");
			if(preferredNameEntityForThePerson.has("forename")){
				JSONArray forename = preferredNameEntityForThePerson.getJSONArray("forename");
				personName.setForename((String) forename.get(0));
			}
			if(preferredNameEntityForThePerson.has("surname")){
				JSONArray surname = preferredNameEntityForThePerson.getJSONArray("surname");
				personName.setSurname((String) surname.get(0));
			}
			if(preferredNameEntityForThePerson.has("nameAddition")){
				JSONArray nameAddition = preferredNameEntityForThePerson.getJSONArray("nameAddition");
				personName.getAddName().add((String) nameAddition.get(0));
			}
			if(preferredNameEntityForThePerson.has("personalName")){
				JSONArray personalName = preferredNameEntityForThePerson.getJSONArray("personalName");
				personName.getAddName().add((String) personalName.get(0));
				
			}
			person.getPersNameOrNoteOrBirth().add(personName);
		}
		
		if(jsonObject.has("variantNameEntityForThePerson"))
		{
			JSONArray variantNameEntityForThePerson = jsonObject.getJSONArray("variantNameEntityForThePerson");
			for (int i = 0; i < variantNameEntityForThePerson.length(); i++) {
				PersName personName = new PersName();
				JSONObject variantName = variantNameEntityForThePerson.getJSONObject(i);

				if(variantName.has("forename")){
					JSONArray forename = variantName.getJSONArray("forename");
					personName.setForename((String) forename.get(0));
				}
				if(variantName.has("surname")){
					JSONArray surname = variantName.getJSONArray("surname");
					personName.setSurname((String) surname.get(0));
				}
				if(variantName.has("nameAddition")){
					JSONArray nameAddition = variantName.getJSONArray("nameAddition");
					personName.getAddName().add((String) nameAddition.get(0));
				}
				if(variantName.has("personalName")){
					JSONArray personalName = variantName.getJSONArray("personalName");
					personName.getAddName().add((String) personalName.get(0));
				
			}
				person.getPersNameOrNoteOrBirth().add(personName);
			}
		
		}
		
		
		ArrayList<Person> arrayListPerson = new ArrayList<Person>(listPersonList);
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (Person listPersonElement : arrayListPerson) {
			Map<QName, String> attributeName = listPersonElement.getOtherAttributes();
			QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
			String xmlID = attributeName.get(qname);
				if (xmlID!=null) {
					if (xmlID.equals("person_" + preferredName)) {
						person = listPersonElement;
						alreadyHasTitle = true;
						List<Object> persNameOrNoteOrBirthList = listPersonElement.getPersNameOrNoteOrBirth();
						for (Object persNameOrNoteOrBirthElement : persNameOrNoteOrBirthList) {
							if (persNameOrNoteOrBirthElement instanceof de.uni_trier.bibliothek.xml.persons.model.generated.Link) {
								de.uni_trier.bibliothek.xml.persons.model.generated.Link linkElement = (de.uni_trier.bibliothek.xml.persons.model.generated.Link)persNameOrNoteOrBirthElement;
								if ((linkElement.getTarget()).equals(fileName + reference)) {
									System.out.println("alreadyhaslink: " + alreadyHasLink);
									alreadyHasLink = true;
								}
							}
						}
					}
				}
		}
		if (!alreadyHasTitle) {

			Map<QName, String> attributesMap = person.getOtherAttributes();
			QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
			attributesMap.put(qname, "person_" + preferredName);
			Death death = new Death();
			if(jsonObject.has("dateOfDeath"))
			{
				JSONArray deathDateArray = jsonObject.getJSONArray("dateOfDeath");
				String deathDateArrayString = deathDateArray.getString(0);
				death.setWhenCustom(deathDateArrayString);
				person.getPersNameOrNoteOrBirth().add(death);
			}

			if(jsonObject.has("dateOfBirth"))
			{
				JSONArray birthDateArray = jsonObject.getJSONArray("dateOfBirth");
				String birthArrayString = birthDateArray.getString(0);
				Birth birth = new Birth();
				birth.setWhenCustom(birthArrayString);
				person.getPersNameOrNoteOrBirth().add(birth);
			}

			if (jsonObject.has("biographicalOrHistoricalInformation")) {
				JSONArray detailedInformationArray = jsonObject.getJSONArray("biographicalOrHistoricalInformation");
				String detailedInformationString = detailedInformationArray.getString(0);
				// String jsonObjectDetailedInformationTermString = jsonObjectDetailedInformation.getString("label");
				de.uni_trier.bibliothek.xml.persons.model.generated.Note note = personsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(detailedInformationString);
				person.getPersNameOrNoteOrBirth().add(note);
			}
			else if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");
				de.uni_trier.bibliothek.xml.persons.model.generated.Note note = personsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(jsonObjectBroaderTermString);
				person.getPersNameOrNoteOrBirth().add(note);
			}
			else if (jsonObject.has("definition")) {
				JSONArray definitionArray = jsonObject.getJSONArray("definition");
				// JSONObject jsonObjectBroaderTerm = definitionArray.getString(0);
				String definitionString = definitionArray.getString(0);
				de.uni_trier.bibliothek.xml.persons.model.generated.Note note = personsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(definitionString);
				person.getPersNameOrNoteOrBirth().add(note);
			}
			de.uni_trier.bibliothek.xml.persons.model.generated.Note categoriesNote = personsTEIObjectFactory
					.createNote();
			categoriesNote.setType("categories");
			de.uni_trier.bibliothek.xml.persons.model.generated.List personListSuperList = new de.uni_trier.bibliothek.xml.persons.model.generated.List();

			de.uni_trier.bibliothek.xml.persons.model.generated.List personListSubList = new de.uni_trier.bibliothek.xml.persons.model.generated.List();

			boolean hasSupercategory = false;
			boolean hasSubcategory = false;

			personListSuperList.setType("supercategory");
			personListSubList.setType("subcategory");

			if (typeTermslist.contains("AuthorityResource")) {
				typeTermslist.remove("AuthorityResource");
			}
			List<String> typeTermsListCopy = new ArrayList<String>(typeTermslist); 
			if (!typeTermsListCopy.isEmpty()) {

				for (String termString : typeTermsListCopy) {
					switch (termString) {

						case "Person":
							typeTermslist.remove("Person");
							personListSuperList.getItem().add("Person");
							hasSupercategory = true;
							break;
						case "Work":
							typeTermslist.remove("Work");
							personListSuperList.getItem().add("Work");
							hasSupercategory = true;
							break;
						case "Family":
							typeTermslist.remove("Family");
							personListSuperList.getItem().add("Family");
							hasSupercategory = true;
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							personListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							personListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							personListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							personListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					hasSubcategory = true;
					personListSubList.getItem().add(subcategory);
				}

				if (hasSupercategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.persons.model.generated.List> personNoteList = personsTEIObjectFactory.createNoteList(personListSuperList);
					categoriesNote.getContent().add(personNoteList);
				}

				if (hasSubcategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.persons.model.generated.List> personNoteList = personsTEIObjectFactory.createNoteList(personListSubList);
					categoriesNote.getContent().add(personNoteList);
				}
				person.getPersNameOrNoteOrBirth().add(categoriesNote);
			}

			de.uni_trier.bibliothek.xml.persons.model.generated.PersonIdno personIdno = personsTEIObjectFactory.createPersonIdno();

			if (jsonObject.has("id")) {
				String dnbURL = jsonObject.getString("id");
				personIdno.setContent(dnbURL);
				personIdno.setType("URI");
				personIdno.setSubtype("GND");
				person.getPersNameOrNoteOrBirth().add(personIdno);
			}

			if (jsonObject.has("sameAs")) {
				JSONArray sameAsArray = jsonObject.getJSONArray("sameAs");
				for (int i = 0; i < sameAsArray.length(); i++) {
					JSONObject idCollectionObject = sameAsArray.getJSONObject(i);
					JSONObject jsonObjectCollection = idCollectionObject.getJSONObject("collection");

					if(jsonObjectCollection.has("name"))
					{
						String collectionName = jsonObjectCollection.getString("name");

						if (collectionName.equals("Wikidata")) {
							de.uni_trier.bibliothek.xml.persons.model.generated.PersonIdno personIdnoWiki = personsTEIObjectFactory.createPersonIdno();
							personIdnoWiki.setContent(jsonObjectCollection.getString("id"));
							personIdnoWiki.setType("URI");
							personIdnoWiki.setSubtype("WIKIDATA");
							person.getPersNameOrNoteOrBirth().add(personIdnoWiki);
						}
					}
				}
			}
			listPersonList.add(person);
		} 
		
		if (divFrontElement.getType() != null) {
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredName);
			de.uni_trier.bibliothek.xml.persons.model.generated.Link linkArtikel = personsTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredName);
			person.getPersNameOrNoteOrBirth().add(linkArtikel);
		}
		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.persons.model.generated.Link link = personsTEIObjectFactory.createLink();
			link.setTarget(fileName + reference);
			person.getPersNameOrNoteOrBirth().add(link);
		}
	}



	//EVENTS ENTITY
	//EVENTS ENTITY
	//EVENTS ENTITY
	//EVENTS ENTITY
	public static void writeEventsEntity(JSONObject jsonObject, String preferredName, List<String> typeTermslist,
			DivFront divFrontElement) {
		Event event = new Event();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");

		event.getLabelOrDescOrNote().add(preferredNameStringOriginal);
		if(jsonObject.has("variantName"))
		{
			JSONArray variantNameArray = jsonObject.getJSONArray("variantName");
			for(int i = 0; i < variantNameArray.length(); i++)
			{
				event.getLabelOrDescOrNote().add(variantNameArray.get(i));
			}
			
		}
		ArrayList<Event> arrayListEvent = new ArrayList<Event>(eventList);
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (Event eventListElement : arrayListEvent) {

			Map<QName, String> attributeName = eventListElement.getOtherAttributes();
			String xmlID = attributeName.get(qname);
				if (xmlID!=null) {
					if (xmlID.equals("event_" + preferredName)) {
						event = eventListElement;
						alreadyHasTitle = true;
						List<Object> labelOrDescOrNoteObjectList = eventListElement.getLabelOrDescOrNote();
						for (Object labelOrDescOrNoteObjectElement : labelOrDescOrNoteObjectList) {
							if (labelOrDescOrNoteObjectElement instanceof de.uni_trier.bibliothek.xml.events.model.generated.Link) {
								de.uni_trier.bibliothek.xml.events.model.generated.Link linkElement = (de.uni_trier.bibliothek.xml.events.model.generated.Link)labelOrDescOrNoteObjectElement;
								if ((linkElement.getTarget()).equals(fileName + reference)) {
									System.out.println("alreadyhaslink: " + alreadyHasLink);
									alreadyHasLink = true;
								}
							}
						}
					}
				}
			
		}
		if (!alreadyHasTitle) {
			Map<QName, String> attributesMap = event.getOtherAttributes();
			attributesMap.put(qname, "event_" + preferredName);
			Desc descEvent = new Desc();
			if(jsonObject.has("dateOfProduction"))
			{
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfProduction");
				for (int i = 0; i < jsonArray.length(); i++) {
					EventDate eventsDate = new EventDate();
					eventsDate.setWhenCustom(jsonArray.getString(i));
					descEvent.getDate().add(eventsDate);
				  }	
			}
			if(jsonObject.has("dateOfEstablishment"))
			{
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfEstablishment");
				for (int i = 0; i < jsonArray.length(); i++) {
					EventDate eventsDate = new EventDate();
					eventsDate.setFromCustom(jsonArray.getString(i));
					descEvent.getDate().add(eventsDate);
				  }	
			}
			if(jsonObject.has("dateOfTermination"))
			{
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfTermination");
				for (int i = 0; i < jsonArray.length(); i++) {
					EventDate eventsDate = new EventDate();
					eventsDate.setToCustom(jsonArray.getString(i));
					descEvent.getDate().add(eventsDate);
				  }	
			}
			if(jsonObject.has("dateOfEstablishmentAndTermination"))
			{
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfEstablishmentAndTermination");
				for (int i = 0; i < jsonArray.length(); i++) {
					EventDate eventsDate = new EventDate();
					eventsDate.setWhenCustom(jsonArray.getString(i));
					descEvent.getDate().add(eventsDate);
				  }	
			}
			if(jsonObject.has("associatedDate"))
			{
				JSONArray jsonArray = jsonObject.getJSONArray("associatedDate");
				for (int i = 0; i < jsonArray.length(); i++) {
					EventDate eventsDate = new EventDate();
					eventsDate.setWhenCustom(jsonArray.getString(i));
					descEvent.getDate().add(eventsDate);
				  }	
			}
			if(!descEvent.getDate().isEmpty())
			{
				event.getLabelOrDescOrNote().add(descEvent);
			}

			if (jsonObject.has("biographicalOrHistoricalInformation")) {
				JSONArray detailedInformationArray = jsonObject.getJSONArray("biographicalOrHistoricalInformation");
				String detailedInformationString = detailedInformationArray.getString(0);
				// String jsonObjectDetailedInformationTermString = jsonObjectDetailedInformation.getString("label");
				de.uni_trier.bibliothek.xml.events.model.generated.Note note = eventsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(detailedInformationString);
				event.getLabelOrDescOrNote().add(note);
			}
			else if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");
				de.uni_trier.bibliothek.xml.events.model.generated.Note note = eventsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(jsonObjectBroaderTermString);
				event.getLabelOrDescOrNote().add(note);
			}
			else if (jsonObject.has("definition")) {
				JSONArray definitionArray = jsonObject.getJSONArray("definition");
				// JSONObject jsonObjectBroaderTerm = definitionArray.getString(0);
				String definitionString = definitionArray.getString(0);
				de.uni_trier.bibliothek.xml.events.model.generated.Note note = eventsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(definitionString);
				event.getLabelOrDescOrNote().add(note);
			}
			
			de.uni_trier.bibliothek.xml.events.model.generated.Note categoriesNote = eventsTEIObjectFactory
					.createNote();
			categoriesNote.setType("categories");
			de.uni_trier.bibliothek.xml.events.model.generated.List eventsListSuperList = new de.uni_trier.bibliothek.xml.events.model.generated.List();

			de.uni_trier.bibliothek.xml.events.model.generated.List eventsListSubList = new de.uni_trier.bibliothek.xml.events.model.generated.List();

			boolean hasSupercategory = false;
			boolean hasSubcategory = false;

			eventsListSuperList.setType("supercategory");
			eventsListSubList.setType("subcategory");

			if (typeTermslist.contains("AuthorityResource")) {
				typeTermslist.remove("AuthorityResource");
			}
			List<String> typeTermsListCopy = new ArrayList<String>(typeTermslist); 
			if (!typeTermsListCopy.isEmpty()) {
				for (String termString : typeTermsListCopy) {
					switch (termString) {
						case "Person":
							typeTermslist.remove("Person");
							eventsListSuperList.getItem().add("Person");
							hasSupercategory = true;
							break;
						case "Work":
							typeTermslist.remove("Work");
							eventsListSuperList.getItem().add("Work");
							hasSupercategory = true;
							break;
						case "Family":
							typeTermslist.remove("Family");
							eventsListSuperList.getItem().add("Family");
							hasSupercategory = true;
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							eventsListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							eventsListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							eventsListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							eventsListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					hasSubcategory = true;
					eventsListSubList.getItem().add(subcategory);
				}

				if (hasSupercategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.events.model.generated.List> eventNoteList = eventsTEIObjectFactory
							.createNoteList(eventsListSuperList);
					categoriesNote.getContent().add(eventNoteList);
				}

				if (hasSubcategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.events.model.generated.List> eventNoteList = eventsTEIObjectFactory
							.createNoteList(eventsListSubList);
					categoriesNote.getContent().add(eventNoteList);
				}
				event.getLabelOrDescOrNote().add(categoriesNote);
			}

			de.uni_trier.bibliothek.xml.events.model.generated.EventIdno eventIdno = eventsTEIObjectFactory
					.createEventIdno();

			if (jsonObject.has("id")) {
				String dnbURL = jsonObject.getString("id");
				eventIdno.setContent(dnbURL);
				eventIdno.setType("URI");
				eventIdno.setSubtype("GND");
				event.getLabelOrDescOrNote().add(eventIdno);
			}

			if (jsonObject.has("sameAs")) {
				JSONArray sameAsArray = jsonObject.getJSONArray("sameAs");
				for (int i = 0; i < sameAsArray.length(); i++) {
					JSONObject idCollectionObject = sameAsArray.getJSONObject(i);
					JSONObject jsonObjectCollection = idCollectionObject.getJSONObject("collection");
					String collectionName = jsonObjectCollection.getString("name");
					if(jsonObjectCollection.has("name"))
					{
						if (collectionName.equals("Wikidata")) {
							de.uni_trier.bibliothek.xml.events.model.generated.EventIdno eventIdnoWiki = eventsTEIObjectFactory
								.createEventIdno();
							eventIdnoWiki.setContent(jsonObjectCollection.getString("id"));
							eventIdnoWiki.setType("URI");
							eventIdnoWiki.setSubtype("WIKIDATA");
							event.getLabelOrDescOrNote().add(eventIdnoWiki);
						}
					}

				}

			}
			eventList.add(event);
		} 
		if (divFrontElement.getType() != null) {
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredName);
			de.uni_trier.bibliothek.xml.events.model.generated.Link linkArtikel = eventsTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredName);
			event.getLabelOrDescOrNote().add(linkArtikel);
		}
		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.events.model.generated.Link link = eventsTEIObjectFactory.createLink();
			link.setTarget(fileName + reference);
			event.getLabelOrDescOrNote().add(link);
		}
	}



	//ORGS ENTITY
	//ORGS ENTITY
	//ORGS ENTITY
	//ORGS ENTITY
	public static void writeOrgsEntity(JSONObject jsonObject, String preferredName, List<String> typeTermslist,	DivFront divFrontElement) {
		Org org = new Org();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");
		de.uni_trier.bibliothek.xml.orgs.model.generated.Note categoriesNote = orgsTEIObjectFactory.createNote();
		categoriesNote.setType("categories");
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
		org.getOrgNameOrIdnoOrLink().add(preferredNameStringOriginal);
		if(jsonObject.has("variantName"))
		{
			JSONArray variantNameArray = jsonObject.getJSONArray("variantName");
			for(int i = 0; i < variantNameArray.length(); i++)
			{
				org.getOrgNameOrIdnoOrLink().add(variantNameArray.get(i));
			}
		}

		de.uni_trier.bibliothek.xml.orgs.model.generated.OrgsIdno orgsIdno = orgsTEIObjectFactory
					.createOrgsIdno();

			if (jsonObject.has("id")) {
				String dnbURL = jsonObject.getString("id");
				orgsIdno.setContent(dnbURL);
				orgsIdno.setType("URI");
				orgsIdno.setSubtype("GND");
				org.getOrgNameOrIdnoOrLink().add(orgsIdno);
			}

			if (jsonObject.has("sameAs")) {
				JSONArray sameAsArray = jsonObject.getJSONArray("sameAs");
				for (int i = 0; i < sameAsArray.length(); i++) {
					JSONObject idCollectionObject = sameAsArray.getJSONObject(i);
					JSONObject jsonObjectCollection = idCollectionObject.getJSONObject("collection");
					if(jsonObjectCollection.has("name"))
					{
						String collectionName = jsonObjectCollection.getString("name");
						if (collectionName.equals("Wikidata")) {
							de.uni_trier.bibliothek.xml.orgs.model.generated.OrgsIdno orgsIdnoWiki = orgsTEIObjectFactory
								.createOrgsIdno();
							orgsIdnoWiki.setContent(jsonObjectCollection.getString("id"));
							orgsIdnoWiki.setType("URI");
							orgsIdnoWiki.setSubtype("WIKIDATA");
							org.getOrgNameOrIdnoOrLink().add(orgsIdnoWiki);
						}
					}

				}

			}

			
			


		ArrayList<Org> arrayListOrgs = new ArrayList<Org>(orgsList);
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (Org orgListElement : arrayListOrgs) {
			Map<QName, String> attributeName = orgListElement.getOtherAttributes();
			String xmlID = attributeName.get(qname);
				if (xmlID!=null) {
					if (xmlID.equals("org_" + preferredName)) {
						org = orgListElement;
						alreadyHasTitle = true;
						List<Object> orgNameOrIdnoOrLinkObjectList = orgListElement.getOrgNameOrIdnoOrLink();
						for (Object orgNameOrIdnoOrLinkObjectElement : orgNameOrIdnoOrLinkObjectList) {
							if (orgNameOrIdnoOrLinkObjectElement instanceof de.uni_trier.bibliothek.xml.orgs.model.generated.Link) {
								de.uni_trier.bibliothek.xml.orgs.model.generated.Link linkElement = (de.uni_trier.bibliothek.xml.orgs.model.generated.Link)orgNameOrIdnoOrLinkObjectElement;
								if ((linkElement.getTarget()).equals(fileName + reference)) {
									System.out.println("alreadyhaslink: " + alreadyHasLink);
									alreadyHasLink = true;
								}
							}
						}
					}
				}
			
		}

		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.orgs.model.generated.Link link = orgsTEIObjectFactory.createLink();
			link.setTarget(fileName + reference);
			org.getOrgNameOrIdnoOrLink().add(link);
		}

		if (divFrontElement.getType() != null) {
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredName);
			de.uni_trier.bibliothek.xml.orgs.model.generated.Link linkArtikel = orgsTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredName);
			org.getOrgNameOrIdnoOrLink().add(linkArtikel);
		}
		
		if (!alreadyHasTitle) {
			Map<QName, String> attributesMap = org.getOtherAttributes();
			attributesMap.put(qname, "org_" + preferredName);

			

			
			
			de.uni_trier.bibliothek.xml.orgs.model.generated.List orgsListSuperList = new de.uni_trier.bibliothek.xml.orgs.model.generated.List();

			de.uni_trier.bibliothek.xml.orgs.model.generated.List orgsListSubList = new de.uni_trier.bibliothek.xml.orgs.model.generated.List();

			boolean hasSupercategory = false;
			boolean hasSubcategory = false;

			orgsListSuperList.setType("supercategory");
			orgsListSubList.setType("subcategory");

			if (typeTermslist.contains("AuthorityResource")) {
				typeTermslist.remove("AuthorityResource");
			}
			List<String> typeTermsListCopy = new ArrayList<String>(typeTermslist); 
			if (!typeTermsListCopy.isEmpty()) {
				for (String termString : typeTermsListCopy) {
					switch (termString) {
						case "Person":
							typeTermslist.remove("Person");
							orgsListSuperList.getItem().add("Person");
							hasSupercategory = true;
							break;
						case "Work":
							typeTermslist.remove("Work");
							orgsListSuperList.getItem().add("Work");
							hasSupercategory = true;
							break;
						case "Family":
							typeTermslist.remove("Family");
							orgsListSuperList.getItem().add("Family");
							hasSupercategory = true;
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							orgsListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							orgsListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							orgsListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							orgsListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					hasSubcategory = true;
					orgsListSubList.getItem().add(subcategory);
				}

				if (hasSupercategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.orgs.model.generated.List> orgNoteList = orgsTEIObjectFactory
							.createNoteList(orgsListSuperList);
					categoriesNote.getContent().add(orgNoteList);
				}

				if (hasSubcategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.orgs.model.generated.List> orgsNoteList = orgsTEIObjectFactory
							.createNoteList(orgsListSubList);
					categoriesNote.getContent().add(orgsNoteList);
				}
			}

			if (jsonObject.has("biographicalOrHistoricalInformation")) {
				JSONArray detailedInformationArray = jsonObject.getJSONArray("biographicalOrHistoricalInformation");
				String detailedInformationString = detailedInformationArray.getString(0);
				// String jsonObjectDetailedInformationTermString = jsonObjectDetailedInformation.getString("label");
				de.uni_trier.bibliothek.xml.orgs.model.generated.Note note = orgsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(detailedInformationString);
				org.getOrgNameOrIdnoOrLink().add(note);
			} else if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");				
				de.uni_trier.bibliothek.xml.orgs.model.generated.Note descNote = orgsTEIObjectFactory.createNote();
				descNote.setType("desc");
				descNote.getContent().add(jsonObjectBroaderTermString);	
				org.getOrgNameOrIdnoOrLink().add(descNote);			
			}
			else if (jsonObject.has("definition")) {
				JSONArray definitionArray = jsonObject.getJSONArray("definition");
				// JSONObject jsonObjectBroaderTerm = definitionArray.getString(0);
				String definitionString = definitionArray.getString(0);
				de.uni_trier.bibliothek.xml.orgs.model.generated.Note note = orgsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(definitionString);
				org.getOrgNameOrIdnoOrLink().add(note);
			}

			
			System.out.println("org eingetragen");
			orgsList.add(org);
		} 
		
		
		
		if (!alreadyHasTitle) {		
			org.getOrgNameOrIdnoOrLink().add(categoriesNote);
		}
	}





	//OBJECTS
	//OBJECTS
	//OBJECTS
	//OBJECTS
	public static void writeObjectsEntity(JSONObject jsonObject, String preferredName, List<String> typeTermslist,	DivFront divFrontElement) {
		de.uni_trier.bibliothek.xml.objects.model.generated.Object object = new de.uni_trier.bibliothek.xml.objects.model.generated.Object();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");
		de.uni_trier.bibliothek.xml.objects.model.generated.Note categoriesNote = objectsTEIObjectFactory.createNote();
		categoriesNote.setType("categories");
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
		ArrayList<de.uni_trier.bibliothek.xml.objects.model.generated.Object> arrayListObjects = new ArrayList<de.uni_trier.bibliothek.xml.objects.model.generated.Object>(objectList);
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (de.uni_trier.bibliothek.xml.objects.model.generated.Object objectListElement : arrayListObjects) {
			Map<QName, String> attributeName = objectListElement.getOtherAttributes();
			String xmlID = attributeName.get(qname);
				if (xmlID!=null) {
					if (xmlID.equals("object_" + preferredName)) {
						object = objectListElement;
						alreadyHasTitle = true;
						List<Object> orgNameOrIdnoOrLinkObjectList = objectListElement.getObjectIdentifierOrNoteOrLink();
						for (Object orgNameOrIdnoOrLinkObjectElement : orgNameOrIdnoOrLinkObjectList) {
							if (orgNameOrIdnoOrLinkObjectElement instanceof de.uni_trier.bibliothek.xml.objects.model.generated.Link) {
								de.uni_trier.bibliothek.xml.objects.model.generated.Link linkElement = (de.uni_trier.bibliothek.xml.objects.model.generated.Link)orgNameOrIdnoOrLinkObjectElement;
								if ((linkElement.getTarget()).equals(fileName + reference)) {
									// System.out.println("alreadyhaslink: " + alreadyHasLink);
									alreadyHasLink = true;
								}
							}
						}
					}
				}
			
		}
		ObjectIdentifier objectIdentifier = new ObjectIdentifier();
		if (!alreadyHasTitle) {
			Map<QName, String> attributesMap = object.getOtherAttributes();
			attributesMap.put(qname, "object_" + preferredName);
			objectIdentifier.getObjectNameOrIdno().add(preferredNameStringOriginal);
			de.uni_trier.bibliothek.xml.objects.model.generated.List objectsListSuperList = new de.uni_trier.bibliothek.xml.objects.model.generated.List();

			de.uni_trier.bibliothek.xml.objects.model.generated.List objectsListSubList = new de.uni_trier.bibliothek.xml.objects.model.generated.List();

			boolean hasSupercategory = false;
			boolean hasSubcategory = false;

			objectsListSuperList.setType("supercategory");
			objectsListSubList.setType("subcategory");

			if (typeTermslist.contains("AuthorityResource")) {
				typeTermslist.remove("AuthorityResource");
			}
			List<String> typeTermsListCopy = new ArrayList<String>(typeTermslist); 
			if (!typeTermsListCopy.isEmpty()) {
				for (String termString : typeTermsListCopy) {
					switch (termString) {
						case "Person":
							typeTermslist.remove("Person");
							objectsListSuperList.getItem().add("Person");
							hasSupercategory = true;
							break;
						case "Work":
							typeTermslist.remove("Work");
							objectsListSuperList.getItem().add("Work");
							hasSupercategory = true;
							break;
						case "Family":
							typeTermslist.remove("Family");
							objectsListSuperList.getItem().add("Family");
							hasSupercategory = true;
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							objectsListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							objectsListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							objectsListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							objectsListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					hasSubcategory = true;
					objectsListSubList.getItem().add(subcategory);
				}

				if (hasSupercategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.objects.model.generated.List> objectNoteList = objectsTEIObjectFactory
							.createNoteList(objectsListSuperList);
					categoriesNote.getContent().add(objectNoteList);
				}

				if (hasSubcategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.objects.model.generated.List> objectNoteList = objectsTEIObjectFactory
							.createNoteList(objectsListSubList);
					categoriesNote.getContent().add(objectNoteList);
				}
			}

			de.uni_trier.bibliothek.xml.objects.model.generated.ObjectIdno objectIdno = objectsTEIObjectFactory.createObjectIdno();

			if (jsonObject.has("id")) {
				String dnbURL = jsonObject.getString("id");
				objectIdno.setContent(dnbURL);
				objectIdno.setType("URI");
				objectIdno.setSubtype("GND");
				objectIdentifier.getObjectNameOrIdno().add(objectIdno);
			}

			if (jsonObject.has("sameAs")) {
				JSONArray sameAsArray = jsonObject.getJSONArray("sameAs");
				for (int i = 0; i < sameAsArray.length(); i++) {
					JSONObject idCollectionObject = sameAsArray.getJSONObject(i);
					JSONObject jsonObjectCollection = idCollectionObject.getJSONObject("collection");
					if(jsonObjectCollection.has("name"))
					{
						String collectionName = jsonObjectCollection.getString("name");
						if (collectionName.equals("Wikidata")) {
							de.uni_trier.bibliothek.xml.objects.model.generated.ObjectIdno objectIdnoWiki = objectsTEIObjectFactory.createObjectIdno();
							objectIdnoWiki.setContent(jsonObjectCollection.getString("id"));
							objectIdnoWiki.setType("URI");
							objectIdnoWiki.setSubtype("WIKIDATA");
							objectIdentifier.getObjectNameOrIdno().add(objectIdnoWiki);
						}
					}
				}
			}
			System.out.println("object eingetragen");
			object.getObjectIdentifierOrNoteOrLink().add(objectIdentifier);
			objectList.add(object);
		} 
		if (divFrontElement.getType() != null) {
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredName);
			de.uni_trier.bibliothek.xml.objects.model.generated.Link linkArtikel = objectsTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredName);
			object.getObjectIdentifierOrNoteOrLink().add(linkArtikel);
		}
		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.objects.model.generated.Link link = objectsTEIObjectFactory.createLink();
			link.setTarget(fileName + reference);
			object.getObjectIdentifierOrNoteOrLink().add(link);
		}

		if (!alreadyHasTitle) {
			object.getObjectIdentifierOrNoteOrLink().add(categoriesNote);
			if(jsonObject.has("variantName"))
			{
				JSONArray variantNameArray = jsonObject.getJSONArray("variantName");
				for(int i = 0; i < variantNameArray.length(); i++)
				{
					objectIdentifier.getObjectNameOrIdno().add(variantNameArray.get(i));
				}
			}
			if (jsonObject.has("biographicalOrHistoricalInformation")) {
				JSONArray detailedInformationArray = jsonObject.getJSONArray("biographicalOrHistoricalInformation");
				String detailedInformationString = detailedInformationArray.getString(0);
				de.uni_trier.bibliothek.xml.objects.model.generated.Note note = objectsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(detailedInformationString);
				object.getObjectIdentifierOrNoteOrLink().add(note);
			} 
			else if (jsonObject.has("usingInstructions")) {
				JSONArray usingInstructionsArray = jsonObject.getJSONArray("usingInstructions");
				String usingInstructionsString = usingInstructionsArray.getString(0);
				de.uni_trier.bibliothek.xml.objects.model.generated.Note note = objectsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(usingInstructionsString);
				object.getObjectIdentifierOrNoteOrLink().add(note);
			}
			else if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");				
				de.uni_trier.bibliothek.xml.objects.model.generated.Note descNote = objectsTEIObjectFactory.createNote();
				descNote.setType("desc");
				descNote.getContent().add(jsonObjectBroaderTermString);	
				object.getObjectIdentifierOrNoteOrLink().add(descNote);			
			}
			else if (jsonObject.has("definition")) {
				JSONArray definitionArray = jsonObject.getJSONArray("definition");
				String definitionString = definitionArray.getString(0);
				de.uni_trier.bibliothek.xml.objects.model.generated.Note note = objectsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(definitionString);
				object.getObjectIdentifierOrNoteOrLink().add(note);
			}
		}
	}




	//PLACES
	//PLACES
	//PLACES
	//PLACES
	public static void writePlacesEntity(JSONObject jsonObject, String preferredName, List<String> typeTermslist,	DivFront divFrontElement) {
		Place place = new Place();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");
		de.uni_trier.bibliothek.xml.places.model.generated.Note categoriesNote = placesTEIObjectFactory.createNote();
		categoriesNote.setType("categories");
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");

		place.getPlaceNameOrLabelOrLocation().add(placesTEIObjectFactory.createPlacePlaceName(preferredNameStringOriginal));
		if(jsonObject.has("variantName"))
		{
			JSONArray variantNameArray = jsonObject.getJSONArray("variantName");
			for(int i = 0; i < variantNameArray.length(); i++)
			{
				place.getPlaceNameOrLabelOrLocation().add(placesTEIObjectFactory.createPlacePlaceName(variantNameArray.getString(i)));
			}
		}
		ArrayList<Place> arrayListPlaces = new ArrayList<Place>(placesList);
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (Place placesListElement : arrayListPlaces) {
			Map<QName, String> attributeName = placesListElement.getOtherAttributes();
			String xmlID = attributeName.get(qname);
				if (xmlID!=null) {
					if (xmlID.equals("place_" + preferredName)) {
						place = placesListElement;
						alreadyHasTitle = true;
						List<JAXBElement<?>> placeNameOrLabelOrLocation = placesListElement.getPlaceNameOrLabelOrLocation();
						for (JAXBElement<?> placeNameOrLabelOrLocationElement : placeNameOrLabelOrLocation) {
							if (placeNameOrLabelOrLocationElement.getValue() instanceof de.uni_trier.bibliothek.xml.places.model.generated.Link) {
								de.uni_trier.bibliothek.xml.places.model.generated.Link linkElement = (de.uni_trier.bibliothek.xml.places.model.generated.Link)placeNameOrLabelOrLocationElement.getValue();
								if ((linkElement.getTarget()).equals(fileName + reference)) {
									alreadyHasLink = true;
								}
							}
						}
					}
				}
			
		}

		if (!alreadyHasTitle) {
			Map<QName, String> attributesMap = place.getOtherAttributes();
			attributesMap.put(qname, "place_" + preferredName);
			de.uni_trier.bibliothek.xml.places.model.generated.Location location = new de.uni_trier.bibliothek.xml.places.model.generated.Location();

			JAXBElement<?> locationJAXB = placesTEIObjectFactory.createPlaceLocation(location);
			boolean hasLocation = false;

			if(jsonObject.has("hasGeometry"))
			{
				String geoCoordinates ="";
				JSONArray hasGeometry = jsonObject.getJSONArray("hasGeometry");
				for(int i = 0; i < hasGeometry.length(); i++)
				{
					JSONObject asWKT = hasGeometry.getJSONObject(i);
					if(asWKT.has("asWKT"))
					{
						hasLocation = true;
						JSONArray asWKTarray = asWKT.getJSONArray("asWKT");
						geoCoordinates = asWKTarray.getString(0);
					}
			
					
					geoCoordinates = geoCoordinates.substring(8, 31);
					location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationGeo(geoCoordinates));
				}
				
			}

			if(jsonObject.has("geographicAreaCode"))
			{
				String country ="";
				String region ="";
				JSONArray geographicAreaCode = jsonObject.getJSONArray("geographicAreaCode");
				boolean hasCountry = false;
				for(int i = 0; i < geographicAreaCode.length(); i++)
				{
					JSONObject geogrObject = geographicAreaCode.getJSONObject(i);
					if(geogrObject.has("id"))
					{
						hasLocation = true;
						String geographicURL = geogrObject.getString("id");
						geographicURL = geographicURL.substring(59,geographicURL.length());
						System.out.println("geographic: " + geographicURL);

						if(geographicURL.contains("DE") && !hasCountry)
						{
							location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationCountry("Deutschland"));
							hasCountry = true;
						}
						else if(geographicURL.contains("AT") && !hasCountry)
						{
							location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationCountry("Österreich"));
							hasCountry = true;
						}
						else if(geographicURL.contains("CH") && !hasCountry)
						{
							location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationCountry("Schweiz"));
							hasCountry = true;
						}

						if(geographicURL.contains("AT"))
						{						
							if(geographicURL.contains("-1"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Burgenland"));
							}
							else if(geographicURL.contains("-2"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Kärnten"));
							}
							else if(geographicURL.contains("-3"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Niederösterreich"));
							}
							else if(geographicURL.contains("-4"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Oberösterreich"));
							}
							else if(geographicURL.contains("-5"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Salzburg"));
							}
							else if(geographicURL.contains("-6"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Steiermark"));
							}
							else if(geographicURL.contains("-7"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Tirol"));
							}
							else if(geographicURL.contains("-8"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Vorarlberg"));
							}
							else if(geographicURL.contains("-9"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Wien"));
							}
							
						}

						if(geographicURL.contains("CH"))
						{						
							if(geographicURL.contains("AG"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Aargau"));
							}
							else if(geographicURL.contains("AI"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Appenzell <Innerrhoden>"));
							}
							else if(geographicURL.contains("AR"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Appenzell <Ausserrhoden>"));
							}
							else if(geographicURL.contains("BE"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Bern <Kanton>"));
							}
							else if(geographicURL.contains("BL"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Basel-Landschaft"));
							}
							else if(geographicURL.contains("BS"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Basel"));
							}
							else if(geographicURL.contains("FR"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Freiburg <Üechtland, Kanton>"));
							}
							else if(geographicURL.contains("GE"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Genf <Kanton>"));
							}
							else if(geographicURL.contains("GL"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Glarus <Kanton>"));
							}
							else if(geographicURL.contains("GR"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Graubünden"));
							}
							else if(geographicURL.contains("JU"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Jura <Kanton>"));
							}
							else if(geographicURL.contains("LU"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Luzern <Kanton>"));
							}
							else if(geographicURL.contains("NE"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Neuenburg <Schweiz, Kanton>"));
							}
							else if(geographicURL.contains("NW"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Nidwalden"));
							}
							else if(geographicURL.contains("OW"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Obwalden"));
							}
							else if(geographicURL.contains("SG"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Sankt Gallen <Kanton>"));
							}
							else if(geographicURL.contains("SH"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Schaffhausen <Kanton>"));
							}
							else if(geographicURL.contains("SO"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Solothurn <Kanton>"));
							}
							else if(geographicURL.contains("SZ"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Schwyz"));
							}
							else if(geographicURL.contains("TG"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Thurgau"));
							}
							else if(geographicURL.contains("TI"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Tessin"));
							}
							else if(geographicURL.contains("UR"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Uri"));
							}
							else if(geographicURL.contains("VD"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Waadt"));
							}
							else if(geographicURL.contains("VS"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Wallis"));
							}
							else if(geographicURL.contains("ZG"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Zug <Kanton>"));
							}
							else if(geographicURL.contains("ZH"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Zürich <Kanton>"));
							}
						}

						if(geographicURL.contains("DE"))
						{						
							if(geographicURL.contains("BB"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Brandenburg"));
							}
							else if(geographicURL.contains("BE"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Berlin"));
							}
							else if(geographicURL.contains("BW"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Baden-Württemberg"));
							}
							else if(geographicURL.contains("BY"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Bayern"));
							}
							else if(geographicURL.contains("HB"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Bremen"));
							}
							else if(geographicURL.contains("HE"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Hessen"));
							}
							else if(geographicURL.contains("HH"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Hamburg"));
							}
							else if(geographicURL.contains("MV"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Mecklenburg-Vorpommern"));
							}
							else if(geographicURL.contains("NI"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Niedersachsen"));
							}
							else if(geographicURL.contains("NW"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Nordrhein-Westfalen"));
							}
							else if(geographicURL.contains("RP"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Rheinland-Pfalz"));
							}
							else if(geographicURL.contains("SH"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Schleswig-Holstein"));
							}
							else if(geographicURL.contains("SL"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Saarland"));
							}
							else if(geographicURL.contains("SN"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Sachsen"));
							}
							else if(geographicURL.contains("ST"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Sachsen-Anhalt"));
							}
							else if(geographicURL.contains("TH"))
							{
								location.getRegionOrGeoOrCountry().add(placesTEIObjectFactory.createLocationRegion("Thüringen"));
							}	
						}
					}
				}
				
			}

			if(hasLocation)
			{
				place.getPlaceNameOrLabelOrLocation().add(locationJAXB);
			}
			

			de.uni_trier.bibliothek.xml.places.model.generated.List placesListSuperList = new de.uni_trier.bibliothek.xml.places.model.generated.List();

			de.uni_trier.bibliothek.xml.places.model.generated.List placesListSubList = new de.uni_trier.bibliothek.xml.places.model.generated.List();

			boolean hasSupercategory = false;
			boolean hasSubcategory = false;

			placesListSuperList.setType("supercategory");
			placesListSubList.setType("subcategory");

			if (typeTermslist.contains("AuthorityResource")) {
				typeTermslist.remove("AuthorityResource");
			}
			List<String> typeTermsListCopy = new ArrayList<String>(typeTermslist); 
			if (!typeTermsListCopy.isEmpty()) {
				for (String termString : typeTermsListCopy) {
					switch (termString) {
						case "Person":
							typeTermslist.remove("Person");
							placesListSuperList.getItem().add("Person");
							hasSupercategory = true;
							break;
						case "Work":
							typeTermslist.remove("Work");
							placesListSuperList.getItem().add("Work");
							hasSupercategory = true;
							break;
						case "Family":
							typeTermslist.remove("Family");
							placesListSuperList.getItem().add("Family");
							hasSupercategory = true;
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							placesListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							placesListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							placesListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							placesListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					hasSubcategory = true;
					placesListSubList.getItem().add(subcategory);
				}

				if (hasSupercategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.List> placesNoteList = placesTEIObjectFactory
							.createNoteList(placesListSuperList);
					categoriesNote.getContent().add(placesNoteList);
				}

				if (hasSubcategory) {
					JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.List> placesNoteList = placesTEIObjectFactory
							.createNoteList(placesListSubList);
					categoriesNote.getContent().add(placesNoteList);
				}

				
			}

			
			if (jsonObject.has("id")) {
				PlaceIdno placesIdno = new PlaceIdno();
				String dnbURL = jsonObject.getString("id");
				placesIdno.setContent(dnbURL);
				placesIdno.setType("URI");
				placesIdno.setSubtype("GND");
				JAXBElement<PlaceIdno> placesIdnoJAXB = placesTEIObjectFactory.createPlaceIdno(placesIdno);
				place.getPlaceNameOrLabelOrLocation().add(placesIdnoJAXB);
			}

			if (jsonObject.has("sameAs")) {
				JSONArray sameAsArray = jsonObject.getJSONArray("sameAs");
				for (int i = 0; i < sameAsArray.length(); i++) {
					JSONObject idCollectionObject = sameAsArray.getJSONObject(i);
					JSONObject jsonObjectCollection = idCollectionObject.getJSONObject("collection");
					if(jsonObjectCollection.has("name"))
					{
						String collectionName = jsonObjectCollection.getString("name");
						if (collectionName.equals("Wikidata")) {
							PlaceIdno placesIdnoWiki = new PlaceIdno();							
							placesIdnoWiki.setContent(jsonObjectCollection.getString("id"));
							placesIdnoWiki.setType("URI");
							placesIdnoWiki.setSubtype("WIKIDATA");
							JAXBElement<PlaceIdno> placesIdnoWikiJAXB = placesTEIObjectFactory.createPlaceIdno(placesIdnoWiki);
							place.getPlaceNameOrLabelOrLocation().add(placesIdnoWikiJAXB);
						}
					}
				}
			}
		} 
		if (divFrontElement.getType() != null) {
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredName);
			de.uni_trier.bibliothek.xml.places.model.generated.Link artikelLink = new de.uni_trier.bibliothek.xml.places.model.generated.Link();
			artikelLink.setTarget(fileName + reference + "_art_" + preferredName);
			JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.Link> artikelLinkJAXB = placesTEIObjectFactory.createPlaceLink(artikelLink);
			place.getPlaceNameOrLabelOrLocation().add(artikelLinkJAXB);
		}
		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.places.model.generated.Link bandLink = new de.uni_trier.bibliothek.xml.places.model.generated.Link();
			bandLink.setTarget(fileName + reference);
			JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.Link> bandLinkJAXB = placesTEIObjectFactory.createPlaceLink(bandLink);
			place.getPlaceNameOrLabelOrLocation().add(bandLinkJAXB);
		}

		if (!alreadyHasTitle) {
			System.out.println("place eingetragen");
			placesList.add(place);
			if (jsonObject.has("biographicalOrHistoricalInformation")) {
				de.uni_trier.bibliothek.xml.places.model.generated.Note note = new de.uni_trier.bibliothek.xml.places.model.generated.Note();
				JSONArray detailedInformationArray = jsonObject.getJSONArray("biographicalOrHistoricalInformation");
				String detailedInformationString = detailedInformationArray.getString(0);
				// de.uni_trier.bibliothek.xml.places.model.generated.Note note = placesTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(detailedInformationString);
				JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.Note> categoriesNoteJAXB = placesTEIObjectFactory.createPlaceNote(note);
				place.getPlaceNameOrLabelOrLocation().add(categoriesNoteJAXB);
			} 
			else if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");				
				de.uni_trier.bibliothek.xml.places.model.generated.Note note = new de.uni_trier.bibliothek.xml.places.model.generated.Note();
				note.setType("desc");
				note.getContent().add(jsonObjectBroaderTermString);	
				JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.Note> categoriesNoteJAXB = placesTEIObjectFactory.createPlaceNote(note);
				place.getPlaceNameOrLabelOrLocation().add(categoriesNoteJAXB);			
			}
			else if (jsonObject.has("definition")) {
				JSONArray definitionArray = jsonObject.getJSONArray("definition");
				String definitionString = definitionArray.getString(0);
				de.uni_trier.bibliothek.xml.places.model.generated.Note note = placesTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(definitionString);
				JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.Note> categoriesNoteJAXB = placesTEIObjectFactory.createPlaceNote(note);
				place.getPlaceNameOrLabelOrLocation().add(categoriesNoteJAXB);
			}

			JAXBElement<de.uni_trier.bibliothek.xml.places.model.generated.Note> categoriesNoteJAXB = placesTEIObjectFactory.createPlaceNote(categoriesNote);
				place.getPlaceNameOrLabelOrLocation().add(categoriesNoteJAXB);


		}
		
	}
}