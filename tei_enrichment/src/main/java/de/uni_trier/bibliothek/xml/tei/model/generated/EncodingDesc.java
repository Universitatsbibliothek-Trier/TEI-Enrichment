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
 * <p>Java-Klasse für EncodingDesc complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="EncodingDesc">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="editorialDecl" type="{http://www.tei-c.org/ns/1.0}EditorialDecl"/>
 *         <element name="listPrefixDef" type="{http://www.tei-c.org/ns/1.0}ListPrefixDef"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncodingDesc", propOrder = {
    "editorialDecl",
    "listPrefixDef"
})
public class EncodingDesc {

    @XmlElement(required = true)
    protected EditorialDecl editorialDecl;
    @XmlElement(required = true)
    protected ListPrefixDef listPrefixDef;

    /**
     * Ruft den Wert der editorialDecl-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EditorialDecl }
     *     
     */
    public EditorialDecl getEditorialDecl() {
        return editorialDecl;
    }

    /**
     * Legt den Wert der editorialDecl-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EditorialDecl }
     *     
     */
    public void setEditorialDecl(EditorialDecl value) {
        this.editorialDecl = value;
    }

    /**
     * Ruft den Wert der listPrefixDef-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ListPrefixDef }
     *     
     */
    public ListPrefixDef getListPrefixDef() {
        return listPrefixDef;
    }

    /**
     * Legt den Wert der listPrefixDef-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ListPrefixDef }
     *     
     */
    public void setListPrefixDef(ListPrefixDef value) {
        this.listPrefixDef = value;
    }

}
