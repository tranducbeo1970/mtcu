/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.common.MtCommon;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.config.TraceInfo;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.entity.SysConfig;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.isode.x400api.Recip;
import com.isode.x400api.Traceinfo;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class DeliverReport extends DeliverMessageBase {

    private String originator;
    private List<Recipient> recipient;
    private String arrivalTime;
    private String messageId;
    private String messageSubjectId;
    private String contentId;
    private String encodeInfomationType;
    private String contentCorrelatorIA5String;
    private Integer priority;
    private Integer contentType;

    private List<RptRecipient> recips;
    // private MtCommon mtcommon = new MtCommon();

    public DeliverReport() {
        super();
        encodeInfomationType = "ia5-text";
        recips = new ArrayList<>();

    }

    public void add(RptRecipient recip) {
        recips.add(recip);
    }

    @Override
    public void build(MTMessage mtmessage) {
        if (this.recips.isEmpty()) { return; }
	
        this.messageId = MtCommon.generateMessageId();
        this.arrivalTime = MtCommon.generateArrivalTime();

        set(mtmessage, X400_att.X400_S_SUBJECT_IDENTIFIER, this.messageSubjectId);
        set(mtmessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES,  this.encodeInfomationType);
        
        set(mtmessage, X400_att.X400_S_OR_ADDRESS, this.originator);
        set(mtmessage, X400_att.X400_S_MESSAGE_IDENTIFIER, this.messageId);
        
        set(mtmessage, X400_att.X400_S_CONTENT_CORRELATOR_IA5_STRING, this.contentCorrelatorIA5String);
        set(mtmessage, X400_att.X400_S_CONTENT_IDENTIFIER, this.contentId);
        set(mtmessage, X400_att.X400_N_CONTENT_TYPE, this.contentType);

        addMtRecip(mtmessage, this.originator, X400_att.X400_RECIP_ENVELOPE, 1);

        int rno = 1;
        for (RptRecipient recip : this.getRecips()) {
            recip.getRecip(mtmessage, rno++, arrivalTime);
        }

        buildTraceInfo(mtmessage, arrivalTime);
    }

    @Override
    public MessageConversionLog createMessageConversionLog() {
        final Date date = new Date();
        final MessageConversionLog log = new MessageConversionLog();
        log.setType(MessageType.AMHS);
        log.setCategory(MessageCategory.REPORT);
        log.setConvertedDate(date);
        log.setSubjectId(this.messageSubjectId);
        log.setOrigin(this.originator);
        log.setPriority(parseAmhsPriority(this.priority));
        log.setSubject("<SYSTEM>");
        log.setContent(this.toString());
        log.setFilingTime(submissionTime);  // Submission time in amhs
        return log;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Delivery Report for your message with\n");
        builder.append("Subject Identifier: ").append(this.messageSubjectId).append("\n");

        for (RptRecipient recip : this.recips) {
            builder.append(recip.toString()).append("\n");
        }

        return builder.toString();
    }

    private void buildTraceInfo(MTMessage mtMessage, String arrivalTime) {
        // get config
        final TraceInfo config = Config.instance.getAftnChannel().getTraceInfo();

        // Instance new trace
        final Traceinfo traceInfo = new Traceinfo();

        final int status = com.isode.x400mtapi.X400mt.x400_mt_traceinfonew(mtMessage, traceInfo, X400_att.X400_TRACE_INFO);
        if (status != X400_att.X400_E_NOERROR) {
            return;
        }

        set(traceInfo, X400_att.X400_S_GLOBAL_DOMAIN_ID, config.getGlobalDomainID(), -1);
        set(traceInfo, X400_att.X400_S_DSI_ARRIVAL_TIME, arrivalTime, -1);
        set(traceInfo, X400_att.X400_N_DSI_ROUTING_ACTION, config.getSuppliedDomainRoutingAction());
    }

    private void addMtRecip(MTMessage mtMessage, String address, int type, int rno) {
        final Recip recip = new Recip();

        int status = com.isode.x400mtapi.X400mt.x400_mt_recipnew(mtMessage, type, recip);
        if (status != X400_att.X400_E_NOERROR) {
            return;
        }

        set(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, rno);
        set(recip, X400_att.X400_S_OR_ADDRESS, address, address.length());
        set(recip, X400_att.X400_N_REPORT_REQUEST, 2);
        set(recip, X400_att.X400_N_MTA_REPORT_REQUEST, 3);
        set(recip, X400_att.X400_S_FREE_FORM_NAME, address, address.length());
        
        
    }

    /*
    private void set(Traceinfo traceObject, int attribute, String value, int length, Integer mode) {
        if (value == null) {
            return;
        }

        com.isode.x400api.X400.x400_traceinfoaddstrparam(traceObject, attribute, value, length);
    }
    */

    private void set(Traceinfo traceObject, int attribute, String value, int length) {
        // set(traceObject, attribute, value, length, 0);
        com.isode.x400api.X400.x400_traceinfoaddstrparam(traceObject, attribute, value, length);
    }

    private void set(Traceinfo traceObject, int attribute, Integer value, Integer mode) {
        if (value == null) {
            return;
        }

        com.isode.x400api.X400.x400_traceinfoaddintparam(traceObject, attribute, value);

    }

    private void set(Traceinfo traceObject, int attribute, Integer value) {
        set(traceObject, attribute, value, 0);
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the origin
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * @param origin the origin to set
     */
    public void setOriginator(String origin) {
        this.originator = origin;
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
     * @return the messageSubjectId
     */
    public String getMessageSubjectId() {
        return messageSubjectId;
    }

    /**
     * @param messageSubjectId the messageSubjectId to set
     */
    public void setMessageSubjectId(String messageSubjectId) {
        this.messageSubjectId = messageSubjectId;
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
     * @return the contentCorrelatorIA5String
     */
    public String getContentCorrelatorIA5String() {
        return contentCorrelatorIA5String;
    }

    /**
     * @param contentCorrelatorIA5String the contentCorrelatorIA5String to set
     */
    public void setContentCorrelatorIA5String(String contentCorrelatorIA5String) {
        this.contentCorrelatorIA5String = contentCorrelatorIA5String;
    }

    /**
     * @return the recips
     */
    public List<RptRecipient> getRecips() {
        return recips;
    }

    /**
     * @param recips the recips to set
     */
    public void setRecips(List<RptRecipient> recips) {
        this.recips = recips;
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
     * @return the recipient
     */
    public List<Recipient> getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(List<Recipient> recipient) {
        this.recipient = recipient;
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
        DeliverReport other = (DeliverReport) object;
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
//        System.out.println(this.getClass() + " successfully garbage collected");
    }
}
