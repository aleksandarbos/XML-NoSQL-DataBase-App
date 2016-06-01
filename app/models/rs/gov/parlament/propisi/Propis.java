//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.01 at 11:17:58 AM CEST 
//


package models.rs.gov.parlament.propisi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.parlament.gov.rs/propisi}Preambula" minOccurs="0"/>
 *         &lt;element name="Naziv">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *               &lt;whiteSpace value="preserve"/>
 *               &lt;minLength value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.parlament.gov.rs/propisi}Deo" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.parlament.gov.rs/propisi}Status"/>
 *         &lt;element ref="{http://www.parlament.gov.rs/propisi}Prilozi"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Propis_je_usvojen" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Datum_izdavanja" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="Datum_stupanja_na_snagu" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "preambula",
    "naziv",
    "deo",
    "status",
    "prilozi"
})
@XmlRootElement(name = "Propis")
public class Propis {

    @XmlElement(name = "Preambula")
    protected Preambula preambula;
    @XmlElement(name = "Naziv", required = true)
    protected String naziv;
    @XmlElement(name = "Deo", required = true)
    protected List<Deo> deo;
    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "Prilozi", required = true)
    protected Prilozi prilozi;
    @XmlAttribute(name = "Propis_je_usvojen")
    protected Boolean propisJeUsvojen;
    @XmlAttribute(name = "Datum_izdavanja")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumIzdavanja;
    @XmlAttribute(name = "Datum_stupanja_na_snagu")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumStupanjaNaSnagu;

    /**
     * Gets the value of the preambula property.
     * 
     * @return
     *     possible object is
     *     {@link Preambula }
     *     
     */
    public Preambula getPreambula() {
        return preambula;
    }

    /**
     * Sets the value of the preambula property.
     * 
     * @param value
     *     allowed object is
     *     {@link Preambula }
     *     
     */
    public void setPreambula(Preambula value) {
        this.preambula = value;
    }

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
    }

    /**
     * Gets the value of the deo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deo }
     * 
     * 
     */
    public List<Deo> getDeo() {
        if (deo == null) {
            deo = new ArrayList<Deo>();
        }
        return this.deo;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the prilozi property.
     * 
     * @return
     *     possible object is
     *     {@link Prilozi }
     *     
     */
    public Prilozi getPrilozi() {
        return prilozi;
    }

    /**
     * Sets the value of the prilozi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Prilozi }
     *     
     */
    public void setPrilozi(Prilozi value) {
        this.prilozi = value;
    }

    /**
     * Gets the value of the propisJeUsvojen property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPropisJeUsvojen() {
        return propisJeUsvojen;
    }

    /**
     * Sets the value of the propisJeUsvojen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPropisJeUsvojen(Boolean value) {
        this.propisJeUsvojen = value;
    }

    /**
     * Gets the value of the datumIzdavanja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumIzdavanja() {
        return datumIzdavanja;
    }

    /**
     * Sets the value of the datumIzdavanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumIzdavanja(XMLGregorianCalendar value) {
        this.datumIzdavanja = value;
    }

    /**
     * Gets the value of the datumStupanjaNaSnagu property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumStupanjaNaSnagu() {
        return datumStupanjaNaSnagu;
    }

    /**
     * Sets the value of the datumStupanjaNaSnagu property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumStupanjaNaSnagu(XMLGregorianCalendar value) {
        this.datumStupanjaNaSnagu = value;
    }

}