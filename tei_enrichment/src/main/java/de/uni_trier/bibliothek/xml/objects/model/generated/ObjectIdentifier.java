//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.objects.model.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ObjectIdentifier complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="ObjectIdentifier">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <element name="objectName" type="{http://www.tei-c.org/ns/1.0}ObjectName" maxOccurs="unbounded"/>
 *         <element name="idno" type="{http://www.tei-c.org/ns/1.0}ObjectIdno" maxOccurs="unbounded"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectIdentifier", propOrder = {
    "objectNameOrIdno"
})
public class ObjectIdentifier {

    @XmlElements({
        @XmlElement(name = "objectName", type = String.class),
        @XmlElement(name = "idno", type = ObjectIdno.class)
    })
    protected List<java.lang.Object> objectNameOrIdno;

    /**
     * Gets the value of the objectNameOrIdno property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the objectNameOrIdno property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectNameOrIdno().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectIdno }
     * {@link String }
     * 
     * 
     * @return
     *     The value of the objectNameOrIdno property.
     */
    public List<java.lang.Object> getObjectNameOrIdno() {
        if (objectNameOrIdno == null) {
            objectNameOrIdno = new ArrayList<>();
        }
        return this.objectNameOrIdno;
    }

}
