/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message.gateway;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
public class Config {
    
    public static Config instance;
    
    @XmlElement(name = "HostName")
    private String hostname;
    
    @XmlElement(name = "DomainID")
    private String domainId;
    
    @XmlElement(name = "ChannelName")
    private String channelName;
    
    @XmlElement(name = "LogFile")
    private String logFile;

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return the domainId
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * @param domainId the domainId to set
     */
    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
    
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
    
    public static void load(String file) {
        instance = (Config) XmlSerializer.deserialize(file, Config.class);
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
    
}
