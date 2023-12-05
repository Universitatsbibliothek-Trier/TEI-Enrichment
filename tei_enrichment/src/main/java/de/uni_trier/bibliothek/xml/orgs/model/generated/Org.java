//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.orgs.model.generated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Org complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Org">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <element name="orgName" type="{http://www.tei-c.org/ns/1.0}OrgName" maxOccurs="unbounded"/>
 *         <element name="idno" type="{http://www.tei-c.org/ns/1.0}OrgsIdno" maxOccurs="unbounded"/>
 *         <element name="link" type="{http://www.tei-c.org/ns/1.0}Link" maxOccurs="unbounded"/>
 *         <element name="note" type="{http://www.tei-c.org/ns/1.0}Note" maxOccurs="unbounded"/>
 *       </choice>
 *       <anyAttribute/>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Org", propOrder = {
    "orgNameOrIdnoOrLink"
})
public class Org {

    @XmlElements({
        @XmlElement(name = "orgName", type = String.class),
        @XmlElement(name = "idno", type = OrgsIdno.class),
        @XmlElement(name = "link", type = Link.class),
        @XmlElement(name = "note", type = Note.class)
    })
    protected List<Object> orgNameOrIdnoOrLink;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<>();

    /**
     * Gets the value of the orgNameOrIdnoOrLink property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the orgNameOrIdnoOrLink property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrgNameOrIdnoOrLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Link }
     * {@link Note }
     * {@link OrgsIdno }
     * {@link String }
     * 
     * 
     * @return
     *     The value of the orgNameOrIdnoOrLink property.
     */
    public List<Object> getOrgNameOrIdnoOrLink() {
        if (orgNameOrIdnoOrLink == null) {
            orgNameOrIdnoOrLink = new ArrayList<>();
        }
        return this.orgNameOrIdnoOrLink;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
