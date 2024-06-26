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
// @XmlRootElement(name = "DeliverMessage")
@XmlRootElement(name = "AFTNChannel")
@XmlAccessorType(XmlAccessType.NONE)
public class AFTNChannelConfig {

    // host name
    @XmlElement(name = "ChannelName")
    private String channelName = "x400mt";

    @XmlElement(name = "LogFile")
    private String logFile = "x400api.xml";

    @XmlElement(name = "Interval")
    private int intervalTime = 1000;

    @XmlElement(name = "RetryInterval")
    private Long retryInterval;

    @XmlElement(name = "MessageExpire")
    private Long messageExpire;

    @XmlElement(name = "MtMessageConfig")
    private DefaultMessageValue defaultMessageValue;

    @XmlElement(name = "TraceInfo")
    private TraceInfo traceInfo;

    @XmlElement(name = "InternalTraceInfo")
    private InternalTraceInfo internalTraceInfo;

    @XmlElement(name = "MaxRecordFetching")
    private Integer maxRecordFetching;

    @XmlElement(name = "GwinName")
    private String gwinName;

    public AFTNChannelConfig() {
        this.defaultMessageValue = new DefaultMessageValue();
        this.traceInfo = new TraceInfo();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param channelName the channelName to set
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * @return the logFile
     */
    public String getLogFile() {
        return logFile;
    }

    /**
     * @param logFile the logFile to set
     */
    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    /**
     * @return the interval time to refetching message from aftn for converting into amhs
     */
    public int getIntervalTime() {
        return intervalTime;
    }

    /**
     * @param intervalTime
     * @param the interval time to refetching message from aftn for converting into amhs
     */
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    /**
     * @return the defaultMessageValue
     */
    public DefaultMessageValue getDefaultMessageValue() {
        return defaultMessageValue;
    }

    /**
     * @param mtMessageConfig the defaultMessageValue to set
     */
    public void setDefaultMessageValue(DefaultMessageValue mtMessageConfig) {
        this.defaultMessageValue = mtMessageConfig;
    }

    /**
     * @return the traceInfo
     */
    public TraceInfo getTraceInfo() {
        return traceInfo;
    }

    /**
     * @param traceInfo the traceInfo to set
     */
    public void setTraceInfo(TraceInfo traceInfo) {
        this.traceInfo = traceInfo;
    }

    /**
     * @return the maxRecordFetching
     */
    public Integer getMaxRecordFetching() {
        return maxRecordFetching;
    }

    /**
     * @param maxRecordFetching the maxRecordFetching to set
     */
    public void setMaxRecordFetching(Integer maxRecordFetching) {
        this.maxRecordFetching = maxRecordFetching;
    }

    /**
     * @return the internalTraceInfo
     */
    public InternalTraceInfo getInternalTraceInfo() {
        return internalTraceInfo;
    }

    /**
     * @param internalTraceInfo the internalTraceInfo to set
     */
    public void setInternalTraceInfo(InternalTraceInfo internalTraceInfo) {
        this.internalTraceInfo = internalTraceInfo;
    }

    /**
     * @return the retryInterval
     */
    public Long getRetryInterval() {
        return retryInterval;
    }

    /**
     * @param retryInterval the retryInterval to set
     */
    public void setRetryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
    }

    /**
     * @return the messageExpire
     */
    public Long getMessageExpire() {
        return messageExpire;
    }

    /**
     * @param messageExpire the messageExpire to set
     */
    public void setMessageExpire(Long messageExpire) {
        this.messageExpire = messageExpire;
    }

    /**
     * @return the gwinName
     */
    public String getGwinName() {
        return gwinName;
    }

    /**
     * @param gwinName the gwinName to set
     */
    public void setGwinName(String gwinName) {
        this.gwinName = gwinName;
    }
    //</editor-fold>
}
