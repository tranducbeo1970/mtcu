/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.resubmit;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hong
 */
@XmlRootElement(name = "DrawItem")
@XmlAccessorType(XmlAccessType.NONE)
public class Config {

    @XmlElement(name = "type")
    private String msgTableName;
    
    @XmlElementWrapper(name = "Addresses")
    @XmlElement(name = "Address")
    private List<String> addresses;

    @XmlElement(name = "Interval")
    private int interval;

    @XmlElement(name = "Repeat")
    private boolean repeat;
    
    public Config() {
        this.addresses = new ArrayList<>();
    }
    
    public static void main(String [] args) {
        Config config = new Config();
        config.setAddresses("VVTSYOYX");
        config.setInterval(5000);
        config.setMsgTableName("msg01012015");
        config.setRepeat(true);
        XmlSerializer.serialize("config.xml", config);
        System.out.println("Done");
    }

    /**
     * @return the msgTableName
     */
    public String getMsgTableName() {
        return msgTableName;
    }

    /**
     * @param msgTableName the msgTableName to set
     */
    public void setMsgTableName(String msgTableName) {
        this.msgTableName = msgTableName;
    }

    /**
     * @return the addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
    
    public void setAddresses(String address) {
        this.addresses.add(address);
    }

    /**
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * @return the repeat
     */
    public boolean isRepeat() {
        return repeat;
    }

    /**
     * @param repeat the repeat to set
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
}
