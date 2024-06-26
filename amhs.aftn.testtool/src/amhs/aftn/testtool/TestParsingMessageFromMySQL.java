/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool;

import amhs.aftn.ITA2Message;
import amhs.aftn.Message;
import amhs.database.CommonUtils;
import amhs.database.dao.GatewayInDao;
import amhs.database.entities.GatewayIn;
import java.util.List;

/**
 *
 * @author andh
 */
public class TestParsingMessageFromMySQL {
    public static void main(String [] args) throws Exception {
        // DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        
        GatewayInDao dbUtil = new GatewayInDao();
        List<GatewayIn> gwins =  dbUtil.getGatewayInMessages(10);
        ITA2Message.setTrace(true);
        
        for (GatewayIn gwin : gwins) {
            Message message = ITA2Message.parsing(gwin.getText());
        }
    }
}
