//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.26 at 08:47:22 PM IST 
//


package org.sonar.plugins.resharperplugin.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{}Information"/>
 *         &lt;element ref="{}IssueTypes"/>
 *         &lt;element ref="{}Issues"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ToolsVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "information",
    "issueTypes",
    "issues"
})
@XmlRootElement(name = "Report")
public class Report {

    @XmlElement(name = "Information", required = true)
    protected Information information;
    @XmlElement(name = "IssueTypes", required = true)
    protected IssueTypes issueTypes;
    @XmlElement(name = "Issues", required = true)
    protected Issues issues;
    @XmlAttribute(name = "ToolsVersion", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String toolsVersion;

    /**
     * Gets the value of the information property.
     * 
     * @return
     *     possible object is
     *     {@link org.sonar.plugins.resharperplugin.objects.Information }
     *     
     */
    public Information getInformation() {
        return information;
    }

    /**
     * Sets the value of the information property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.sonar.plugins.resharperplugin.objects.Information }
     *     
     */
    public void setInformation(Information value) {
        this.information = value;
    }

    /**
     * Gets the value of the issueTypes property.
     * 
     * @return
     *     possible object is
     *     {@link org.sonar.plugins.resharperplugin.objects.IssueTypes }
     *     
     */
    public IssueTypes getIssueTypes() {
        return issueTypes;
    }

    /**
     * Sets the value of the issueTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.sonar.plugins.resharperplugin.objects.IssueTypes }
     *     
     */
    public void setIssueTypes(IssueTypes value) {
        this.issueTypes = value;
    }

    /**
     * Gets the value of the issues property.
     * 
     * @return
     *     possible object is
     *     {@link org.sonar.plugins.resharperplugin.objects.Issues }
     *     
     */
    public Issues getIssues() {
        return issues;
    }

    /**
     * Sets the value of the issues property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.sonar.plugins.resharperplugin.objects.Issues }
     *     
     */
    public void setIssues(Issues value) {
        this.issues = value;
    }

    /**
     * Gets the value of the toolsVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolsVersion() {
        return toolsVersion;
    }

    /**
     * Sets the value of the toolsVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolsVersion(String value) {
        this.toolsVersion = value;
    }

}
