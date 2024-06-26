/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.amhs.mtcu.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "Recipient")
@XmlAccessorType(XmlAccessType.NONE)
public class RecipientConfig {
    
    @XmlElement(name = "Responsibility")
    private Integer responsibility;
    
    @XmlElement(name = "ReportRequest")
    private Integer reportRequest;
    
    @XmlElement(name = "MtaReportRequest")
    private Integer mtaReportRequest;
    
    @XmlElement(name = "NotificationRequest")
    private Integer notificationRequest;
    
    @XmlElement(name = "ReplyRequest")
    private Integer replyRequest;
    
    @XmlElement(name = "Precedence")
    private Integer precedence;
    
    @XmlElement(name = "ConvertInformationType")
    private String convertEit;
    

    /**
     * @return the responsibility
     */
    public Integer getResponsibility() {
        return responsibility;
    }

    /**
     * @param responsibility the responsibility to set
     */
    public void setResponsibility(Integer responsibility) {
        this.responsibility = responsibility;
    }

    /**
     * @return the reportRequest
     */
    public Integer getReportRequest() {
        return reportRequest;
    }

    /**
     * @param reportRequest the reportRequest to set
     */
    public void setReportRequest(Integer reportRequest) {
        this.reportRequest = reportRequest;
    }

    /**
     * @return the mtaReportRequest
     */
    public Integer getMtaReportRequest() {
        return mtaReportRequest;
    }

    /**
     * @param mtaReportRequest the mtaReportRequest to set
     */
    public void setMtaReportRequest(Integer mtaReportRequest) {
        this.mtaReportRequest = mtaReportRequest;
    }

    /**
     * @return the notificationRequest
     */
    public Integer getNotificationRequest() {
        return notificationRequest;
    }

    /**
     * @param notificationRequest the notificationRequest to set
     */
    public void setNotificationRequest(Integer notificationRequest) {
        this.notificationRequest = notificationRequest;
    }

    /**
     * @return the replyRequest
     */
    public Integer getReplyRequest() {
        return replyRequest;
    }

    /**
     * @param replyRequest the replyRequest to set
     */
    public void setReplyRequest(Integer replyRequest) {
        this.replyRequest = replyRequest;
    }

    /**
     * @return the precedence
     */
    public Integer getPrecedence() {
        return precedence;
    }

    /**
     * @param precedence the precedence to set
     */
    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    /**
     * @return the convertEit
     */
    public String getConvertEit() {
        return convertEit;
    }

    /**
     * @param convertEit the convertEit to set
     */
    public void setConvertEit(String convertEit) {
        this.convertEit = convertEit;
    }

    
    
}
