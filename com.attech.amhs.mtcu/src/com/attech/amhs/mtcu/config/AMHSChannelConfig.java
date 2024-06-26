/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.config;

import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "ReceivingMessage")
@XmlAccessorType(XmlAccessType.NONE)
public class AMHSChannelConfig {




    // Receiver
    @XmlElement(name = "Channel")
    private String channelName = "x400mt";

    @XmlElement(name = "LogFile")
    private String logFile = "x400api.xml";

    @XmlElement(name = "WaitTimeout")
    private Integer waitTimeout = 60;

    @XmlElement(name = "RetryInterval")
    private Integer retryInterval;

    @XmlElement(name = "RejectLongMessage")
    private Boolean rejectLongMessage;

    @XmlElement(name = "AllowedSymbols")
    private String allowSymbols;

    @XmlElement(name = "AllowedCharacters")
    private String allowedCharacters;

    @XmlElement(name = "AllowedEIT")
    private EncodedInformationType allowEIT;

    @XmlElement(name = "CharacterLimit")
    private int characterLimit;

    @XmlElement(name = "ProbeValidEIT")
    private String probeValidEIT;

    @XmlElement(name = "AddressLimit")
    private int addressLimit;

    @XmlElement(name = "AllowedCharacterSet")
    private String allowedCharacterSet;
    
    @XmlElement(name = "GWoutName")
    private String goutName;
    
    @XmlElement(name = "DeleteMSGObject")
    private boolean deleteMessageObject;

    private List<String> allowedCharSet;

    public void build() {
        // allowedCharSet = new ArrayList<>();
        String[] chars = allowedCharacterSet.split(",");
        allowedCharSet = Arrays.asList(chars);
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the receiverName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param name the receiverName to set
     */
    public void setChannelName(String name) {
        this.channelName = name;
    }

    /**
     * @return the intervalTime
     */
    public Integer getWaitTimeout() {
        return waitTimeout;
    }

    /**
     * @param intervalTime the intervalTime to set
     */
    public void setWaitTimeout(Integer intervalTime) {
        this.waitTimeout = intervalTime;
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
     * @return the rejectLongMessage
     */
    public Boolean getRejectLongMessage() {
        return rejectLongMessage;
    }

    /**
     * @param rejectLongMessage the rejectLongMessage to set
     */
    public void setRejectLongMessage(Boolean rejectLongMessage) {

        this.rejectLongMessage = rejectLongMessage;
    }

    /**
     * @return the allowSymbols
     */
    public String getAllowSymbols() {
        return allowSymbols;
    }

    /**
     * @param allowSymbols the allowSymbols to set
     */
    public void setAllowSymbols(String allowSymbols) {
        this.allowSymbols = allowSymbols;
    }

    /**
     * @return the allowCharacter
     */
    public String getAllowedCharacters() {
        return allowedCharacters;
    }

    /**
     * @param allowCharacter the allowCharacter to set
     */
    public void setAllowedCharacters(String allowCharacter) {
        this.allowedCharacters = allowCharacter;
    }

    /**
     * @return the characterSet
     */
    public EncodedInformationType getAllowEIT() {
        return allowEIT;
    }

    /**
     * @param allowEIT
     */
    public void setAllowEIT(EncodedInformationType allowEIT) {
        this.allowEIT = allowEIT;
    }

    /**
     * @return the characterLimit
     */
    public int getCharacterLimit() {
        return characterLimit;
    }

    /**
     * @param characterLimit the characterLimit to set
     */
    public void setCharacterLimit(int characterLimit) {
        this.characterLimit = characterLimit;
    }

    /**
     * @return the probeValidEIT
     */
    public String getProbeValidEIT() {
        return probeValidEIT;
    }

    /**
     * @param probeValidEIT the probeValidEIT to set
     */
    public void setProbeValidEIT(String probeValidEIT) {
        this.probeValidEIT = probeValidEIT;
    }

    /**
     * @return the maximumAddressAllow
     */
    public int getAddressLimit() {
        return addressLimit;
    }

    /**
     * @param maximumAddressAllow the maximumAddressAllow to set
     */
    public void setAddressLimit(int maximumAddressAllow) {
        this.addressLimit = maximumAddressAllow;
    }

    /**
     * @return the allowedCharacterSet
     */
    public String getAllowedCharacterSet() {
        return allowedCharacterSet;
    }

    /**
     * @param allowedCharacterSet the allowedCharacterSet to set
     */
    public void setAllowedCharacterSet(String allowedCharacterSet) {
        this.allowedCharacterSet = allowedCharacterSet;
    }

    /**
     * @return the allowedCharSet
     */
    public List<String> getAllowedCharSet() {
        return allowedCharSet;
    }

    /**
     * @return the retryInterval
     */
    public Integer getRetryInterval() {
        return retryInterval;
    }

    /**
     * @param retryInterval the retryInterval to set
     */
    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }
    
    /**
     * @return the goutName
     */
    public String getGoutName() {
        return goutName;
    }

    /**
     * @param goutName the goutName to set
     */
    public void setGoutName(String goutName) {
        this.goutName = goutName;
    }

    /**
     * @return the deleteMessageObject
     */
    public boolean isDeleteMessageObject() {
        return deleteMessageObject;
    }

    /**
     * @param deleteMessageObject the deleteMessageObject to set
     */
    public void setDeleteMessageObject(boolean deleteMessageObject) {
        this.deleteMessageObject = deleteMessageObject;
    }

    //</editor-fold>

}
