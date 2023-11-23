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
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Subst complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Subst">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <element name="del" type="{http://www.tei-c.org/ns/1.0}Del" maxOccurs="unbounded"/>
 *         <element name="add" type="{http://www.tei-c.org/ns/1.0}Add" maxOccurs="unbounded"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Subst", propOrder = {
    "delOrAdd"
})
public class Subst {

    @XmlElements({
        @XmlElement(name = "del", type = Del.class),
        @XmlElement(name = "add", type = Add.class)
    })
    protected List<Object> delOrAdd;

    /**
     * Gets the value of the delOrAdd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the delOrAdd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelOrAdd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Add }
     * {@link Del }
     * 
     * 
     * @return
     *     The value of the delOrAdd property.
     */
    public List<Object> getDelOrAdd() {
        if (delOrAdd == null) {
            delOrAdd = new ArrayList<>();
        }
        return this.delOrAdd;
    }

}
