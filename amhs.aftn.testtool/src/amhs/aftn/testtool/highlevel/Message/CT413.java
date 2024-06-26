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
public class CT413 {
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        String account = "message/accounts/AAAAMHAZ.account.xml";
        test("CT413M01", account, "message/CT413/CT413M01.xml", "message/CT413/CT413M01.aftn.exp.xml", "message/CT413/CT413M01.ndr.exp.xml");
    }
    
    public static void test(String caseNo, String account, String input, String expectedAFTN, String expectedAMHS) throws X400APIException {
        Out.printHeader(caseNo);
        X400MailUA.cleaningAllMessage(account);
        AFTNUtil.clean();
        Out.cleanOutputFolder();
        
        Sending.send(account, input);
        
        List<amhs.aftn.Message> actuals = new ArrayList<>();
        
        amhs.aftn.Message actual = AFTNUtil.getMessage(20);
        XmlSerializer.serialize(Out.output + "\\" + actual.getFilingTime() + "." + actual.getOriginator() + ".xml", actual);
        amhs.aftn.Message expected = (amhs.aftn.Message) XmlSerializer.deserialize(expectedAFTN, amhs.aftn.Message.class);
        boolean result = AssertUtil.equalAFTN(expected, actual);
        if (!result) return;

        Report report = X400MailUA.gettingReport(account, 10);
        XmlSerializer.serialize(Out.output + "\\" + report.getContentID() + ".xml", report);
        Report expectedReport = (Report) XmlSerializer.deserialize(expectedAMHS, Report.class);
        result = AssertUtil.equal(expectedReport, report);
        
        if (!result) return;
        Out.printResult(caseNo, result);
    }
    
   
}
