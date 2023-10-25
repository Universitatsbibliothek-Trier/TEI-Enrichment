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

import de.uni_trier.bibliothek.xml.tei.model.generated.TEI;

public class EntityListEnricher {


	public static List<Object> enrichList(List<Object> entityList)
	{
		TEI originalTEI = (TEI) entityList.get(0);
		de.uni_trier.bibliothek.xml.events.model.generated.TEI teiEventsFile = (de.uni_trier.bibliothek.xml.events.model.generated.TEI) entityList.get(1);
		de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI teiListBiblFile = (de.uni_trier.bibliothek.xml.listBibl.model.generated.TEI) entityList.get(2);
		de.uni_trier.bibliothek.xml.objects.model.generated.TEI teiObjectsFile = (de.uni_trier.bibliothek.xml.objects.model.generated.TEI) entityList.get(3);
		de.uni_trier.bibliothek.xml.orgs.model.generated.TEI teiOrgsFile = (de.uni_trier.bibliothek.xml.orgs.model.generated.TEI) entityList.get(4);
		de.uni_trier.bibliothek.xml.persons.model.generated.TEI teiPersonsFile = (de.uni_trier.bibliothek.xml.persons.model.generated.TEI) entityList.get(5);
		de.uni_trier.bibliothek.xml.places.model.generated.TEI teiPlacesFile = (de.uni_trier.bibliothek.xml.places.model.generated.TEI) entityList.get(6);
		// insert TEI-Schema for easier validation
		
		
		List<Object> entityListEnriched = new ArrayList<Object>();
		entityListEnriched.add(originalTEI);
		entityListEnriched.add(teiEventsFile);
		entityListEnriched.add(teiListBiblFile);
		entityListEnriched.add(teiObjectsFile);
		entityListEnriched.add(teiOrgsFile);
		entityListEnriched.add(teiPersonsFile);
		entityListEnriched.add(teiPlacesFile);
		return entityListEnriched;
	}

}
