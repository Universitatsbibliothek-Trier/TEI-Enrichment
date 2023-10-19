//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.tei.model.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
 *       <choice>
 *         <element name="div" type="{http://www.tei-c.org/ns/1.0}DivFront"/>
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
    "div"
})
public class Body {

    protected DivFront div;

    /**
     * Ruft den Wert der div-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link DivFront }
     *     
     */
    public DivFront getDiv() {
        return div;
    }

    /**
     * Legt den Wert der div-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link DivFront }
     *     
     */
    public void setDiv(DivFront value) {
        this.div = value;
    }

}
