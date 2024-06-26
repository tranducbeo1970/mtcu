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
public class CT418 {
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        String account = "message/accounts/AAAAMHAZ.account.xml";
        test("CT418M01", account, "message/CT418/CT418M01.xml", "message/CT418/CT418M01.aftn.xml");
    }
    
    public static void test(String caseNo, String account, String input, String aftnMessage) throws X400APIException {
        
        Out.printHeader(caseNo);
        X400MailUA.cleaningAllMessage(account);
        AFTNUtil.clean();
        Out.cleanOutputFolder();
        
        Sending.send(account, input);
        
        List<amhs.aftn.Message> actuals = new ArrayList<>();
        
        amhs.aftn.Message actual = AFTNUtil.getMessage(20);
        if (actual == null) {
            Out.printResult(caseNo, false);
            return;
        }
        XmlSerializer.serialize(Out.output + "\\" + actual.getFilingTime() + "." + actual.getOriginator() + ".xml", actual);
        
        
        Sending.sendAFTN(aftnMessage);
        Report ipn = X400MailUA.gettingReport(account, 30);
        
        if (ipn == null) {
            Out.printResult(caseNo, false);
            return;
        }
        
        
        XmlSerializer.serialize(Out.output + "\\REPORT.xml", ipn);
        Out.printResult(caseNo, true);
    }
}
