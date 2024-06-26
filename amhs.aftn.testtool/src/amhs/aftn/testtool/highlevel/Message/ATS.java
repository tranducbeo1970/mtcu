/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "AtsHeading")
@XmlAccessorType(XmlAccessType.NONE)
public class ATS {
    
    @XmlElement(name = "Extended")
    private Integer extended;
    
    @XmlElement(name = "EOHMode")
    private Integer eohMode;
    
    @XmlElement(name = "Priority")
    private String priority;
    
    @XmlElement(name = "FilingTime")
    private String filingTime;
    
    @XmlElement(name = "OptionalHeading")
    private String optionalHeading;
    
    @XmlElement(name = "Text")
    private String text;

    /**
     * @return the extended
     */
    public Integer getExtended() {
        return extended;
    }

    /**
     * @param extended the extended to set
     */
    public void setExtended(Integer extended) {
        this.extended = extended;
    }

    /**
     * @return the eohMode
     */
    public Integer getEohMode() {
        return eohMode;
    }

    /**
     * @param eohMode the eohMode to set
     */
    public void setEohMode(Integer eohMode) {
        this.eohMode = eohMode;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return the filingTime
     */
    public String getFilingTime() {
        return filingTime;
    }

    /**
     * @param filingTime the filingTime to set
     */
    public void setFilingTime(String filingTime) {
        this.filingTime = filingTime;
    }

    /**
     * @return the optionalHeading
     */
    public String getOptionalHeading() {
        return optionalHeading;
    }

    /**
     * @param optionalHeading the optionalHeading to set
     */
    public void setOptionalHeading(String optionalHeading) {
        this.optionalHeading = optionalHeading;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
