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
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import de.uni_trier.bibliothek.xml.tei.model.generated.Back;
import de.uni_trier.bibliothek.xml.tei.model.generated.Body;
import de.uni_trier.bibliothek.xml.tei.model.generated.Choice;
import de.uni_trier.bibliothek.xml.tei.model.generated.DivFront;
import de.uni_trier.bibliothek.xml.tei.model.generated.DocImprint;
import de.uni_trier.bibliothek.xml.tei.model.generated.DocTitle;
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
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import de.uni_trier.bibliothek.xml.tei.model.generated.Table;
import de.uni_trier.bibliothek.xml.tei.model.generated.Text;
import de.uni_trier.bibliothek.xml.tei.model.generated.TitlePage;
import de.uni_trier.bibliothek.xml.tei.model.generated.TitlePart;
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

	public static List<Object> enrichList(List<Object> originalObjectTEIList) throws IOException {
		objectTEIList = originalObjectTEIList;
		createJavaObjects();
		Text originText = originalTEI.getText();
		checkText(originText);

		// JAXBElement<String> persNameJAXB;
		// persNameJAXB = personsTEIObjectFactory.createPersonPersName("persname2");

		return objectTEIList;
	}

	public static void checkText(Text text) throws IOException {
		Front front = text.getFront();
		checkFront(front);

		if (text.getBody() != null) {
			checkBody(text.getBody());
		}

		if (text.getGroup() != null) {
			checkOuterGroup(text.getGroup());
		}

		if (text.getBack() != null) {
			checkBack(text.getBack());
		}
	}

	public static void checkFront(Front front) throws IOException {
		List<Object> pbOrDivOrTitlePageList = front.getPbOrDivOrTitlePage();
		for (Object pbOrDivOrTitlePage : pbOrDivOrTitlePageList) {
			if (pbOrDivOrTitlePage instanceof DivFront) {
				DivFront divFrontElement = (DivFront) pbOrDivOrTitlePage;
				checkDivFront(divFrontElement);
			} else if (pbOrDivOrTitlePage instanceof TitlePage) {
				TitlePage titlePage = (TitlePage) pbOrDivOrTitlePage;
				checkTitlePage(titlePage);
			}

		}
	}

	public static void checkBody(Body body) throws IOException {
		List<JAXBElement<?>> getDivOrPbOrLbList = body.getDivOrPbOrLb();
		for (JAXBElement<?> divOrPbOrLbListElement : getDivOrPbOrLbList) {
			if (divOrPbOrLbListElement.getValue() instanceof DivFront) {
				DivFront divFrontElement = (DivFront) divOrPbOrLbListElement.getValue();
				checkDivFront(divFrontElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Choice) {
				Choice choiceElement = (Choice) divOrPbOrLbListElement.getValue();
				checkChoice(choiceElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof NameGND) {
				NameGND nameElement = (NameGND) divOrPbOrLbListElement.getValue();
				checkNameGnd(nameElement, null, null);
			} else if (divOrPbOrLbListElement.getValue() instanceof SourceGND) {
				SourceGND sourceElement = (SourceGND) divOrPbOrLbListElement.getValue();
				checkSourceGND(sourceElement, null, null);
			} else if (divOrPbOrLbListElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) divOrPbOrLbListElement.getValue();
				checkLbEtc(lbEtcElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divOrPbOrLbListElement.getValue();
				checkTable(tableElement);
			}

		}
	}

	public static void checkOuterGroup(OuterGroup group) throws IOException {
		List<InnerGroup> innerGroupList = group.getGroup();
		for (InnerGroup innerGroup : innerGroupList) {
			checkInnerGroup(innerGroup);
		}

	}

	public static void checkInnerGroup(InnerGroup innerGroup) throws IOException {
		List<GroupText> groupText = innerGroup.getText();
		for (GroupText groupTextElement : groupText) {
			checkInnerGroupText(groupTextElement);
		}

	}

	public static void checkInnerGroupText(GroupText groupText) throws IOException {

		if (groupText.getBody() != null) {
			checkGroupBody(groupText.getBody());
		}
		if (groupText.getFront() != null) {
			checkFront(groupText.getFront());
		}

	}

	private static void checkGroupBody(GroupBody groupBody) throws IOException {
		List<JAXBElement<?>> getDivOrPbOrLbList = groupBody.getDivOrPbOrLb();
		for (JAXBElement<?> divOrPbOrLbListElement : getDivOrPbOrLbList) {
			if (divOrPbOrLbListElement.getValue() instanceof DivFront) {
				DivFront divFrontElement = (DivFront) divOrPbOrLbListElement.getValue();
				checkDivFront(divFrontElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Choice) {
				Choice choiceElement = (Choice) divOrPbOrLbListElement.getValue();
				checkChoice(choiceElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof NameGND) {
				NameGND nameElement = (NameGND) divOrPbOrLbListElement.getValue();
				checkNameGnd(nameElement, null, null);
			} else if (divOrPbOrLbListElement.getValue() instanceof SourceGND) {
				SourceGND sourceElement = (SourceGND) divOrPbOrLbListElement.getValue();
				checkSourceGND(sourceElement, null, null);
			} else if (divOrPbOrLbListElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) divOrPbOrLbListElement.getValue();
				checkLbEtc(lbEtcElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divOrPbOrLbListElement.getValue();
				checkTable(tableElement);
			}

		}
	}

	public static void checkBack(Back back) throws IOException {
		List<JAXBElement<?>> divOrPbOrLb = back.getDivOrPbOrLb();
		for (JAXBElement<?> divOrPbOrLbElement : divOrPbOrLb) {
			if (divOrPbOrLbElement.getValue() instanceof DivFront) {
				DivFront divFrontElement = (DivFront) divOrPbOrLbElement.getValue();
				checkDivFront(divFrontElement);
			} else if (divOrPbOrLbElement.getValue() instanceof Table) {
				Table table = (Table) divOrPbOrLbElement.getValue();
				checkTable(table);
			} else if (divOrPbOrLbElement.getValue() instanceof de.uni_trier.bibliothek.xml.tei.model.generated.List) {
				de.uni_trier.bibliothek.xml.tei.model.generated.List list = (de.uni_trier.bibliothek.xml.tei.model.generated.List) divOrPbOrLbElement
						.getValue();
				checkList(list);
			} else if (divOrPbOrLbElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) divOrPbOrLbElement.getValue();
				checkLbEtc(lbEtcElement);
			}

		}
	}

	public static void checkList(de.uni_trier.bibliothek.xml.tei.model.generated.List list) throws IOException {
		List<Serializable> listElement = list.getContent();
		for (Object listElementObject : listElement) {
			if (!(listElementObject instanceof String)) {

				JAXBElement<?> jaxbElement = (JAXBElement<?>) listElementObject;
				if (jaxbElement.getValue() instanceof Item) {
					Item itemElement = (Item) jaxbElement.getValue();
					checkItem(itemElement);
				}
			}

		}
	}

	public static void checkItem(Item item) throws IOException {
		List<Serializable> itemElement = item.getContent();
		for (Object itemElementObject : itemElement) {
			if (!(itemElementObject instanceof String)) {

				JAXBElement<?> jaxbElement = (JAXBElement<?>) itemElementObject;
				if (jaxbElement.getValue() instanceof Choice) {
					Choice choiceElement = (Choice) jaxbElement.getValue();
					checkChoice(choiceElement);
				} else if (jaxbElement.getValue() instanceof de.uni_trier.bibliothek.xml.tei.model.generated.List) {
					de.uni_trier.bibliothek.xml.tei.model.generated.List listElement = (de.uni_trier.bibliothek.xml.tei.model.generated.List) jaxbElement
							.getValue();
					checkList(listElement);
				}

			}

		}
	}

	public static void checkTable(Table table) throws IOException {
		List<Serializable> tableList = table.getContent();
		for (Object tableElement : tableList) {
			if (!(tableElement instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) tableElement;
				if (jaxbElement.getValue() instanceof Row) {
					Row rowElement = (Row) jaxbElement.getValue();
					checkRow(rowElement);
				}
			}
		}
	}

	public static void checkRow(Row row) throws IOException {
		List<Serializable> rowList = row.getContent();
		for (Object rowElement : rowList) {
			if (!(rowElement instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) rowElement;
				if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtcElement = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtcElement);
				}
			}
		}
	}

	public static void checkDivFront(DivFront divFrontElement) throws IOException {
		List<JAXBElement<?>> divFrontList = divFrontElement.getFwOrPOrFigure();

		for (JAXBElement<?> divFrontListElement : divFrontList) {
			if (divFrontListElement.getValue() instanceof PFront) {
				PFront pElement = (PFront) divFrontListElement.getValue();
				checkPFront(pElement);
			} else if (divFrontListElement.getValue() instanceof Head) {
				Head headElement = (Head) divFrontListElement.getValue();
				checkHead(headElement, divFrontElement);
			} else if (divFrontListElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) divFrontListElement.getValue();
				checkLbEtc(lbEtcElement);
			} else if (divFrontListElement.getValue() instanceof DivFront) {
				DivFront recurisveDivFrontElement = (DivFront) divFrontListElement.getValue();
				checkDivFront(recurisveDivFrontElement);
			} else if (divFrontListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divFrontListElement.getValue();
				checkTable(tableElement);
			} else if (divFrontListElement.getValue() instanceof de.uni_trier.bibliothek.xml.tei.model.generated.List) {
				de.uni_trier.bibliothek.xml.tei.model.generated.List ListElement = (de.uni_trier.bibliothek.xml.tei.model.generated.List) divFrontListElement
						.getValue();
				checkList(ListElement);
			}

		}
	}

	public static void checkHead(Head head, DivFront divFrontElement) throws IOException {
		List<Serializable> headList = head.getContent();
		for (Object headListElement : headList) {

			if (!(headListElement instanceof String)) {
				JAXBElement<?> jaxbHeadListElement = (JAXBElement<?>) headListElement;
				if (jaxbHeadListElement.getValue() instanceof NameGND) {
					NameGND nameGNDElement = (NameGND) jaxbHeadListElement.getValue();
					checkNameGnd(nameGNDElement, head, divFrontElement);
				} else if (jaxbHeadListElement.getValue() instanceof SourceGND) {
					SourceGND sourceGNDElement = (SourceGND) jaxbHeadListElement.getValue();
					checkSourceGND(sourceGNDElement, head, divFrontElement);
				} else if (jaxbHeadListElement.getValue() instanceof LbEtc) {
					LbEtc lbEtcElement = (LbEtc) jaxbHeadListElement.getValue();
					checkLbEtc(lbEtcElement);
				}

			}
		}
	}

	public static void checkTitlePage(TitlePage titlePage) throws IOException {
		List<Serializable> titlePageList = titlePage.getContent();
		for (Object titlePageListElement : titlePageList) {
			if (!(titlePageListElement instanceof String)) {
				JAXBElement<?> titlePageListElementObject = (JAXBElement<?>) titlePageListElement;
				if (titlePageListElementObject.getValue() instanceof DocTitle) {
					DocTitle docTitle = (DocTitle) titlePageListElementObject.getValue();
					checkDocTitle(docTitle);
				} else if (titlePageListElementObject.getValue() instanceof Choice) {
					Choice choice = (Choice) titlePageListElementObject.getValue();
					checkChoice(choice);
				} else if (titlePageListElementObject.getValue() instanceof PFront) {
					PFront pFront = (PFront) titlePageListElementObject.getValue();
					checkPFront(pFront);
				} else if (titlePageListElementObject.getValue() instanceof DocImprint) {
					DocImprint docImprint = (DocImprint) titlePageListElementObject.getValue();
					checkDocImprint(docImprint);
				} else if (titlePageListElementObject.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc) titlePageListElementObject.getValue();
					checkLbEtc(lbEtc);
				}

			}

		}
	}

	public static void checkDocImprint(DocImprint docImprint) throws IOException {
		List<JAXBElement<?>> docImprintList = docImprint.getPubPlaceOrPublisherOrLb();
		for (JAXBElement<?> jaxbElement : docImprintList) {
			if (jaxbElement.getValue() instanceof LbEtc) {
				LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
				checkLbEtc(lbEtc);
			} else if (jaxbElement.getValue() instanceof Choice) {
				Choice choiceElement = (Choice) jaxbElement.getValue();
				checkChoice(choiceElement);
			} else if (jaxbElement.getValue() instanceof NameGND) {
				NameGND nameGND = (NameGND) jaxbElement.getValue();
				checkNameGnd(nameGND, null, null);
			} else if (jaxbElement.getValue() instanceof SourceGND) {
				SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
				checkSourceGND(sourceGND, null, null);
			}
		}

	}

	public static void checkDocTitle(DocTitle docTitle) throws IOException {
		List<JAXBElement<?>> docTitleList = docTitle.getLbOrFigureOrTitlePart();
		for (Object docTitleElement : docTitleList) {
			if (!(docTitleElement instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) docTitleElement;
				if (jaxbElement.getValue() instanceof Choice) {
					Choice choiceElement = (Choice) jaxbElement.getValue();
					checkChoice(choiceElement);
				} else if (jaxbElement.getValue() instanceof TitlePart) {
					TitlePart titlePart = (TitlePart) jaxbElement.getValue();
					checkTitlePart(titlePart);
				} else if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtc);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameGND = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameGND, null, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGND, null, null);
				}
			}

		}

	}

	public static void checkTitlePart(TitlePart titlePart) throws IOException {
		List<Serializable> titlePartList = titlePart.getContent();
		for (Object titlePartObject : titlePartList) {
			if (!(titlePartObject instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) titlePartObject;
				if (jaxbElement.getValue() instanceof Choice) {
					Choice choiceElement = (Choice) jaxbElement.getValue();
					checkChoice(choiceElement);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameGND = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameGND, null, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGND, null, null);
				} else if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtc);
				}
			}

		}

	}

	public static void checkNameGnd(NameGND nameGND, Head head, DivFront divFrontElement) throws IOException {
		List<Serializable> nameGNDList = nameGND.getContent();
		// block für artikel
		if (head != null && divFrontElement != null) {
			String[] refURLList = nameGND.getRef().split(" ");
			for (int i = 0; i < refURLList.length; i++) {
				String refURL = refURLList[i];
				System.out.println("artikel namegnd-url nummer " + i + " von wort: " + refURL);
				System.out.println("artikel head: " + head.getContent());
				System.out.println("artikel div: " + divFrontElement.getFwOrPOrFigure());
				String prefix = makeHTTPRequest(refURL);
			}
		} else {
			// block for loose name-tags
			String[] refURLList = nameGND.getRef().split(" ");
			for (int i = 0; i < refURLList.length; i++) {
				String refURL = refURLList[i];
				System.out.println("loser namegnd-url nummer " + i + " von wort: " + refURL);
				String prefix = makeHTTPRequest(refURL);
			}
		}

		for (Object nameGNDObject : nameGNDList) {
			if (!(nameGNDObject instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) nameGNDObject;
				if (jaxbElement.getValue() instanceof Choice) {
					Choice choiceElement = (Choice) jaxbElement.getValue();
					checkChoice(choiceElement);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameInNameGND = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameInNameGND, head, divFrontElement);
				} else if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtc);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGND, head, divFrontElement);
				}
			}

		}

	}

	public static void checkSourceGND(SourceGND sourceGNDGND, Head head, DivFront divFrontElement) throws IOException {
		List<Serializable> sourceGNDList = sourceGNDGND.getContent();
		// block für artikel
		if (head != null && divFrontElement != null) {
			String[] refURLList = sourceGNDGND.getSource().split(" ");
			for (int i = 0; i < refURLList.length; i++) {
				String refURL = refURLList[i];
				System.out.println("source artikel namegnd-url nummer " + i + " von wort: " + refURL);
				System.out.println("source artikel head: " + head.getContent());
				System.out.println("source artikel div: " + divFrontElement.getFwOrPOrFigure());
				String prefix = makeHTTPRequest(refURL);
			}
		} else {
			// block for loose name-tags
			String[] refURLList = sourceGNDGND.getSource().split(" ");
			for (int i = 0; i < refURLList.length; i++) {
				String refURL = refURLList[i];
				System.out.println("source loser namegnd-url nummer " + i + " von wort: " + refURL);
				String prefix = makeHTTPRequest(refURL);
			}
		}
		for (Object sourceGNDObject : sourceGNDList) {
			if (!(sourceGNDObject instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) sourceGNDObject;
				if (jaxbElement.getValue() instanceof Choice) {
					Choice choiceElement = (Choice) jaxbElement.getValue();
					checkChoice(choiceElement);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameInNameGND = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameInNameGND, head, divFrontElement);
				} else if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtc);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGND, head, divFrontElement);
				}
			}

		}

	}

	public static String makeHTTPRequest(String refURL) throws IOException {
		String entityPrefix = "";
		URL url = new URL(refURL);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		System.out.println("responselines: " + content);
		return entityPrefix;
	}

	public static void writePersonEntity(SourceGND sourceGNDGND) {

	}

	public static void writeEventsEntity(SourceGND sourceGNDGND) {

	}

	public static void writeListBiblEntity(SourceGND sourceGNDGND) {

	}

	public static void writeObjectsEntity(SourceGND sourceGNDGND) {

	}

	public static void writeOrgsEntity(SourceGND sourceGNDGND) {

	}

	public static void writePlacesEntity(SourceGND sourceGNDGND) {

	}

	public static void addPersonEntity() {

	}

	public static void checkPFront(PFront pfront) throws IOException {
		List<Serializable> pList = pfront.getContent();
		for (Object pListElement : pList) {
			if (!(pListElement instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) pListElement;
				if (jaxbElement.getValue() instanceof Choice) {
					Choice choiceElement = (Choice) jaxbElement.getValue();
					checkChoice(choiceElement);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameGNDElement = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameGNDElement, null, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGNDElement = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGNDElement, null, null);
				} else if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtcElement = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtcElement);
				} else if (jaxbElement.getValue() instanceof Table) {
					Table tableElement = (Table) jaxbElement.getValue();
					checkTable(tableElement);
				}
			}
		}
	}

	public static void checkChoice(Choice choice) throws IOException {
		List<JAXBElement<?>> choiceElementsList = choice.getAbbrOrExpanOrOrig();

		for (JAXBElement<?> jaxbElement : choiceElementsList) {
			if (jaxbElement.getValue() instanceof LbEtc) {
				LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
				checkLbEtc(lbEtc);
			} else if (jaxbElement.getValue() instanceof NameGND) {
				NameGND nameGND = (NameGND) jaxbElement.getValue();
				checkNameGnd(nameGND, null, null);
			} else if (jaxbElement.getValue() instanceof SourceGND) {
				SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
				checkSourceGND(sourceGND, null, null);
			}

		}

	}

	public static void checkLbEtc(LbEtc lbEtc) throws IOException {

		List<Serializable> lbEtcList = lbEtc.getContent();
		for (Object lbEtcListElement : lbEtcList) {
			if (!(lbEtcListElement instanceof String)) {
				JAXBElement<?> jaxbLbEtcListElement = (JAXBElement<?>) lbEtcListElement;
				if (jaxbLbEtcListElement.getValue() instanceof Choice) {
					Choice choice = (Choice) jaxbLbEtcListElement.getValue();
					checkChoice(choice);

				} else if (jaxbLbEtcListElement.getValue() instanceof NameGND) {
					NameGND nameGNDElement = (NameGND) jaxbLbEtcListElement.getValue();
					checkNameGnd(nameGNDElement, null, null);
				} else if (jaxbLbEtcListElement.getValue() instanceof SourceGND) {
					SourceGND sourceGNDElement = (SourceGND) jaxbLbEtcListElement.getValue();
					checkSourceGND(sourceGNDElement, null, null);
				} else if (jaxbLbEtcListElement.getValue() instanceof Table) {
					Table tableElement = (Table) jaxbLbEtcListElement.getValue();
					checkTable(tableElement);
				}
			}
		}
	}

	// public static void checkFw(Fw fwElement) {

	// List<Serializable> fwElementList = fwElement.getContent();
	// for (Object fwElementListElement : fwElementList) {
	// if (!(fwElementListElement instanceof String)) {
	// JAXBElement<?> jaxbfwElementListElement = (JAXBElement<?>)
	// fwElementListElement;

	// }
	// }
	// }

	public static void createLists() {

		de.uni_trier.bibliothek.xml.persons.model.generated.Text personsText = teiPersons.getText();
		de.uni_trier.bibliothek.xml.persons.model.generated.Body personsBody = personsText.getBody();
		de.uni_trier.bibliothek.xml.persons.model.generated.Div personsDiv = personsBody.getDiv();
		ListPerson listPerson = personsDiv.getListPerson();
		listPersonList = listPerson.getPerson();

		de.uni_trier.bibliothek.xml.listBibl.model.generated.Text listBiblText = teiListBibl.getText();
		de.uni_trier.bibliothek.xml.listBibl.model.generated.Body listBiblBody = listBiblText.getBody();
		de.uni_trier.bibliothek.xml.listBibl.model.generated.Div listBiblDiv = listBiblBody.getDiv();
		ListBibl listBibl = listBiblDiv.getListBibl();
		listBiblList = listBibl.getBibl();

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

}
