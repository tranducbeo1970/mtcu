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
public class CT423 {

    public static void main(String[] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        Config.load("message/accounts/gateway.config.xml");

        String account = "message/accounts/IUTAMHAA.account.xml";
        Sending.send(account, "message/CT423/CT423M01.xml");
        
        // MtSessionManager.send("message/CT423/CT423M02.gw.xml");
        // Sending.send(account, "message/CT423/CT423M02.xml");
        Sending.send(account, "message/CT423/CT423M03.xml");
        Sending.send(account, "message/CT423/CT423M04.xml");
        Sending.send(account, "message/CT423/CT423M05.xml");
        Sending.send(account, "message/CT423/CT423M06.xml");
        Sending.send(account, "message/CT423/CT423M07.xml");
        Sending.send(account, "message/CT423/CT423M08.xml");
        Sending.send(account, "message/CT423/CT423M09.xml");
        Sending.send(account, "message/CT423/CT423M10.xml");

    }
}
