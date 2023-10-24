//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.orgs.model.generated;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.uni_trier.bibliothek.xml.orgs.model.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OrgOrgName_QNAME = new QName("http://www.tei-c.org/ns/1.0", "orgName");
    private final static QName _OrgIdno_QNAME = new QName("http://www.tei-c.org/ns/1.0", "idno");
    private final static QName _OrgLink_QNAME = new QName("http://www.tei-c.org/ns/1.0", "link");
    private final static QName _OrgNote_QNAME = new QName("http://www.tei-c.org/ns/1.0", "note");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.uni_trier.bibliothek.xml.orgs.model.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TEI }
     * 
     * @return
     *     the new instance of {@link TEI }
     */
    public TEI createTEI() {
        return new TEI();
    }

    /**
     * Create an instance of {@link TeiHeader }
     * 
     * @return
     *     the new instance of {@link TeiHeader }
     */
    public TeiHeader createTeiHeader() {
        return new TeiHeader();
    }

    /**
     * Create an instance of {@link Text }
     * 
     * @return
     *     the new instance of {@link Text }
     */
    public Text createText() {
        return new Text();
    }

    /**
     * Create an instance of {@link FileDesc }
     * 
     * @return
     *     the new instance of {@link FileDesc }
     */
    public FileDesc createFileDesc() {
        return new FileDesc();
    }

    /**
     * Create an instance of {@link Extent }
     * 
     * @return
     *     the new instance of {@link Extent }
     */
    public Extent createExtent() {
        return new Extent();
    }

    /**
     * Create an instance of {@link Measure }
     * 
     * @return
     *     the new instance of {@link Measure }
     */
    public Measure createMeasure() {
        return new Measure();
    }

    /**
     * Create an instance of {@link EditionStmt }
     * 
     * @return
     *     the new instance of {@link EditionStmt }
     */
    public EditionStmt createEditionStmt() {
        return new EditionStmt();
    }

    /**
     * Create an instance of {@link PublicationStmt }
     * 
     * @return
     *     the new instance of {@link PublicationStmt }
     */
    public PublicationStmt createPublicationStmt() {
        return new PublicationStmt();
    }

    /**
     * Create an instance of {@link Availability }
     * 
     * @return
     *     the new instance of {@link Availability }
     */
    public Availability createAvailability() {
        return new Availability();
    }

    /**
     * Create an instance of {@link TitleStmt }
     * 
     * @return
     *     the new instance of {@link TitleStmt }
     */
    public TitleStmt createTitleStmt() {
        return new TitleStmt();
    }

    /**
     * Create an instance of {@link SourceDesc }
     * 
     * @return
     *     the new instance of {@link SourceDesc }
     */
    public SourceDesc createSourceDesc() {
        return new SourceDesc();
    }

    /**
     * Create an instance of {@link Body }
     * 
     * @return
     *     the new instance of {@link Body }
     */
    public Body createBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link Div }
     * 
     * @return
     *     the new instance of {@link Div }
     */
    public Div createDiv() {
        return new Div();
    }

    /**
     * Create an instance of {@link ListOrg }
     * 
     * @return
     *     the new instance of {@link ListOrg }
     */
    public ListOrg createListOrg() {
        return new ListOrg();
    }

    /**
     * Create an instance of {@link Org }
     * 
     * @return
     *     the new instance of {@link Org }
     */
    public Org createOrg() {
        return new Org();
    }

    /**
     * Create an instance of {@link OrgsIdno }
     * 
     * @return
     *     the new instance of {@link OrgsIdno }
     */
    public OrgsIdno createOrgsIdno() {
        return new OrgsIdno();
    }

    /**
     * Create an instance of {@link Link }
     * 
     * @return
     *     the new instance of {@link Link }
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "orgName", scope = Org.class)
    public JAXBElement<String> createOrgOrgName(String value) {
        return new JAXBElement<>(_OrgOrgName_QNAME, String.class, Org.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrgsIdno }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OrgsIdno }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "idno", scope = Org.class)
    public JAXBElement<OrgsIdno> createOrgIdno(OrgsIdno value) {
        return new JAXBElement<>(_OrgIdno_QNAME, OrgsIdno.class, Org.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Link }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Link }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "link", scope = Org.class)
    public JAXBElement<Link> createOrgLink(Link value) {
        return new JAXBElement<>(_OrgLink_QNAME, Link.class, Org.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "note", scope = Org.class)
    public JAXBElement<String> createOrgNote(String value) {
        return new JAXBElement<>(_OrgNote_QNAME, String.class, Org.class, value);
    }

}
