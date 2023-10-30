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

import de.uni_trier.bibliothek.xml.tei.model.generated.Choice;
import de.uni_trier.bibliothek.xml.tei.model.generated.DivFront;
import de.uni_trier.bibliothek.xml.tei.model.generated.DocTitle;
import de.uni_trier.bibliothek.xml.tei.model.generated.Front;
import de.uni_trier.bibliothek.xml.tei.model.generated.Lb;
import de.uni_trier.bibliothek.xml.tei.model.generated.LbEtc;
import de.uni_trier.bibliothek.xml.tei.model.generated.ObjectFactory;
import de.uni_trier.bibliothek.xml.tei.model.generated.PFront;
import de.uni_trier.bibliothek.xml.tei.model.generated.Pb;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import de.uni_trier.bibliothek.xml.tei.model.generated.Text;
import de.uni_trier.bibliothek.xml.tei.model.generated.TitlePage;
import jakarta.xml.bind.JAXBElement;

public class LineCounter {

	public static int lineNumber;
	public static TEI originalTEI;
	public static ObjectFactory personsTEIObjectFactory = new ObjectFactory();

	public static TEI countLines(TEI originalTEIParameter) {
		lineNumber = 1;
		originalTEI = originalTEIParameter;
		System.out.println("start counting Lines");
		Text originText = originalTEI.getText();
		checkText(originText);
		return originalTEI;
	}

	public static void checkText(Text text) {
		Front front = text.getFront();
		checkFront(front);
	}

	public static void checkFront(Front front) {
		List<Object> pbOrDivOrTitlePageList = front.getPbOrDivOrTitlePage();
		// System.out.println("liste von front: " + pbOrDivOrTitlePageList.toString());
		for (Object pbOrDivOrTitlePage : pbOrDivOrTitlePageList) {
			if (pbOrDivOrTitlePage instanceof DivFront) {
				DivFront divFrontElement = (DivFront) pbOrDivOrTitlePage;
				checkDivFront(divFrontElement);
			}
			if (pbOrDivOrTitlePage instanceof TitlePage) {
				TitlePage titlePage = (TitlePage) pbOrDivOrTitlePage;
				checkTitlePage(titlePage);
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
				// System.out.println("pElements: " + pElement.getContent());
				
			}

			}
			
		}
	}

	public static void checkDocTitle(DocTitle docTitle) {
		//todo
	}
	

	public static void checkPFront(PFront pfront) {
		List<Serializable> pList = pfront.getContent();
				// System.out.println("pList: " + pList);
				for (Object pListElement : pList) {
					// JAXBElement<?> jaxbElement;
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
					}
				}
	}

	public static void checkChoice(Choice choice) {
			List<JAXBElement<?>> choiceElementsList = choice.getAbbrOrExpanOrOrig();

			for (JAXBElement<?> abbrOrExpanOrOrigElement : choiceElementsList) {
				if (abbrOrExpanOrOrigElement.getValue() instanceof LbEtc) {
					LbEtc lbEtc = (LbEtc)abbrOrExpanOrOrigElement.getValue();
					checkLbEtc(lbEtc);
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
			 else if (lbEtcListElement instanceof Lb) {
				
				Lb LbElement = (Lb) jaxbLbEtcListElement.getValue();
					setNinLb(LbElement);
				
			}
		}
		}
	}

	

	public static void setNinLb(Lb lbElement) {

			System.out.println("lb ist: " + lbElement.getN());
			lbElement.setN(Integer.toString(lineNumber));
			lineNumber++;

	}

}
