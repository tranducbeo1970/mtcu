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
public class CT410 {
    private static final String output = "D:\\Projects\\AMHS\\output";
    
    
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        
        testCase01();
    }

    public static void testCase01() throws X400APIException {
        String account = "message/accounts/IUTAMHAA.account.xml"; //"message/accounts/AAAAMHAZ.account.xml";
        String account1 = "message/accounts/ABAAMHAA.account.xml";
        String account2 = "message/accounts/ABAAMHAB.account.xml";
        String input = "message/CT410/CT410M01.xml";
        String input2 = "message/CT410/CT410M02.xml";
        String expected1 = "";
        String expected2 = "";
        
        
//        Out.printHeader("CT410M01");
//        X400MailUA.cleaningAllMessage(account);
//        X400MailUA.cleaningAllMessage(account1);
//        X400MailUA.cleaningAllMessage(account2);
        AFTNUtil.clean();
        
        Sending.send(account, input);
        Sending.send(account, input2);
        
        
        Report report = X400MailUA.gettingReport(account, 10);
        Out.print("Checking non report received", report == null);
    }
}
