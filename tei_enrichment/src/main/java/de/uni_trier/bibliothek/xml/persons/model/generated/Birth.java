//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.persons.model.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Birth complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Birth">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="when-custom">
 *         <simpleType>
 *           <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *           </restriction>
 *         </simpleType>
 *       </attribute>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Birth")
public class Birth {

    @XmlAttribute(name = "when-custom")
    protected String whenCustom;

    /**
     * Ruft den Wert der whenCustom-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhenCustom() {
        return whenCustom;
    }

    /**
     * Legt den Wert der whenCustom-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhenCustom(String value) {
        this.whenCustom = value;
    }

}
