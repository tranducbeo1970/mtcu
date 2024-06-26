/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.Message.gateway.Config;
import amhs.aftn.testtool.highlevel.Message.gateway.MtSessionManager;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT425 {
    public static void main(String[] args) throws X400APIException {
        
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        Config.load("message/accounts/gateway.config.xml");
        
        MtSessionManager.send("message/CT425/CT425M02.gw.xml");
        
//        String account = "message/accounts/IUTAMHAA.account.xml";
//        String message = "message/CT425/CT425M01.xml";
//        Sending.send(account, message);

//        message = "message/CT425/CT425M02.xml";
//        Sending.send(account, message);
//
//        message = "message/CT425/CT425M03.xml";
//        Sending.send(account, message);
//
//        message = "message/CT425/CT425M04.xml";
//        Sending.send(account, message);
    }
}
