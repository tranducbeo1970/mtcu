/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT404 {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String [] args) throws X400APIException, Exception {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");

        String account = "message/accounts/AAAAMHAA.account.xml";
        X400MS.deleteMessage(account);
        // X400UA.gettingReport(account, 20);
        test("CT404M01", account, "message/CT404/CT404M01.xml", "message/CT404/CT404M01.aftn.exp.xml");
        testReport("CT404M02", account, "message/CT404/CT404M02.xml", "message/CT404/CT404M02.ndr.exp.xml");
//        Sending.send(account, "message/CT404/CT404M02.xml");
//        // X400UA.gettingReport(account);
//        
//        // Sending.send(account, "message/CT404/CT404M02.xml");
//        
//        Report report =  X400MailUA.gettingReport(account, 5);
//        // X400UA.gettingReport(account);
//        // X400UA.gettingReport(account);
//        // Sending.send(account, "message/CT404/CT404M02.xml");
    }
    
    private static void test(String caseNo, String account, String input, String expected) throws X400APIException, Exception {
        Out.printHeader(caseNo);
        X400MS.deleteMessage(account);
        AFTNUtil.clean();
        
        Sending.send(account, input);
        amhs.aftn.Message actual = AFTNUtil.getMessage(300);
        amhs.aftn.Message expect = (amhs.aftn.Message) XmlSerializer.deserialize(expected, amhs.aftn.Message.class);
        boolean result = AssertUtil.equalAFTN(expect, actual);
        
        Report report = X400MS.gettingReport(account, 10);
        
        Out.print("Checking non report received", report == null);
        result = result ? report == null : result;
        
        Out.printResult(caseNo, result);
    }
    
    private static void testReport(String caseNo, String account, String input, String expected) throws X400APIException, Exception {
        Out.printHeader(caseNo);
        X400MS.deleteMessage(account);
        AFTNUtil.clean();
        
        String messageID =  Sending.send(account, input);
        
        amhs.aftn.Message actual = AFTNUtil.getMessage(20);
        Out.print("Checking AMHS message is not converted", actual == null);
        
        Report report = X400MS.gettingReport(account, 30);
        Report expect = (Report) XmlSerializer.deserialize(expected, Report.class);
        expect.setSubjectID(messageID);
        
        boolean result = AssertUtil.equal(expect, report);
        Out.printResult(caseNo, result);
    }
  
}
