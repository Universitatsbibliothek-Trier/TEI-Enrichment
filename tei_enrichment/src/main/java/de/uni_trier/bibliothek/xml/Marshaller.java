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

package de.uni_trier.bibliothek.xml;

import java.io.StringWriter;
import java.io.Writer;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.PropertyException;

public class Marshaller<T> 
{
    private jakarta.xml.bind.Marshaller marshaller;

    public Marshaller(Class<T> rootElementClass) throws RuntimeException 
    {
        try 
        {
            JAXBContext jaxb = JAXBContext.newInstance(rootElementClass);
            this.marshaller = jaxb.createMarshaller();
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
    }

    public String marshal(T object) throws JAXBException 
    {
        StringWriter writer = new StringWriter();
        this.marshaller.marshal(object, writer);
        return writer.toString();
    }

    public void marshal(T object, Writer writer) throws JAXBException 
    {
        this.marshaller.marshal(object, writer);
    }

    public void setNamespacePrefixMapper(NamespacePrefixMapper namespacePrefixMapper) throws PropertyException
    {
        marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", namespacePrefixMapper);
    }

    public void setSchemaLocation(String schemaLocation) throws PropertyException
    {
        marshaller.setProperty(jakarta.xml.bind.Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
    }
    
    public void setFormat(String formatOutput) throws PropertyException
    {
        marshaller.setProperty(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }
}
