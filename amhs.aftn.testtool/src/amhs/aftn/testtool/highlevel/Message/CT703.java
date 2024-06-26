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
public class CT703 {

    public static void main(String[] args) throws X400APIException {

        DOMConfigurator.configure("config/log.xml");
        Config.load("message/accounts/gateway.config.xml");
        
        MtSessionManager.sendReport("message/CT703/CT703M01.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M02.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M03.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M04.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M05.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M06.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M07.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M08.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M09.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M10.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M11.ndr.xml");
        MtSessionManager.sendReport("message/CT703/CT703M12.ndr.xml");
        
    }
}
