/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.X400APIException;

/**
 *
 * @author andh
 */
public class CT603 {
    public static void main(String[] args) throws X400APIException {
        
        String acc1 = "message/accounts/IUTAMHAA.account.xml";
        X400MS.deleteMessage(acc1);
        
        String msg01 = "message/CT603/CT603M01.xml";
        String msg02 = "message/CT603/CT603M02.xml";
        String msg03 = "message/CT603/CT603M03.xml";
        String msg04 = "message/CT603/CT603M04.xml";
        String msg05 = "message/CT603/CT603M05.xml";
        String msg06 = "message/CT603/CT603M06.xml";
        
        Sending.send(acc1, msg01);
        save(acc1);
        
        Sending.send(acc1, msg02);
        save(acc1);
        
        Sending.send(acc1, msg03);
        save(acc1);
        
        Sending.send(acc1, msg04);
        save(acc1);
        
        Sending.send(acc1, msg05);
        save(acc1);
        
        Sending.send(acc1, msg06);
        save(acc1);
    }
    
        
    private static void save(String account) throws X400APIException {
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        Report message = X400MS.gettingReport(account, 10);
        if  (message != null) {
            XmlSerializer.serialize(Out.output + "\\"+ acc.getName() + "." + message.getContentID() + ".xml", message);
        }
    }
}
