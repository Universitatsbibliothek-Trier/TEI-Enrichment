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
 * <p>Java-Klasse für PublicationStmt complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="PublicationStmt">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="publisher" type="{http://www.tei-c.org/ns/1.0}Publisher" maxOccurs="2"/>
 *         <element name="distributor" type="{http://www.tei-c.org/ns/1.0}Distributor"/>
 *         <element name="pubPlace" type="{http://www.tei-c.org/ns/1.0}pubPlace"/>
 *         <element name="date" type="{http://www.tei-c.org/ns/1.0}DateHeader"/>
 *         <element name="availability" type="{http://www.tei-c.org/ns/1.0}Availability"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublicationStmt", propOrder = {
    "publisher",
    "distributor",
    "pubPlace",
    "date",
    "availability"
})
public class PublicationStmt {

    @XmlElement(required = true)
    protected List<Publisher> publisher;
    @XmlElement(required = true)
    protected String distributor;
    @XmlElement(required = true)
    protected String pubPlace;
    @XmlElement(required = true)
    protected DateHeader date;
    @XmlElement(required = true)
    protected Availability availability;

    /**
     * Gets the value of the publisher property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the publisher property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublisher().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Publisher }
     * 
     * 
     * @return
     *     The value of the publisher property.
     */
    public List<Publisher> getPublisher() {
        if (publisher == null) {
            publisher = new ArrayList<>();
        }
        return this.publisher;
    }

    /**
     * Ruft den Wert der distributor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributor() {
        return distributor;
    }

    /**
     * Legt den Wert der distributor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributor(String value) {
        this.distributor = value;
    }

    /**
     * Ruft den Wert der pubPlace-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubPlace() {
        return pubPlace;
    }

    /**
     * Legt den Wert der pubPlace-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubPlace(String value) {
        this.pubPlace = value;
    }

    /**
     * Ruft den Wert der date-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link DateHeader }
     *     
     */
    public DateHeader getDate() {
        return date;
    }

    /**
     * Legt den Wert der date-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link DateHeader }
     *     
     */
    public void setDate(DateHeader value) {
        this.date = value;
    }

    /**
     * Ruft den Wert der availability-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Availability }
     *     
     */
    public Availability getAvailability() {
        return availability;
    }

    /**
     * Legt den Wert der availability-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Availability }
     *     
     */
    public void setAvailability(Availability value) {
        this.availability = value;
    }

}
