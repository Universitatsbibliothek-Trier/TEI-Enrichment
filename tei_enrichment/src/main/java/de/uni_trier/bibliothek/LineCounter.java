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

import de.uni_trier.bibliothek.xml.tei.model.generated.Front;
import de.uni_trier.bibliothek.xml.tei.model.generated.Lb;
import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;
import de.uni_trier.bibliothek.xml.tei.model.generated.Text;
import jakarta.xml.bind.JAXBElement;

public class LineCounter {

	public static TEI originalTEI;
	public static de.uni_trier.bibliothek.xml.tei.model.generated.ObjectFactory personsTEIObjectFactory = new de.uni_trier.bibliothek.xml.tei.model.generated.ObjectFactory();

	public static TEI countLines(TEI originalTEIParameter)
	{
		originalTEI = originalTEIParameter;		
		
		Text originText = originalTEI.getText();
		Front front = originText.getFront();
		List<Object> pbOrDivOrTitlePage = front.getPbOrDivOrTitlePage();		

		return originalTEI;
	}


}
