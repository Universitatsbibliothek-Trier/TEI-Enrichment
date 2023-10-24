//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.0 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package de.uni_trier.bibliothek.xml.places.model.generated;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.uni_trier.bibliothek.xml.places.model.generated package. 
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

    private final static QName _LocationRegion_QNAME = new QName("http://www.tei-c.org/ns/1.0", "region");
    private final static QName _LocationGeo_QNAME = new QName("http://www.tei-c.org/ns/1.0", "geo");
    private final static QName _PlacePlaceName_QNAME = new QName("http://www.tei-c.org/ns/1.0", "placeName");
    private final static QName _PlaceLabel_QNAME = new QName("http://www.tei-c.org/ns/1.0", "label");
    private final static QName _PlaceLocation_QNAME = new QName("http://www.tei-c.org/ns/1.0", "location");
    private final static QName _PlaceNote_QNAME = new QName("http://www.tei-c.org/ns/1.0", "note");
    private final static QName _PlaceLink_QNAME = new QName("http://www.tei-c.org/ns/1.0", "link");
    private final static QName _PlaceIdno_QNAME = new QName("http://www.tei-c.org/ns/1.0", "idno");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.uni_trier.bibliothek.xml.places.model.generated
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
     * Create an instance of {@link ListPlace }
     * 
     * @return
     *     the new instance of {@link ListPlace }
     */
    public ListPlace createListPlace() {
        return new ListPlace();
    }

    /**
     * Create an instance of {@link Place }
     * 
     * @return
     *     the new instance of {@link Place }
     */
    public Place createPlace() {
        return new Place();
    }

    /**
     * Create an instance of {@link Location }
     * 
     * @return
     *     the new instance of {@link Location }
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link PlaceIdno }
     * 
     * @return
     *     the new instance of {@link PlaceIdno }
     */
    public PlaceIdno createPlaceIdno() {
        return new PlaceIdno();
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
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "region", scope = Location.class)
    public JAXBElement<String> createLocationRegion(String value) {
        return new JAXBElement<>(_LocationRegion_QNAME, String.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "geo", scope = Location.class)
    public JAXBElement<String> createLocationGeo(String value) {
        return new JAXBElement<>(_LocationGeo_QNAME, String.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "placeName", scope = Place.class)
    public JAXBElement<String> createPlacePlaceName(String value) {
        return new JAXBElement<>(_PlacePlaceName_QNAME, String.class, Place.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "label", scope = Place.class)
    public JAXBElement<String> createPlaceLabel(String value) {
        return new JAXBElement<>(_PlaceLabel_QNAME, String.class, Place.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Location }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Location }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "location", scope = Place.class)
    public JAXBElement<Location> createPlaceLocation(Location value) {
        return new JAXBElement<>(_PlaceLocation_QNAME, Location.class, Place.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "note", scope = Place.class)
    public JAXBElement<String> createPlaceNote(String value) {
        return new JAXBElement<>(_PlaceNote_QNAME, String.class, Place.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Link }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Link }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "link", scope = Place.class)
    public JAXBElement<Link> createPlaceLink(Link value) {
        return new JAXBElement<>(_PlaceLink_QNAME, Link.class, Place.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlaceIdno }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PlaceIdno }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.tei-c.org/ns/1.0", name = "idno", scope = Place.class)
    public JAXBElement<PlaceIdno> createPlaceIdno(PlaceIdno value) {
        return new JAXBElement<>(_PlaceIdno_QNAME, PlaceIdno.class, Place.class, value);
    }

}
