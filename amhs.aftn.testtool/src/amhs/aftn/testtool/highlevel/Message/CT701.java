/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.Message.gateway.Config;
import amhs.aftn.testtool.highlevel.Message.gateway.MtSessionManager;
import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT701 {

    public static void main(String[] args) throws X400APIException {

        DOMConfigurator.configure("config/log.xml");
        Config.load("message/accounts/gateway.config.xml");
        
        MtSessionManager.sendReport("message/CT701/CT701M01.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M02.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M03.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M04.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M05.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M06.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M07.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M08.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M09.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M10.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M11.ndr.xml");
        MtSessionManager.sendReport("message/CT701/CT701M12.ndr.xml");
        
    }
}
