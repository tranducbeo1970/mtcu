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
@XmlRootElement(name = "ReportRecipient")
@XmlAccessorType(XmlAccessType.NONE)
public class ReportRecipient extends Recipient {
    
     @XmlElement(name = "SupplementInfo")
    private String supplementInfo;

    @XmlElement(name = "UserType")
    private Integer userType;

    @XmlElement(name = "ArrivalTime")
    private String arrivalTime;

    @XmlElement(name = "NonDeliveryReasonCode")
    private Integer nonDeliveryReasonCode;

    @XmlElement(name = "NonDeliveryDiagnosticCode")
    private Integer nonDeliveryDiagnosticCode;
    
    @XmlElement(name = "DeliverTime")
    private String deliverTime;

    /**
     * @return the supplementInfo
     */
    public String getSupplementInfo() {
        return supplementInfo;
    }

    /**
     * @param supplementInfo the supplementInfo to set
     */
    public void setSupplementInfo(String supplementInfo) {
        this.supplementInfo = supplementInfo;
    }

    /**
     * @return the userType
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * @return the arrivalTime
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the nonDeliveryReasonCode
     */
    public Integer getNonDeliveryReasonCode() {
        return nonDeliveryReasonCode;
    }

    /**
     * @param nonDeliveryReasonCode the nonDeliveryReasonCode to set
     */
    public void setNonDeliveryReasonCode(Integer nonDeliveryReasonCode) {
        this.nonDeliveryReasonCode = nonDeliveryReasonCode;
    }

    /**
     * @return the nonDeliveryDiagnosticCode
     */
    public Integer getNonDeliveryDiagnosticCode() {
        return nonDeliveryDiagnosticCode;
    }

    /**
     * @param nonDeliveryDiagnosticCode the nonDeliveryDiagnosticCode to set
     */
    public void setNonDeliveryDiagnosticCode(Integer nonDeliveryDiagnosticCode) {
        this.nonDeliveryDiagnosticCode = nonDeliveryDiagnosticCode;
    }

    /**
     * @return the deliverTime
     */
    public String getDeliverTime() {
        return deliverTime;
    }

    /**
     * @param deliverTime the deliverTime to set
     */
    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }
}
