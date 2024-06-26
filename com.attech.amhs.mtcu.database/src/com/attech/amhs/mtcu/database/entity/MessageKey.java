/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ANDH
 */
@Embeddable
public class MessageKey implements Serializable {

    private static final long serialVersionUID = -4468475200968449789L;

    @Column(name = "cpa", length = 1)
    private String cpa;

    @Column(name = "msgid")
    private long msgId;

    //<editor-fold defaultstate="collapsed" desc="Class property methods">
    /**
     * @return the cpa
     */
    public String getCpa() {
        return cpa;
    }

    /**
     * @param cpa the cpa to set
     */
    public void setCpa(String cpa) {
        this.cpa = cpa;
    }

    /**
     * @return the msgId
     */
    public long getMsgId() {
        return msgId;
    }

    /**
     * @param msgId the msgId to set
     */
    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        return String.format("%s:%s", this.cpa, this.msgId).hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof MessageKey)) {
            return false;
        }
        MessageKey other = (MessageKey) object;
        if ((this.cpa == null && other.cpa != null) || (this.cpa != null && !this.cpa.equals(other.cpa))) {
            return false;
        }

        if (other.getMsgId() != this.msgId) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("com.attech.amhs.mtcu.database.entity.MessageKey[ cpa=" + this.cpa + " msgId=" + msgId + " ]");
    }

    @Override
    /* Overriding finalize method to check which object is garbage collected */
    protected void finalize() throws Throwable {
//        super.finalize();
        // will print name of object 
//        System.out.println(this.getClass() + " successfully garbage collected");
    }
}
