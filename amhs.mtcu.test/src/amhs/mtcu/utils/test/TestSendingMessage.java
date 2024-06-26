/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.mt.Address;
import amhs.mt.IPMBuilder;
import amhs.mt.parameters.IPMParameter;
import amhs.mt.MtCommon;
import amhs.mt.MtSessionManager;
import amhs.mt.enums.Priority;
import amhs.mtcu.config.Config;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestSendingMessage {
    public static void main(String [] args) {
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");
        
        IPMParameter parameter = new IPMParameter();
        parameter.setAmhsPriority(Priority.SS);
        // parameter.setOriginator("/OU=/O=AFTN/PRMD=AFTNLAND-3/ADMD=ICAO/C=XX/");
        parameter.setOriginator("/OU=BCAAFTAA/O=AFTM/PRMD=AFTNLAND-3/ADMD=ICAO/C=XX/");
        parameter.addRecipient(new Address("/OU=BCAAFTAA/O=AFTN/PRMD=AFTNLAND-3/ADMD=ICAO/C=XX/"));
        parameter.setContent("HELLO");
        parameter.setAtsMessage(false);
        parameter.setMessageID(MtCommon.generateMessageId());
        
        MtSessionManager.open();
        MTMessage message = MtSessionManager.newMessage(X400_att.X400_MSG_MESSAGE);
        IPMBuilder.build(message, parameter);
        
        int result = MtSessionManager.deliver(message);
        String log = result == X400_att.X400_E_NOERROR ? "Delivery OK" : "Delivery error";
        System.out.println(log);
        MtSessionManager.close();
    }
}
