/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.common.MtCommon;
import com.attech.amhs.mtcu.Priority;
import com.attech.amhs.mtcu.aftn.Message;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.config.DefaultMessageValue;
import com.attech.amhs.mtcu.config.TraceInfo;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.entity.SysConfig;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.attech.amhs.mtcu.common.PriorityUtil;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.Traceinfo;
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
public class DeliverMessage extends DeliverMessageBase {

    private final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyMMddHHmmss");

    // Envelope
    private Priority amhsPriority;
    private int priority;
    private String messageId;
    private String contentId;
    private String arrivalTime;
    private String originator;

    // Content
    private String ipmId;
    private List<Recipient> recipients;
    private String subject;
    private String content;
    private int importance; // IPM Importance: 0 - low, 1 - normal, 2 - high
    private int sensitve; // IPM Sensitivity: 1 - personal, 2 - private, 3 - company-confidential

    // ATS
    private Boolean atsMessage = true;
    private String atsPriority;
    private String atsOHI;
    private String atsFilingTime;
    private String atsContent;

    private Date time;
    // private MtCommon mtcommon = new MtCommon();
    
    
    private boolean extended; 
    

    public DeliverMessage() {
        super();
        this.recipients = new ArrayList<>();
        this.originator = Config.instance.getAtnAddress();
    }

    @Override
    public void build(MTMessage mtmessage) {
        this.messageId = MtCommon.generateMessageId();
        this.time = new Date();
        this.arrivalTime = datetimeFormat.format(time) + "Z";
        this.ipmId = MtCommon.generateIpmId(this.originator);

        buildEnvelope(mtmessage);
        buildAts(mtmessage);
        buildContent(mtmessage);
        buildRecipients(mtmessage);

    }

    public void addRecip(Recipient recipient) {
        this.recipients.add(recipient);
    }
    
    public void createRecip() {
        
    }

    public void setAftnMessage(Message message) {
        // this.messageId = MtCommon.generateMessageId();
        this.subject = String.format("%s - %s", message.getChannelId(), message.getOriginId());
        this.content = message.getContent();
        this.amhsPriority = convertPriority(message.getPriority());
        // this.amhsPriority = Priority.parse(message.getPriority());
        // this.priority = this.amhsPriority.getValue();

        // Adding ats
        this.atsContent = message.getContent();
        this.atsFilingTime = message.getFilingTime();
        this.atsOHI = message.getAdditionInfo();
        this.atsPriority = message.getPriority();
        this.atsMessage = true;
    }

    @Override
    public MessageConversionLog createMessageConversionLog() {
        final MessageConversionLog log = new MessageConversionLog();
        log.setType(MessageType.AMHS);
        log.setCategory(MessageCategory.IPM);
        log.setContent(this.content);
        log.setContentId(this.contentId);
        log.setConvertedDate(new Date());
        // log.setDate(dateFormat.format(date));
        log.setFilingTime(this.atsFilingTime);
        log.setIpmId(this.ipmId);
        log.setMessageId(this.messageId);
        log.setOhi(this.atsOHI);
        log.setOrigin(this.originator);
        log.setPriority(parseAmhsPriority(this.priority));
        log.setSubject(this.subject);

        for (Recipient recip : this.recipients) {
            log.addAddressLog(recip.getAddressConvertionLog());
        }

        return log;
    }

    // PRIVATE METHODS
    public Priority convertPriority(String name) {
        switch (name) {
            case "SS":
                return Priority.SS;
            case "DD":
                return Priority.DD;
            case "FF":
                return Priority.FF;
            case "GG":
                return Priority.GG;
            case "KK":
                return Priority.KK;
        }
        return null;
    }
    
    /*-----------------------------------------------------------
    
    
    
    -----------------------------------------------------------*/
    private void buildEnvelope(MTMessage mtMessage) {
        
        
        
        
        final DefaultMessageValue config = Config.instance.getAftnChannel().getDefaultMessageValue();
        
        /* Use for debug value */
        
        String oeit =  config.getOriginEIT();
        
        set(mtMessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, config.getOriginEIT());
        set(mtMessage, X400_att.X400_N_CONTENT_TYPE, config.getContentType());
        set(mtMessage, X400_att.X400_N_DISCLOSURE, config.getDisclosureRecip());
        set(mtMessage, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, config.getConversionProhibited());
        set(mtMessage, X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED, config.getAlterRecipAllow());
        set(mtMessage, X400_att.X400_N_CONTENT_RETURN_REQUEST, config.getContentReturnRequest());
        set(mtMessage, X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED, config.getRecipReassignmentProhibited());
        set(mtMessage, X400_att.X400_N_DL_EXPANSION_PROHIBITED, config.getDlExpProhibited());
        set(mtMessage, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, config.getConversionLossProhibited());
        set(mtMessage, X400_att.X400_S_ORIGINATOR_RETURN_ADDRESS, config.getOriginReturnAddress());
        //set(mtMessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, config.getOriginEIT());

        set(mtMessage, X400_att.X400_S_MESSAGE_IDENTIFIER, this.messageId);
        set(mtMessage, X400_att.X400_S_OR_ADDRESS, this.originator);
        set(mtMessage, X400_att.X400_N_PRIORITY, this.priority);
        set(mtMessage, X400_att.X400_S_CONTENT_IDENTIFIER, this.contentId);

        // Build trace & internal trace info
        buildTraceInfo(mtMessage, X400_att.X400_TRACE_INFO, this.arrivalTime);
    }
    /*-----------------------------------------------------------
    
    
    
    -----------------------------------------------------------*/
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

    /*-----------------------------------------------------------
    
    
    
    -----------------------------------------------------------*/
    private void buildRecipients(MTMessage mtMessage) {
        int rno = 1;
        for (Recipient recipient : this.recipients) {
            addRecipient(mtMessage, X400_att.X400_RECIP_STANDARD, rno++, recipient);
        }
    }

    private void buildAts(MTMessage mtMessage) {
        if (!this.atsMessage) {
            return;
        }

        final DefaultMessageValue config = Config.instance.getAftnChannel().getDefaultMessageValue();
        // DUC 19102024
        int ext = config.getAtsExtended();
        if(ext == 1) {
            if(this.isExtended()) {
                set(mtMessage, AMHS_att.ATS_N_EXTENDED,1);
            } else {
                set(mtMessage, AMHS_att.ATS_N_EXTENDED,0);
            }
        } else {
            set(mtMessage, AMHS_att.ATS_N_EXTENDED, 0);     // BASIC
        }
        set(mtMessage, AMHS_att.ATS_S_PRIORITY_INDICATOR, this.atsPriority);
        set(mtMessage, AMHS_att.ATS_S_FILING_TIME, this.atsFilingTime);
        set(mtMessage, AMHS_att.ATS_S_TEXT, this.content);
        
        if(this.atsOHI.length() > 0) {
            set(mtMessage, AMHS_att.ATS_S_OPTIONAL_HEADING_INFO, this.atsOHI);
        }
        
        //et(mtMessage, AMHS_att.ATS_EOH_MODE, AMHS_att.ATS_EOH_MODE_NONE);

        /*------------------------------
        
        DUC 19102024
        
        --------------------------------*/
        int i = config.getAtsExtendSupported();
        if (config.getAtsExtendSupported() == 1) {
            String authorizedTime = MtCommon.getAuthorizedTimeFromFilingTime(this.atsFilingTime);
            Integer precedence = PriorityUtil.toPrecedence(this.atsPriority);
            set(mtMessage, X400_att.X400_S_AUTHORIZATION_TIME, authorizedTime);
            set(mtMessage, X400_att.X400_S_PRECEDENCE_POLICY_ID, precedence);
            if(this.atsOHI.length() > 0) {
                set(mtMessage, X400_att.X400_S_ORIGINATORS_REFERENCE, this.atsOHI);
            }
        }
    }

    private void buildContent(MTMessage mtMessage) {
        set(mtMessage, X400_att.X400_S_IPM_IDENTIFIER, this.ipmId);
        set(mtMessage, X400_att.X400_S_SUBJECT, this.subject);
        // X400_att.X400_S_OBSOLETED_IPMS
        // X400_att.X400_S_RELATED_IPMS
        // X400_att.X400_S_EXPIRY_TIME
        // X400_att.X400_S_REPLY_TIME
        
        /*
        set(mtMessage, X400_att.X400_N_IMPORTANCE, this.importance);            // NEW NOT SET
        set(mtMessage, X400_att.X400_N_SENSITIVITY, this.sensitve);             // NEW NOT SET
        */
        
        // X400_att.X400_N_AUTOFORWARDED
        if (this.atsMessage) {
            return;
        }

        // Add an IA5 attachment using AddStrParam
        // String content = message.getContent() == null ? "" : message.getContent();
        // set(mtMessage, X400_att.X400_T_IA5TEXT, content, 1);
        set(mtMessage, X400_att.X400_T_IA5TEXT, this.content);
        set(mtMessage, X400_att.X400_N_NUM_ATTACHMENTS, 1);
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

    //<editor-fold defaultstate="collapsed" desc="Class Properties">

    /**
     * @return the amhsPriority
     */
    public Priority getAmhsPriority() {
        return amhsPriority;
    }

    /**
     * @param amhsPriority the amhsPriority to set
     */
    public void setAmhsPriority(Priority amhsPriority) {
        this.amhsPriority = amhsPriority;
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
     * @return the sensitve
     */
    public Integer getSensitve() {
        return sensitve;
    }

    /**
     * @param sensitve the sensitve to set
     */
    public void setSensitve(Integer sensitve) {
        this.sensitve = sensitve;
    }

    /**
     * @return the atsMessage
     */
    public Boolean getAtsMessage() {
        return atsMessage;
    }

    /**
     * @param atsMessage the atsMessage to set
     */
    public void setAtsMessage(Boolean atsMessage) {
        this.atsMessage = atsMessage;
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
     * @return the atsOHI
     */
    public String getAtsOHI() {
        return atsOHI;
    }

    /**
     * @param atsOHI the atsOHI to set
     */
    public void setAtsOHI(String atsOHI) {
        this.atsOHI = atsOHI;
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
     * @return the atsContent
     */
    public String getAtsContent() {
        return atsContent;
    }

    /**
     * @param atsContent the atsContent to set
     */
    public void setAtsContent(String atsContent) {
        this.atsContent = atsContent;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
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
        DeliverMessage other = (DeliverMessage) object;
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

    /**
     * @return the extended
     */
    public boolean isExtended() {
        return extended;
    }

    /**
     * @param extended the extended to set
     */
    public void setExtended(boolean extended) {
        this.extended = extended;
    }
}
