/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "gwout")
public class GatewayOut implements Serializable {

    private static final long serialVersionUID = 1173934597226119598L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msgid", nullable = false)
    private Long msgid;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "time")
    private Date insertedTime;

    @Column(name = "text", length = 2200)
    private String text;

    @Column(name = "origin", length = 8)
    private String originator;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "amhsid", length = 200)
    private String amhsid;

    @Column(name = "filing_time", length = 6)
    private String filingTime;

    @Column(name = "optional_heading", length = 60)
    private String optionalHeading;

    @Transient
    private MessageConversionLog log;

    // CONSTRUCTORS
    public GatewayOut() {
        this.insertedTime = new Date();
    }

    public GatewayOut(String amhsID, int priority, String originator, String address, String filingTime, String ohi, String text) {
        this.amhsid = amhsID;
        this.priority = priority;
        this.originator = originator;
        this.address = address;
        this.filingTime = filingTime;
        this.optionalHeading = ohi;
        this.text = text;
        this.insertedTime = new Date();
    }

    @Override
    public String toString() {
        StringBuilder buidler = new StringBuilder();
        buidler.append("\nOUTPUT: --------------------------  \n");
        buidler.append(String.format("FROM: %s \n", this.originator));
        buidler.append(String.format("TO: %s \n", this.address));
        buidler.append(String.format("PRI: %s \n", this.priority));
        buidler.append(String.format("DTG: %s \n", this.filingTime));
        buidler.append(String.format("OHI: %s \n", this.optionalHeading));
        buidler.append("****  \n");
        buidler.append(this.text);
        buidler.append("\n");
        buidler.append("--------------------------  \n");
        return buidler.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the msgid
     */
    public Long getMsgid() {
        return msgid;
    }

    /**
     * @param msgid the msgid to set
     */
    public void setMsgid(Long msgid) {
        this.msgid = msgid;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the amhsid
     */
    public String getAmhsid() {
        return amhsid;
    }

    /**
     * @param amhsid the amhsid to set
     */
    public void setAmhsid(String amhsid) {
        this.amhsid = amhsid;
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
     * @return the insertedTime
     */
    public Date getInsertedTime() {
        return insertedTime;
    }

    /**
     * @param insertedTime the insertedTime to set
     */
    public void setInsertedTime(Date insertedTime) {
        this.insertedTime = insertedTime;
    }

    /**
     * @return the optionalHeading
     */
    public String getOptionalHeading() {
        return optionalHeading;
    }

    /**
     * @param optionalHeading the optionalHeading to set
     */
    public void setOptionalHeading(String optionalHeading) {
        this.optionalHeading = optionalHeading;
    }

    /**
     * @return the log
     */
    public MessageConversionLog getLog() {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog(MessageConversionLog log) {
        this.log = log;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        return this.msgid.hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof GatewayOut)) {
            return false;
        }
        GatewayOut other = (GatewayOut) object;
        if (!Objects.equals(this.msgid, other.getMsgid())) {
            return false;
        }

        return true;
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        // super.finalize();
        // will print name of object 
//        System.out.println(this.getClass() + " successfully garbage collected");
    }

}
