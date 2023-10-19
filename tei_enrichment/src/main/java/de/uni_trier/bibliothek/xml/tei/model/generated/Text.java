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
 * <p>Java-Klasse für Text complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Text">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <all>
 *         <element name="front" type="{http://www.tei-c.org/ns/1.0}Front" minOccurs="0"/>
 *         <element name="body" type="{http://www.tei-c.org/ns/1.0}Body" minOccurs="0"/>
 *         <element name="group" type="{http://www.tei-c.org/ns/1.0}OuterGroup" minOccurs="0"/>
 *         <element name="back" type="{http://www.tei-c.org/ns/1.0}Back" minOccurs="0"/>
 *       </all>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Text", propOrder = {

})
public class Text {

    protected Front front;
    protected Body body;
    protected OuterGroup group;
    protected Back back;

    /**
     * Ruft den Wert der front-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Front }
     *     
     */
    public Front getFront() {
        return front;
    }

    /**
     * Legt den Wert der front-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Front }
     *     
     */
    public void setFront(Front value) {
        this.front = value;
    }

    /**
     * Ruft den Wert der body-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Body }
     *     
     */
    public Body getBody() {
        return body;
    }

    /**
     * Legt den Wert der body-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Body }
     *     
     */
    public void setBody(Body value) {
        this.body = value;
    }

    /**
     * Ruft den Wert der group-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link OuterGroup }
     *     
     */
    public OuterGroup getGroup() {
        return group;
    }

    /**
     * Legt den Wert der group-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link OuterGroup }
     *     
     */
    public void setGroup(OuterGroup value) {
        this.group = value;
    }

    /**
     * Ruft den Wert der back-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Back }
     *     
     */
    public Back getBack() {
        return back;
    }

    /**
     * Legt den Wert der back-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Back }
     *     
     */
    public void setBack(Back value) {
        this.back = value;
    }

}
