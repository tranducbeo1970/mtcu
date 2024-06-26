/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author andh
 */
@Entity
@Table(name = "gwin")
public class GatewayIn implements Serializable {

    private static final long serialVersionUID = -3575256240177216640L;

    @Column(name = "cpa", nullable = false, length = 1)
    private String cpa;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msgid", nullable = false)
    private Long msgid;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "time")
    private Date time;

    @Column(name = "text", length = 2200)
    private String text;

    @Column(name = "source", length = 200)
    private String source;

    @Column(name = "address", length = 250)
    private String address;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(msgid).append(" ");
        builder.append("CPA: ").append(cpa).append(" ");
        builder.append("SRC: ").append(source).append(" ");
        builder.append("ADD: ").append(address).append(" ");
        builder.append("PRI: ").append(priority).append(" ");
        builder.append("TME: ").append(time).append(" \n");
        builder.append("TXT: ").append(text).append(" \n");
        return builder.toString();
    }

    public boolean isExpire(long period) {
        if (this.time == null) {
            return false;
        }
        return this.time.getTime() + period < System.currentTimeMillis();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the CPA
     */
    public String getCpa() {
        return cpa;
    }

    /**
     * @param cpa the CPA to set
     */
    public void setCpa(String cpa) {
        this.cpa = cpa;
    }

    /**
     * @return the MSGID
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
     * @return the text
     */
    public String getText() {
        return text;
    }

//    public String getLogger() {
//        String logContent = this.text.replace("\n", "\n                                           ");
//    }
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
    //</editor-fold>

    @Override
    public int hashCode() {
        return String.format("%s", this.msgid).hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof GatewayIn)) {
            return false;
        }
        GatewayIn other = (GatewayIn) object;
        if (this.msgid != other.getMsgid()) {
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
