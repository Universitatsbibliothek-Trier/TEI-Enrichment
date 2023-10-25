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
 * <p>Java-Klasse für Front complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType name="Front">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice maxOccurs="unbounded">
 *         <element name="pb" type="{http://www.tei-c.org/ns/1.0}PbFront"/>
 *         <element name="div" type="{http://www.tei-c.org/ns/1.0}DivFront"/>
 *         <element name="titlePage" type="{http://www.tei-c.org/ns/1.0}titlePage"/>
 *         <element name="figure" type="{http://www.tei-c.org/ns/1.0}Figure" maxOccurs="unbounded"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Front", propOrder = {
    "pbOrDivOrTitlePage"
})
public class Front {

    @XmlElements({
        @XmlElement(name = "pb", type = PbFront.class),
        @XmlElement(name = "div", type = DivFront.class),
        @XmlElement(name = "titlePage", type = TitlePage.class),
        @XmlElement(name = "figure", type = Figure.class)
    })
    protected List<Object> pbOrDivOrTitlePage;

    /**
     * Gets the value of the pbOrDivOrTitlePage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the pbOrDivOrTitlePage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPbOrDivOrTitlePage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DivFront }
     * {@link Figure }
     * {@link PbFront }
     * {@link TitlePage }
     * 
     * 
     * @return
     *     The value of the pbOrDivOrTitlePage property.
     */
    public List<Object> getPbOrDivOrTitlePage() {
        if (pbOrDivOrTitlePage == null) {
            pbOrDivOrTitlePage = new ArrayList<>();
        }
        return this.pbOrDivOrTitlePage;
    }

}
