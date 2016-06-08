//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.03 at 04:28:13 PM CEST 
//


package models.rs.gov.parlament.korisnici;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://www.parlament.gov.rs/korisnici}Gradjani" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.parlament.gov.rs/korisnici}Odbornici" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.parlament.gov.rs/korisnici}Predsednik_skupstine"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "gradjani",
    "odbornici",
    "predsednikSkupstine"
})
@XmlRootElement(name = "Korisnici")
public class Korisnici {

    @XmlElement(name = "Gradjani", required = true)
    protected List<Gradjani> gradjani;
    @XmlElement(name = "Odbornici", required = true)
    protected List<Odbornici> odbornici;
    @XmlElement(name = "Predsednik_skupstine", required = true)
    protected PredsednikSkupstineTip predsednikSkupstine;

    /**
     * Gets the value of the gradjani property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gradjani property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGradjani().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Gradjani }
     * 
     * 
     */
    public List<Gradjani> getGradjani() {
        if (gradjani == null) {
            gradjani = new ArrayList<Gradjani>();
        }
        return this.gradjani;
    }

    /**
     * Gets the value of the odbornici property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the odbornici property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOdbornici().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Odbornici }
     * 
     * 
     */
    public List<Odbornici> getOdbornici() {
        if (odbornici == null) {
            odbornici = new ArrayList<Odbornici>();
        }
        return this.odbornici;
    }

    /**
     * Gets the value of the predsednikSkupstine property.
     * 
     * @return
     *     possible object is
     *     {@link PredsednikSkupstineTip }
     *     
     */
    public PredsednikSkupstineTip getPredsednikSkupstine() {
        return predsednikSkupstine;
    }

    /**
     * Sets the value of the predsednikSkupstine property.
     * 
     * @param value
     *     allowed object is
     *     {@link PredsednikSkupstineTip }
     *     
     */
    public void setPredsednikSkupstine(PredsednikSkupstineTip value) {
        this.predsednikSkupstine = value;
    }

}