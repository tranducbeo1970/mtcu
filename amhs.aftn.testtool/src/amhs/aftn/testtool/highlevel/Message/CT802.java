/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT802 {
    public static void main(String[] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");

        String account = "message/accounts/AAAAMHAA.account.xml";
        Sending.send(account, "message/CT802/CT802M01.xml");
//        Sending.send(account, "message/CT802/CT802M02.xml");
//        Sending.send(account, "message/CT802/CT802M03.xml");
//        Sending.send(account, "message/CT802/CT802M04.xml");
//        Sending.send(account, "message/CT802/CT802M05.xml");
        
        String reAccount = "message/accounts/AAAAMHAA.account.xml";
        NormalMessage message = X400MS.getMessage(reAccount, 20);
        System.out.println("Extended: " + message.getAts().getExtended());
    }
}
