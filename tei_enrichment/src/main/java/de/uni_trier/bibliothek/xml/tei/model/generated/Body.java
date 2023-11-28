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
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Body complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Body">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <element name="div" type="{http://www.tei-c.org/ns/1.0}DivFront" maxOccurs="unbounded"/>
 *         <element name="pb" type="{http://www.tei-c.org/ns/1.0}Pb" maxOccurs="unbounded"/>
 *         <element name="lb" type="{http://www.tei-c.org/ns/1.0}Lb" maxOccurs="unbounded"/>
 *         <element name="fw" type="{http://www.tei-c.org/ns/1.0}Fw" maxOccurs="unbounded"/>
 *         <element name="figure" type="{http://www.tei-c.org/ns/1.0}Figure" maxOccurs="unbounded"/>
 *         <element name="choice" type="{http://www.tei-c.org/ns/1.0}Choice" maxOccurs="unbounded"/>
 *         <element name="name" type="{http://www.tei-c.org/ns/1.0}NameGND" maxOccurs="unbounded"/>
 *         <element name="quote" type="{http://www.tei-c.org/ns/1.0}SourceGND" maxOccurs="unbounded"/>
 *         <element name="rs" type="{http://www.tei-c.org/ns/1.0}NameGND" maxOccurs="unbounded"/>
 *         <element name="num" type="{http://www.tei-c.org/ns/1.0}Num" maxOccurs="unbounded"/>
 *         <element name="gap" type="{http://www.tei-c.org/ns/1.0}Gap" maxOccurs="unbounded"/>
 *         <element name="note" type="{http://www.tei-c.org/ns/1.0}Note" maxOccurs="unbounded"/>
 *         <element name="supplied" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="w" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="milestone" type="{http://www.tei-c.org/ns/1.0}Milestone" maxOccurs="unbounded"/>
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
@XmlType(name = "Body", propOrder = {
    "divOrPbOrLb"
})
public class Body {

    @XmlElementRefs({
        @XmlElementRef(name = "div", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "pb", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lb", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "fw", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "figure", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "choice", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "name", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "quote", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "rs", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "num", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "gap", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "note", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "supplied", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "w", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "milestone", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "table", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "subst", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "foreign", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> divOrPbOrLb;

    /**
     * Gets the value of the divOrPbOrLb property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the divOrPbOrLb property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDivOrPbOrLb().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Choice }{@code >}
     * {@link JAXBElement }{@code <}{@link DivFront }{@code >}
     * {@link JAXBElement }{@code <}{@link Figure }{@code >}
     * {@link JAXBElement }{@code <}{@link Foreign }{@code >}
     * {@link JAXBElement }{@code <}{@link Fw }{@code >}
     * {@link JAXBElement }{@code <}{@link Gap }{@code >}
     * {@link JAXBElement }{@code <}{@link Lb }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link Milestone }{@code >}
     * {@link JAXBElement }{@code <}{@link NameGND }{@code >}
     * {@link JAXBElement }{@code <}{@link NameGND }{@code >}
     * {@link JAXBElement }{@code <}{@link Note }{@code >}
     * {@link JAXBElement }{@code <}{@link Num }{@code >}
     * {@link JAXBElement }{@code <}{@link Pb }{@code >}
     * {@link JAXBElement }{@code <}{@link SourceGND }{@code >}
     * {@link JAXBElement }{@code <}{@link Subst }{@code >}
     * {@link JAXBElement }{@code <}{@link Table }{@code >}
     * 
     * 
     * @return
     *     The value of the divOrPbOrLb property.
     */
    public List<JAXBElement<?>> getDivOrPbOrLb() {
        if (divOrPbOrLb == null) {
            divOrPbOrLb = new ArrayList<>();
        }
        return this.divOrPbOrLb;
    }

}
