/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.config.RecipientConfig;
import com.attech.amhs.mtcu.database.entity.AddressConversionLog;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.BodyPart;
import com.isode.x400api.DLExpHist;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.isode.x400mtapi.X400mt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ANDH
 */
public final class ReceiveMessage extends ReceiveMessageBase {

    private final SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");

    private String messageId;
    private String ipmId;

    private String contentId;
    private Integer contentType;

    private Integer priority;
    private Boolean implicitConversionProhibited;
    private Boolean alternativeRecipienAllowed;
    private Boolean dlExpansionProhibited;
    private Boolean conversionWithLossProhibited;
    private String originEncodeInformationType;
    private String sumissionTime;

    private String subject;
    private String content;
    private Integer bodyPartType;
    private String bodyPartCharacterSet;
    private Integer numberOfAttachment;
    private Integer numberOfDLAddress;
    private String contentCorrelatorString;

    private List<Recipient> primaryRecipients;
    private List<Recipient> envelopeRecipients;

    private String atsFilingTime;
    private String atsPriority;
    private String atsOhi;
    private Boolean atsExtention;
    private String orAddress;

    public ReceiveMessage(MTMessage mtmessage) {
        parse(mtmessage);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        // builder.append("MESSAGE PROPERTIES ----------------------------------------------------- \n");
        builder.append(String.format("ID: %20s\n", this.messageId));
        builder.append(String.format("IPM ID: %20s\n",this.ipmId));
        builder.append(String.format("CONTENT ID: %20s\n", this.contentId));
        builder.append(String.format("CONTENT TYPE: %20s\n", this.contentType ));
        builder.append(String.format("PRIORITY: %20s\n", this.priority ));
        builder.append(String.format("CON_PROHI: %20s\n",this.implicitConversionProhibited));
        // builder.append("RECIPIENT ALLOW: " + this.alternativeRecipienAllowed + "\n");
        builder.append(String.format("DL_EX_PROHI: %20s\n", this.dlExpansionProhibited));
        builder.append(String.format("CON_LOSS_PROHI: %20s\n", this.conversionWithLossProhibited));
        builder.append(String.format("ORIGIN EIT: %20s\n", this.originEncodeInformationType));
        builder.append(String.format("BODYPART TYPE: %20s\n", this.bodyPartType));
        builder.append(String.format("ATTACHMENT: %20s\n", this.numberOfAttachment));
        builder.append(String.format("SUBMISSION TIME: %20s\n",this.getSumissionTime()));
        // builder.append("DL ADDRESS: " + this.numberOfDLAddress + "\n");
        builder.append("ATN PROPERTIES **** \n");
        builder.append(String.format("FILING TIME: %20s\n", this.atsFilingTime));
        builder.append(String.format("ATS PRIORITY: %20s\n", this.atsPriority));
        builder.append(String.format("OHI: %20s\n", this.atsOhi));
        builder.append("ADDRESS **** \n");
        builder.append(String.format("FROM: %20s\n", this.orAddress));
        builder.append("PRIMARY ****\n");
        for (Recipient address : this.primaryRecipients) {
            builder.append(String.format("> TO: %20s\n", address.toString()));
        }
        builder.append("\n");
        builder.append("ENVELOPE ****\n");
        for (Recipient address : this.envelopeRecipients) {
            builder.append(String.format("> TO: %20s\n", address.toString()));
        }
        builder.append("SUBJECT ****\n");
        builder.append(this.subject + "\n");
        builder.append("CONTENT ****\n");
        builder.append(this.content + "\n");
        return builder.toString();
    }
    
    @Override
    public DeliverReport createNonDeliverReport(int ndrReason, int ndrDianogsticReason, String suplementInfo) throws X400APIException {
        // RecipientConfig config = Config.instance.getReportRecipient();
        DeliverReport report = new DeliverReport();
        report.setOriginator(this.orAddress);
        report.setMessageSubjectId(this.messageId);
        report.setContentId(this.contentId);
        // report.setContentType(this.contentType);
        // report.setEncodeInfomationType(this.originEncodeInformationType);
        report.setContentType(22);
        report.setEncodeInfomationType("ia5-text");
        // report.setContentCorrelatorIA5String(this.contentCorrelatorString);
        //report.setPriority(this.priority);

        for (Recipient add : this.envelopeRecipients) {
            RptRecipient reportRecipient = new RptRecipient(add.getAddress(), ndrReason, ndrDianogsticReason);
            reportRecipient.setMtaReportRequest(add.getMtaReportRequest());
            reportRecipient.setReportRequest(add.getReportRequest());
            //reportRecipient.setReceiptNotification(add.getNotificationRequest());
            reportRecipient.setSuplementInfo(suplementInfo);
            reportRecipient.setUserType(6);
            report.add(reportRecipient);
        }
        return report;
    }
    
    @Override
    public DeliverReport createDeliverReport() throws X400APIException {
        
        RecipientConfig config = Config.instance.getReportRecipient();
        DeliverReport report = new DeliverReport();
        report.setOriginator(this.orAddress);
        report.setMessageSubjectId(this.messageId);
        report.setContentId(this.contentId);
        report.setContentType(22);
        report.setEncodeInfomationType("ia5-text");
        // report.setContentCorrelatorIA5String(this.contentCorrelatorString);
        report.setPriority(this.priority);

        String deliveriedTime = format.format(new Date()) + "Z";

        for (Recipient add : this.envelopeRecipients) {

            if (!add.allowDeliveriedReport()) {
                continue;
            }

            RptRecipient recipient = new RptRecipient();
            recipient.setAddress(add.getAddress());
            recipient.setMtaReportRequest(add.getMtaReportRequest());
            recipient.setReportRequest(add.getReportRequest());
            // recipient.setReceiptNotification(config.getNotificationRequest());
            recipient.setSuplementInfo("This report only indicates successful (potential) conversion to AFTN, not delivery to a recipient");
            recipient.setDeliveryTime(deliveriedTime);
            // recipient.setUserType(6);
            report.add(recipient);
        }
        return report;
//        report.deliver(connection);
    }

//    public void returnDeliverReport(MTConnection connection) throws X400APIException {
//        
//        RecipientConfig config = Config.instance.getReportRecipient();
//        DeliverReport report = new DeliverReport();
//        report.setOriginator(this.orAddress);
//        report.setMessageSubjectId(this.messageId);
//        report.setContentId(this.contentId);
//        report.setContentType(22);
//        report.setEncodeInfomationType("ia5-text");
//        // report.setContentCorrelatorIA5String(this.contentCorrelatorString);
//        report.setPriority(this.priority);
//
//        String deliveriedTime = format.format(new Date()) + "Z";
//
//        for (Recipient add : this.envelopeRecipients) {
//
//            if (!add.allowDeliveriedReport()) {
//                continue;
//            }
//
//            RptRecipient recipient = new RptRecipient();
//            recipient.setAddress(add.getAddress());
//            recipient.setMtaReportRequest(add.getMtaReportRequest());
//            recipient.setReportRequest(add.getReportRequest());
//            // recipient.setReceiptNotification(config.getNotificationRequest());
//            recipient.setSuplementInfo("This report only indicates successful (potential) conversion to AFTN, not delivery to a recipient");
//            recipient.setDeliveryTime(deliveriedTime);
//            // recipient.setUserType(6);
//            report.add(recipient);
//        }
//        report.deliver(connection);
//    }

    @Override
    public MessageConversionLog createMessageConversionLog() {
        final Date date = new Date();
        final MessageConversionLog log = new MessageConversionLog();
        log.setConvertedDate(date);
        log.setType(MessageType.AMHS);
        log.setCategory(MessageCategory.IPM);
        log.setContent(this.content);
        log.setContentId(this.contentId);
        log.setFilingTime(this.atsFilingTime);
        log.setIpmId(this.ipmId);
        log.setMessageId(this.messageId);
        log.setOhi(this.atsOhi);
        log.setOrigin(this.orAddress);
        log.setPriority(parseAmhsPriority(this.priority));
        log.setSubject(this.subject);
        
        AddressConversionLog addConversionLog;
        for (Recipient address : this.envelopeRecipients) {
            addConversionLog = new AddressConversionLog();
            addConversionLog.setAddress(address.getAddress());
            addConversionLog.setMtaReportRequest(address.getMtaReportRequest());
            addConversionLog.setReportRequest(address.getReportRequest());
            addConversionLog.setNotifyRequest(address.getReceiptNotification());
            log.addAddressLog(addConversionLog);
        }
        return log;
    }

    // PRIVATE METHODS
    @Override
    protected void parse(MTMessage mtMessage) {
        this.messageId = getStr(mtMessage, X400_att.X400_S_MESSAGE_IDENTIFIER);
        this.ipmId = getStr(mtMessage, X400_att.X400_S_IPM_IDENTIFIER);
        this.contentId = getStr(mtMessage, X400_att.X400_S_CONTENT_IDENTIFIER);
        this.contentType = getInt(mtMessage, X400_att.X400_N_CONTENT_TYPE);
        this.priority = getInt(mtMessage, X400_att.X400_N_PRIORITY);
        this.implicitConversionProhibited = convertInt2Bool(getInt(mtMessage, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED));
        this.contentCorrelatorString = getStr(mtMessage, X400_att.X400_S_CONTENT_CORRELATOR_IA5_STRING);
        this.dlExpansionProhibited = convertInt2Bool(getInt(mtMessage, X400_att.X400_N_DL_EXPANSION_PROHIBITED));
        this.conversionWithLossProhibited = convertInt2Bool(getInt(mtMessage, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED));
        this.originEncodeInformationType = getStr(mtMessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES);
        this.sumissionTime = getStr(mtMessage, X400_att.X400_S_MESSAGE_SUBMISSION_TIME);
        this.numberOfAttachment = getInt(mtMessage, X400_att.X400_N_NUM_ATTACHMENTS);
        this.subject = getStr(mtMessage, X400_att.X400_S_SUBJECT);

        MessageContent mscontent = this.getBodyPart(mtMessage, X400_att.X400_T_IA5TEXT);
        if (mscontent == null) {
            mscontent = this.getBodyPart(mtMessage, X400_att.X400_T_GENERAL_TEXT);
        }
        if (mscontent != null) {
            // this.content = mscontent.getContent();
            this.bodyPartType = mscontent.getType();
            this.bodyPartCharacterSet = mscontent.getCharacterSet();
            Ats ats = extractATS(mtMessage, mscontent);
            this.atsFilingTime = ats.getFilingTime();
            this.atsPriority = ats.getPriotity();
            this.atsOhi = ats.getOptionalHeading();
            // this.atsExtention = ats.get;
        }
        this.envelopeRecipients = getAddress(mtMessage, X400_att.X400_RECIP_ENVELOPE);
        this.primaryRecipients = getAddress(mtMessage, X400_att.X400_RECIP_PRIMARY);
        this.numberOfDLAddress = dlAddressCount(mtMessage);
        this.orAddress = getStr(mtMessage, X400_att.X400_S_OR_ADDRESS);

        for (Recipient envelope : envelopeRecipients) {
            for (Recipient primary : primaryRecipients) {
                if (!primary.getAddress().equals(envelope.getAddress())) {
                    continue;
                }
                envelope.setReceiptNotification(primary.getReceiptNotification());
                break;
            }
        }

        if (mscontent != null) {
            this.content = processMessageContent(mscontent.getContent());
        }
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
            final Recipient recipient = new Recipient();
            recipient.setAddress(amhsAddress);

            switch (type) {
                case X400_att.X400_RECIP_ENVELOPE:
                    recipient.setMtaReportRequest(getInt(recip_obj, X400_att.X400_N_MTA_REPORT_REQUEST));
                    recipient.setReportRequest(getInt(recip_obj, X400_att.X400_N_REPORT_REQUEST));
                    
//                    System.out.println(String.format("%s > report request: %s > mta request: %s", recipient.address, recipient.reportRequest, recipient.mtaReportRequest));
                    break;

                case X400_att.X400_RECIP_PRIMARY:
                    recipient.setReceiptNotification(getInt(recip_obj, X400_att.X400_N_NOTIFICATION_REQUEST));
//                    address.setNonDeliverDianogtiscCode(getInt(recip_obj, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC));
//                    address.setNonDeliverReasonCode(getInt(recip_obj, X400_att.X400_N_NON_DELIVERY_REASON));
//                    address.setSupplementInfo(getStr(recip_obj, X400_att.X400_S_SUPPLEMENTARY_INFO));
//                    address.setDeliveryTime(getStr(recip_obj, X400_att.X400_S_MESSAGE_DELIVERY_TIME));
                    break;

                default:

                    break;
            }

            adds.add(recipient);
        }

        return adds;
    }

    private Boolean convertInt2Bool(Integer intVal) {
        return (intVal != null && intVal > 0);
    }

    private Ats extractATS(MTMessage message, MessageContent mscontent) {
        Ats ats = new Ats();
        // get(message, AMHS_att.ATS_S_TEXT)
        // Integer exted  = MtCommon.getInt(message, AMHS_att.ATS_N_EXTENDED, 1);
        // exted  = MtCommon.getInt(message, AMHS_att.ATS_EOH_MODE, 1);
        // System.out.print("EXTEDND: " + exted);

        switch (mscontent.getType()) {
            case X400_att.X400_T_GENERAL_TEXT:

                String heading = mscontent.getContent();
                String[] lines = heading.split("\u0002");

                if (lines.length > 1) {
                    ats.setText(lines[1]);
                }

                String header = lines[0].replace("\u0001", "");

                String[] headers = header.split("\r\n");
                for (String h : headers) {
                    if (h.startsWith("PRI:")) {
                        ats.setPriotity(h.split(":")[1].trim());
                    }
                    if (h.startsWith("OHI:")) {
                        ats.setOptionalHeading(h.split(":")[1].trim());
                    }
                    if (h.startsWith("FT:")) {
                        ats.setFilingTime(h.split(":")[1].trim());
                    }
                }
                break;

            default:
                ats.setText(getStr(message, AMHS_att.ATS_S_TEXT));
                ats.setPriotity(getStr(message, AMHS_att.ATS_S_PRIORITY_INDICATOR));
                ats.setOptionalHeading(getStr(message, AMHS_att.ATS_S_OPTIONAL_HEADING_INFO));
                ats.setFilingTime(getStr(message, AMHS_att.ATS_S_FILING_TIME));
                break;
        }
        return ats;
    }

    private String processMessageContent(String content) {
        int index1 = content.indexOf("\u0002");
        if (index1 >= 0) {
            content = content.substring(index1 + 1);
        }
//         String startOfHeader = "\u0001";
//        String startOfText = "\u0002";
//        String pageFeedSequence = "\u000b";
//        String endOfText = "\u0003";
        // logger.info("SOH: " + content.indexOf("\u0001")+ " SOT: " + content.indexOf("\u0002") + " VT: " + content.indexOf("\u000b") + " EOT: " + content.indexOf("\u0003"));
        content = content.replace("\u0001", "").replace("\u0002", "").replace("\u000b", "").replace("\u0003", "");

//        if (index1 < 0) return content;
//        // int index2 = content.indexOf("\u0001");
//        // int index3 = content.indexOf("\u0003");
//        System.out.println("INDEX 1: " + index1);
//        // System.out.println("INDEX 2: " + index2);
//        // System.out.println("INDEX 3: " + index3);
//        return content.substring(index1 + 1);
        return content;
    }

    private MessageContent getBodyPart(MTMessage mtMessage, int type) {

        int num = 0;
        int status = 0;

        for (num = 0;; num++) {
            final BodyPart bodyPart = new BodyPart();
            status = X400mt.x400_mt_msggetbodypart(mtMessage, num, bodyPart);
            if (status == X400_att.X400_E_MISSING_ATTR || status == X400_att.X400_E_NO_VALUE || status != X400_att.X400_E_NOERROR) {
                break;
            }
            Integer bodytype = getInt(bodyPart, X400_att.X400_N_BODY_TYPE);
            if (bodytype == null || bodytype == 0 || bodytype != type) {
                continue;
            }
            String bodyContent = getStr(bodyPart, X400_att.X400_S_BODY_DATA);
            String charset = null;
            if (type == X400_att.X400_T_GENERAL_TEXT) {
                charset = getStr(bodyPart, X400_att.X400_S_GENERAL_TEXT_CHARSETS);
            }
            return new MessageContent(bodytype, bodyContent, charset);
        }
        return null;
    }

    private int dlAddressCount(MTMessage message) {

        List<String> distAddresses = new ArrayList<>();

        int entry = 0;
        int status = X400_att.X400_E_NOERROR;
        while (status == X400_att.X400_E_NOERROR) {
            DLExpHist dleh_obj = new DLExpHist();
            status = com.isode.x400mtapi.X400mt.x400_mt_DLexphistget(message, entry, dleh_obj);
            // logger.info(String.format("GET DLHIST = %s", status));
            if (status != X400_att.X400_E_NOERROR) {
                break;
            }

            String dlAddress = getStr(dleh_obj, X400_att.X400_S_OR_ADDRESS);
            if (dlAddress == null || dlAddress.isEmpty()) {
                continue;
            }
            distAddresses.add(dlAddress);
            entry++;
        }

        return entry;
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
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
     * @return the implicitConversionProhibited
     */
    public Boolean getImplicitConversionProhibited() {
        return implicitConversionProhibited;
    }

    /**
     * @param implicitConversionProhibited the implicitConversionProhibited to set
     */
    public void setImplicitConversionProhibited(Boolean implicitConversionProhibited) {
        this.implicitConversionProhibited = implicitConversionProhibited;
    }

    /**
     * @return the alternativeRecipienAllowed
     */
    public Boolean getAlternativeRecipienAllowed() {
        return alternativeRecipienAllowed;
    }

    /**
     * @param alternativeRecipienAllowed the alternativeRecipienAllowed to set
     */
    public void setAlternativeRecipienAllowed(Boolean alternativeRecipienAllowed) {
        this.alternativeRecipienAllowed = alternativeRecipienAllowed;
    }

    /**
     * @return the dlExpansionProhibited
     */
    public Boolean getDlExpansionProhibited() {
        return dlExpansionProhibited;
    }

    /**
     * @param dlExpansionProhibited the dlExpansionProhibited to set
     */
    public void setDlExpansionProhibited(Boolean dlExpansionProhibited) {
        this.dlExpansionProhibited = dlExpansionProhibited;
    }

    /**
     * @return the conversionWithLossProhibited
     */
    public Boolean getConversionWithLossProhibited() {
        return conversionWithLossProhibited;
    }

    /**
     * @param conversionWithLossProhibited the conversionWithLossProhibited to set
     */
    public void setConversionWithLossProhibited(Boolean conversionWithLossProhibited) {
        this.conversionWithLossProhibited = conversionWithLossProhibited;
    }

    /**
     * @return the originEncodeInformationType
     */
    public String getOriginEncodeInformationType() {
        return originEncodeInformationType;
    }

    /**
     * @param originEncodeInformationType the originEncodeInformationType to set
     */
    public void setOriginEncodeInformationType(String originEncodeInformationType) {
        this.originEncodeInformationType = originEncodeInformationType;
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the bodyPartType
     */
    public Integer getBodyPartType() {
        return bodyPartType;
    }

    /**
     * @param bodyPartType the bodyPartType to set
     */
    public void setBodyPartType(Integer bodyPartType) {
        this.bodyPartType = bodyPartType;
    }

    /**
     * @return the numberOfAttachment
     */
    public Integer getNumberOfAttachment() {
        return numberOfAttachment;
    }

    /**
     * @param numberOfAttachment the numberOfAttachment to set
     */
    public void setNumberOfAttachment(Integer numberOfAttachment) {
        this.numberOfAttachment = numberOfAttachment;
    }

    /**
     * @return the numberOfDLAddress
     */
    public Integer getNumberOfDLAddress() {
        return numberOfDLAddress;
    }

    /**
     * @param numberOfDLAddress the numberOfDLAddress to set
     */
    public void setNumberOfDLAddress(Integer numberOfDLAddress) {
        this.numberOfDLAddress = numberOfDLAddress;
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
     * @return the atsFilingTime
     */
    public String getAtsFilingTime() {
        return atsFilingTime;
    }

    /**
     * @param atsFilingTime the atsFilingTime to set
     */
    public void setAtsFilingTime(String atsFilingTime) {
        this.atsFilingTime = atsFilingTime;
    }

    /**
     * @return the atsPriority
     */
    public String getAtsPriority() {
        return atsPriority;
    }

    /**
     * @param atsPriority the atsPriority to set
     */
    public void setAtsPriority(String atsPriority) {
        this.atsPriority = atsPriority;
    }

    /**
     * @return the atsOhi
     */
    public String getAtsOhi() {
        return atsOhi;
    }

    /**
     * @param atsOhi the atsOhi to set
     */
    public void setAtsOhi(String atsOhi) {
        this.atsOhi = atsOhi;
    }

    /**
     * @return the atsExtention
     */
    public Boolean getAtsExtention() {
        return atsExtention;
    }

    /**
     * @param atsExtention the atsExtention to set
     */
    public void setAtsExtention(Boolean atsExtention) {
        this.atsExtention = atsExtention;
    }

    /**
     * @return the orAddress
     */
    public String getOrAddress() {
        return orAddress;
    }

    /**
     * @param orAddress the orAddress to set
     */
    public void setOrAddress(String orAddress) {
        this.orAddress = orAddress;
    }

    /**
     * @return the bodyPartCharacterSet
     */
    public String getBodyPartCharacterSet() {
        return bodyPartCharacterSet;
    }

    /**
     * @param bodyPartCharacterSet the bodyPartCharacterSet to set
     */
    public void setBodyPartCharacterSet(String bodyPartCharacterSet) {
        this.bodyPartCharacterSet = bodyPartCharacterSet;
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
     * @return the contentCorrelatorString
     */
    public String getContentCorrelatorString() {
        return contentCorrelatorString;
    }

    /**
     * @param contentCorrelatorString the contentCorrelatorString to set
     */
    public void setContentCorrelatorString(String contentCorrelatorString) {
        this.contentCorrelatorString = contentCorrelatorString;
    }
    //</editor-fold>
    
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

    
}
