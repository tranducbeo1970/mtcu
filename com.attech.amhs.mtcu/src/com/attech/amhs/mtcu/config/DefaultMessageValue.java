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
 * @author andh
 */
@XmlRootElement(name = "MtMessageConfig")
@XmlAccessorType(XmlAccessType.NONE)
public class DefaultMessageValue implements Serializable {

    // Default value
    // @XmlElement(name = "MtaReportRequest")
    // private Integer mtaReportRequest = 3;
    // @XmlElement(name = "ReportRequest")
    // private Integer reportRequest = 2;
    // @XmlElement(name = "NotificationRequest")
    // private Integer notificationRequest = 7;
    /**
     *
     */
    private static final long serialVersionUID = 1190523821855798281L;

    @XmlElement(name = "AltReciptAllow")
    private Integer alterRecipAllow = 0;

    @XmlElement(name = "ReplyRequest")
    private Integer replyRequet = 0; // Boolean 0: false 1: true

    @XmlElement(name = "ContentReturnRequest")
    private Integer contentReturnRequest = 0;

    @XmlElement(name = "ConvertionWithLossProhibited")
    private Integer conversionLossProhibited = 0;

    @XmlElement(name = "DisclosureOfRecipts")
    private Integer disclosureRecip = 1;

    @XmlElement(name = "DlExpansionProhibited")
    private Integer dlExpProhibited = null;

    @XmlElement(name = "ImplicitConversionProhibited")
    private Integer conversionProhibited = null;

    @XmlElement(name = "OriginReturnAddress")
    private String originReturnAddress = null;

    @XmlElement(name = "ReciptReassignmentProhibited")
    private Integer recipReassignmentProhibited = null;

    @XmlElement(name = "Sensitive")
    private Integer sensitive = null;

    @XmlElement(name = "UnknowRecipt")
    private String unknowRecip = "/C=XX/ADMD=ICAO/PRMD=VV/O=AFTN/OU=REJX";

    @XmlElement(name = "ArrivalTimeFormat")
    private String arrivalTimeFormat = "yyMMddHHmmssz";

    @XmlElement(name = "Importance")
    private Integer importance = null;

    @XmlElement(name = "Responsibility")
    private Integer responsibility = 1; // 0: none 1: responsibility

    @XmlElement(name = "ConvertedInformationType")
    private String convertedEIT;

    @XmlElement(name = "OriginEncodedInformationType")
    private String originEIT;

    @XmlElement(name = "ContentType")
    private Integer contentType;

    @XmlElement(name = "GenerateContentIdenfier")
    private Boolean generateContentIdentifer;

    @XmlElement(name = "AtsExtendSupported")
    private Integer atsExtendSupported;

    @XmlElement(name = "AtsExtend")
    private Integer atsExtended;

    /**
     * @return the mtaReportRequest
     */
//    public Integer getMtaReportRequest() {
//        return mtaReportRequest;
//    }
    /**
     * @param mtaReportRequest the mtaReportRequest to set
     */
//    public void setMtaReportRequest(Integer mtaReportRequest) {
//        this.mtaReportRequest = mtaReportRequest;
//    }
    /**
     * @return the reportRequest
     */
//    public Integer getReportRequest() {
//        return reportRequest;
//    }
    /**
     * @param reportRequest the reportRequest to set
     */
//    public void setReportRequest(Integer reportRequest) {
//        this.reportRequest = reportRequest;
//    }
    /**
     * @return the notificationRequest
     */
//    public Integer getNotificationRequest() {
//        return notificationRequest;
//    }
    /**
     * @param notificationRequest the notificationRequest to set
     */
//    public void setNotificationRequest(Integer notificationRequest) {
//        this.notificationRequest = notificationRequest;
//    }
    /**
     * @return the alternativeRecieptAllow
     */
    public Integer getAlterRecipAllow() {
        return alterRecipAllow;
    }

    /**
     * @param alternativeRecieptAllow the alternativeRecieptAllow to set
     */
    public void setAlterRecipAllow(Integer alternativeRecieptAllow) {
        this.alterRecipAllow = alternativeRecieptAllow;
    }

    /**
     * @return the replyRequet
     */
    public Integer getReplyRequet() {
        return replyRequet;
    }

    /**
     * @param replyRequet the replyRequet to set
     */
    public void setReplyRequet(Integer replyRequet) {
        this.replyRequet = replyRequet;
    }

    /**
     * @return the contentReturnRequest
     */
    public Integer getContentReturnRequest() {
        return contentReturnRequest;
    }

    /**
     * @param contentReturnRequest the contentReturnRequest to set
     */
    public void setContentReturnRequest(Integer contentReturnRequest) {
        this.contentReturnRequest = contentReturnRequest;
    }

    /**
     * @return the conversionWithLossProhibited
     */
    public Integer getConversionLossProhibited() {
        return conversionLossProhibited;
    }

    /**
     * @param conversionWithLossProhibited the conversionWithLossProhibited to set
     */
    public void setConversionLossProhibited(Integer conversionWithLossProhibited) {
        this.conversionLossProhibited = conversionWithLossProhibited;
    }

    /**
     * @return the disclosureOfRecipients
     */
    public Integer getDisclosureRecip() {
        return disclosureRecip;
    }

    /**
     * @param disclosureOfRecipients the disclosureOfRecipients to set
     */
    public void setDisclosureRecip(Integer disclosureOfRecipients) {
        this.disclosureRecip = disclosureOfRecipients;
    }

    /**
     * @return the dlExpansionProhibited
     */
    public Integer getDlExpProhibited() {
        return dlExpProhibited;
    }

    /**
     * @param dlExpansionProhibited the dlExpansionProhibited to set
     */
    public void setDlExpProhibited(Integer dlExpansionProhibited) {
        this.dlExpProhibited = dlExpansionProhibited;
    }

    /**
     * @return the implicitConversionProhibited
     */
    public Integer getConversionProhibited() {
        return conversionProhibited;
    }

    /**
     * @param implicitConversionProhibited the implicitConversionProhibited to set
     */
    public void setConversionProhibited(Integer implicitConversionProhibited) {
        this.conversionProhibited = implicitConversionProhibited;
    }

    /**
     * @return the originReturnAddress
     */
    public String getOriginReturnAddress() {
        return originReturnAddress;
    }

    /**
     * @param originReturnAddress the originReturnAddress to set
     */
    public void setOriginReturnAddress(String originReturnAddress) {
        this.originReturnAddress = originReturnAddress;
    }

    /**
     * @return the recipientReassignmentProhibited
     */
    public Integer getRecipReassignmentProhibited() {
        return recipReassignmentProhibited;
    }

    /**
     * @param recipientReassignmentProhibited the recipientReassignmentProhibited to set
     */
    public void setRecipReassignmentProhibited(Integer recipientReassignmentProhibited) {
        this.recipReassignmentProhibited = recipientReassignmentProhibited;
    }

    /**
     * @return the sensitive
     */
    public Integer getSensitive() {
        return sensitive;
    }

    /**
     * @param sensitive the sensitive to set
     */
    public void setSensitive(Integer sensitive) {
        this.sensitive = sensitive;
    }

    /**
     * @return the unknowRecip
     */
    public String getUnknowRecip() {
        return unknowRecip;
    }

    /**
     * @param unknowRecip the unknowRecip to set
     */
    public void setUnknowRecip(String unknowRecip) {
        this.unknowRecip = unknowRecip;
    }

    /**
     * @return the arrivalTimeFormat
     */
    public String getArrivalTimeFormat() {
        return arrivalTimeFormat;
    }

    /**
     * @param arrivalTimeFormat the arrivalTimeFormat to set
     */
    public void setArrivalTimeFormat(String arrivalTimeFormat) {
        this.arrivalTimeFormat = arrivalTimeFormat;
    }

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
     * @return the importance
     */
    public Integer getImportance() {
        return importance;
    }

    /**
     * @param importance the importance to set
     */
    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    /**
     * @return the convertedInformationType
     */
    public String getConvertedEIT() {
        return convertedEIT;
    }

    /**
     * @param convertedInformationType the convertedInformationType to set
     */
    public void setConvertedEIT(String convertedInformationType) {
        this.convertedEIT = convertedInformationType;
    }

    /**
     * @return the originEncodedInfomationType
     */
    public String getOriginEIT() {
        return originEIT;
    }

    /**
     * @param originEncodedInfomationType the originEncodedInfomationType to set
     */
    public void setOriginEIT(String originEncodedInfomationType) {
        this.originEIT = originEncodedInfomationType;
    }

    /**
     * @return the contentType
     */
    public Integer getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the generateContentIdentifer
     */
    public Boolean getGenerateContentIdentifer() {
        return generateContentIdentifer;
    }

    /**
     * @param generateContentIdentifer the generateContentIdentifer to set
     */
    public void setGenerateContentIdentifer(Boolean generateContentIdentifer) {
        this.generateContentIdentifer = generateContentIdentifer;
    }

    /**
     * @return the atsExtendSupported
     */
    public Integer getAtsExtendSupported() {
        return atsExtendSupported;
    }

    /**
     * @param atsExtendSupported the atsExtendSupported to set
     */
    public void setAtsExtendSupported(Integer atsExtendSupported) {
        this.atsExtendSupported = atsExtendSupported;
    }

    /**
     * @return the atsExtended
     */
    public Integer getAtsExtended() {
        return atsExtended;
    }

    /**
     * @param atsExtended the atsExtended to set
     */
    public void setAtsExtended(Integer atsExtended) {
        this.atsExtended = atsExtended;
    }
}
