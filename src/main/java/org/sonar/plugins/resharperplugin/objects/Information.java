//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.26 at 08:47:22 PM IST 
//


package org.sonar.plugins.resharperplugin.objects;

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
 *         &lt;element ref="{}Solution"/>
 *         &lt;element ref="{}InspectionScope"/>
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
    "solution",
    "inspectionScope"
})
@XmlRootElement(name = "Information")
public class Information {

    @XmlElement(name = "Solution", required = true)
    protected String solution;
    @XmlElement(name = "InspectionScope", required = true)
    protected InspectionScope inspectionScope;

    /**
     * Gets the value of the solution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSolution() {
        return solution;
    }

    /**
     * Sets the value of the solution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSolution(String value) {
        this.solution = value;
    }

    /**
     * Gets the value of the inspectionScope property.
     * 
     * @return
     *     possible object is
     *     {@link org.sonar.plugins.resharperplugin.objects.InspectionScope }
     *     
     */
    public InspectionScope getInspectionScope() {
        return inspectionScope;
    }

    /**
     * Sets the value of the inspectionScope property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.sonar.plugins.resharperplugin.objects.InspectionScope }
     *     
     */
    public void setInspectionScope(InspectionScope value) {
        this.inspectionScope = value;
    }

}
