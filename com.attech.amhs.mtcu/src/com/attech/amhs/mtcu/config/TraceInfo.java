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
public class TraceInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8066500809852632677L;

    // Trace
    @XmlElement(name = "GlobalDomainID")
    private String globalDomainID = "/C=XX/ADMD=ICAO/PRMD=VV/";

    @XmlElement(name = "AttempDomain")
    private String attempDomain = "/C=XX/ADMD=ICAO/PRMD=VV/";

    @XmlElement(name = "SuppliedDomainRoutingAction")
    private Integer suppliedDomainRoutingAction = 1;

    @XmlElement(name = "SuppliedDomainAdditionalActionRedirection")
    private Integer suppliedDomainAdditionalActionRedrection = 0;

    @XmlElement(name = "SuppliedDomainAdditionalDlOpertion")
    private Integer suppliedDomainAdditionalDLOperation = 0;

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
     * @return the attempDomain
     */
    public String getAttempDomain() {
        return attempDomain;
    }

    /**
     * @param attempDomain the attempDomain to set
     */
    public void setAttempDomain(String attempDomain) {
        this.attempDomain = attempDomain;
    }

    /**
     * @return the suppliedDomainRoutingAction
     */
    public Integer getSuppliedDomainRoutingAction() {
        return suppliedDomainRoutingAction;
    }

    /**
     * @param suppliedDomainRoutingAction the suppliedDomainRoutingAction to set
     */
    public void setSuppliedDomainRoutingAction(Integer suppliedDomainRoutingAction) {
        this.suppliedDomainRoutingAction = suppliedDomainRoutingAction;
    }

    /**
     * @return the suppliedDomainAdditionalActionRedrection
     */
    public Integer getSuppliedDomainAdditionalActionRedrection() {
        return suppliedDomainAdditionalActionRedrection;
    }

    /**
     * @param suppliedDomainAdditionalActionRedrection the suppliedDomainAdditionalActionRedrection to set
     */
    public void setSuppliedDomainAdditionalActionRedrection(Integer suppliedDomainAdditionalActionRedrection) {
        this.suppliedDomainAdditionalActionRedrection = suppliedDomainAdditionalActionRedrection;
    }

    /**
     * @return the suppliedDomainAdditionalDLOperation
     */
    public Integer getSuppliedDomainAdditionalDLOperation() {
        return suppliedDomainAdditionalDLOperation;
    }

    /**
     * @param suppliedDomainAdditionalDLOperation the suppliedDomainAdditionalDLOperation to set
     */
    public void setSuppliedDomainAdditionalDLOperation(Integer suppliedDomainAdditionalDLOperation) {
        this.suppliedDomainAdditionalDLOperation = suppliedDomainAdditionalDLOperation;
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
