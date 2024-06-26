/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.config;

import com.attech.amhs.mtcu.common.XmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "GatewayConfig")
@XmlAccessorType(XmlAccessType.NONE)
public class Config extends XmlSerializer {

    @XmlElement(name = "AtnAddress")
    private String atnAddress;

    @XmlElement(name = "HostName")
    private String hostName;

    @XmlElement(name = "GlobalDomainID")
    private String globalDomainID;

    @XmlElement(name = "AFTNChannel")
    private AFTNChannelConfig aftnChannel;

    @XmlElement(name = "AMHSChannel")
    private AMHSChannelConfig amhsChannel;

    @XmlElement(name = "DSAChannel")
    private AddressConversion addressConversion;

    @XmlElement(name = "Controller")
    private List<StringValue> controllers;

    @XmlElement(name = "ReportRecipConfig")
    private RecipientConfig reportRecipient;

    @XmlElement(name = "ConvertRecipConfig")
    private RecipientConfig convertRecipient;

    @XmlElement(name = "PriorityRecipient")
    private RecipientConfig priorityRecipient;

    @XmlElement(name = "AcknownledgeRecipient")
    private RecipientConfig acknownlegdeRecipient;

    @XmlElement(name = "HouseKeeper")
    private Cleaner houseKeeper;

    @XmlElement(name = "DebugMode")
    private boolean debugMode;

    @XmlElement(name = "ProcessAckAsIPM")
    private boolean processAckAsIPM;

    @XmlElement(name = "AutoReloadingConfig")
    private boolean autoReloadConfig;

    @XmlElement(name = "ThreadingStatusReportPeriodTimeout")
    private long threadingStatusReportPeriodTime;

    @XmlElement(name = "ReportAsymAddress")
    private boolean reportAsymAddress;

    @XmlElement(name = "ReportReportType")
    private boolean reportWrongReportType;

    private String sysAFTNAddress;

    public static Config instance;

    public Config() {
        aftnChannel = new AFTNChannelConfig();
        amhsChannel = new AMHSChannelConfig();
        addressConversion = new AddressConversion();
        controllers = new ArrayList<>();
    }

    public static void configure(String file) throws JAXBException, IOException {
        instance = Config.load(file, Config.class);
        // instance = (Config) XmlSerializer.deserialize(file, Config.class);
        if (instance == null) {
            return;
        }
        if (instance.getAmhsChannel() != null) {
            instance.getAmhsChannel().build();
        }
    }

    public static void main(String[] args) throws JAXBException, IOException {

        Config.configure("gateway.xml");
        Config.instance.save("gateway.test.xml");
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the deliverMessage
     */
    public AFTNChannelConfig getAftnChannel() {
        return aftnChannel;
    }

    /**
     * @param deliverMessage the deliverMessage to set
     */
    public void setAftnChannel(AFTNChannelConfig deliverMessage) {
        this.aftnChannel = deliverMessage;
    }

    /**
     * @return the receivingMessage
     */
    public AMHSChannelConfig getAmhsChannel() {
        return amhsChannel;
    }

    /**
     * @param receivingMessage the receivingMessage to set
     */
    public void setAmhsChannel(AMHSChannelConfig receivingMessage) {
        this.amhsChannel = receivingMessage;
    }

    /**
     * @return the addressConversion
     */
    public AddressConversion getAddressConversion() {
        return addressConversion;
    }

    /**
     * @param addressConversion the addressConversion to set
     */
    public void setAddressConversion(AddressConversion addressConversion) {
        this.addressConversion = addressConversion;
    }

    /**
     * @return the controllers
     */
    public List<StringValue> getControllers() {
        return controllers;
    }

    /**
     * @param controlers the controllers to set
     */
    public void setControllers(List<StringValue> controlers) {
        this.controllers = controlers;
    }

    /**
     * @return the atnAddress
     */
    public String getAtnAddress() {
        return atnAddress;
    }

    /**
     * @param atnAddress the atnAddress to set
     */
    public void setAtnAddress(String atnAddress) {
        this.setAtnAddress(atnAddress);
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the globalDomainID
     */
    public String getGlobalDomainID() {
        return globalDomainID;
    }

    /**
     * @param globalDomainID the globalDomainID to set
     */
    public void setGlobalDomainID(String globalDomainID) {
        this.globalDomainID = globalDomainID;
    }

    /**
     * @return the reportRecipient
     */
    public RecipientConfig getReportRecipient() {
        return reportRecipient;
    }

    /**
     * @param reportRecipient the reportRecipient to set
     */
    public void setReportRecipient(RecipientConfig reportRecipient) {
        this.reportRecipient = reportRecipient;
    }

    /**
     * @return the convertRecipient
     */
    public RecipientConfig getConvertRecipient() {
        return convertRecipient;
    }

    /**
     * @param convertRecipient the convertRecipient to set
     */
    public void setConvertRecipient(RecipientConfig convertRecipient) {
        this.convertRecipient = convertRecipient;
    }

    /**
     * @return the priorityRecipient
     */
    public RecipientConfig getPriorityRecipient() {
        return priorityRecipient;
    }

    /**
     * @param priorityRecipient the priorityRecipient to set
     */
    public void setPriorityRecipient(RecipientConfig priorityRecipient) {
        this.priorityRecipient = priorityRecipient;
    }

    /**
     * @return the houseKeeper
     */
    public Cleaner getHouseKeeper() {
        return houseKeeper;
    }

    /**
     * @param houseKeeper the houseKeeper to set
     */
    public void setHouseKeeper(Cleaner houseKeeper) {
        this.houseKeeper = houseKeeper;
    }

    /**
     * @return the debugMode
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * @param debugMode the debugMode to set
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * @return the processAckAsIPM
     */
    public boolean isProcessAckAsIPM() {
        return processAckAsIPM;
    }

    /**
     * @param processAckAsIPM the processAckAsIPM to set
     */
    public void setProcessAckAsIPM(boolean processAckAsIPM) {
        this.processAckAsIPM = processAckAsIPM;
    }

    /**
     * @return the autoReloadConfig
     */
    public boolean isAutoReloadConfig() {
        return autoReloadConfig;
    }

    /**
     * @param autoReloadConfig the autoReloadConfig to set
     */
    public void setAutoReloadConfig(boolean autoReloadConfig) {
        this.autoReloadConfig = autoReloadConfig;
    }

    /**
     * @return the threadingStatusReportPeriodTime
     */
    public long getThreadingStatusReportPeriodTime() {
        return threadingStatusReportPeriodTime;
    }

    /**
     * @param threadingStatusReportPeriodTime the threadingStatusReportPeriodTime to set
     */
    public void setThreadingStatusReportPeriodTime(long threadingStatusReportPeriodTime) {
        this.threadingStatusReportPeriodTime = threadingStatusReportPeriodTime;
    }

    /**
     * @return the reportAsymAddress
     */
    public boolean isReportAsymAddress() {
        return reportAsymAddress;
    }

    /**
     * @param reportAsymAddress the reportAsymAddress to set
     */
    public void setReportAsymAddress(boolean reportAsymAddress) {
        this.reportAsymAddress = reportAsymAddress;
    }

    /**
     * @return the reportWrongReportType
     */
    public boolean isReportWrongReportType() {
        return reportWrongReportType;
    }

    /**
     * @param reportWrongReportType the reportWrongReportType to set
     */
    public void setReportWrongReportType(boolean reportWrongReportType) {
        this.reportWrongReportType = reportWrongReportType;
    }

    /**
     * @return the acknownlegdeRecipient
     */
    public RecipientConfig getAcknownlegdeRecipient() {
        return acknownlegdeRecipient;
    }

    /**
     * @param acknownlegdeRecipient the acknownlegdeRecipient to set
     */
    public void setAcknownlegdeRecipient(RecipientConfig acknownlegdeRecipient) {
        this.acknownlegdeRecipient = acknownlegdeRecipient;
    }

    /**
     * @return the sysAFTNAddress
     */
    public String getSysAFTNAddress() {
        return sysAFTNAddress;
    }

    /**
     * @param sysAFTNAddress the sysAFTNAddress to set
     */
    public void setSysAFTNAddress(String sysAFTNAddress) {
        this.sysAFTNAddress = sysAFTNAddress;
    }
    //</editor-fold>
}
