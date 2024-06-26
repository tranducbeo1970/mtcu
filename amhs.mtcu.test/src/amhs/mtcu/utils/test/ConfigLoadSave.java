/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.mtcu.config.Config;
import amhs.mtcu.config.NameValueItem;
import amhs.mtcu.config.StringValue;
import amhs.mtcu.utils.XmlSerializer;

/**
 *
 * @author andh
 */
public class ConfigLoadSave {
    public static void main(String [] args) throws Exception {
        /*
        GatewayConfig config = new GatewayConfig();
        config.getControlers().add(new StringValue("#1", "/CN=VVNBYFYX/"));
        config.getControlers().add(new StringValue("#2", "/CN=VVNBYFYX/"));
        XmlSerializer.serialize("D:\\gateway_config.xml", config);
        */
        
        Config config = (Config) XmlSerializer.deserialize("config/gateway.xml", Config.class);
        
        System.out.println("SIZE:" + config.getControlers().size());
    }
}
