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
public class CommonCase {

    public static void testReport(String caseNo, String account, String input, String expected) throws X400APIException {
        Out.printHeader(caseNo);
        X400MS.deleteMessage(account);
        AFTNUtil.clean();

        String messageID = Sending.send(account, input);

        amhs.aftn.Message actual = AFTNUtil.getMessage(20);
        Out.print("Checking AMHS message is not converted", actual == null);

        Report report = X400MS.gettingReport(account, 30);
        Report expect = (Report) XmlSerializer.deserialize(expected, Report.class);
        expect.setSubjectID(messageID);

        boolean result = AssertUtil.equal(expect, report);
        Out.printResult(caseNo, result);
    }
    
    public static void test(String caseNo, String account, String input, String expected) throws X400APIException {
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
    
    public static void testIPM01(String caseNo, String account, String input, String expected) throws X400APIException {
        Out.printHeader(caseNo);
        X400MS.deleteMessage(account);
        AFTNUtil.clean();
        
        Sending.sendAFTN(input);
        NormalMessage actual = X400MS.getMessage(account, 10);
        XmlSerializer.serialize(Out.output + "\\" + caseNo + ".xml", actual);
        NormalMessage expect = (NormalMessage) XmlSerializer.deserialize(expected, NormalMessage.class);
        boolean result = AssertUtil.equal(expect, actual);
        Out.printResult(caseNo, result);
    }
    
    
}
