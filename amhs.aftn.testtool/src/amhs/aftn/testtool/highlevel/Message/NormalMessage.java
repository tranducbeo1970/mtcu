/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class NormalMessage {
    
    @XmlElement(name = "Subject")
    private String subject;
    
    @XmlElement(name = "ContentID")
    private String contentId;
    
    @XmlElement(name = "ContentType")
    private Integer contentType;
    
    @XmlElement(name = "MessageID")
    private String messageId;
    
    @XmlElement(name = "IPMID")
    private String ipmId;
    
    @XmlElement(name = "OriginEncodeInformationType")
    private String originEIT;
    
    @XmlElement(name = "GlobalDomain")
    private String globalDomainID;
    
    @XmlElement(name = "Priority")
    private Integer priority;
    
    @XmlElement(name = "Disclosure")
    private Integer disclosure;
    
    @XmlElement(name = "ConversionProhibited")
    private Integer conversionProhibited;
    
    @XmlElement(name = "AlternativeRecipientAllow")
    private Integer alternativeRecipientAllow;
    
    @XmlElement(name = "ContentReturnRequest")
    private Integer contentReturnRequest;
    
    @XmlElement(name = "ReassignmentProhibited")
    private Integer ReassignmentProhibited;
    
    @XmlElement(name = "ConversionWithLossProhibited")
    private Integer conversionWithLossProhibited;
    
    @XmlElement(name = "DLExpasionProhibited")
    private Integer dlExpasionProhibited;
    
    @XmlElement(name = "ATS")
    private ATS ats;
    
    @XmlElement(name = "Recipient")
    private List<Recipient> recipients;
    
    @XmlElement(name = "HeadingRecipient")
    private List<Recipient> headingRecipient;
    
    @XmlElement(name = "CopyRecipient")
    private List<Recipient> copyRecipient;
    
    @XmlElement(name = "BlindCopyRecipient")
    private List<Recipient> blindCopyRecipient;
    
    @XmlElement(name = "Body")
    private List<Body> bodies;
    
    @XmlElement(name = "Originator")
    private String originator;
    
    @XmlElement(name = "Envelope")
    private List<Recipient> envelopeRecipient;
    
    public NormalMessage() {
        this.recipients = new ArrayList<>();
        this.bodies = new ArrayList<>();
        this.headingRecipient = new ArrayList<>();
        this.copyRecipient = new ArrayList<>();
        this.blindCopyRecipient = new ArrayList<>();
    }
    
    public void addRecipient(Recipient recipient){
        this.recipients.add(recipient);
    }
    
    public void addBody(Body body) {
        this.bodies.add(body);
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the originEIT
     */
    public String getOriginEIT() {
        return originEIT;
    }

    /**
     * @param originEIT the originEIT to set
     */
    public void setOriginEIT(String originEIT) {
        this.originEIT = originEIT;
    }

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
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the disclosure
     */
    public Integer getDisclosure() {
        return disclosure;
    }

    /**
     * @param disclosure the disclosure to set
     */
    public void setDisclosure(Integer disclosure) {
        this.disclosure = disclosure;
    }

    /**
     * @return the conversionProhibited
     */
    public Integer getConversionProhibited() {
        return conversionProhibited;
    }

    /**
     * @param conversionProhibited the conversionProhibited to set
     */
    public void setConversionProhibited(Integer conversionProhibited) {
        this.conversionProhibited = conversionProhibited;
    }

    /**
     * @return the alternativeRecipientAllow
     */
    public Integer getAlternativeRecipientAllow() {
        return alternativeRecipientAllow;
    }

    /**
     * @param alternativeRecipientAllow the alternativeRecipientAllow to set
     */
    public void setAlternativeRecipientAllow(Integer alternativeRecipientAllow) {
        this.alternativeRecipientAllow = alternativeRecipientAllow;
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
     * @return the ReassignmentProhibited
     */
    public Integer getReassignmentProhibited() {
        return ReassignmentProhibited;
    }

    /**
     * @param ReassignmentProhibited the ReassignmentProhibited to set
     */
    public void setReassignmentProhibited(Integer ReassignmentProhibited) {
        this.ReassignmentProhibited = ReassignmentProhibited;
    }

    /**
     * @return the conversionWithLossProhibited
     */
    public Integer getConversionWithLossProhibited() {
        return conversionWithLossProhibited;
    }

    /**
     * @param conversionWithLossProhibited the conversionWithLossProhibited to set
     */
    public void setConversionWithLossProhibited(Integer conversionWithLossProhibited) {
        this.conversionWithLossProhibited = conversionWithLossProhibited;
    }

    /**
     * @return the dlExpasionProhibited
     */
    public Integer getDlExpasionProhibited() {
        return dlExpasionProhibited;
    }

    /**
     * @param dlExpasionProhibited the dlExpasionProhibited to set
     */
    public void setDlExpasionProhibited(Integer dlExpasionProhibited) {
        this.dlExpasionProhibited = dlExpasionProhibited;
    }

    /**
     * @return the ipmId
     */
    public String getIpmId() {
        return ipmId;
    }

    /**
     * @param ipmId the ipmId to set
     */
    public void setIpmId(String ipmId) {
        this.ipmId = ipmId;
    }

    /**
     * @return the ats
     */
    public ATS getAts() {
        return ats;
    }

    /**
     * @param ats the ats to set
     */
    public void setAts(ATS ats) {
        this.ats = ats;
    }

    /**
     * @return the recipients
     */
    public List<Recipient> getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    /**
     * @return the bodies
     */
    public List<Body> getBodies() {
        return bodies;
    }

    /**
     * @param bodies the bodies to set
     */
    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
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
     * @return the originator
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * @param originator the originator to set
     */
    public void setOriginator(String originator) {
        this.originator = originator;
    }

    /**
     * @return the headingRecipient
     */
    public List<Recipient> getHeadingRecipient() {
        return headingRecipient;
    }

    /**
     * @param headingRecipient the headingRecipient to set
     */
    public void setHeadingRecipient(List<Recipient> headingRecipient) {
        this.headingRecipient = headingRecipient;
    }

    /**
     * @return the copyRecipient
     */
    public List<Recipient> getCopyRecipient() {
        return copyRecipient;
    }

    /**
     * @param copyRecipient the copyRecipient to set
     */
    public void setCopyRecipient(List<Recipient> copyRecipient) {
        this.copyRecipient = copyRecipient;
    }

    /**
     * @return the blindCopyRecipient
     */
    public List<Recipient> getBlindCopyRecipient() {
        return blindCopyRecipient;
    }

    /**
     * @param blindCopyRecipient the blindCopyRecipient to set
     */
    public void setBlindCopyRecipient(List<Recipient> blindCopyRecipient) {
        this.blindCopyRecipient = blindCopyRecipient;
    }

    /**
     * @return the envelopeRecipient
     */
    public List<Recipient> getEnvelopeRecipient() {
        return envelopeRecipient;
    }

    /**
     * @param envelopeRecipient the envelopeRecipient to set
     */
    public void setEnvelopeRecipient(List<Recipient> envelopeRecipient) {
        this.envelopeRecipient = envelopeRecipient;
    }
}
