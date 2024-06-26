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
public class CT405 {
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        
        String account = "message/accounts/AAAAMHAA.account.xml";
        String message = "message/CT405/CT405M01.xml";
        String expected = "message/CT405/CT405M01.aftn.exp.xml";
        
        /*
        Sending.send(account, message);
        Sending.send(account, "message/CT405/CT405M02.xml");
        Sending.send(account, "message/CT405/CT405M03.xml");
        Sending.send(account, "message/CT405/CT405M04.xml");
        Sending.send(account, "message/CT405/CT405M05.xml");
        Sending.send(account, "message/CT405/CT405M06.xml");
        Sending.send(account, "message/CT405/CT405M07.xml");
        */
        
        CommonCase.testReport("CT405M01", account, "message/CT405/CT405M01.xml", "message/CT405/CT405M01.ndr.exp.xml");
        CommonCase.testReport("CT405M02", account, "message/CT405/CT405M02.xml", "message/CT405/CT405M02.ndr.exp.xml");
        CommonCase.testReport("CT405M03", account, "message/CT405/CT405M03.xml", "message/CT405/CT405M03.ndr.exp.xml");
        CommonCase.testReport("CT405M04", account, "message/CT405/CT405M04.xml", "message/CT405/CT405M04.ndr.exp.xml");
        CommonCase.testReport("CT405M05", account, "message/CT405/CT405M05.xml", "message/CT405/CT405M05.ndr.exp.xml");
        CommonCase.testReport("CT405M06", account, "message/CT405/CT405M06.xml", "message/CT405/CT405M06.ndr.exp.xml");
        CommonCase.test("CT405M07", account, "message/CT405/CT405M07.xml", "message/CT405/CT405M07.aftn.exp.xml");
       
    }
    
    
}
