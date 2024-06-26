/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message.gateway;

import amhs.aftn.testtool.highlevel.XmlSerializer;

/**
 *
 * @author andh
 */
public class ConfigSave {
    public static void main(String [] args) {
        Config config = new Config();
        config.setChannelName("x400mt");
        config.setDomainId(";;;");
        config.setHostname("gateway.attech");
        config.setLogFile("s");
        
        XmlSerializer.serialize("D:\\config.xml", config);
        System.out.println("OK");
    }
}
