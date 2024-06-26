/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
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
@XmlRootElement(name = "Report")
@XmlAccessorType(XmlAccessType.NONE)
public class Report {
    
    @XmlElement(name = "Originator")
    private String originator;
    
    @XmlElement(name = "SubjectID")
    private String subjectID;
    
    @XmlElement(name = "EncodedInfomationType")
    private String originEIT;
    
    @XmlElement(name = "ContentID")
    private String contentID;
    
    @XmlElement(name = "ContentType")
    private Integer contentType;
    
    @XmlElement(name = "ReportRecipient")
    private List<ReportRecipient> recipients;
    
    @XmlElement(name = "DeliverTime")
    private String deliverTime;
    
    public Report() {
        recipients = new ArrayList<>();
    }
    
    public static void main(String [] args) {
        Report report = new Report();
        report.setOriginator("SDSDSDSD");
        List<ReportRecipient> recipients = new ArrayList<>();
        ReportRecipient recipient = new ReportRecipient();
        recipient.setNonDeliveryDiagnosticCode(0);
        recipient.setNonDeliveryReasonCode(0);
        recipient.setOr("///sdsd/sds/d/sd/s/s/d/sd/sd/");
        recipient.setSupplementInfo("SDSDSĐ");
        recipient.setUserType(0);
        recipients.add(recipient);
        report.setRecipients(recipients);
        
        XmlSerializer.serialize("D:\\Report.xml", report);
        System.out.println("OK");
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
     * @return the subjectID
     */
    public String getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
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
     * @return the recipients
     */
    public List<ReportRecipient> getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<ReportRecipient> recipients) {
        this.recipients = recipients;
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
