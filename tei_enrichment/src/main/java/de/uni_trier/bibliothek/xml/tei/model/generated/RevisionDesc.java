//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.tei.model.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für RevisionDesc complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="RevisionDesc">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="change" type="{http://www.tei-c.org/ns/1.0}Change"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RevisionDesc", propOrder = {
    "change"
})
public class RevisionDesc {

    @XmlElement(required = true)
    protected Change change;

    /**
     * Ruft den Wert der change-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Change }
     *     
     */
    public Change getChange() {
        return change;
    }

    /**
     * Legt den Wert der change-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Change }
     *     
     */
    public void setChange(Change value) {
        this.change = value;
    }

}
