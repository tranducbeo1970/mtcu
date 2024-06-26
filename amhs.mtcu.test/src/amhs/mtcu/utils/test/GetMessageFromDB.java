/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.aftn.ITA2Message;
import amhs.aftn.Message;
import amhs.database.CommonUtils;
import amhs.database.entities.MessageConversionLog;
import amhs.database.utils.gateway.ConversionLogDbUtil;
import amhs.mtcu.config.Config;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class GetMessageFromDB {
    
    private static ConversionLogDbUtil dbUtil = new ConversionLogDbUtil();
    
    public static void main(String [] args) {
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");
        
        showMessage(454074);
        showMessage(454071);
        showMessage(454047);
        showMessage(454035);
        showMessage(454026);
        showMessage(454008);
        showMessage(452946);
        showMessage(452940);
        showMessage(452928);
        showMessage(452922);

        showMessage(454056);
        showMessage(452655);
        showMessage(452646);
        showMessage(452637);
        showMessage(452592);
        showMessage(452577);
    }
    
    private static void showMessage(long id) {
        MessageConversionLog log = dbUtil.get(id);
        String message = log.getContent().replace("\r", "[r]").replace("\n", "[n]").replace("\u0007","[b]");
        System.out.println(message);
        
        ITA2Message.setTrace(true);
        Message msg = ITA2Message.parsing(log.getContent());
        System.out.println(msg);
    }
}
