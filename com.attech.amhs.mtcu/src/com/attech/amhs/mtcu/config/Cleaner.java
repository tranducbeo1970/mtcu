/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "MtAttributes")
@XmlAccessorType(XmlAccessType.NONE)
public class Cleaner {

    @XmlElement(name = "Enabled")
    private boolean enable = false;

    @XmlElement(name = "Hour")
    private Integer hour;

    @XmlElement(name = "Minute")
    private Integer minute;

    @XmlElement(name = "OutDate")
    private int outdatedLog;

    @Override
    public String toString() {
        return String.format("Status: %s Time: %s", (enable ? "on" : "off"), hour);
    }

    public Cleaner() {
    }

    /**
     * @return the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * @return the time
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * @param time the time to set
     */
    public void setHour(Integer time) {
        this.hour = time;
    }

    /**
     * @return the outdatedLog
     */
    public int getOutdatedLog() {
        return outdatedLog;
    }

    /**
     * @param outdatedLog the outdatedLog to set
     */
    public void setOutdatedLog(int outdatedLog) {
        this.outdatedLog = outdatedLog;
    }

    /**
     * @return the minute
     */
    public Integer getMinute() {
        return minute;
    }

    /**
     * @param minute the minute to set
     */
    public void setMinute(Integer minute) {
        this.minute = minute;
    }

}
