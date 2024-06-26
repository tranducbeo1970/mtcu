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
public class CT705 {

    public static void main(String[] args) throws X400APIException {

        DOMConfigurator.configure("config/log.xml");
        Config.load("message/accounts/gateway.config.xml");
        
        MtSessionManager.sendReport("message/CT705/CT705M01.ndr.xml");
        MtSessionManager.sendReport("message/CT705/CT705M02.ndr.xml");
        MtSessionManager.sendReport("message/CT705/CT705M03.ndr.xml");
        MtSessionManager.sendReport("message/CT705/CT705M04.ndr.xml");
        MtSessionManager.sendReport("message/CT705/CT705M05.ndr.xml");
        MtSessionManager.sendReport("message/CT705/CT705M06.ndr.xml");
    }
}
