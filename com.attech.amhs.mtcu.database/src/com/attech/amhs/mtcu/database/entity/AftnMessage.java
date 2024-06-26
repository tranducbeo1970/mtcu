/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author andh
 */
@Entity
@Table(name = "MSGX")
public class AftnMessage implements Serializable {

    private static final long serialVersionUID = -7327903170154819859L;

    @EmbeddedId
    private MessageKey messageKey;

    @Column(name = "cct", length = 3)
    private String cct;

    @Column(name = "cid", length = 3)
    private String cid;

    @Column(name = "csn", length = 11)
    private Integer sequenceNumber;

    @Column(name = "chan", length = 1)
    private String channel;

    @Column(name = "prio")
    private Integer priority;

    @Column(name = "dtg", length = 6)
    private String filingTime;

    @Column(name = "org", length = 8)
    private String origin;

    @Column(name = "time")
    private Date time;

    @Column(name = "div", length = 1)
    private String div;

    @Column(name = "svc", length = 3)
    private String svc;

    @Column(name = "rpt", length = 1)
    private String rpt;

    @Column(name = "rpt_time")
    private Integer rptTime;

    @Column(name = "flag", length = 1)
    private String flag;

    @Column(name = "text", length = 2200)
    private String text;

    @Column(name = "source", length = 200)
    private String source;

    // CONSTRUCTORS
    public AftnMessage() {
        this.messageKey = new MessageKey();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the cpa
     */
    public String getCpa() {
        return this.messageKey.getCpa();
    }

    /**
     * @param cpa the cpa to set
     */
    public void setCpa(String cpa) {
        this.messageKey.setCpa(cpa);
    }

    /**
     * @return the msgId
     */
    public Long getMsgId() {
        return this.messageKey.getMsgId();
    }

    /**
     * @param msgId the msgId to set
     */
    public void setMsgId(Long msgId) {
        this.messageKey.setMsgId(msgId);
    }

    /**
     * @return the messageKey
     */
    public MessageKey getMessageKey() {
        return messageKey;
    }

    /**
     * @param messageKey the messageKey to set
     */
    public void setMessageKey(MessageKey messageKey) {
        this.messageKey = messageKey;
    }

    /**
     * @return the cct
     */
    public String getCct() {
        return cct;
    }

    /**
     * @param cct the cct to set
     */
    public void setCct(String cct) {
        this.cct = cct;
    }

    /**
     * @return the cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * @return the sequenceNumber
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @param sequenceNumber the sequenceNumber to set
     */
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
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
     * @return the filingTime
     */
    public String getFilingTime() {
        return filingTime;
    }

    /**
     * @param filingTime the filingTime to set
     */
    public void setFilingTime(String filingTime) {
        this.filingTime = filingTime;
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

    /**
     * @return the div
     */
    public String getDiv() {
        return div;
    }

    /**
     * @param div the div to set
     */
    public void setDiv(String div) {
        this.div = div;
    }

    /**
     * @return the svc
     */
    public String getSvc() {
        return svc;
    }

    /**
     * @param svc the svc to set
     */
    public void setSvc(String svc) {
        this.svc = svc;
    }

    /**
     * @return the rpt
     */
    public String getRpt() {
        return rpt;
    }

    /**
     * @param rpt the rpt to set
     */
    public void setRpt(String rpt) {
        this.rpt = rpt;
    }

    /**
     * @return the rptTime
     */
    public Integer getRptTime() {
        return rptTime;
    }

    /**
     * @param rptTime the rptTime to set
     */
    public void setRptTime(Integer rptTime) {
        this.rptTime = rptTime;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        return this.cct.hashCode() + this.sequenceNumber.hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof AftnMessage)) {
            return false;
        }
        AftnMessage other = (AftnMessage) object;
        if ((this.cct == null && other.cct != null) || (this.cct != null && !this.cct.equals(other.cct))) {
            return false;
        }

        if (this.sequenceNumber != other.getSequenceNumber()) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
        // will print name of object 
//        System.out.println(this.getClass() + " successfully garbage collected");
    }

}
