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
import de.uni_trier.bibliothek.xml.orgs.model.generated.ListOrg;
import de.uni_trier.bibliothek.xml.orgs.model.generated.Org;
import de.uni_trier.bibliothek.xml.persons.model.generated.Birth;
import de.uni_trier.bibliothek.xml.persons.model.generated.Death;
import de.uni_trier.bibliothek.xml.persons.model.generated.ListPerson;
import de.uni_trier.bibliothek.xml.persons.model.generated.PersName;
import de.uni_trier.bibliothek.xml.persons.model.generated.Person;
import de.uni_trier.bibliothek.xml.places.model.generated.ListPlace;
import de.uni_trier.bibliothek.xml.places.model.generated.Note;
import de.uni_trier.bibliothek.xml.places.model.generated.Place;
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
		// System.out.println("listBibl: " + listBibl.toString());

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
		String preferredNameString = preferredNameStringOriginal.replaceAll(", ", "_");
		preferredNameString = preferredNameString.replaceAll(" ", "_");
		preferredNameString = preferredNameString.replaceAll(",", "_");
		System.out.println("PreferredNameString ist: " + preferredNameString);

		Map<QName, String> attributesMap = bibl.getOtherAttributes();
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
		attributesMap.put(qname, "listBibl_" + preferredNameString);

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
			List<Object> titleOrNoteOrLink = listBiblElement.getTitleOrNoteOrLink();
			for (Object titleOrNoteOrLinkObject : titleOrNoteOrLink) {
				if (titleOrNoteOrLinkObject instanceof String) {
					if (titleOrNoteOrLinkObject.equals(preferredNameStringOriginal)) {
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
		}
		if (!alreadyHasTitle) {
			if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");
				de.uni_trier.bibliothek.xml.listBibl.model.generated.Note note = listBiblTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(jsonObjectBroaderTermString);
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
					// System.out.println("termString: " + termString);
					switch (termString) {

						case "Person":
							typeTermslist.remove("Person");
							biblListSuperList.getItem().add("Person");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Person");
							break;
						case "Work":
							typeTermslist.remove("Work");
							biblListSuperList.getItem().add("Work");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Work");
							break;
						case "Family":
							typeTermslist.remove("Family");
							biblListSuperList.getItem().add("Family");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Family");
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							biblListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "ConferenceOrEvent");
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							biblListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "PlaceOrGeographicName");
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							biblListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "CorporateBody");
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							biblListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "SubjectHeading");
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					// System.out.println("subcategory: " + subcategory);
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
				// System.out.println("sameAsArray: " + sameAsArray);
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
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredNameString);
			de.uni_trier.bibliothek.xml.listBibl.model.generated.Link linkArtikel = listBiblTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredNameString);
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
		//todo person
		
		Person person = new Person();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");

		if(preferredNameStringOriginal.contains("Familie")){
			System.out.println("preferred name contains family");
			PersName personName = new PersName();
			personName.setSurname(preferredNameStringOriginal);
			// System.out.println("0-6 substring in preferredname" + preferredNameStringOriginal.substring(0, 6));
			// if(preferredNameStringOriginal.substring(0, 7).equals("Familie"))
			// {
			// 	System.out.println("first substring is familie");
			// 	String surname = preferredNameStringOriginal.substring(8, preferredNameStringOriginal.length());
			// 	personName.setSurname(surname);
			// }
			// else{
			// 	System.out.println("familie nach komma");
			// 	String surname = preferredNameStringOriginal.substring(0, preferredNameStringOriginal.indexOf(","));
			// 	personName.setSurname(surname);
			// }
			person.getPersNameOrNoteOrBirth().add(personName);

		}
		else if(preferredNameStringOriginal.contains("Adelsgeschlecht")){
			PersName personName = new PersName();
			personName.setSurname(preferredNameStringOriginal);
			person.getPersNameOrNoteOrBirth().add(personName);
		}

		// System.out.println(preferredNameStringOriginal + "");
		String preferredNameString = preferredNameStringOriginal.replaceAll(", ", "_");
		preferredNameString = preferredNameString.replaceAll(" ", "_");
		preferredNameString = preferredNameString.replaceAll("-", "_");
		preferredNameString = preferredNameString.replaceAll(",", "_");
		preferredNameString = preferredNameString.replaceAll(":", "");
		preferredNameString = preferredNameString.replaceAll("___", "_");
		preferredNameString = preferredNameString.replaceAll("__", "_");
		// System.out.println("PreferredNameString ist: " + preferredNameString);

		

		//family: get preferred name, get nachname, wenn family im string enthalten ist
		if(jsonObject.has("titleOfNobility"))
		{
			JSONArray titleOfNobilityArray = jsonObject.getJSONArray("titleOfNobility");
			JSONObject titleOfNobilityObject = titleOfNobilityArray.getJSONObject(0);
			if(titleOfNobilityObject.has("label"))
			{
				person.setRole(titleOfNobilityObject.getString("label"));
			}
			// JSONArray titleOfNobilityArray = titleOfNobility.getJSONArray("titleOfNobility");
		}

		if(jsonObject.has("preferredNameEntityForThePerson"))
		{
			PersName personName = new PersName();
			JSONObject preferredNameEntityForThePerson = jsonObject.getJSONObject("preferredNameEntityForThePerson");
			// System.out.println("preferredNameEntityForThePerson ist: " + preferredNameEntityForThePerson);

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
					if (xmlID.equals("person_" + preferredNameString)) {
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
			// }
		}
		if (!alreadyHasTitle) {

			Map<QName, String> attributesMap = person.getOtherAttributes();
			QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
			attributesMap.put(qname, "person_" + preferredNameString);
			Death death = new Death();
			if(jsonObject.has("dateOfDeath"))
			{
				JSONArray deathDateArray = jsonObject.getJSONArray("dateOfDeath");
				String deathDateArrayString = deathDateArray.getString(0);
				// char minus = '-';
				// String deathDateArrayStringYear = "";
				// if(deathDateArrayString.charAt(0)==(minus))
				// {
				// 	deathDateArrayStringYear = deathDateArrayString;
				// }
				// else{
				// 	for (int i = 0; i < deathDateArrayString.length(); i++) {
				// 	char c = deathDateArrayString.charAt(i); 
					
				// 	if(c=='-')
				// 	{
				// 		break;
				// 	}
				// 	else{
				// 		deathDateArrayStringYear = deathDateArrayStringYear + c;
				// 	}
					
				// 	// System.out.print(c);
				// }

				// }
				
				// System.out.println("deathDateArrayString: " + deathDateArrayStringYear);
				
				// BigInteger deathDateBigInteger = new BigInteger(deathDateArrayStringYear);
				death.setWhenCustom(deathDateArrayString);
				person.getPersNameOrNoteOrBirth().add(death);
			}

			if(jsonObject.has("dateOfBirth"))
			{
				JSONArray birthDateArray = jsonObject.getJSONArray("dateOfBirth");
				String birthArrayString = birthDateArray.getString(0);
				// String birthArrayStringYear = "";
				// char minus = '-';
				// // String birthDateArrayStringYear = "";
				// if(birthArrayString.charAt(0)==(minus))
				// {
				// 	birthArrayStringYear = birthArrayString;
				// }
				// for (int i = 0; i < birthArrayString.length(); i++) {
				// 	char c = birthArrayString.charAt(i); 
					
				// 	if(c=='-')
				// 	{
				// 		break;
				// 	}
				// 	else{
				// 		birthArrayStringYear = birthArrayStringYear + c;
				// 	}
					
				// 	// System.out.print(c);
				// }
				// // System.out.println("birthDateArrayString: " + birthArrayString);
				Birth birth = new Birth();
				// BigInteger deathDateBigInteger = new BigInteger(birthArrayStringYear);
				birth.setWhenCustom(birthArrayString);
				person.getPersNameOrNoteOrBirth().add(birth);
			}


			if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");
				de.uni_trier.bibliothek.xml.persons.model.generated.Note note = personsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(jsonObjectBroaderTermString);
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
			System.out.println("divFrontElement: " + divFrontElement.getType());
			//bis hierhin funktionierts
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredNameString);
			de.uni_trier.bibliothek.xml.persons.model.generated.Link linkArtikel = personsTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredNameString);
			System.out.println("linkTarget: " + linkArtikel.getTarget());


			// Map<QName, String> attributesMap = divFrontElement.getOtherAttributes();
			// QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
			// attributesMap.put(qname, "person" + preferredNameString);


			person.getPersNameOrNoteOrBirth().add(linkArtikel);
			System.out.println("linkTarget added: " + linkArtikel.getTarget());
		}
		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.persons.model.generated.Link link = personsTEIObjectFactory.createLink();
			link.setTarget(fileName + reference);
			System.out.println("bandlik added: " + link.getTarget());
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
		String preferredNameString = preferredNameStringOriginal.replaceAll(", ", "_");
		preferredNameString = preferredNameString.replaceAll(">", "");
		preferredNameString = preferredNameString.replaceAll("<", "_");
		// preferredNameString = preferredNameString.replaceAll("<", "_");
		preferredNameString = preferredNameString.replaceAll(" ", "_");
		preferredNameString = preferredNameString.replaceAll(",", "_");
		preferredNameString = preferredNameString.replaceAll("___", "_");
		preferredNameString = preferredNameString.replaceAll("__", "_");
		
		System.out.println("PreferredNameString ist: " + preferredNameString);

		Map<QName, String> attributesMap = event.getOtherAttributes();
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
		attributesMap.put(qname, "event_" + preferredNameString);

		event.getLabelOrDescOrNote().add(preferredNameStringOriginal);
		if(jsonObject.has("variantName"))
		{
			JSONArray variantNameArray = jsonObject.getJSONArray("variantName");
			for(int i = 0; i < variantNameArray.length(); i++)
			{
				System.out.println("variantName von event ist: " + variantNameArray.get(i));
				event.getLabelOrDescOrNote().add(variantNameArray.get(i));
			}
			
		}
		ArrayList<Event> arrayListEvent = new ArrayList<Event>(eventList);
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (Event eventListElement : arrayListEvent) {
			List<Object> labelOrDescOrNote = eventListElement.getLabelOrDescOrNote();
			for (Object labelOrDescOrNoteObject : labelOrDescOrNote) {
				if (labelOrDescOrNoteObject instanceof String) {
					if (labelOrDescOrNoteObject.equals(preferredNameStringOriginal)) {
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
		}
		if (!alreadyHasTitle) {
			// System.out.println("");
			Desc descEvent = new Desc();
			if(jsonObject.has("dateOfProduction"))
			{
				EventDate eventsDate = new EventDate();
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfProduction");
				String dateOfProduction = jsonArray.getString(0);
				if (jsonArray.length() > 1 )
				{
					dateOfProduction = jsonArray.getString(1);
				}
				eventsDate.setWhenCustom(dateOfProduction);
				descEvent.getDate().add(eventsDate);
			}
			if(jsonObject.has("dateOfEstablishment"))
			{
				EventDate eventsDate = new EventDate();
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfEstablishment");
				String dateOfEstablishment = jsonArray.getString(0);
				if (jsonArray.length() > 1 )
				{
					dateOfEstablishment = jsonArray.getString(1);
				}
				eventsDate.setFromCustom(dateOfEstablishment);
				descEvent.getDate().add(eventsDate);
			}
			if(jsonObject.has("dateOfTermination"))
			{
				EventDate eventsDate = new EventDate();
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfTermination");
				String dateOfTermination = jsonArray.getString(0);
				if (jsonArray.length() > 1 )
				{
					dateOfTermination = jsonArray.getString(1);
				}
				eventsDate.setToCustom(dateOfTermination);
				descEvent.getDate().add(eventsDate);
			}
			if(jsonObject.has("dateOfEstablishmentAndTermination"))
			{
				EventDate eventsDate = new EventDate();
				JSONArray jsonArray = jsonObject.getJSONArray("dateOfEstablishmentAndTermination");
				String dateOfProduction = jsonArray.getString(0);
				if (jsonArray.length() > 1 )
				{
					dateOfProduction = jsonArray.getString(1);
				}
				eventsDate.setWhenCustom(dateOfProduction);
				descEvent.getDate().add(eventsDate);
			}
			if(jsonObject.has("associatedDate"))
			{
				EventDate eventsDate = new EventDate();
				JSONArray jsonArray = jsonObject.getJSONArray("associatedDate");
				String dateOfProduction = jsonArray.getString(0);
				if (jsonArray.length() > 1 )
				{
					dateOfProduction = jsonArray.getString(1);
				}
				eventsDate.setWhenCustom(dateOfProduction);
				descEvent.getDate().add(eventsDate);
			}
			

			


			if(!descEvent.getDate().isEmpty())
			{
				event.getLabelOrDescOrNote().add(descEvent);
			}
			if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");
				de.uni_trier.bibliothek.xml.events.model.generated.Note note = eventsTEIObjectFactory.createNote();
				note.setType("desc");
				note.getContent().add(jsonObjectBroaderTermString);
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
					// System.out.println("termString: " + termString);
					switch (termString) {

						case "Person":
							typeTermslist.remove("Person");
							eventsListSuperList.getItem().add("Person");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Person");
							break;
						case "Work":
							typeTermslist.remove("Work");
							eventsListSuperList.getItem().add("Work");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Work");
							break;
						case "Family":
							typeTermslist.remove("Family");
							eventsListSuperList.getItem().add("Family");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Family");
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							eventsListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "ConferenceOrEvent");
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							eventsListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "PlaceOrGeographicName");
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							eventsListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "CorporateBody");
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							eventsListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "SubjectHeading");
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					// System.out.println("subcategory: " + subcategory);
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
				// System.out.println("sameAsArray: " + sameAsArray);
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
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredNameString);
			de.uni_trier.bibliothek.xml.events.model.generated.Link linkArtikel = eventsTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredNameString);
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
	public static void writeOrgsEntity(JSONObject jsonObject, String preferredName, List<String> typeTermslist,
			DivFront divFrontElement) {

				//warum immer wieder neuer eintrag???
		Org org = new Org();
		String preferredNameStringOriginal = jsonObject.getString("preferredName");
		String preferredNameString = preferredNameStringOriginal.replaceAll(", ", "_");
		preferredNameString = preferredNameString.replaceAll(" ", "_");
		preferredNameString = preferredNameString.replaceAll("v.", "v");
		preferredNameString = preferredNameString.replaceAll("Chr.", "Chr");
		preferredNameString = preferredNameString.replaceAll("<", "_");
		preferredNameString = preferredNameString.replaceAll(">", "");
		preferredNameString = preferredNameString.replaceAll(",", "_");
		preferredNameString = preferredNameString.replaceAll("[()]", "");
		System.out.println("PreferredNameString ist: " + preferredNameString);

		de.uni_trier.bibliothek.xml.orgs.model.generated.Note descNote = orgsTEIObjectFactory.createNote();
		descNote.setType("desc");
		de.uni_trier.bibliothek.xml.orgs.model.generated.Note categoriesNote = orgsTEIObjectFactory.createNote();
		categoriesNote.setType("categories");

		Map<QName, String> attributesMap = org.getOtherAttributes();
		QName qname = new QName("http://www.w3.org/XML/1998/namespace", "id");
		attributesMap.put(qname, "org_" + preferredNameString);




		// System.out.println("PreferredNameString ist: " + preferredNameString);

		org.getOrgNameOrIdnoOrLink().add(preferredNameStringOriginal);
		if(jsonObject.has("variantName"))
		{
			JSONArray variantNameArray = jsonObject.getJSONArray("variantName");
			for(int i = 0; i < variantNameArray.length(); i++)
			{
				org.getOrgNameOrIdnoOrLink().add(variantNameArray.get(i));
			}
			
		}

		

		ArrayList<Org> arrayListOrgs = new ArrayList<Org>(orgsList);
		boolean alreadyHasLink = false;
		boolean alreadyHasTitle = false;
		for (Org orgListElement : arrayListOrgs) {
			List<Object> orgNameOrIdnoOrLink = orgListElement.getOrgNameOrIdnoOrLink();
			for (Object orgNameOrIdnoOrLinkObject : orgNameOrIdnoOrLink) {
				if (orgNameOrIdnoOrLinkObject instanceof String) {
					if (orgNameOrIdnoOrLinkObject.equals(preferredNameStringOriginal)) {
						
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
		}
		if (!alreadyHasTitle) {
			if (jsonObject.has("broaderTermInstantial")) {
				JSONArray broaderTerm = jsonObject.getJSONArray("broaderTermInstantial");
				JSONObject jsonObjectBroaderTerm = broaderTerm.getJSONObject(0);
				String jsonObjectBroaderTermString = jsonObjectBroaderTerm.getString("label");
				
				descNote.setType("desc");
				descNote.getContent().add(jsonObjectBroaderTermString);
				
			}
			
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
					// System.out.println("termString: " + termString);
					switch (termString) {

						case "Person":
							typeTermslist.remove("Person");
							orgsListSuperList.getItem().add("Person");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Person");
							break;
						case "Work":
							typeTermslist.remove("Work");
							orgsListSuperList.getItem().add("Work");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Work");
							break;
						case "Family":
							typeTermslist.remove("Family");
							orgsListSuperList.getItem().add("Family");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "Family");
							break;
						case "ConferenceOrEvent":
							typeTermslist.remove("ConferenceOrEvent");
							orgsListSuperList.getItem().add("ConferenceOrEvent");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "ConferenceOrEvent");
							break;
						case "PlaceOrGeographicName":
							typeTermslist.remove("PlaceOrGeographicName");
							orgsListSuperList.getItem().add("PlaceOrGeographicName");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "PlaceOrGeographicName");
							break;
						case "CorporateBody":
							typeTermslist.remove("CorporateBody");
							orgsListSuperList.getItem().add("CorporateBody");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "CorporateBody");
							break;
						case "SubjectHeading":
							typeTermslist.remove("SubjectHeading");
							orgsListSuperList.getItem().add("SubjectHeading");
							hasSupercategory = true;
							// System.out.println("Item to add: " + "SubjectHeading");
							break;
					}
				}

				for (String subcategory : typeTermslist) {
					// System.out.println("subcategory: " + subcategory);
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
				// System.out.println("sameAsArray: " + sameAsArray);
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
			System.out.println("org eingetragen");
			orgsList.add(org);
		} 
		if (divFrontElement.getType() != null) {
			String referenceWithoutHash = reference.substring(1, reference.length());
			divFrontElement.setId(referenceWithoutHash + "_art_" + preferredNameString);
			de.uni_trier.bibliothek.xml.orgs.model.generated.Link linkArtikel = orgsTEIObjectFactory.createLink();
			linkArtikel.setTarget(fileName + reference + "_art_" + preferredNameString);
			org.getOrgNameOrIdnoOrLink().add(linkArtikel);
		}
		if (!alreadyHasLink) {
			de.uni_trier.bibliothek.xml.orgs.model.generated.Link link = orgsTEIObjectFactory.createLink();
			link.setTarget(fileName + reference);
			org.getOrgNameOrIdnoOrLink().add(link);
		}

		if (!alreadyHasTitle) {
			org.getOrgNameOrIdnoOrLink().add(descNote);
			org.getOrgNameOrIdnoOrLink().add(categoriesNote);
		}
	}
}