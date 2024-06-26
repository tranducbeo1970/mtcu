/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.database.entities.GatewayOut;
import amhs.database.utils.gateway.GatewayOutDbUtils;
import java.util.Date;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestGatewayoutInsert {
    public static void main(String [] args) {
        
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        
        
        GatewayOut gatewayOut = new GatewayOut();
        
        gatewayOut.setAddress("ADD");
        gatewayOut.setAmhsid("ID");
        gatewayOut.setFilingTime("234212");
        gatewayOut.setInsertedTime(new Date());
        gatewayOut.setOptionalHeading(null);
        gatewayOut.setPriority(1);
        gatewayOut.setText("Test\r\nTest\r\n");
        
        GatewayOutDbUtils dbUtil = new GatewayOutDbUtils();
        dbUtil.add(gatewayOut);
    }
}
