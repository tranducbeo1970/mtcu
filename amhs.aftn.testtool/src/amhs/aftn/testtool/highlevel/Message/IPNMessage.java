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
@XmlRootElement(name = "IPN")
@XmlAccessorType(XmlAccessType.NONE)
public class IPNMessage {
    
    @XmlElement(name = "ContentID")
    private String contentID;
    
    @XmlElement(name = "SubjectIPM")
    private String subjectIPM;
    
    @XmlElement(name = "ConversionEIT")
    private String conversionEIT;
    
    @XmlElement(name = "NonReceiptReason")
    private Integer nonReceiptReason;
    
    @XmlElement(name = "DiscardReason")
    private Integer discardReason;
    
    @XmlElement(name = "AutoForwardComment")
    private String autoForwardComment;
    
    @XmlElement(name = "ReceiptTime")
    private String receiptTime;
    
    @XmlElement(name = "AckMode")
    private Integer ackMode;
    
    @XmlElement(name = "SupplementInfo")
    private String supplementInfo;
    
    @XmlElement(name = "Recipients")
    private RecipientList recipients;
    
    public IPNMessage() {
        this.recipients = new RecipientList();
    }

    /**
     * @return the subjectIPM
     */
    public String getSubjectIPM() {
        return subjectIPM;
    }

    /**
     * @param subjectIPM the subjectIPM to set
     */
    public void setSubjectIPM(String subjectIPM) {
        this.subjectIPM = subjectIPM;
    }

    /**
     * @return the conversionEIT
     */
    public String getConversionEIT() {
        return conversionEIT;
    }

    /**
     * @param conversionEIT the conversionEIT to set
     */
    public void setConversionEIT(String conversionEIT) {
        this.conversionEIT = conversionEIT;
    }

    /**
     * @return the nonReceiptReason
     */
    public Integer getNonReceiptReason() {
        return nonReceiptReason;
    }

    /**
     * @param nonReceiptReason the nonReceiptReason to set
     */
    public void setNonReceiptReason(Integer nonReceiptReason) {
        this.nonReceiptReason = nonReceiptReason;
    }

    /**
     * @return the discardReason
     */
    public Integer getDiscardReason() {
        return discardReason;
    }

    /**
     * @param discardReason the discardReason to set
     */
    public void setDiscardReason(Integer discardReason) {
        this.discardReason = discardReason;
    }

    /**
     * @return the receiptTime
     */
    public String getReceiptTime() {
        return receiptTime;
    }

    /**
     * @param receiptTime the receiptTime to set
     */
    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    /**
     * @return the ackMode
     */
    public Integer getAckMode() {
        return ackMode;
    }

    /**
     * @param ackMode the ackMode to set
     */
    public void setAckMode(Integer ackMode) {
        this.ackMode = ackMode;
    }

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
     * @return the recipients
     */
    public RecipientList getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(RecipientList recipients) {
        this.recipients = recipients;
    }

    /**
     * @return the autoForwardComment
     */
    public String getAutoForwardComment() {
        return autoForwardComment;
    }

    /**
     * @param autoForwardComment the autoForwardComment to set
     */
    public void setAutoForwardComment(String autoForwardComment) {
        this.autoForwardComment = autoForwardComment;
    }

    /**
     * @return the contentID
     */
    public String getContentID() {
        return contentID;
    }

    /**
     * @param contentID the contentID to set
     */
    public void setContentID(String contentID) {
        this.contentID = contentID;
    }
}
