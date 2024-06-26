/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.Message.gateway.Config;
import amhs.aftn.testtool.highlevel.Message.gateway.MtSessionManager;
import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT604 {
    public static void main(String[] args) throws X400APIException {
        
        DOMConfigurator.configure("config/log.xml");
        
        String acc1 = "message/accounts/IUTAMHAA.account.xml";

        String msg01 = "message/CT604/CT604M01.xml";
        String msg02 = "message/CT604/CT604M02.xml";
        String msg03 = "message/CT604/CT604M03.xml";
        String msg04 = "message/CT604/CT604M04.xml";
        String msg05 = "message/CT604/CT604M05.xml";
        String msg06 = "message/CT604/CT604M06.xml";

        Sending.send(acc1, msg01);
        save(acc1);
        
        Sending.send(acc1, msg02);
        save(acc1);
        
        Sending.send(acc1, msg03);
        save(acc1);
        
        // Send via gateway
        Config.load("message/accounts/gateway.config.xml");
        MtSessionManager.send(msg04);
        MtSessionManager.send(msg05);
    }

    private static void save(String account) throws X400APIException {
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        Report message = X400MS.gettingReport(account, 10);
        if (message != null) {
            XmlSerializer.serialize(Out.output + "\\" + acc.getName() + "." + message.getContentID() + ".xml", message);
        }
    }
}
