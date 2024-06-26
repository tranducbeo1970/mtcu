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
public class CT402 {
    
    public static void main(String [] args) throws X400APIException, Exception {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        
        String account = "message/accounts/AAAAMHAA.account.xml";
        
        test("CT402M01", account, "message/CT402/CT402M01.xml", "message/CT402/CT402M01.aftn.exp.xml");
        test("CT402M02", account, "message/CT402/CT402M02.xml", "message/CT402/CT402M02.aftn.exp.xml");
        testReport("CT402M03", account, "message/CT402/CT402M03.xml", "message/CT402/CT402M03.ndr.exp.xml");
        test("CT402M04", account, "message/CT402/CT402M04.xml", "message/CT402/CT402M04.aftn.exp.xml");
        test("CT402M05", account, "message/CT402/CT402M05.xml", "message/CT402/CT402M05.aftn.exp.xml");
        testReport("CT402M06", account, "message/CT402/CT402M06.xml", "message/CT402/CT402M06.ndr.exp.xml");
       
    }
    
    private static void test(String caseNo, String account, String input, String expected) throws X400APIException, Exception {
        Out.printHeader(caseNo);
        X400MS.deleteMessage(account);
        AFTNUtil.clean();
        
        Sending.send(account, input);
        amhs.aftn.Message actual = AFTNUtil.getMessage(300);
        amhs.aftn.Message expect = (amhs.aftn.Message) XmlSerializer.deserialize(expected, amhs.aftn.Message.class);
        boolean result = AssertUtil.equalAFTN(expect, actual);
        Out.printResult("case " + caseNo, result);
    }
    
    private static void testReport(String caseNo, String account, String input, String expected) throws X400APIException, Exception {
         Out.printHeader(caseNo);
        X400MS.deleteMessage(account);
        AFTNUtil.clean();

        String messageID = Sending.send(account, input);

        amhs.aftn.Message actual = AFTNUtil.getMessage(20);
        Out.print("Checking AMHS message isnot converted", actual == null);

        Report report = X400MS.gettingReport(account);
        Report expect = (Report) XmlSerializer.deserialize(expected, Report.class);
        expect.setSubjectID(messageID);

        boolean result = AssertUtil.equal(expect, report);
        Out.printResult("case " + caseNo, result);
    }
}
