/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "TraceInfo")
@XmlAccessorType(XmlAccessType.NONE)
public class InternalTraceInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6183648518450318415L;

    // Trace
    @XmlElement(name = "GlobalDomainID")
    private String globalDomainID = "/C=XX/ADMD=ICAO/PRMD=VV/";

    @XmlElement(name = "MTA")
    private String mta;

    @XmlElement(name = "AttempedMTA")
    private String attempedMTA;

    @XmlElement(name = "ConvertedEncodedInformationTypes")
    private String convertedEncodedInformationTypes;

    /**
     * @return the globalDomainID
     */
    public String getGlobalDomainID() {
        return globalDomainID;
    }

    /**
     * @param globalDomainID the globalDomainID to set
     */
    public void setGlobalDomainID(String globalDomainID) {
        this.globalDomainID = globalDomainID;
    }

    /**
     * @return the mta
     */
    public String getMta() {
        return mta;
    }

    /**
     * @param mta the mta to set
     */
    public void setMta(String mta) {
        this.mta = mta;
    }

    /**
     * @return the attempedMTA
     */
    public String getAttempedMTA() {
        return attempedMTA;
    }

    /**
     * @param attempedMTA the attempedMTA to set
     */
    public void setAttempedMTA(String attempedMTA) {
        this.attempedMTA = attempedMTA;
    }

    /**
     * @return the convertedEncodedInformationTypes
     */
    public String getConvertedEncodedInformationTypes() {
        return convertedEncodedInformationTypes;
    }

    /**
     * @param convertedEncodedInformationTypes the convertedEncodedInformationTypes to set
     */
    public void setConvertedEncodedInformationTypes(String convertedEncodedInformationTypes) {
        this.convertedEncodedInformationTypes = convertedEncodedInformationTypes;
    }

}
