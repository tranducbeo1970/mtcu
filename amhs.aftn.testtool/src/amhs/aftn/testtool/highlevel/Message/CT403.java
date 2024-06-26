/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.Message.gateway.Config;
import amhs.aftn.testtool.highlevel.Message.gateway.MtSessionManager;
import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT403 {
    
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        Config.load("message/accounts/gateway.config.xml");
        
        String account = "message/accounts/AAAAMHAZ.account.xml";
     
        // X400UA.deleteMessage(account);
        send() ;
        // X400UA.gettingReport(account);
//        testNoReport("CT403M01", account, "message/CT403/CT403M01.gw.xml", "message/CT403/CT403M01.aftn.exp.xml");
        // testReport("CT403M02", account, "message/CT403/CT403M02.gw.xml", "message/CT403/CT403M02.aftn.exp.xml", "message/CT403/CT403M02.ndr.exp.xml");
//        testReport("CT403M03", account, "message/CT403/CT403M03.gw.xml", "message/CT403/CT403M03.aftn.exp.xml", "message/CT403/CT403M03.ndr.exp.xml");
//        testNoReport("CT402M04", account, "message/CT403/CT403M04.gw.xml", "message/CT403/CT403M04.aftn.exp.xml");
//        testReport("CT403M05", account, "message/CT403/CT403M05.gw.xml", "message/CT403/CT403M05.aftn.exp.xml", "message/CT403/CT403M05.ndr.exp.xml");
//        testReport("CT403M06", account, "message/CT403/CT403M06.gw.xml", "message/CT403/CT403M06.aftn.exp.xml", "message/CT403/CT403M06.ndr.exp.xml");
//        testReport("CT403M07", account, "message/CT403/CT403M07.gw.xml", "message/CT403/CT403M07.aftn.exp.xml", "message/CT403/CT403M07.ndr.exp.xml");
//        testReport("CT403M08", account, "message/CT403/CT403M08.gw.xml", "message/CT403/CT403M08.aftn.exp.xml", "message/CT403/CT403M08.ndr.exp.xml");

       
    }
    
    private static void send() {
        MtSessionManager.send("message/CT403/CT403M01.gw.xml");
        MtSessionManager.send("message/CT403/CT403M02.gw.xml");
        MtSessionManager.send("message/CT403/CT403M03.gw.xml");
        MtSessionManager.send("message/CT403/CT403M04.gw.xml");
        MtSessionManager.send("message/CT403/CT403M05.gw.xml");
        MtSessionManager.send("message/CT403/CT403M06.gw.xml");
        MtSessionManager.send("message/CT403/CT403M07.gw.xml");
        MtSessionManager.send("message/CT403/CT403M08.gw.xml");
    }
    
    private static void testNoReport(String caseNo, String account, String input, String expected) throws X400APIException, Exception {
        printHeader(caseNo);
        X400UA.deleteMessage(account);
        AFTNUtil.clean();
        
        MtSessionManager.send(input);
        amhs.aftn.Message actual = AFTNUtil.getMessage(30);
        amhs.aftn.Message expect = (amhs.aftn.Message) XmlSerializer.deserialize(expected, amhs.aftn.Message.class);
        boolean result = AssertUtil.equalAFTN(expect, actual);
        
        Report report = X400UA.gettingReport(account, 10);
        
        Out.print("Varify not receiving any report", report == null);
        if(result && report != null) {
            result = false;
        }
        
        printResult(caseNo, result);
    }
    
    private static void testReport(String caseNo, String account, String input, String expected, String deliverReport) throws X400APIException, Exception {
        printHeader(caseNo);
        X400UA.deleteMessage(account);
        AFTNUtil.clean();
        
        MtSessionManager.send(input);
        
        amhs.aftn.Message actualAFTN = AFTNUtil.getMessage(20);
        amhs.aftn.Message expectAFTN = (amhs.aftn.Message) XmlSerializer.deserialize(expected, amhs.aftn.Message.class);
        boolean result = AssertUtil.equalAFTN(expectAFTN, actualAFTN);
        
        Report report = X400UA.gettingReport(account);
        Report expect = (Report) XmlSerializer.deserialize(deliverReport, Report.class);
        result = result ? AssertUtil.equal(expect, report) : result;
        printResult(caseNo, result);
    }
    
    public static void printHeader(String caseNo) {
        System.out.println();
        System.out.println();
        System.out.println(caseNo);
        System.out.println("----------------------------------------------------------------------------------");
    }
    
    public static void printResult(String caseNo, boolean result) {
        System.out.println("----------------------------------------------------------------------------------");
        Out.print("case " + caseNo, result);
    }
}
