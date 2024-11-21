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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class ReceiveProbe extends ReceiveMessageBase {
    
    private final SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");

    private String origin;
    private String messageId;
    private String contenId;
    private List<Recipient> primaryRecipients;
    private List<Recipient> envelopeRecipients;
    private Integer contentType;
    private String encodeInformationType;
    private Integer implicitConversionProhibited;
    private Integer contentLength;
    private String submissionTime;
    private String contentCorrelate;
    private Integer priority;

    public ReceiveProbe(MTMessage message) {
        parse(message);
    }

    @Override
    public MessageConversionLog createMessageConversionLog() {

        final MessageConversionLog log = new MessageConversionLog();
        log.setConvertedDate(new Date());
        log.setCategory(MessageCategory.PROBE);
        log.setType(MessageType.AMHS);
        log.setContent("PROBE");
        log.setMessageId(this.messageId);
        log.setOrigin(this.origin);
        log.setPriority(parseAmhsPriority(this.priority));
        log.setFilingTime(this.submissionTime);  // Submission time in amhs

        for (Recipient address : this.envelopeRecipients) {
            log.addAddressLog(address.getAddressConvertionLog());
        }

        return log;
    }
    
    @Override
    public DeliverReport createNonDeliverReport(int ndrReason, int ndrDianogsticReason, String suplementInfo) throws X400APIException {
        final DeliverReport report = new DeliverReport();
        report.setOriginator(this.origin);
        report.setMessageSubjectId(this.messageId);
        report.setContentId(this.contenId);
        report.setContentType(this.contentType);
        report.setEncodeInfomationType(this.encodeInformationType);
        report.setContentType(22);
        report.setEncodeInfomationType("ia5-text");
        report.setContentCorrelatorIA5String(this.contentCorrelate);
        report.setPriority(this.priority);

        /*
        if (nonDeliverRecipientList == null) {
            nonDeliverRecipientList = this.envelopeRecipients;
        }
        */
        // DUC THEM VAO 20/06/2024
        RptRecipient reportRecipient;
        for (Recipient add : envelopeRecipients) {
            if(!add.isPhankenhduocaftn()) {
                reportRecipient = new RptRecipient(add.getAddress(), ndrReason, ndrDianogsticReason);
                reportRecipient.setMtaReportRequest(add.getMtaReportRequest());
                reportRecipient.setReportRequest(add.getReportRequest());
                if(suplementInfo!=null) {
                    reportRecipient.setSuplementInfo(suplementInfo);
                }
                reportRecipient.setUserType(6);
                report.add(reportRecipient);
            }
        }
        return report;
//        report.deliver(connection);
    }

    /*-----------------------------------------
    
    
    -----------------------------------------*/
    
    @Override
    public DeliverReport createDeliverReport() throws X400APIException {
//        final RecipientConfig config = Config.instance.getReportRecipient();
        final DeliverReport report = new DeliverReport();
        report.setOriginator(this.origin);
        report.setMessageSubjectId(this.messageId);
        report.setContentId(this.contenId);
        report.setContentType(22);
        report.setEncodeInfomationType("ia5-text");
        report.setContentCorrelatorIA5String(this.contentCorrelate);
        report.setPriority(this.priority);
        
        final String deliveriedTime = format.format(new Date()) + "Z";
//        if (deliverRecipientList == null) {
//            deliverRecipientList = this.envelopeRecipients;
//        }
        
        // for (Recipient add : deliverRecipientList) {
	for (Recipient add : envelopeRecipients) {
            if (!add.allowDeliveriedReport()) {
                continue;
            }
            if (add.isPhankenhduocaftn()) {
                final RptRecipient recipient = new RptRecipient();
                recipient.setAddress(add.getAddress());
                // recipient.setMtaReportRequest(config.getMtaReportRequest());
                // recipient.setReportRequest(config.getReportRequest());
                recipient.setMtaReportRequest(add.getMtaReportRequest());
                recipient.setReportRequest(add.getReportRequest());
                // recipient.setReceiptNotification(config.getNotificationRequest());
                recipient.setSuplementInfo("This report only indicates successful (potential) conversion to AFTN, not delivery to a recipient (2)");
                recipient.setDeliveryTime(deliveriedTime);
                recipient.setUserType(6);
                report.add(recipient);
            }
        }
        return report;
    }
    
    // probe message
    @Override
    protected void parse(MTMessage message) {
        this.origin = getStr(message, X400_att.X400_S_OR_ADDRESS);
        this.messageId = getStr(message, X400_att.X400_S_MESSAGE_IDENTIFIER);
        this.contenId = getStr(message, X400_att.X400_S_CONTENT_IDENTIFIER);
        this.contentType = getInt(message, X400_att.X400_N_CONTENT_TYPE);
        this.encodeInformationType = getStr(message, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES);
        this.implicitConversionProhibited = getInt(message, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED);
        this.contentLength = getInt(message, X400_att.X400_N_CONTENT_LENGTH);
        this.submissionTime = getStr(message, X400_att.X400_S_MESSAGE_SUBMISSION_TIME);
        this.contentCorrelate = getStr(message, X400_att.X400_S_CONTENT_CORRELATOR_IA5_STRING);
        this.setPriority(getInt(message, X400_att.X400_N_PRIORITY));

        this.envelopeRecipients = getAddress(message, X400_att.X400_RECIP_ENVELOPE);
  //      
       // X400Msg.DR_Request
      // int ii = getInt(message,X400_att.X400_N_REPORTS);
//        int iii = getInt(message,X400_att.X400_N_REPORT_REQUEST);
        //int iiiii = getInt(message,X400Msg.DR_Request.
        this.primaryRecipients = getAddress(message, X400_att.X400_RECIP_PRIMARY);
        
        
    }

    private List<Recipient> getAddress(MTMessage message, int type) {
        int status;
        int num = 1;

        final List<Recipient> adds = new ArrayList<>();

        final Recip recipObj = new Recip();
        for (num = 1;; num++) {

            status = com.isode.x400mtapi.X400mt.x400_mt_recipget(message, type, num, recipObj);
            if (status == X400_att.X400_E_NO_RECIP || status != X400_att.X400_E_NOERROR) {
                break;
            }

            final String amhsAddress = getStr(recipObj, X400_att.X400_S_OR_ADDRESS);
            final Recipient recipient = new Recipient();
            recipient.setAddress(amhsAddress);

            switch (type) {
                case X400_att.X400_RECIP_ENVELOPE:
                    // ii = 1 iii = 1  NDR
                    // ii = 1 iii = 0  NR
                    // ii =2  iii = 2  DR
                    int ii = getInt(recipObj,X400_att.X400_N_MTA_REPORT_REQUEST);
                    recipient.setMtaReportRequest(getInt(recipObj, X400_att.X400_N_MTA_REPORT_REQUEST));
                    int iii = getInt(recipObj,X400_att.X400_N_REPORT_REQUEST);
                    recipient.setReportRequest(getInt(recipObj, X400_att.X400_N_REPORT_REQUEST));
                    break;

                case X400_att.X400_RECIP_PRIMARY:
                    recipient.setReceiptNotification(getInt(recipObj, X400_att.X400_N_NOTIFICATION_REQUEST));
                    break;

                default:

                    break;
            }

            adds.add(recipient);
        }

        return adds;
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
     * @return the contenId
     */
    public String getContenId() {
        return contenId;
    }

    /**
     * @param contenId the contenId to set
     */
    public void setContenId(String contenId) {
        this.contenId = contenId;
    }

    /**
     * @return the primaryRecipients
     */
    public List<Recipient> getPrimaryRecipients() {
        return primaryRecipients;
    }

    /**
     * @param primaryRecipients the primaryRecipients to set
     */
    public void setPrimaryRecipients(List<Recipient> primaryRecipients) {
        this.primaryRecipients = primaryRecipients;
    }

    /**
     * @return the envelopeRecipients
     */
    public List<Recipient> getEnvelopeRecipients() {
        return envelopeRecipients;
    }

    /**
     * @param envelopeRecipients the envelopeRecipients to set
     */
    public void setEnvelopeRecipients(List<Recipient> envelopeRecipients) {
        this.envelopeRecipients = envelopeRecipients;
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
     * @return the encodeInformationType
     */
    public String getEncodeInformationType() {
        return encodeInformationType;
    }

    /**
     * @param encodeInformationType the encodeInformationType to set
     */
    public void setEncodeInformationType(String encodeInformationType) {
        this.encodeInformationType = encodeInformationType;
    }

    /**
     * @return the implicitConversionProhibited
     */
    public Integer getImplicitConversionProhibited() {
        return implicitConversionProhibited;
    }

    /**
     * @param implicitConversionProhibited the implicitConversionProhibited to set
     */
    public void setImplicitConversionProhibited(Integer implicitConversionProhibited) {
        this.implicitConversionProhibited = implicitConversionProhibited;
    }

    /**
     * @return the contentLength
     */
    public Integer getContentLength() {
        return contentLength;
    }

    /**
     * @param contentLength the contentLength to set
     */
    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
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

    /**
     * @return the contentCorrelate
     */
    public String getContentCorrelate() {
        return contentCorrelate;
    }

    /**
     * @param contentCorrelate the contentCorrelate to set
     */
    public void setContentCorrelate(String contentCorrelate) {
        this.contentCorrelate = contentCorrelate;
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
    //</editor-fold>

 


}
