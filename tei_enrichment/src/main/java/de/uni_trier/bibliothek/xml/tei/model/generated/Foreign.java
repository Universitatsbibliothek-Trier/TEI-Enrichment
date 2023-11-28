//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.tei.model.generated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Foreign complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Foreign">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <element name="abbr" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="expan" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="orig" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="reg" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="seg" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="lb" type="{http://www.tei-c.org/ns/1.0}Lb" maxOccurs="unbounded"/>
 *         <element name="name" type="{http://www.tei-c.org/ns/1.0}NameGND" maxOccurs="unbounded"/>
 *         <element name="quote" type="{http://www.tei-c.org/ns/1.0}SourceGND" maxOccurs="unbounded"/>
 *         <element name="rs" type="{http://www.tei-c.org/ns/1.0}NameGND" maxOccurs="unbounded"/>
 *         <element name="sic" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="corr" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="note" type="{http://www.tei-c.org/ns/1.0}Note" maxOccurs="unbounded"/>
 *         <element name="supplied" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *         <element name="w" type="{http://www.tei-c.org/ns/1.0}LbEtc" maxOccurs="unbounded"/>
 *       </choice>
 *       <attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Foreign", propOrder = {
    "content"
})
public class Foreign {

    @XmlElementRefs({
        @XmlElementRef(name = "abbr", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "expan", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "orig", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "reg", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "seg", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lb", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "name", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "quote", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "rs", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "sic", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "corr", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "note", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "supplied", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "w", namespace = "http://www.tei-c.org/ns/1.0", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Lb }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link LbEtc }{@code >}
     * {@link JAXBElement }{@code <}{@link NameGND }{@code >}
     * {@link JAXBElement }{@code <}{@link NameGND }{@code >}
     * {@link JAXBElement }{@code <}{@link Note }{@code >}
     * {@link JAXBElement }{@code <}{@link SourceGND }{@code >}
     * {@link String }
     * 
     * 
     * @return
     *     The value of the content property.
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return this.content;
    }

    /**
     * Ruft den Wert der lang-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Legt den Wert der lang-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

}
