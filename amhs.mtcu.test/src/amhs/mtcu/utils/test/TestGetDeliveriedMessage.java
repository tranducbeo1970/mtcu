/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.database.entities.MSG;
import amhs.database.utils.gateway.DeliveriedMessageDBUtils;
import amhs.mtcu.config.Config;
import java.util.Date;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestGetDeliveriedMessage {
    public static void main(String [] args) {
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");
        
        DeliveriedMessageDBUtils messageUtils = new DeliveriedMessageDBUtils();
        MSG messages = messageUtils.getDeliveriedMessage("HJA", 1, new Date());
        // System.out.println("SELECT: " + messages.size());
    }
}
