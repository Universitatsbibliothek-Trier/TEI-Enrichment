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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import de.uni_trier.bibliothek.xml.tei.model.generated.Back;
import de.uni_trier.bibliothek.xml.tei.model.generated.Body;
import de.uni_trier.bibliothek.xml.tei.model.generated.Choice;
import de.uni_trier.bibliothek.xml.tei.model.generated.DivFront;
import de.uni_trier.bibliothek.xml.tei.model.generated.DocImprint;
import de.uni_trier.bibliothek.xml.tei.model.generated.DocTitle;
import de.uni_trier.bibliothek.xml.tei.model.generated.Front;
import de.uni_trier.bibliothek.xml.tei.model.generated.Fw;
import de.uni_trier.bibliothek.xml.tei.model.generated.Gap;
import de.uni_trier.bibliothek.xml.tei.model.generated.GroupBody;
import de.uni_trier.bibliothek.xml.tei.model.generated.GroupText;
import de.uni_trier.bibliothek.xml.tei.model.generated.Head;
import de.uni_trier.bibliothek.xml.tei.model.generated.InnerGroup;
import de.uni_trier.bibliothek.xml.tei.model.generated.Item;
import de.uni_trier.bibliothek.xml.tei.model.generated.Lb;
import de.uni_trier.bibliothek.xml.tei.model.generated.LbEtc;
import de.uni_trier.bibliothek.xml.tei.model.generated.NameGND;
import de.uni_trier.bibliothek.xml.tei.model.generated.Num;
import de.uni_trier.bibliothek.xml.tei.model.generated.ObjectFactory;
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

public class LineCounter {

	public static int lineNumber;
	public static TEI originalTEI;
	public static ObjectFactory personsTEIObjectFactory = new ObjectFactory();

	public static TEI countLines(TEI originalTEIParameter) {
		lineNumber = 1;
		originalTEI = originalTEIParameter;
		// System.out.println("start counting Lines, TEI file: " + originalTEIParameter);
		Text originText = originalTEI.getText();
		// System.out.println("start counting Lines, body Text: " + originText.toString());
		checkText(originText);
		return originalTEI;
	}

	public static void checkText(Text text) {
		Front front = text.getFront();
		checkFront(front);
		
		if(text.getBody() != null)
		{
			checkBody(text.getBody());
		}
		
		// System.out.println("inhalt von body: " + body.toString() );
		
		if(text.getGroup() != null)
		{
			checkOuterGroup(text.getGroup());
		}
		
		if(text.getBack() != null)
		{
			System.out.println("back ist !null!");
			checkBack(text.getBack());
		}
	}

	public static void checkFront(Front front) {
		List<Object> pbOrDivOrTitlePageList = front.getPbOrDivOrTitlePage();
		// System.out.println("liste von front: " + pbOrDivOrTitlePageList.toString());
		for (Object pbOrDivOrTitlePage : pbOrDivOrTitlePageList) {
			if (pbOrDivOrTitlePage instanceof DivFront) {
				DivFront divFrontElement = (DivFront) pbOrDivOrTitlePage;
				checkDivFront(divFrontElement);
			}
			else if (pbOrDivOrTitlePage instanceof TitlePage) {
				TitlePage titlePage = (TitlePage) pbOrDivOrTitlePage;
				checkTitlePage(titlePage);
			}
			else if (pbOrDivOrTitlePage instanceof PbFront) {
				lineNumber = 1;
			}
			else if (pbOrDivOrTitlePage instanceof Pb) {
				lineNumber = 1;
			}

		}
	}

	public static void checkBody(Body body) {
		
		// System.out.println("body as to string: " + body.toString());
		// System.out.println("body getDivOrPbOrLb: " + body.getDivOrPbOrLb());
		List<JAXBElement<?>> getDivOrPbOrLbList = body.getDivOrPbOrLb();
		for (JAXBElement<?> divOrPbOrLbListElement : getDivOrPbOrLbList) {
			if (divOrPbOrLbListElement.getValue() instanceof DivFront) {
				DivFront divFrontElement = (DivFront) divOrPbOrLbListElement.getValue();
				checkDivFront(divFrontElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Lb) {
				Lb lbElement = (Lb) divOrPbOrLbListElement.getValue();
				setNinLb(lbElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Fw) {
				Fw fwElement = (Fw) divOrPbOrLbListElement.getValue();
				checkFw(fwElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Choice) {
				Choice choiceElement = (Choice) divOrPbOrLbListElement.getValue();
				checkChoice(choiceElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof NameGND) {
				NameGND nameElement = (NameGND) divOrPbOrLbListElement.getValue();
				checkNameGnd(nameElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof SourceGND) {
				SourceGND sourceElement = (SourceGND) divOrPbOrLbListElement.getValue();
				checkSourceGND(sourceElement);
			}
			// else if (dS
			// else if (divOrPbOrLbListElement.getValue() instanceof Num) {
			// 	Num numElement = (Num) divOrPbOrLbListElement.getValue();
			// 	checkNum(numElement);
			// }
			else if (divOrPbOrLbListElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) divOrPbOrLbListElement.getValue();
				checkLbEtc(lbEtcElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divOrPbOrLbListElement.getValue();
				checkTable(tableElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof PbFront) {
				lineNumber = 1;
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Pb) {
				lineNumber = 1;
			}

		}
	}

	public static void checkOuterGroup(OuterGroup group) {
		List<InnerGroup> innerGroupList = group.getGroup();
		for (InnerGroup innerGroup : innerGroupList) {
			checkInnerGroup(innerGroup);
		}

	}


	public static void checkInnerGroup(InnerGroup innerGroup) {
		List<GroupText> groupText = innerGroup.getText();
		for (GroupText groupTextElement : groupText) {
			checkInnerGroupText(groupTextElement);
		}

	}

	public static void checkInnerGroupText(GroupText groupText) {

		if(groupText.getBody() != null)
		{
			checkGroupBody(groupText.getBody());	
		}
		if(groupText.getFront() != null)
		{
			checkFront(groupText.getFront());
		}
		
		
	}

		
	

	private static void checkGroupBody(GroupBody groupBody) {
		List<JAXBElement<?>> getDivOrPbOrLbList = groupBody.getDivOrPbOrLb();
		for (JAXBElement<?> divOrPbOrLbListElement : getDivOrPbOrLbList) {
			if (divOrPbOrLbListElement.getValue() instanceof DivFront) {
				DivFront divFrontElement = (DivFront) divOrPbOrLbListElement.getValue();
				checkDivFront(divFrontElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Lb) {
				Lb lbElement = (Lb) divOrPbOrLbListElement.getValue();
				setNinLb(lbElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Fw) {
				Fw fwElement = (Fw) divOrPbOrLbListElement.getValue();
				checkFw(fwElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Choice) {
				Choice choiceElement = (Choice) divOrPbOrLbListElement.getValue();
				checkChoice(choiceElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof NameGND) {
				NameGND nameElement = (NameGND) divOrPbOrLbListElement.getValue();
				checkNameGnd(nameElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof SourceGND) {
				SourceGND sourceElement = (SourceGND) divOrPbOrLbListElement.getValue();
				checkSourceGND(sourceElement);
			}
			// else if (divOrPbOrLbListElement.getValue() instanceof Gap) {
			// 	Gap gapElement = (Gap) divOrPbOrLbListElement.getValue();
			// 	checkGap(gapElement);
			// }
			// else if (divOrPbOrLbListElement.getValue() instanceof Num) {
			// 	Num numElement = (Num) divOrPbOrLbListElement.getValue();
			// 	checkNum(numElement);
			// }
			else if (divOrPbOrLbListElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) divOrPbOrLbListElement.getValue();
				checkLbEtc(lbEtcElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Table) {
				Table tableElement = (Table) divOrPbOrLbListElement.getValue();
				checkTable(tableElement);
			}
			else if (divOrPbOrLbListElement.getValue() instanceof PbFront) {
				lineNumber = 1;
			}
			else if (divOrPbOrLbListElement.getValue() instanceof Pb) {
				lineNumber = 1;
			}

		}
	}

	public static void checkBack(Back back) {
		System.out.println("inhalt von back: " + back.getDivOrPbOrLb());
		List<JAXBElement<?>> divOrPbOrLb = back.getDivOrPbOrLb();
		// System.out.println("liste von front: " + pbOrDivOrTitlePageList.toString());
		for (JAXBElement<?> divOrPbOrLbElement : divOrPbOrLb) {
			System.out.println("divOrPbOrLbElement liste wird gecheckt");
			if (divOrPbOrLbElement.getValue() instanceof DivFront) {
				System.out.println("divfront gefunden");
				DivFront divFrontElement = (DivFront) divOrPbOrLbElement.getValue();
				checkDivFront(divFrontElement);
			}
			else if (divOrPbOrLbElement.getValue() instanceof Table) {
				System.out.println("table gefunden");
				Table table = (Table) divOrPbOrLbElement.getValue();
				checkTable(table);
			}
			else if (divOrPbOrLbElement.getValue() instanceof de.uni_trier.bibliothek.xml.tei.model.generated.List) {
				System.out.println("liste gefunden");
				de.uni_trier.bibliothek.xml.tei.model.generated.List list = (de.uni_trier.bibliothek.xml.tei.model.generated.List) divOrPbOrLbElement.getValue();
				checkList(list);
			}
			else if (divOrPbOrLbElement.getValue() instanceof PbFront) {
				System.out.println("pbfront gefunden");
				lineNumber = 1;
			}
			else if (divOrPbOrLbElement.getValue() instanceof LbEtc) {
				System.out.println("lbetc gefunden");
				LbEtc lbEtcElement = (LbEtc) divOrPbOrLbElement.getValue();
				checkLbEtc(lbEtcElement);
			}
			else if (divOrPbOrLbElement.getValue() instanceof Lb) {
				System.out.println("lb element gefunden");
				Lb lbElement = (Lb) divOrPbOrLbElement.getValue();
				setNinLb(lbElement);
			}
			else if (divOrPbOrLbElement.getValue() instanceof Fw) {
				System.out.println("fw gefunden");
				Fw fwElement = (Fw) divOrPbOrLbElement.getValue();
				checkFw(fwElement);
			}
			else if (divOrPbOrLbElement.getValue() instanceof Pb) {
				System.out.println("pb gefunden");
				lineNumber = 1;
			}


		}
	}

	public static void checkList(de.uni_trier.bibliothek.xml.tei.model.generated.List list) {
		List<Serializable> listElement = list.getContent();
		for (Object listElementObject : listElement) {
			if (!(listElementObject instanceof String)) {
			
			
			JAXBElement<?> jaxbElement = (JAXBElement<?>) listElementObject;
			if (jaxbElement.getValue() instanceof Lb) {
				Lb lbElement = (Lb) jaxbElement.getValue();
				setNinLb(lbElement);
			}
			else if (jaxbElement.getValue() instanceof Item) {
				Item itemElement = (Item) jaxbElement.getValue();
				checkItem(itemElement);
			}
			else if (jaxbElement.getValue() instanceof Fw) {
				Fw fwElement = (Fw) jaxbElement.getValue();
				checkFw(fwElement);
			}	
			else if (jaxbElement.getValue() instanceof Pb) {
				lineNumber = 1;
			}
			else if (jaxbElement.getValue() instanceof PbFront) {
				lineNumber = 1;
			}		
			}


		}
	}

	public static void checkItem(Item item) {
		List<Serializable> itemElement = item.getContent();
		for (Object itemElementObject : itemElement) {
			if (!(itemElementObject instanceof String)) {
			
			
			JAXBElement<?> jaxbElement = (JAXBElement<?>) itemElementObject;
			if (jaxbElement.getValue() instanceof Lb) {
				Lb lbElement = (Lb) jaxbElement.getValue();
				setNinLb(lbElement);
			}
			else if (jaxbElement.getValue() instanceof Choice) {
				Choice choiceElement = (Choice) jaxbElement.getValue();
				checkChoice(choiceElement);
			}
			else if (jaxbElement.getValue() instanceof de.uni_trier.bibliothek.xml.tei.model.generated.List) {
				de.uni_trier.bibliothek.xml.tei.model.generated.List listElement = (de.uni_trier.bibliothek.xml.tei.model.generated.List) jaxbElement.getValue();
				checkList(listElement);
			}	


			
			}


		}
	}

	public static void checkTable(Table table) {
		List<Serializable> tableList = table.getContent();
		for (Object tableElement : tableList) {
			if (!(tableElement instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) tableElement;
	
			// System.out.println("liste von front: " + pbOrDivOrTitlePageList.toString());
	
			if (jaxbElement.getValue() instanceof Lb) {
				Lb lbElement = (Lb) jaxbElement.getValue();
				setNinLb(lbElement);
			}
			else if (jaxbElement.getValue() instanceof Row) {
				Row rowElement = (Row) jaxbElement.getValue();
				checkRow(rowElement);
			}
		}
		}
	}


	public static void checkRow(Row row) {
		List<Serializable> rowList = row.getContent();
		for (Object rowElement : rowList) {
			if (!(rowElement instanceof String)) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) rowElement;
	
			// System.out.println("liste von front: " + pbOrDivOrTitlePageList.toString());
	
			if (jaxbElement.getValue() instanceof Lb) {
				Lb lbElement = (Lb) jaxbElement.getValue();
				setNinLb(lbElement);
			}
			else if (jaxbElement.getValue() instanceof LbEtc) {
				LbEtc lbEtcElement = (LbEtc) jaxbElement.getValue();
				checkLbEtc(lbEtcElement);
			}
		}
		}
	}

	public static void checkDivFront(DivFront divFrontElement) {
		List<JAXBElement<?>> divFrontList = divFrontElement.getFwOrPOrFigure();
		
		for (JAXBElement<?> divFrontListElement : divFrontList) {
			if (divFrontListElement.getValue() instanceof PFront) {
				PFront pElement = (PFront) divFrontListElement.getValue();
				checkPFront(pElement);
				// System.out.println("pElements: " + pElement.getContent());				
			}
			else if (divFrontListElement.getValue() instanceof Fw) {
				Fw fwElement = (Fw) divFrontListElement.getValue();
				checkFw(fwElement);
			}
			else if (divFrontListElement.getValue() instanceof Head) {
				Head headElement = (Head) divFrontListElement.getValue();
				checkHead(headElement);
			}
			else if(divFrontListElement.getValue() instanceof Lb)
			{
				Lb lbElement = (Lb)divFrontListElement.getValue();
				setNinLb(lbElement);
			}
			else if(divFrontListElement.getValue() instanceof LbEtc)
			{
				
				LbEtc lbEtcElement = (LbEtc)divFrontListElement.getValue();
				checkLbEtc(lbEtcElement);
			}
			else if(divFrontListElement.getValue() instanceof DivFront)
			{
				DivFront recurisveDivFrontElement = (DivFront)divFrontListElement.getValue();
				checkDivFront(recurisveDivFrontElement);
			}
			else if(divFrontListElement.getValue() instanceof Table)
			{
				Table tableElement = (Table)divFrontListElement.getValue();
				checkTable(tableElement);
			}
			else if(divFrontListElement.getValue() instanceof de.uni_trier.bibliothek.xml.tei.model.generated.List)
			{
				de.uni_trier.bibliothek.xml.tei.model.generated.List ListElement = (de.uni_trier.bibliothek.xml.tei.model.generated.List)divFrontListElement.getValue();
				checkList(ListElement);
			}
			else if(divFrontListElement.getValue() instanceof Pb)
			{
				System.out.println("pb element gefunden");
				lineNumber = 1;
			}
			else if(divFrontListElement.getValue() instanceof PbFront)
			{
				System.out.println("pbfront element gefunden");
				lineNumber = 1;
			}


			
		}
	}

	public static void checkHead(Head head) {
		List<Serializable> headList = head.getContent();
		// System.out.println("liste von front: " + pbOrDivOrTitlePageList.toString());
		for (Object headListElement : headList) {
			
			if (!(headListElement instanceof String))
			{
				JAXBElement<?> jaxbHeadListElement = (JAXBElement<?>) headListElement;
				if (jaxbHeadListElement.getValue() instanceof Lb) {
					Lb lbElement = (Lb) jaxbHeadListElement.getValue();
					setNinLb(lbElement);
				}
				else if (jaxbHeadListElement.getValue() instanceof NameGND) {
					NameGND nameGNDElement = (NameGND) jaxbHeadListElement.getValue();
					checkNameGnd(nameGNDElement);
				}
				else if (jaxbHeadListElement.getValue() instanceof SourceGND) {
					SourceGND sourceGNDElement = (SourceGND) jaxbHeadListElement.getValue();
					checkSourceGND(sourceGNDElement);
				}
				else if (jaxbHeadListElement.getValue() instanceof LbEtc) {
					LbEtc lbEtcElement = (LbEtc) jaxbHeadListElement.getValue();
					checkLbEtc(lbEtcElement);
				}

		}
		}
	}


	public static void checkTitlePage(TitlePage titlePage) {
		List<Serializable> titlePageList = titlePage.getContent();
		for (Object titlePageListElement : titlePageList) {
			if (!(titlePageListElement instanceof String))
			{
				JAXBElement<?> titlePageListElementObject = (JAXBElement<?>) titlePageListElement;

				if (titlePageListElementObject.getValue() instanceof DocTitle) {
					DocTitle docTitle = (DocTitle) titlePageListElementObject.getValue();
					checkDocTitle(docTitle);
				}
				else if (titlePageListElementObject.getValue() instanceof Lb) {
					Lb lbElement = (Lb) titlePageListElementObject.getValue();
					setNinLb(lbElement);
				}
				else if (titlePageListElementObject.getValue() instanceof Choice) {
					Choice choice = (Choice) titlePageListElementObject.getValue();
					checkChoice(choice);
				}
				else if (titlePageListElementObject.getValue() instanceof PFront) {
					PFront pFront = (PFront) titlePageListElementObject.getValue();
					checkPFront(pFront);
				}
				else if (titlePageListElementObject.getValue() instanceof DocImprint) {
					DocImprint docImprint = (DocImprint) titlePageListElementObject.getValue();
					checkDocImprint(docImprint);
				}
				else if (titlePageListElementObject.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc) titlePageListElementObject.getValue();
					checkLbEtc(lbEtc);
				}
				else if (titlePageListElementObject.getValue() instanceof Fw) {
					Fw fwElement = (Fw) titlePageListElementObject.getValue();
					checkFw(fwElement);
				}


			}
			
		}
	}

	public static void checkDocImprint(DocImprint docImprint)
	{
		List<JAXBElement<?>> docImprintList = docImprint.getPubPlaceOrPublisherOrLb();
		for (JAXBElement<?> jaxbElement : docImprintList) {
						if(jaxbElement.getValue() instanceof LbEtc)
						{
							LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
							checkLbEtc(lbEtc);
						}
						else if(jaxbElement.getValue() instanceof Lb)
						{
							Lb LbElement = (Lb) jaxbElement.getValue();
							setNinLb(LbElement);
						}
						else if(jaxbElement.getValue() instanceof Choice)
						{
							Choice choiceElement = (Choice) jaxbElement.getValue();
							checkChoice(choiceElement);
						}
						
						else if(jaxbElement.getValue() instanceof NameGND)
						{
							NameGND nameGND = (NameGND) jaxbElement.getValue();
							checkNameGnd(nameGND);
						}
						else if(jaxbElement.getValue() instanceof SourceGND)
						{
							SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
							checkSourceGND(sourceGND);
						}
					}
		
	}

	public static void checkDocTitle(DocTitle docTitle) {
		List<JAXBElement<?>> docTitleList = docTitle.getLbOrFigureOrTitlePart();
		for (Object docTitleElement : docTitleList) {
			if (!(docTitleElement instanceof String)) {
						JAXBElement<?> jaxbElement = (JAXBElement<?>) docTitleElement;
						if(jaxbElement.getValue() instanceof Lb)
						{
							Lb LbElement = (Lb) jaxbElement.getValue();
							setNinLb(LbElement);
						}
						else if(jaxbElement.getValue() instanceof Choice)
						{
							Choice choiceElement = (Choice) jaxbElement.getValue();
							checkChoice(choiceElement);
						}
						else if(jaxbElement.getValue() instanceof TitlePart)
						{
							TitlePart titlePart = (TitlePart) jaxbElement.getValue();
							checkTitlePart(titlePart);
						}
						else if(jaxbElement.getValue() instanceof LbEtc)
						{
							LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
							checkLbEtc(lbEtc);
						}
						else if(jaxbElement.getValue() instanceof NameGND)
						{
							NameGND nameGND = (NameGND) jaxbElement.getValue();
							checkNameGnd(nameGND);
						}
						else if(jaxbElement.getValue() instanceof SourceGND)
						{
							SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
							checkSourceGND(sourceGND);
						}
					}

		}

	}

	public static void checkTitlePart(TitlePart titlePart) {
		List<Serializable> titlePartList = titlePart.getContent();
		for (Object titlePartObject : titlePartList) {
			if (!(titlePartObject instanceof String)) {
						JAXBElement<?> jaxbElement = (JAXBElement<?>) titlePartObject;
						if(jaxbElement.getValue() instanceof Lb)
						{
							Lb LbElement = (Lb) jaxbElement.getValue();
							setNinLb(LbElement);
						}
						else if(jaxbElement.getValue() instanceof Choice)
						{
							Choice choiceElement = (Choice) jaxbElement.getValue();
							checkChoice(choiceElement);
						}
						else if(jaxbElement.getValue() instanceof NameGND)
						{
							NameGND nameGND = (NameGND) jaxbElement.getValue();
							checkNameGnd(nameGND);
						}
						else if(jaxbElement.getValue() instanceof SourceGND)
						{
							SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
							checkSourceGND(sourceGND);
						}
						else if(jaxbElement.getValue() instanceof LbEtc)
						{
							LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
							checkLbEtc(lbEtc);
						}
					}

		}

	}

	public static void checkNameGnd(NameGND nameGND) {
		List<Serializable> nameGNDList = nameGND.getContent();
		for (Object nameGNDObject : nameGNDList) {
			if (!(nameGNDObject instanceof String)) {
						JAXBElement<?> jaxbElement = (JAXBElement<?>) nameGNDObject;
						if(jaxbElement.getValue() instanceof Lb)
						{
							Lb LbElement = (Lb) jaxbElement.getValue();
							setNinLb(LbElement);
						}
						else if(jaxbElement.getValue() instanceof Choice)
						{
							Choice choiceElement = (Choice) jaxbElement.getValue();
							checkChoice(choiceElement);
						}
						else if(jaxbElement.getValue() instanceof NameGND)
						{
							NameGND nameInNameGND = (NameGND) jaxbElement.getValue();
							checkNameGnd(nameInNameGND);
						}
						else if(jaxbElement.getValue() instanceof LbEtc)
						{
							LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
							checkLbEtc(lbEtc);
						}
						else if(jaxbElement.getValue() instanceof SourceGND)
						{
							SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
							checkSourceGND(sourceGND);
						}
					}

		}

	}

	public static void checkSourceGND(SourceGND sourceGNDGND) {
		List<Serializable> sourceGNDList = sourceGNDGND.getContent();
		for (Object sourceGNDObject : sourceGNDList) {
			if (!(sourceGNDObject instanceof String)) {
						JAXBElement<?> jaxbElement = (JAXBElement<?>) sourceGNDObject;
						if(jaxbElement.getValue() instanceof Lb)
						{
							Lb LbElement = (Lb) jaxbElement.getValue();
							setNinLb(LbElement);
						}
						else if(jaxbElement.getValue() instanceof Choice)
						{
							Choice choiceElement = (Choice) jaxbElement.getValue();
							checkChoice(choiceElement);
						}
						else if(jaxbElement.getValue() instanceof NameGND)
						{
							NameGND nameInNameGND = (NameGND) jaxbElement.getValue();
							checkNameGnd(nameInNameGND);
						}
						else if(jaxbElement.getValue() instanceof LbEtc)
						{
							LbEtc lbEtc = (LbEtc) jaxbElement.getValue();
							checkLbEtc(lbEtc);
						}
						else if(jaxbElement.getValue() instanceof SourceGND)
						{
							SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
							checkSourceGND(sourceGND);
						}
					}

		}

	}
	

	public static void checkPFront(PFront pfront) {
		List<Serializable> pList = pfront.getContent();
				for (Object pListElement : pList) {
					if (!(pListElement instanceof String)) {
						JAXBElement<?> jaxbElement = (JAXBElement<?>) pListElement;
						if(jaxbElement.getValue() instanceof Lb)
						{
							Lb LbElement = (Lb) jaxbElement.getValue();
							setNinLb(LbElement);
						}
						else if(jaxbElement.getValue() instanceof Choice)
						{
							Choice choiceElement = (Choice) jaxbElement.getValue();
							checkChoice(choiceElement);
						}
						else if(jaxbElement.getValue() instanceof Pb)
						{
							lineNumber = 1;
						}
						else if(jaxbElement.getValue() instanceof Fw)
						{
							Fw fwElement = (Fw) jaxbElement.getValue();
							checkFw(fwElement);
						}
						else if(jaxbElement.getValue() instanceof NameGND)
						{
							NameGND nameGNDElement = (NameGND) jaxbElement.getValue();
							checkNameGnd(nameGNDElement);
						}
						else if(jaxbElement.getValue() instanceof SourceGND)
						{
							SourceGND sourceGNDElement = (SourceGND) jaxbElement.getValue();
							checkSourceGND(sourceGNDElement);
						}
						// else if(jaxbElement.getValue() instanceof Num)
						// {
						// 	Num numElement = (Num) jaxbElement.getValue();
						// 	checkNum(numElement);
						// }
						// else if(jaxbElement.getValue() instanceof Gap)
						// {
						// 	Gap gapElement = (Gap) jaxbElement.getValue();
						// 	checkGap(gapElement);
						// }
						else if(jaxbElement.getValue() instanceof LbEtc)
						{
							LbEtc lbEtcElement = (LbEtc) jaxbElement.getValue();
							checkLbEtc(lbEtcElement);
						}
						else if(jaxbElement.getValue() instanceof Table)
						{
							Table tableElement = (Table) jaxbElement.getValue();
							checkTable(tableElement);
						}
					}
				}
	}

	public static void checkChoice(Choice choice) {
			List<JAXBElement<?>> choiceElementsList = choice.getAbbrOrExpanOrOrig();

			for (JAXBElement<?> jaxbElement : choiceElementsList) {
				if (jaxbElement.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc)jaxbElement.getValue();
					checkLbEtc(lbEtc);
				}
				else if(jaxbElement.getValue() instanceof Lb) 
				{
					Lb lbElement = (Lb) jaxbElement.getValue();
					setNinLb(lbElement);
				}
				else if(jaxbElement.getValue() instanceof NameGND) 
				{
					NameGND nameGND = (NameGND) jaxbElement.getValue();
					checkNameGnd(nameGND);
				}
				else if(jaxbElement.getValue() instanceof SourceGND) 
				{
					SourceGND sourceGND = (SourceGND) jaxbElement.getValue();
					checkSourceGND(sourceGND);
				}

			}
		

	}

	public static void checkLbEtc(LbEtc lbEtc) {

		List<Serializable> lbEtcList = lbEtc.getContent();
		for (Object lbEtcListElement : lbEtcList) {
			if (!(lbEtcListElement instanceof String)) {
				JAXBElement<?> jaxbLbEtcListElement = (JAXBElement<?>) lbEtcListElement;
				if (jaxbLbEtcListElement.getValue() instanceof Choice) {
					Choice choice = (Choice)jaxbLbEtcListElement.getValue();
					checkChoice(choice);

				}
			 	else if (jaxbLbEtcListElement.getValue() instanceof Lb) 
				{				
					Lb lbElement = (Lb) jaxbLbEtcListElement.getValue();
					setNinLb(lbElement);				
				}
				else if (jaxbLbEtcListElement.getValue() instanceof NameGND) 
				{				
					NameGND nameGNDElement = (NameGND) jaxbLbEtcListElement.getValue();
					checkNameGnd(nameGNDElement);			
				}
				else if (jaxbLbEtcListElement.getValue() instanceof SourceGND) 
				{				
					SourceGND sourceGNDElement = (SourceGND) jaxbLbEtcListElement.getValue();
					checkSourceGND(sourceGNDElement);			
				}
				// else if (jaxbLbEtcListElement.getValue() instanceof Gap) 
				// {				
				// 	Gap gapElement = (Gap) jaxbLbEtcListElement.getValue();
				// 	checkGap(gapElement);			
				// }
				// else if (jaxbLbEtcListElement.getValue() instanceof Num) 
				// {				
				// 	Num numElement = (Num) jaxbLbEtcListElement.getValue();
				// 	checkNum(numElement);			
				// }
				else if (jaxbLbEtcListElement.getValue() instanceof Table) 
				{				
					Table tableElement = (Table) jaxbLbEtcListElement.getValue();
					checkTable(tableElement);			
				}
		}
		}
	}

	public static void checkFw(Fw fwElement) {

		List<Serializable> fwElementList = fwElement.getContent();
		for (Object fwElementListElement : fwElementList) {
			if (!(fwElementListElement instanceof String)) {
				JAXBElement<?> jaxbfwElementListElement = (JAXBElement<?>) fwElementListElement;
			 	if (jaxbfwElementListElement.getValue() instanceof Lb) 
				{				
					Lb lbElement = (Lb) jaxbfwElementListElement.getValue();
					setNinLb(lbElement);			
				}
				
		}
		}
	}

	

	

	public static void setNinLb(Lb lbElement) {

			// System.out.println("lb ist: " + lbElement.getN());
			lbElement.setN(Integer.toString(lineNumber));
			lineNumber++;

	}

}
