//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.tei.model.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Monogr complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Monogr">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="author" type="{http://www.tei-c.org/ns/1.0}Author" maxOccurs="unbounded"/>
 *         <element name="title" type="{http://www.tei-c.org/ns/1.0}TitleMonogr" maxOccurs="2"/>
 *         <element name="edition" type="{http://www.tei-c.org/ns/1.0}Edition"/>
 *         <element name="imprint" type="{http://www.tei-c.org/ns/1.0}Imprint"/>
 *         <element name="extent" type="{http://www.tei-c.org/ns/1.0}Extent"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Monogr", propOrder = {
    "author",
    "title",
    "edition",
    "imprint",
    "extent"
})
public class Monogr {

    @XmlElement(required = true)
    protected List<String> author;
    @XmlElement(required = true)
    protected List<TitleMonogr> title;
    @XmlElement(required = true)
    protected Edition edition;
    @XmlElement(required = true)
    protected Imprint imprint;
    @XmlElement(required = true)
    protected Extent extent;

    /**
     * Gets the value of the author property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the author property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     * @return
     *     The value of the author property.
     */
    public List<String> getAuthor() {
        if (author == null) {
            author = new ArrayList<>();
        }
        return this.author;
    }

    /**
     * Gets the value of the title property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the title property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TitleMonogr }
     * 
     * 
     * @return
     *     The value of the title property.
     */
    public List<TitleMonogr> getTitle() {
        if (title == null) {
            title = new ArrayList<>();
        }
        return this.title;
    }

    /**
     * Ruft den Wert der edition-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Edition }
     *     
     */
    public Edition getEdition() {
        return edition;
    }

    /**
     * Legt den Wert der edition-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Edition }
     *     
     */
    public void setEdition(Edition value) {
        this.edition = value;
    }

    /**
     * Ruft den Wert der imprint-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Imprint }
     *     
     */
    public Imprint getImprint() {
        return imprint;
    }

    /**
     * Legt den Wert der imprint-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Imprint }
     *     
     */
    public void setImprint(Imprint value) {
        this.imprint = value;
    }

    /**
     * Ruft den Wert der extent-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Extent }
     *     
     */
    public Extent getExtent() {
        return extent;
    }

    /**
     * Legt den Wert der extent-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Extent }
     *     
     */
    public void setExtent(Extent value) {
        this.extent = value;
    }

}
