/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool;

import java.io.IOException;
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

    @XmlElement(name = "Channel")
    private String channel;

    @XmlElement(name = "UpdateInterval")
    private int updateInterval;

    public static Config instance;

    public Config() {
    }

    public static void configure(String file) throws JAXBException, IOException {
        instance = XmlSerializer.load(file, Config.class);
    }

    public static void main(String[] args) throws JAXBException, IOException {
        Config config = new Config();
        config.setChannel("x400mt");
        config.setUpdateInterval(3000);
        config.save("config.xml");
        System.out.println("Done");
        
        Config config2 = XmlSerializer.load("config.xml", Config.class);
        System.out.println("Loaded: " + (config2 != null));
        
        Config.configure("config.xml");
//        instance = XmlSerializer.load("config.xml", Config.class);
        System.out.println("Loaded: " + (Config.instance != null));
    } 

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
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
     * @return the updateInterval
     */
    public int getUpdateInterval() {
        return updateInterval;
    }

    /**
     * @param updateInterval the updateInterval to set
     */
    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }
    //</editor-fold>
}
