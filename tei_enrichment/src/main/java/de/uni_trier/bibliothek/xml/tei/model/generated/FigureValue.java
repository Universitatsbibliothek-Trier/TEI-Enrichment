//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.tei.model.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für FigureValue complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="FigureValue">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded" minOccurs="0">
 *         <element name="graphic" type="{http://www.tei-c.org/ns/1.0}Graphic" maxOccurs="unbounded"/>
 *         <element name="figDesc" type="{http://www.tei-c.org/ns/1.0}FigDesc" maxOccurs="unbounded"/>
 *         <element name="lb" type="{http://www.tei-c.org/ns/1.0}Lb" maxOccurs="unbounded"/>
 *         <element name="fw" type="{http://www.tei-c.org/ns/1.0}Fw" maxOccurs="unbounded"/>
 *         <element name="figure" type="{http://www.tei-c.org/ns/1.0}Figure"/>
 *         <element name="name" type="{http://www.tei-c.org/ns/1.0}NameGND" maxOccurs="unbounded"/>
 *         <element name="quote" type="{http://www.tei-c.org/ns/1.0}SourceGND" maxOccurs="unbounded"/>
 *         <element name="head" type="{http://www.tei-c.org/ns/1.0}Head" maxOccurs="unbounded"/>
 *         <element name="p" type="{http://www.tei-c.org/ns/1.0}Pbody" maxOccurs="unbounded"/>
 *         <element name="list" type="{http://www.tei-c.org/ns/1.0}FigureList" maxOccurs="unbounded"/>
 *         <element name="note" type="{http://www.tei-c.org/ns/1.0}Note" maxOccurs="unbounded"/>
 *         <element name="supplied" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="w" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="table" type="{http://www.tei-c.org/ns/1.0}Table" maxOccurs="unbounded"/>
 *         <element name="subst" type="{http://www.tei-c.org/ns/1.0}Subst" maxOccurs="unbounded"/>
 *         <element name="foreign" type="{http://www.tei-c.org/ns/1.0}Foreign" maxOccurs="unbounded"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FigureValue", propOrder = {
    "graphicOrFigDescOrLb"
})
@XmlSeeAlso({
    Figure.class
})
public class FigureValue {

    @XmlElementRefs({
        @XmlElementRef(name = "graphic", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "figDesc", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lb", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "fw", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "figure", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "name", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "quote", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "head", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "p", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "list", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "note", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "supplied", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "w", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "table", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "subst", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "foreign", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> graphicOrFigDescOrLb;

    /**
     * Gets the value of the graphicOrFigDescOrLb property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the graphicOrFigDescOrLb property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGraphicOrFigDescOrLb().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link FigDesc }{@code >}
     * {@link JAXBElement }{@code <}{@link Figure }{@code >}
     * {@link JAXBElement }{@code <}{@link FigureList }{@code >}
     * {@link JAXBElement }{@code <}{@link Foreign }{@code >}
     * {@link JAXBElement }{@code <}{@link Fw }{@code >}
     * {@link JAXBElement }{@code <}{@link Graphic }{@code >}
     * {@link JAXBElement }{@code <}{@link Head }{@code >}
     * {@link JAXBElement }{@code <}{@link Lb }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link NameGND }{@code >}
     * {@link JAXBElement }{@code <}{@link Note }{@code >}
     * {@link JAXBElement }{@code <}{@link Pbody }{@code >}
     * {@link JAXBElement }{@code <}{@link SourceGND }{@code >}
     * {@link JAXBElement }{@code <}{@link Subst }{@code >}
     * {@link JAXBElement }{@code <}{@link Table }{@code >}
     * 
     * 
     * @return
     *     The value of the graphicOrFigDescOrLb property.
     */
    public List<JAXBElement<?>> getGraphicOrFigDescOrLb() {
        if (graphicOrFigDescOrLb == null) {
            graphicOrFigDescOrLb = new ArrayList<>();
        }
        return this.graphicOrFigDescOrLb;
    }

}
