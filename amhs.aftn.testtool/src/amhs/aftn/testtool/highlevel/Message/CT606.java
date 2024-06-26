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
public class CT606 {
    public static void main(String[] args) throws X400APIException {
        
        DOMConfigurator.configure("config/log.xml");
        Config.load("message/accounts/gateway.config.xml");
        
        String msg01 = "message/CT606/CT606M01.xml";
        String msg02 = "message/CT606/CT606M02.xml";
        String msg03 = "message/CT606/CT606M03.xml";
        String msg04 = "message/CT606/CT606M04.xml";
        String msg05 = "message/CT606/CT606M05.xml";
        
        MtSessionManager.send(msg01);
        MtSessionManager.send(msg02);
        MtSessionManager.send(msg03);
        MtSessionManager.send(msg04);
        MtSessionManager.send(msg05);
    }
}
