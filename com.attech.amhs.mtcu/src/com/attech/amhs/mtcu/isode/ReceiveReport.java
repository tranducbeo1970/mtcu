/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.MtAttributes;
import com.attech.amhs.mtcu.database.entity.AddressConversionLog;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ANDH
 */
public final class ReceiveReport extends ReceiveMessageBase {

    private int sequenceNumber;
    private String origin;
    private String messageId;
    private String subjectId;
    private String contentId;
    private Integer priority;
    private String submissionTime;
    private List<RptRecipient> recipients;
    private List<String> unrecognizeAddresses;

    public ReceiveReport(MTMessage message) {
        recipients = new ArrayList<>();
        this.unrecognizeAddresses = new ArrayList<>();
        parse(message);
    }

    public String getReportContent() {
        final StringBuilder builder = new StringBuilder();
        builder.append("From: <SYSTEM>\r\n");
        builder.append(String.format("To: %s\r\n\r\n", this.origin));
        builder.append("Report for your message with\r\n");
        builder.append(String.format("Subject Identifier: %s\r\n", this.subjectId));
        builder.append(String.format("Content ID: %s\r\n", this.contentId));
        for (RptRecipient rptRecipient : this.recipients) {
            builder.append("\r\n").append(rptRecipient.toString());
        }
        return builder.toString();
    }

    @Override
    public MessageConversionLog createMessageConversionLog() {
        final MessageConversionLog log = new MessageConversionLog();
        log.setConvertedDate(new Date());
        log.setType(MessageType.AMHS);
        log.setCategory(MessageCategory.REPORT);
        log.setSubjectId(this.subjectId);
        log.setContent(this.getReportContent());
        log.setFilingTime(this.submissionTime);  // Submission time in amhs
        log.setPriority(parseAmhsPriority(this.priority));
        log.addAddressLog(new AddressConversionLog(this.origin));
        log.setOrigin("<SYSTEM>");
        log.setSubject("<REPORT>");

        /*
	List<String> reportRecipients = mtMessage.getUnrecognizeAddresses();
	for (String reportRecipient : reportRecipients) {
	    log.addAddressLog(new AddressConversionLog(reportRecipient));
	}
         */
        return log;
    }

    @Override
    protected void parse(MTMessage message) {
        this.subjectId = getStr(message, X400_att.X400_S_SUBJECT_IDENTIFIER);
        this.origin = getStr(message, X400_att.X400_S_OR_ADDRESS);
        this.messageId = getStr(message, X400_att.X400_S_MESSAGE_IDENTIFIER);
        this.contentId = getStr(message, X400_att.X400_S_CONTENT_IDENTIFIER);
        this.priority = getInt(message, X400_att.X400_N_PRIORITY);
        this.submissionTime = getStr(message, X400_att.X400_S_MESSAGE_SUBMISSION_TIME);
        this.parseAddresses(message);
        // this.unrecognizeAddresses = getReportAddressFilterByDianogsticCode(message, sequenceNumber)
    }

    private void parseAddresses(MTMessage message) {
        int status;
        int num = 1;
        final List<RptRecipient> adds = new ArrayList<>();
        final Recip recipObj = new Recip();
        for (num = 1;; num++) {
            status = com.isode.x400mtapi.X400mt.x400_mt_recipget(message, X400_att.X400_RECIP_REPORT, num, recipObj);
            if (status == X400_att.X400_E_NO_RECIP || status != X400_att.X400_E_NOERROR) {
                break;
            }
            RptRecipient address = new RptRecipient();
            address.setAddress(getStr(recipObj, X400_att.X400_S_OR_ADDRESS));
            address.setNonDeliveryDiagnosticCode(getInt(recipObj, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC));
            address.setNonDeliveryReason(getInt(recipObj, X400_att.X400_N_NON_DELIVERY_REASON));
            address.setSuplementInfo(getStr(recipObj, X400_att.X400_S_SUPPLEMENTARY_INFO));
            address.setDeliveryTime(getStr(recipObj, X400_att.X400_S_MESSAGE_DELIVERY_TIME));
            this.recipients.add(address);

            if (address.getNonDeliveryDiagnosticCode() != MtAttributes.D_UNRECOGNISED_OR_NAME) {
                continue;
            }
            this.unrecognizeAddresses.add(address.getAddress());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the sequenceNumber
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @param sequenceNumber the sequenceNumber to set
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
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
     * @return the subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the recipients
     */
    public List<RptRecipient> getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<RptRecipient> recipients) {
        this.recipients = recipients;
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
     * @return the unrecognizeAddresses
     */
    public List<String> getUnrecognizeAddresses() {
        return unrecognizeAddresses;
    }

    /**
     * @param unrecognizeAddresses the unrecognizeAddresses to set
     */
    public void setUnrecognizeAddresses(List<String> unrecognizeAddresses) {
        this.unrecognizeAddresses = unrecognizeAddresses;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param piority the priority to set
     */
    public void setPriority(Integer piority) {
        this.priority = piority;
    }

    /**
     * @return the submissionTime
     */
    public String getSubmissionTime() {
        return submissionTime;
    }

    /**
     * @param submissionTime the submissionTime to set
     */
    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        return this.sequenceNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RptRecipient)) {
            return false;
        }
        ReceiveReport other = (ReceiveReport) object;
        return this.sequenceNumber == other.getSequenceNumber();
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
//        System.out.println(this.getClass() + " successfully garbage collected");
    }

    @Override
    public DeliverReport createNonDeliverReport(int ndrReason, int ndrDianogsticReason, String suplementInfo) throws X400APIException {
        return null;
    }

    @Override
    public DeliverReport createDeliverReport() throws X400APIException {
        return null;
    }
}
