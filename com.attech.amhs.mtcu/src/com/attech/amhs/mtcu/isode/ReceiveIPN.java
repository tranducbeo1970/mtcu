/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

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
public final class ReceiveIPN extends ReceiveMessageBase{

    private Integer priority;
    private String origin;
    private Recipient recipient;
    private String subjectIPM;
    private String messageId;
    private String contentId;
    private String sumissionTime;
    private String receiptTime;
    private String autoforwardedComment;
    private Integer discardReason;
    private Integer nonReceipReason;
    private Integer ackMode;

    public ReceiveIPN(MTMessage message) {
        parse(message);
    }

   

    public String getContent() {
        final StringBuilder builder = new StringBuilder();
        if (this.isReceipt()) {
            builder.append("Receipt Notification for your message with\n");
            builder.append(String.format("Subject IPM: %s\n", this.subjectIPM));
            builder.append(String.format("Your message was read at %s", this.receiptTime));
        } else {
            builder.append("Non Receipt Notification for your message with\n");
            builder.append(String.format("Subject IPM: %s\n", this.subjectIPM));
            builder.append(getDiscardReason(this.discardReason));
            builder.append(getNonReceiptReason(this.nonReceipReason));
        }

        return builder.toString();
    }



    public boolean isReceipt() {
        return !(this.receiptTime == null || this.receiptTime.isEmpty());
    }

    @Override
    public DeliverReport createNonDeliverReport(int ndrReason, int ndrDianogsticReason, String suplementInfo) throws X400APIException {
        final DeliverReport report = new DeliverReport();
        report.setOriginator(this.origin);
        report.setMessageSubjectId(this.messageId);
        report.setContentId(this.contentId);
        report.setContentType(22);
        report.setEncodeInfomationType("ia5-text");
        
        final RptRecipient reportRecipient = new RptRecipient(recipient.getAddress(), ndrReason, ndrDianogsticReason);
        reportRecipient.setMtaReportRequest(recipient.getMtaReportRequest());
        reportRecipient.setReportRequest(recipient.getReportRequest());
        reportRecipient.setSuplementInfo(suplementInfo);
        reportRecipient.setUserType(6);
        report.add(reportRecipient);
        return report;
    }

    // PRIVATE METHODS
    @Override
    protected void parse(MTMessage message) {

        this.origin = getStr(message, X400_att.X400_S_OR_ADDRESS);
        this.subjectIPM = getStr(message, X400_att.X400_S_SUBJECT_IPM);
        this.priority = getInt(message, X400_att.X400_N_PRIORITY);
        this.messageId = getStr(message, X400_att.X400_S_MESSAGE_IDENTIFIER);
        this.contentId = getStr(message, X400_att.X400_S_CONTENT_IDENTIFIER);
        this.sumissionTime = getStr(message, X400_att.X400_S_MESSAGE_SUBMISSION_TIME);
        this.receiptTime = getStr(message, X400_att.X400_S_RECEIPT_TIME);
        this.discardReason = getInt(message, X400_att.X400_N_DISCARD_REASON);
        this.nonReceipReason = getInt(message, X400_att.X400_N_NON_RECEIPT_REASON);
        this.autoforwardedComment = getStr(message, X400_att.X400_S_AUTOFORWARD_COMMENT);
        this.ackMode = getInt(message, X400_att.X400_N_ACK_MODE);

        final List<Recipient> addresses = this.getAddress(message, X400_att.X400_RECIP_ENVELOPE);
        final List<Recipient> primaryAddresses = this.getAddress(message, X400_att.X400_RECIP_PRIMARY);
        for (Recipient envelope : addresses) {
            for (Recipient primary : primaryAddresses) {
                if (!primary.getAddress().equals(envelope.getAddress())) {
                    continue;
                }
                envelope.setReceiptNotification(primary.getReceiptNotification());
                break;
            }
        }

        if (!addresses.isEmpty()) {
            this.recipient = addresses.get(0);
        }
    }
    
    @Override
    public MessageConversionLog createMessageConversionLog() {
        final MessageConversionLog log = new MessageConversionLog();
        log.setCategory(MessageCategory.IPN);
        log.setType(MessageType.AMHS);
        log.setConvertedDate(new Date());
        log.setMessageId(this.messageId);
        log.setOrigin(this.origin);
        log.addAddressLog(this.recipient.getAddressConvertionLog());
        log.setPriority(parseAmhsPriority(this.priority));
        log.setSubjectIpm(this.subjectIPM);
        log.setSubject("IPN");
        log.setContent(this.getContent());
        log.setFilingTime(this.sumissionTime != null ? this.sumissionTime : this.receiptTime);  // Submission time in amhs
        return log;
    }

    private List<Recipient> getAddress(MTMessage message, int type) {
        int status;
        int num = 1;

        final List<Recipient> adds = new ArrayList<>();

        final Recip recip_obj = new Recip();
        for (num = 1;; num++) {

            status = com.isode.x400mtapi.X400mt.x400_mt_recipget(message, type, num, recip_obj);
            if (status == X400_att.X400_E_NO_RECIP || status != X400_att.X400_E_NOERROR) {
                break;
            }

            final String amhsAddress = getStr(recip_obj, X400_att.X400_S_OR_ADDRESS);
            final Recipient address = new Recipient();
            address.setAddress(amhsAddress);

            switch (type) {
                case X400_att.X400_RECIP_ENVELOPE:
                    address.setMtaReportRequest(getInt(recip_obj, X400_att.X400_N_MTA_REPORT_REQUEST));
                    address.setReportRequest(getInt(recip_obj, X400_att.X400_N_REPORT_REQUEST));
                    break;

                case X400_att.X400_RECIP_PRIMARY:
                    address.setReceiptNotification(getInt(recip_obj, X400_att.X400_N_NOTIFICATION_REQUEST));
                    break;

                default:
                    break;
            }

            adds.add(address);
        }

        return adds;
    }

    private String getDiscardReason(Integer reason) {
        if (reason == null) {
            return "";
        }

        switch (reason) {
            case 0:
                return "Discard reason: 0 (Message was discarded)\n";
            case 1:
                return "Discard reason: 1 (Message was auto-forwarded)\n";
        }

        return "";
    }

    private String getNonReceiptReason(Integer reason) {
        if (reason == null) {
            return "";
        }

        switch (reason) {
            case 0:
                return "Non receipt reason: 0 (ipm expired)\n";
            case 1:
                return "Non receipt reason: 1 (ipm expired)\n";
            case 2:
                return "Non receipt reason: 2 (user subscription terminated)\n";
        }

        return "";
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
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
     * @return the recipient
     */
    public Recipient getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
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
     * @return the sumissionTime
     */
    public String getSumissionTime() {
        return sumissionTime;
    }

    /**
     * @param sumissionTime the sumissionTime to set
     */
    public void setSumissionTime(String sumissionTime) {
        this.sumissionTime = sumissionTime;
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
     * @return the nonReceipReason
     */
    public Integer getNonReceipReason() {
        return nonReceipReason;
    }

    /**
     * @param nonReceipReason the nonReceipReason to set
     */
    public void setNonReceipReason(Integer nonReceipReason) {
        this.nonReceipReason = nonReceipReason;
    }

    /**
     * @return the autoforwardedComment
     */
    public String getAutoforwardedComment() {
        return autoforwardedComment;
    }

    /**
     * @param autoforwardedComment the autoforwardedComment to set
     */
    public void setAutoforwardedComment(String autoforwardedComment) {
        this.autoforwardedComment = autoforwardedComment;
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
    //</editor-fold>
    
     @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Message type: IPN\r\n");
        builder.append(String.format("From: %s\r\n", this.origin));

        builder.append(String.format("To: %s\r\n", this.recipient.getAddress()));
        builder.append("\r\n");

        if (!this.isReceipt()) {
            builder.append("Receipt Notification for your message with\r\n");
            builder.append(String.format("Subject IPM: %s\r\n", this.subjectIPM));
            builder.append(String.format("Your message was read at %s\r\n", this.receiptTime));
            return builder.toString();
        }

        String nrReason = getNonReceiptReason(this.nonReceipReason);
        String dcReason = getDiscardReason(this.discardReason);

        String autoForward = "";
        if (this.autoforwardedComment != null) {
            autoForward = String.format("AutoForward comment: %s\r\n", this.autoforwardedComment);
        }
        builder.append("Non Receipt Notification for your message with\r\n");
        builder.append(String.format("Subject IPM: %s\r\n", this.subjectIPM));
        builder.append(String.format("%s", nrReason));
        builder.append(String.format("%s", dcReason));
        builder.append(autoForward);
        return builder.toString();
    }
    
    @Override
    public int hashCode() {
        return this.messageId.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RptRecipient)) {
            return false;
        }
        ReceiveMessage other = (ReceiveMessage) object;
        if ((this.messageId == null && other.getMessageId()!= null) || (this.messageId != null && !this.messageId.equals(other.getMessageId()))) {
            return false;
        }
        return true;
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(this.getClass() + " successfully garbage collected");
    }

    @Override
    public DeliverReport createDeliverReport() throws X400APIException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

}
