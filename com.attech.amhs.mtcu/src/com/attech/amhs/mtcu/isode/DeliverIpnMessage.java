/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.common.MtCommon;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.config.DefaultMessageValue;
import com.attech.amhs.mtcu.config.TraceInfo;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.entity.SysConfig;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.isode.x400api.Traceinfo;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ANDH
 */
public class DeliverIpnMessage extends DeliverMessageBase {
    
    private final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyMMddHHmmss");

    private String origin;
    private Recipient recipient;
    private String subjectIpmId;
    private String conversionEITS;
    private String autoForwardComment;
    private String receiptTime;
    private String supplement;
    private String encodeInfomationType;
    private String correlateString;
    private String arrivalTime;
    private String messageId;

    private int ackMode;
    private int conversionProhibited;
    private Integer nonReceiptReason;
    private Integer discardReason;
    // private MtCommon mtcommon = new MtCommon();

    private int priority;

    public DeliverIpnMessage() {
        super();
        this.ackMode = 0;
        this.conversionProhibited = 1;
        this.conversionEITS = "ia5-text";
    }

    @Override
    public void build(MTMessage mtmessage) {
        this.messageId = MtCommon.generateMessageId();
        this.arrivalTime = datetimeFormat.format(new Date()) + "Z";

        // Build Envelope
        set(mtmessage, X400_att.X400_N_IS_IPN, 1);
        

        /* X.411 Report-identifier 12.2.1.3.1.1 */
        set(mtmessage, X400_att.X400_S_OR_ADDRESS, this.origin);
        set(mtmessage, X400_att.X400_S_MESSAGE_IDENTIFIER, this.messageId);
        set(mtmessage, X400_att.X400_N_PRIORITY, this.priority);
        addRecipient(mtmessage, X400_att.X400_RECIP_STANDARD, 1, recipient);
        
        set(mtmessage, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, this.conversionProhibited);
        set(mtmessage, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, 1);
        set(mtmessage, X400_att.X400_N_NON_RECEIPT_REASON, this.nonReceiptReason);
        set(mtmessage, X400_att.X400_N_DISCARD_REASON, this.discardReason);
        set(mtmessage, X400_att.X400_N_ACK_MODE, this.ackMode);
        set(mtmessage, X400_att.X400_S_CONVERSION_EITS, this.conversionEITS);
        set(mtmessage, X400_att.X400_S_AUTOFORWARD_COMMENT, this.autoForwardComment);
        
        set(mtmessage, X400_att.X400_S_SUBJECT_IPM, this.subjectIpmId);
        set(mtmessage, X400_att.X400_S_RECEIPT_TIME, this.receiptTime);
        set(mtmessage, X400_att.X400_S_SUPP_RECEIPT_INFO, this.supplement);
        buildTraceInfo(mtmessage, X400_att.X400_TRACE_INFO, this.arrivalTime);
    }

    private void buildTraceInfo(MTMessage mtMessage, int type, String arrivalTime) {
        // get config
        final TraceInfo traceInfoConfig = Config.instance.getAftnChannel().getTraceInfo();

        // Instance new trace
        final Traceinfo traceInfo = new Traceinfo();

        final int status = com.isode.x400mtapi.X400mt.x400_mt_traceinfonew(mtMessage, traceInfo, type);
        if (status != X400_att.X400_E_NOERROR) {
            return;
        }

        set(traceInfo, X400_att.X400_S_GLOBAL_DOMAIN_ID, traceInfoConfig.getGlobalDomainID(), -1);
        set(traceInfo, X400_att.X400_S_DSI_ARRIVAL_TIME, arrivalTime, -1);
        set(traceInfo, X400_att.X400_N_DSI_ROUTING_ACTION, traceInfoConfig.getSuppliedDomainRoutingAction());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (isReceipt()) {
            builder.append("Receipt Notification for your message with\n");
            builder.append(String.format("Subject IPM: %s\n", this.subjectIpmId));
            builder.append(String.format("Your message was read at %s", this.receiptTime));
        } else {
            builder.append("Non Receipt Notification for your message with\n");
            builder.append(String.format("Subject IPM: %s\n", this.subjectIpmId));
            builder.append(getDiscardReason(this.discardReason));
            builder.append(getNonReceiptReason(this.nonReceiptReason));
        }

        return builder.toString();
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
        log.setSubjectIpm(this.subjectIpmId);
        log.setSubject("IPN");
        log.setContent(this.toString());
        log.setFilingTime(this.receiptTime);  // Submission time in amhs
        return log;
    }

    private boolean isReceipt() {
        return !(this.receiptTime == null || this.receiptTime.isEmpty());
    }

    private String getDiscardReason(int reason) {
//        if (reason == null) {
//            return "";
//        }

        switch (reason) {
            case 0:
                return "Discard reason: 0 (Message was discarded)\n";
            case 1:
                return "Discard reason: 1 (Message was auto-forwarded)\n";
        }

        return "";
    }

    private String getNonReceiptReason(int reason) {
//        if (reason == null) {
//            return "";
//        }

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
     * @return the ipmId
     */
    public String getSubjectIpmId() {
        return subjectIpmId;
    }

    /**
     * @param ipmId the ipmId to set
     */
    public void setSubjectIpmId(String ipmId) {
        this.subjectIpmId = ipmId;
    }

    /**
     * @return the conversionEITS
     */
    public String getConversionEITS() {
        return conversionEITS;
    }

    /**
     * @param conversionEITS the conversionEITS to set
     */
    public void setConversionEITS(String conversionEITS) {
        this.conversionEITS = conversionEITS;
    }

    /**
     * @return the nonReceiptReason
     */
    public int getNonReceiptReason() {
        return nonReceiptReason;
    }

    /**
     * @param nonReceiptReason the nonReceiptReason to set
     */
    public void setNonReceiptReason(int nonReceiptReason) {
        this.nonReceiptReason = nonReceiptReason;
    }

    /**
     * @return the discardReason
     */
    public int getDiscardReason() {
        return discardReason;
    }

    /**
     * @param discardReason the discardReason to set
     */
    public void setDiscardReason(int discardReason) {
        this.discardReason = discardReason;
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
    public int getAckMode() {
        return ackMode;
    }

    /**
     * @param ackMode the ackMode to set
     */
    public void setAckMode(int ackMode) {
        this.ackMode = ackMode;
    }

    /**
     * @return the supplement
     */
    public String getSupplement() {
        return supplement;
    }

    /**
     * @param supplement the supplement to set
     */
    public void setSupplement(String supplement) {
        this.supplement = supplement;
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
     * @return the encodeInfomationType
     */
    public String getEncodeInfomationType() {
        return encodeInfomationType;
    }

    /**
     * @param encodeInfomationType the encodeInfomationType to set
     */
    public void setEncodeInfomationType(String encodeInfomationType) {
        this.encodeInfomationType = encodeInfomationType;
    }

    /**
     * @return the correlateString
     */
    public String getCorrelateString() {
        return correlateString;
    }

    /**
     * @param correlateString the correlateString to set
     */
    public void setCorrelateString(String correlateString) {
        this.correlateString = correlateString;
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
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the conversionProhibited
     */
    public int getConversionProhibited() {
        return conversionProhibited;
    }

    /**
     * @param conversionProhibited the conversionProhibited to set
     */
    public void setConversionProhibited(int conversionProhibited) {
        this.conversionProhibited = conversionProhibited;
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

    //</editor-fold>
    @Override
    public int hashCode() {
        return this.messageId.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SysConfig)) {
            return false;
        }
        DeliverIpnMessage other = (DeliverIpnMessage) object;
        if ((this.messageId == null && other.getMessageId() != null) || (this.messageId != null && !this.messageId.equals(other.getMessageId()))) {
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

    private void set(Traceinfo traceObject, int attribute, String value, int length) {
        set(traceObject, attribute, value, length, 0);
    }

    private void set(Traceinfo traceObject, int attribute, Integer value) {
        if (value == null) {
            return;
        }
        int status = com.isode.x400api.X400.x400_traceinfoaddintparam(traceObject, attribute, value);
    }

    private void set(Traceinfo traceObject, int attribute, String value, int length, Integer mode) {
        if (value == null) {
            return;
        }
        int status = com.isode.x400api.X400.x400_traceinfoaddstrparam(traceObject, attribute, value, length);
    }
}
