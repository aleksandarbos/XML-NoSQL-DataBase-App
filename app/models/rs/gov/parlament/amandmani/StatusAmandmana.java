//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.12 at 03:47:13 PM CEST 
//


package models.rs.gov.parlament.amandmani;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Status_amandmana.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Status_amandmana">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PREDLOZEN"/>
 *     &lt;enumeration value="PRIHVACEN"/>
 *     &lt;enumeration value="ODBIJEN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Status_amandmana")
@XmlEnum
public enum StatusAmandmana {

    PREDLOZEN,
    PRIHVACEN,
    ODBIJEN;

    public String value() {
        return name();
    }

    public static StatusAmandmana fromValue(String v) {
        return valueOf(v);
    }

}