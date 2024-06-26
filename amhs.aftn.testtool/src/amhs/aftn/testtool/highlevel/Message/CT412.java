/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT412 {
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        
        String account = "message/accounts/AAAAMHAZ.account.xml";
        test("CT412M01", account, "message/CT412/CT412M01.xml");
        testReport("CT412M02", account, "message/CT412/CT412M02.xml", "message/CT412/CT412M02.ndr.exp.xml");
    }
    
    public static void test(String caseNo, String account, String input) throws X400APIException {
        Out.printHeader(caseNo);
        X400MailUA.cleaningAllMessage(account);
        AFTNUtil.clean();
        
        Sending.send(account, input);
        
        List<amhs.aftn.Message> actuals = new ArrayList<>();
        
        int index = 1;
        
        while (true){
            amhs.aftn.Message actual = AFTNUtil.getMessage(30);
            if (actual == null) break;
            actuals.add(actual);
            XmlSerializer.serialize(Out.output + "\\" + actual.getFilingTime() + "." + actual.getOriginator() + "." + index + ".xml", actual);
            index++;
        }
        
        boolean result = actuals.size() == 25;
        Out.print("Counting hit", result);
        Report report = X400MailUA.gettingReport(account, 10);
        
        Out.print("Checking non report received", report == null);
        result = result ? report == null : result;
        
        Out.printResult(caseNo, result);
    }
    
    public static void testReport(String caseNo, String account, String input, String expected) throws X400APIException {
        Out.printHeader(caseNo);
        X400MailUA.cleaningAllMessage(account);
        AFTNUtil.clean();

        String messageID = Sending.send(account, input);

        amhs.aftn.Message actual = AFTNUtil.getMessage(20);
        Out.print("Checking AMHS message is not converted", actual == null);

        Report report = X400MailUA.gettingReport(account, 30);
        
        if(report != null) {
            XmlSerializer.serialize(Out.output + "\\" + report.getContentID() + "report.xml", report);
        }
        
        Report expect = (Report) XmlSerializer.deserialize(expected, Report.class);
        expect.setSubjectID(messageID);

        boolean result = AssertUtil.equal(expect, report);
        Out.printResult(caseNo, result);
    }
}
