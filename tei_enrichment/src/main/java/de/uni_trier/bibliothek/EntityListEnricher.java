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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import de.uni_trier.bibliothek.xml.events.model.generated.Event;
import de.uni_trier.bibliothek.xml.events.model.generated.ListEvent;
import de.uni_trier.bibliothek.xml.listBibl.model.generated.ListBibl;
import de.uni_trier.bibliothek.xml.objects.model.generated.ListObject;
import de.uni_trier.bibliothek.xml.orgs.model.generated.ListOrg;
import de.uni_trier.bibliothek.xml.orgs.model.generated.Org;
import de.uni_trier.bibliothek.xml.persons.model.generated.ListPerson;
import de.uni_trier.bibliothek.xml.persons.model.generated.Person;
import de.uni_trier.bibliothek.xml.places.model.generated.ListPlace;
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
import de.uni_trier.bibliothek.xml.tei.model.generated.GroupBody;
import de.uni_trier.bibliothek.xml.tei.model.generated.GroupText;
import de.uni_trier.bibliothek.xml.tei.model.generated.Head;
import de.uni_trier.bibliothek.xml.tei.model.generated.InnerGroup;
import de.uni_trier.bibliothek.xml.tei.model.generated.Item;
import de.uni_trier.bibliothek.xml.tei.model.generated.LbEtc;
import de.uni_trier.bibliothek.xml.tei.model.generated.NameGND;
import de.uni_trier.bibliothek.xml.tei.model.generated.OuterGroup;
import de.uni_trier.bibliothek.xml.tei.model.generated.PFront;
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
	public static ListBibl listBibl;
	public static List<Event> eventList;
	public static List<de.uni_trier.bibliothek.xml.objects.model.generated.Object> objectList;
	public static List<Org> orgsList;
	public static List<Place> placesList;
	public static String doiSuffix;
	public static String reference;
	public static String fileName;
	public static Integer divCount;

	public static List<Object> enrichList(List<Object> originalObjectTEIList, String bandFileName) throws IOException {
		EntityListWriter.initiate(originalObjectTEIList, bandFileName);
		fileName = bandFileName;
		objectTEIList = originalObjectTEIList;
		createJavaObjects();
		TeiHeader teiHeader = originalTEI.getTeiHeader();
		FileDesc fileDesc = teiHeader.getFileDesc();
		TitleStmt titleStmt = fileDesc.getTitleStmt();
		TitleStmtValue title = titleStmt.getTitle();
		reference = title.getRef();
		String bandReference = reference.substring(1, reference.length());
		String bandName = bandReference.substring(5, 9);
		doiSuffix = "topo-" + bandName + "-divi-";
		divCount = 1;
		createLists();
		Text originText = originalTEI.getText();
		checkText(originText);
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
				checkLbEtc(lbEtcElement, null);
			} else if (divOrPbOrLbListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divOrPbOrLbListElement.getValue();
				checkTable(tableElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Subst) {
				Subst substElement = (Subst) divOrPbOrLbListElement.getValue();
				checkSubst(substElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Foreign) {
				Foreign foreignElement = (Foreign) divOrPbOrLbListElement.getValue();
				checkForeign(foreignElement);
			}

		}
	}

	public static void checkForeign(Foreign foreign) throws IOException {
		List<Serializable> foreignList = foreign.getContent();
		for (Object foreignListObject : foreignList) {
			if (!(foreignListObject instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) foreignListObject;
				if (jaxbElement.getValue() instanceof DivFront) {
					DivFront divFrontElement = (DivFront) jaxbElement.getValue();
					checkDivFront(divFrontElement);
				} else if (jaxbElement.getValue() instanceof Choice) {
					Choice choiceElement = (Choice) jaxbElement.getValue();
					checkChoice(choiceElement);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameElement = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameElement, null, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceElement = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceElement, null, null);
				} else if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtcElement = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtcElement, null);
				} else if (jaxbElement.getValue() instanceof Table) {
					Table tableElement = (Table) jaxbElement.getValue();
					checkTable(tableElement);
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				}
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
		DivFront divFrontElement = new DivFront();
		for (JAXBElement<?> divOrPbOrLbListElement : getDivOrPbOrLbList) {
			if (divOrPbOrLbListElement.getValue() instanceof DivFront) {
				divFrontElement = (DivFront) divOrPbOrLbListElement.getValue();
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
				checkLbEtc(lbEtcElement, divFrontElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divOrPbOrLbListElement.getValue();
				checkTable(tableElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Subst) {
				Subst substElement = (Subst) divOrPbOrLbListElement.getValue();
				checkSubst(substElement);
			} else if (divOrPbOrLbListElement.getValue() instanceof Foreign) {
				Foreign foreignElement = (Foreign) divOrPbOrLbListElement.getValue();
				checkForeign(foreignElement);
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
				checkLbEtc(lbEtcElement, null);
			} else if (divOrPbOrLbElement.getValue() instanceof Subst) {
				Subst substElement = (Subst) divOrPbOrLbElement.getValue();
				checkSubst(substElement);
			} else if (divOrPbOrLbElement.getValue() instanceof Foreign) {
				Foreign foreignElement = (Foreign) divOrPbOrLbElement.getValue();
				checkForeign(foreignElement);
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
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
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
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameGNDElement = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameGNDElement, null, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGNDElement = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGNDElement, null, null);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
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
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
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
					checkLbEtc(lbEtcElement, null);
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
				}
			}
		}
	}

	public static void checkDivFront(DivFront divFrontElement) throws IOException {
		List<JAXBElement<?>> divFrontList = divFrontElement.getFwOrPOrFigure();

		if (divCount > 9 && divCount < 100) {
			divFrontElement.setId(doiSuffix + "00" + divCount);
		} else if (divCount > 99) {
			divFrontElement.setId(doiSuffix + "0" + divCount);
		} else {
			divFrontElement.setId(doiSuffix + "000" + divCount);
		}
		divCount++;

		for (JAXBElement<?> divFrontListElement : divFrontList) {
			if (divFrontListElement.getValue() instanceof PFront) {
				PFront pElement = (PFront) divFrontListElement.getValue();
				checkPFront(pElement);
			} else if (divFrontListElement.getValue() instanceof Head) {
				Head headElement = (Head) divFrontListElement.getValue();
				checkHead(headElement, divFrontElement);
			} else if (divFrontListElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) divFrontListElement.getValue();
				checkLbEtc(lbEtcElement, divFrontElement);
			} else if (divFrontListElement.getValue() instanceof DivFront) {
				DivFront recursiveDivFrontElement = (DivFront) divFrontListElement.getValue();
				checkDivFront(recursiveDivFrontElement);
			} else if (divFrontListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divFrontListElement.getValue();
				checkTable(tableElement);
			} else if (divFrontListElement.getValue() instanceof de.uni_trier.bibliothek.xml.tei.model.generated.List) {
				de.uni_trier.bibliothek.xml.tei.model.generated.List ListElement = (de.uni_trier.bibliothek.xml.tei.model.generated.List) divFrontListElement
						.getValue();
				checkList(ListElement);
			} else if (divFrontListElement.getValue() instanceof Subst) {
				Subst substElement = (Subst) divFrontListElement.getValue();
				checkSubst(substElement);
			} else if (divFrontListElement.getValue() instanceof Foreign) {
				Foreign foreignElement = (Foreign) divFrontListElement.getValue();
				checkForeign(foreignElement);
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
					checkLbEtc(lbEtcElement, divFrontElement);
				} else if (jaxbHeadListElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbHeadListElement.getValue();
					checkSubst(substElement);
				} else if (jaxbHeadListElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbHeadListElement.getValue();
					checkForeign(foreignElement);
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
					checkLbEtc(lbEtc, null);
				} else if (titlePageListElementObject.getValue() instanceof Subst) {
					Subst substElement = (Subst) titlePageListElementObject.getValue();
					checkSubst(substElement);
				} else if (titlePageListElementObject.getValue() instanceof TitlePart) {
					TitlePart titlePartElement = (TitlePart) titlePageListElementObject.getValue();
					checkTitlePart(titlePartElement);
				} else if (titlePageListElementObject.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) titlePageListElementObject.getValue();
					checkForeign(foreignElement);
				}

			}

		}
	}

	public static void checkDocImprint(DocImprint docImprint) throws IOException {
		List<JAXBElement<?>> docImprintList = docImprint.getPubPlaceOrPublisherOrLb();
		for (JAXBElement<?> jaxbElement : docImprintList) {
			if (jaxbElement.getValue() instanceof LbEtc) {
				LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
				checkLbEtc(lbEtc, null);
			} else if (jaxbElement.getValue() instanceof Choice) {
				Choice choiceElement = (Choice) jaxbElement.getValue();
				checkChoice(choiceElement);
			} else if (jaxbElement.getValue() instanceof NameGND) {
				NameGND nameGND = (NameGND) jaxbElement.getValue();
				checkNameGnd(nameGND, null, null);
			} else if (jaxbElement.getValue() instanceof SourceGND) {
				SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
				checkSourceGND(sourceGND, null, null);
			} else if (jaxbElement.getValue() instanceof Subst) {
				Subst substElement = (Subst) jaxbElement.getValue();
				checkSubst(substElement);
			} else if (jaxbElement.getValue() instanceof Foreign) {
				Foreign foreignElement = (Foreign) jaxbElement.getValue();
				checkForeign(foreignElement);
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
					checkLbEtc(lbEtc, null);
				} else if (jaxbElement.getValue() instanceof NameGND) {
					NameGND nameGND = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameGND, null, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGND, null, null);
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
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
					checkLbEtc(lbEtc, null);
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
				}
			}

		}

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
					checkLbEtc(lbEtcElement, null);
				} else if (jaxbElement.getValue() instanceof Table) {
					Table tableElement = (Table) jaxbElement.getValue();
					checkTable(tableElement);
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
				}
			}
		}
	}

	public static void checkSubst(Subst subst) throws IOException {
		List<Serializable> substElementsList = subst.getContent();

		for (Object substElemenObject : substElementsList) {
			if (substElemenObject instanceof LbEtc) {
				LbEtc lbEtc = (LbEtc) substElemenObject;
				checkLbEtc(lbEtc, null);
			} else if (substElemenObject instanceof NameGND) {
				NameGND nameGND = (NameGND) substElemenObject;
				checkNameGnd(nameGND, null, null);
			} else if (substElemenObject instanceof SourceGND) {
				SourceGND sourceGND = (SourceGND) substElemenObject;
				checkSourceGND(sourceGND, null, null);
			} else if (substElemenObject instanceof Del) {
				Del delElement = (Del) substElemenObject;
				checkDel(delElement);
			} else if (substElemenObject instanceof Add) {
				Add addElement = (Add) substElemenObject;
				checkAdd(addElement);
			} else if (substElemenObject instanceof Foreign) {
				Foreign foreignElement = (Foreign) substElemenObject;
				checkForeign(foreignElement);
			}

		}

	}

	public static void checkAdd(Add add) throws IOException {
		List<Serializable> addElementsList = add.getContent();

		for (Object addElement : addElementsList) {
			if (addElement instanceof LbEtc) {
				LbEtc lbEtc = (LbEtc) addElement;
				checkLbEtc(lbEtc, null);
			} else if (addElement instanceof NameGND) {
				NameGND nameGND = (NameGND) addElement;
				checkNameGnd(nameGND, null, null);
			} else if (addElement instanceof SourceGND) {
				SourceGND sourceGND = (SourceGND) addElement;
				checkSourceGND(sourceGND, null, null);
			} else if (addElement instanceof Subst) {
				Subst substElement = (Subst) addElement;
				checkSubst(substElement);
			} else if (addElement instanceof Del) {
				Del delElement = (Del) addElement;
				checkDel(delElement);
			} else if (addElement instanceof Add) {
				Add addElementRec = (Add) addElement;
				checkAdd(addElementRec);
			} else if (addElement instanceof Foreign) {
				Foreign foreignElement = (Foreign) addElement;
				checkForeign(foreignElement);
			}

		}

	}

	public static void checkDel(Del del) throws IOException {
		List<Serializable> delElementsList = del.getContent();

		for (Object delElement : delElementsList) {
			if (delElement instanceof LbEtc) {
				LbEtc lbEtc = (LbEtc) delElement;
				checkLbEtc(lbEtc, null);
			} else if (delElement instanceof NameGND) {
				NameGND nameGND = (NameGND) delElement;
				checkNameGnd(nameGND, null, null);
			} else if (delElement instanceof SourceGND) {
				SourceGND sourceGND = (SourceGND) delElement;
				checkSourceGND(sourceGND, null, null);
			} else if (delElement instanceof Subst) {
				Subst substElement = (Subst) delElement;
				checkSubst(substElement);
			} else if (delElement instanceof Add) {
				Add addElement = (Add) delElement;
				checkAdd(addElement);
			} else if (delElement instanceof Del) {
				Del delElementRec = (Del) delElement;
				checkDel(delElementRec);
			} else if (delElement instanceof Foreign) {
				Foreign foreignElement = (Foreign) delElement;
				checkForeign(foreignElement);
			}

		}

	}

	public static void checkChoice(Choice choice) throws IOException {
		List<JAXBElement<?>> choiceElementsList = choice.getAbbrOrExpanOrOrig();

		for (JAXBElement<?> jaxbElement : choiceElementsList) {
			if (jaxbElement.getValue() instanceof LbEtc) {
				LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
				checkLbEtc(lbEtc, null);
			} else if (jaxbElement.getValue() instanceof NameGND) {
				NameGND nameGND = (NameGND) jaxbElement.getValue();
				checkNameGnd(nameGND, null, null);
			} else if (jaxbElement.getValue() instanceof SourceGND) {
				SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
				checkSourceGND(sourceGND, null, null);
			} else if (jaxbElement.getValue() instanceof Subst) {
				Subst substElement = (Subst) jaxbElement.getValue();
				checkSubst(substElement);
			} else if (jaxbElement.getValue() instanceof Foreign) {
				Foreign foreignElement = (Foreign) jaxbElement.getValue();
				checkForeign(foreignElement);
			}

		}

	}

	public static void checkLbEtc(LbEtc lbEtc, DivFront divFrontElement) throws IOException {

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
				} else if (jaxbLbEtcListElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbLbEtcListElement.getValue();
					checkSubst(substElement);
				} else if (jaxbLbEtcListElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbLbEtcListElement.getValue();
					checkForeign(foreignElement);
				}
			}
		}
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

	public static void checkNameGnd(NameGND nameGND, Head head, DivFront divFrontElement) throws IOException {
		List<Serializable> nameGNDList = nameGND.getContent();
		boolean multipleURLs = false;
		SourceGND sourceGND = new SourceGND();
		if (nameGND.getRef() != null) {
			if (!(nameGND.getRef().contains("???")) && !(nameGND.getRef().contains("!!!"))
					&& !(nameGND.getRef().contains("***")) && !(nameGND.getRef().isEmpty())) {
				String[] refURLList = nameGND.getRef().split(" ");
				for (int i = 0; i < refURLList.length; i++) {
					String refURL = refURLList[i];
					if (i > 0) {
						multipleURLs = true;
					}
					makeHTTPRequest(refURL, nameGND, sourceGND, divFrontElement, multipleURLs);
				}
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
					checkNameGnd(nameInNameGND, head, null);
				} else if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
					checkLbEtc(lbEtc, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGNDrec = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGNDrec, head, null);
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
				}

			}
		}

	}

	public static void checkSourceGND(SourceGND sourceGNDGND, Head head, DivFront divFrontElement) throws IOException {
		List<Serializable> sourceGNDList = sourceGNDGND.getContent();
		NameGND nameGND = new NameGND();
		boolean multipleURLs = false;
		if (sourceGNDGND.getSource() != null) {
			if (!(sourceGNDGND.getSource().contains("???")) && !(sourceGNDGND.getSource().contains("!!!"))
					&& !(sourceGNDGND.getSource().contains("***")) && !(sourceGNDGND.getSource().isEmpty())) {
				String[] refURLList = sourceGNDGND.getSource().split(" ");
				for (int i = 0; i < refURLList.length; i++) {
					String refURL = refURLList[i];
					if (i > 0) {
						multipleURLs = true;
					}
					makeHTTPRequest(refURL, nameGND, sourceGNDGND, divFrontElement, multipleURLs);
				}
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
					checkLbEtc(lbEtc, null);
				} else if (jaxbElement.getValue() instanceof SourceGND) {
					SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGND, head, divFrontElement);
				} else if (jaxbElement.getValue() instanceof Subst) {
					Subst substElement = (Subst) jaxbElement.getValue();
					checkSubst(substElement);
				} else if (jaxbElement.getValue() instanceof Foreign) {
					Foreign foreignElement = (Foreign) jaxbElement.getValue();
					checkForeign(foreignElement);
				}
			}
		}
	}

	public static String makeHTTPRequest(String refURL, NameGND nameGND, SourceGND sourceGND, DivFront divFrontElement,
			Boolean multipleURLs)
			throws IOException {
		String entityPrefix = "";
		String cutURL = refURL.substring(18, refURL.length());
		boolean isOrtsartikel = true;
		if (divFrontElement == null || divFrontElement.getType() == null) {
			divFrontElement = new DivFront();
		} else if (divFrontElement.getType().equals("overview") || divFrontElement.getType().equals("transition")
				|| divFrontElement.getType().equals("index") || divFrontElement.getN() != null
				|| (divFrontElement.getType().equals("appendix"))) {
			isOrtsartikel = false;
		}

		String completeURL = "https://lobid.org/" + cutURL + ".json";
		URL url = new URL(completeURL);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		InputStream inputStream = (InputStream) con.getContent();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);
		JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

		String preferredNameString = jsonObject.getString("preferredName");

		preferredNameString = preferredNameString.replaceAll(" ", "_");
		preferredNameString = preferredNameString.replaceAll("/", "_");
		preferredNameString = preferredNameString.replaceAll("<", "_");
		preferredNameString = preferredNameString.replaceAll(">", "");
		preferredNameString = preferredNameString.replaceAll("-", "_");
		preferredNameString = preferredNameString.replaceAll(",", "_");
		preferredNameString = preferredNameString.replaceAll(":", "");
		preferredNameString = preferredNameString.replaceAll("___", "_");
		preferredNameString = preferredNameString.replaceAll("__", "_");
		preferredNameString = preferredNameString.replaceAll("[()]", "");


		if (jsonObject.has("type")) {
			JSONArray typeTerms = jsonObject.getJSONArray("type");
			List<String> typeTermslist = new ArrayList<String>();
			for (int i = 0; i < typeTerms.length(); i++) {

				String jsonObjectString = typeTerms.getString(i);
				typeTermslist.add(jsonObjectString);
			}

			String oberkategorie = getOberkategorie(typeTermslist, jsonObject);

			if (oberkategorie.equals("listBibl")) {

				String oldRef = "";
				if (!(sourceGND.getRef() == null) && multipleURLs) {
					oldRef = sourceGND.getRef() + " ";
				} else if (!(nameGND.getRef() == null) && multipleURLs) {
					oldRef = nameGND.getRef() + " ";
				}
				sourceGND.setSource(oldRef + "mgndbibl:listBibl_" + preferredNameString);
				nameGND.setRef(oldRef + "mgndbibl:listBibl_" + preferredNameString);
				if (isOrtsartikel) {
					divFrontElement.setCorresp("mgndbibl:listBibl_" + preferredNameString);
				}
				EntityListWriter.writeListBiblEntity(jsonObject, preferredNameString, typeTermslist, divFrontElement,
						isOrtsartikel);

			} else if (oberkategorie.equals("person")) {
				String oldRef = "";
				if (!(sourceGND.getRef() == null) && multipleURLs) {
					oldRef = sourceGND.getRef() + " ";
				} else if (!(nameGND.getRef() == null) && multipleURLs) {
					oldRef = nameGND.getRef() + " ";
				}

				nameGND.setRef(oldRef + "mgndper:person_" + preferredNameString);
				sourceGND.setSource(oldRef + "mgndper:person_" + preferredNameString);
				if (isOrtsartikel) {
					divFrontElement.setCorresp("mgndper:person_" + preferredNameString);
				}
				EntityListWriter.writePersonEntity(jsonObject, preferredNameString, typeTermslist, divFrontElement,
						isOrtsartikel);

			} else if (oberkategorie.equals("event")) {
				String oldRef = "";
				if (!(sourceGND.getRef() == null) && multipleURLs) {
					oldRef = sourceGND.getRef() + " ";
				} else if (!(nameGND.getRef() == null) && multipleURLs) {
					oldRef = nameGND.getRef() + " ";
				}
				nameGND.setRef(oldRef + "mgndeve:event_" + preferredNameString);
				sourceGND.setSource(oldRef + "mgndeve:event_" + preferredNameString);
				if (isOrtsartikel) {
					divFrontElement.setCorresp("mgndeve:event_" + preferredNameString);
				}
				EntityListWriter.writeEventsEntity(jsonObject, preferredNameString, typeTermslist, divFrontElement,
						isOrtsartikel);
			} else if (oberkategorie.equals("org")) {
				String oldRef = "";
				if (!(sourceGND.getRef() == null) && multipleURLs) {
					oldRef = sourceGND.getRef() + " ";
				} else if (!(nameGND.getRef() == null) && multipleURLs) {
					oldRef = nameGND.getRef() + " ";
				}
				nameGND.setRef(oldRef + "mgndorgs:org_" + preferredNameString);
				sourceGND.setSource(oldRef + "mgndorgs:org_" + preferredNameString);
				if (isOrtsartikel) {
					divFrontElement.setCorresp("mgndorgs:org_" + preferredNameString);
				}
				EntityListWriter.writeOrgsEntity(jsonObject, preferredNameString, typeTermslist, divFrontElement,
						isOrtsartikel);
			} else if (oberkategorie.equals("object")) {
				String oldRef = "";
				if (!(sourceGND.getRef() == null) && multipleURLs) {
					oldRef = sourceGND.getRef() + " ";
				} else if (!(nameGND.getRef() == null) && multipleURLs) {
					oldRef = nameGND.getRef() + " ";
				}
				nameGND.setRef(oldRef + "mgndobj:object_" + preferredNameString);
				sourceGND.setSource(oldRef + "mgndobj:object_" + preferredNameString);
				if (isOrtsartikel) {
					divFrontElement.setCorresp("mgndeve:object_" + preferredNameString);
				}
				EntityListWriter.writeObjectsEntity(jsonObject, preferredNameString, typeTermslist, divFrontElement,
						isOrtsartikel);
			} else if (oberkategorie.equals("place")) {
				String oldRef = "";
				if (!(sourceGND.getRef() == null) && multipleURLs) {
					oldRef = sourceGND.getRef() + " ";
				} else if (!(nameGND.getRef() == null) && multipleURLs) {
					oldRef = nameGND.getRef() + " ";
				}
				nameGND.setRef(oldRef + "mgndobj:place_" + preferredNameString);
				sourceGND.setSource(oldRef + "mgndobj:place_" + preferredNameString);
				if (isOrtsartikel) {
					divFrontElement.setCorresp("mgndeve:place_" + preferredNameString);
				}
				EntityListWriter.writePlacesEntity(jsonObject, preferredNameString, typeTermslist, divFrontElement,
						isOrtsartikel);
			}
		}
		return entityPrefix;
	}


	
	public static String getOberkategorie(List<String> typeTermslist, JSONObject jsonObject) {
		String supercategories = "";

		HashMap<String, Integer> categoriesPoints = new HashMap();
		categoriesPoints.put("person", 0);
		categoriesPoints.put("listBibl", 0);
		categoriesPoints.put("org", 0);
		categoriesPoints.put("event", 0);
		categoriesPoints.put("object", 0);
		categoriesPoints.put("place", 0);

		for (String termString : typeTermslist) {
			switch (termString) {
				case "Person":
				case "DifferentiatedPerson":
				case "UndifferentiatedPerson":
				case "Pseudonym":
				case "RoyalOrMemberOfARoyalHouse":
				case "LiteraryOrLegendaryCharacter":
				case "Gods":
				case "Spirits":
				case "CollectivePseudonym":
					categoriesPoints.put("person", categoriesPoints.get("person") + 1);
					break;
				case "Work":
				case "Collection":
				case "CollectiveManuscript":
				case "Expression":
				case "Manuscript":
				case "MusicalWork":
				case "ProvenanceCharacteristic":
				case "VersionOfAMusicalWork":
					categoriesPoints.put("listBibl", categoriesPoints.get("listBibl") + 1);
					break;
				case "Family":
					categoriesPoints.put("person", categoriesPoints.get("person") + 1);
					break;
				case "ConferenceOrEvent":
				case "SeriesOfConferenceOrEvent":
					categoriesPoints.put("event", categoriesPoints.get("event") + 1);
					break;
				case "HistoricSingleEventOrEra":
					categoriesPoints.put("event", categoriesPoints.get("event") + 10);
					break;
				case "PlaceOrGeographicName":
				case "AdministrativeUnit":
				case "BuildingOrMemorial":
				case "Country":
				case "ExtraterrestialTerritory":
				case "FictivePlace":
				case "MemberState":
				case "NameOfSmallGeographicUnityLyingWithinAnotherGeographicUnit":
				case "NaturalGeographicUnit":
				case "ReligiousTerritory":
				case "TerritorialCorporateBodyOrAdministrativeUnit":
				case "WayBorderOrLine":
					categoriesPoints.put("place", categoriesPoints.get("place") + 1);
					break;
				case "SubjectHeading":
				case "EthnographicName":
				case "FictiveTerm":
				case "Language":
				case "CharactersOrMorphemes":
				case "GroupOfPersons":
				case "MeansOfTransportWithIndividualName":
				case "NomenClatureInBiologyOrChemistry":
				case "ProductNameOrBrandName":
				case "SoftwareProduct":
				case "SubjectHeadingSensoStricto":
					categoriesPoints.put("object", categoriesPoints.get("object") + 1);
					break;
				case "CorporateBody":
				case "Company":
				case "FictiveCorporateBody":
				case "MusicalCorporateBody":
				case "OrganOfCorporateBody":
				case "ProjectOrProgram":
				case "ReligiousAdminstrativeUnit":
				case "ReligiousCorporateBody":
					categoriesPoints.put("org", categoriesPoints.get("org") + 1);
			}

		}

		// hashmap: größter int-wert ist oberkategorie
		int highestPoints = 0;
		for (Map.Entry<String, Integer> entry : categoriesPoints.entrySet()) {
			if (highestPoints < entry.getValue()) {
				highestPoints = entry.getValue();
				supercategories = entry.getKey();
			}
		}

		return supercategories;

	}

	public static ArrayList<String> getUnterkategorie(List<String> typeTermslist, JSONObject jsonObject) {
		ArrayList<String> subcategories = new ArrayList<String>();
		for (String termString : typeTermslist) {
			subcategories.add(termString);
		}
		return subcategories;

	}

}
