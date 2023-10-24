//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.listBibl.model.generated;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.uni_trier.bibliothek.xml.listBibl.model.generated package. 
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

    private final static QName _BiblTitle_QNAME = new QName("http://www.tei-c.org/ns/1.0", "title");
    private final static QName _BiblNote_QNAME = new QName("http://www.tei-c.org/ns/1.0", "note");
    private final static QName _BiblLink_QNAME = new QName("http://www.tei-c.org/ns/1.0", "link");
    private final static QName _BiblIdno_QNAME = new QName("http://www.tei-c.org/ns/1.0", "idno");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.uni_trier.bibliothek.xml.listBibl.model.generated
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
     * Create an instance of {@link ListBibl }
     * 
     * @return
     *     the new instance of {@link ListBibl }
     */
    public ListBibl createListBibl() {
        return new ListBibl();
    }

    /**
     * Create an instance of {@link Bibl }
     * 
     * @return
     *     the new instance of {@link Bibl }
     */
    public Bibl createBibl() {
        return new Bibl();
    }

    /**
     * Create an instance of {@link BiblIdno }
     * 
     * @return
     *     the new instance of {@link BiblIdno }
     */
    public BiblIdno createBiblIdno() {
        return new BiblIdno();
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
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "title", scope = Bibl.class)
    public JAXBElement<String> createBiblTitle(String value) {
        return new JAXBElement<>(_BiblTitle_QNAME, String.class, Bibl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "note", scope = Bibl.class)
    public JAXBElement<String> createBiblNote(String value) {
        return new JAXBElement<>(_BiblNote_QNAME, String.class, Bibl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Link }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Link }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "link", scope = Bibl.class)
    public JAXBElement<Link> createBiblLink(Link value) {
        return new JAXBElement<>(_BiblLink_QNAME, Link.class, Bibl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BiblIdno }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BiblIdno }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "idno", scope = Bibl.class)
    public JAXBElement<BiblIdno> createBiblIdno(BiblIdno value) {
        return new JAXBElement<>(_BiblIdno_QNAME, BiblIdno.class, Bibl.class, value);
    }

}
