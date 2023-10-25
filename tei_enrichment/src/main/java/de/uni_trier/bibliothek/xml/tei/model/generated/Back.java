//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.tei.model.generated;

import java.util.ArrayList;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Back complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Back">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <element name="div" type="{http://www.tei-c.org/ns/1.0}DivFront" maxOccurs="unbounded"/>
 *         <element name="pb" type="{http://www.tei-c.org/ns/1.0}PbFront" maxOccurs="unbounded"/>
 *         <element name="lb" type="{http://www.tei-c.org/ns/1.0}Lb" maxOccurs="unbounded"/>
 *         <element name="fw" type="{http://www.tei-c.org/ns/1.0}Fw" maxOccurs="unbounded"/>
 *         <element name="figure" type="{http://www.tei-c.org/ns/1.0}Figure" maxOccurs="unbounded"/>
 *         <element name="note" type="{http://www.tei-c.org/ns/1.0}Note" maxOccurs="unbounded"/>
 *         <element name="supplied" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="w" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="table" type="{http://www.tei-c.org/ns/1.0}Table" maxOccurs="unbounded"/>
 *         <element name="list" type="{http://www.tei-c.org/ns/1.0}List" maxOccurs="unbounded"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Back", propOrder = {
    "divOrPbOrLb"
})
public class Back {

    @XmlElementRefs({
        @XmlElementRef(name = "div", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "pb", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lb", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "fw", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "figure", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "note", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "supplied", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "w", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "table", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "list", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false)
    })
    protected java.util.List<JAXBElement<?>> divOrPbOrLb;

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
     * {@link JAXBElement }{@code <}{@link DivFront }{@code >}
     * {@link JAXBElement }{@code <}{@link Figure }{@code >}
     * {@link JAXBElement }{@code <}{@link Fw }{@code >}
     * {@link JAXBElement }{@code <}{@link Lb }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link de.uni_trier.bibliothek.xml.tei.model.generated.List }{@code >}
     * {@link JAXBElement }{@code <}{@link Note }{@code >}
     * {@link JAXBElement }{@code <}{@link PbFront }{@code >}
     * {@link JAXBElement }{@code <}{@link Table }{@code >}
     * 
     * 
     * @return
     *     The value of the divOrPbOrLb property.
     */
    public java.util.List<JAXBElement<?>> getDivOrPbOrLb() {
        if (divOrPbOrLb == null) {
            divOrPbOrLb = new ArrayList<>();
        }
        return this.divOrPbOrLb;
    }

}
