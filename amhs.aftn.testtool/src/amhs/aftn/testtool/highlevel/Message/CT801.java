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
public class CT801 {

    public static void main(String[] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");

        String account = "message/accounts/AAAAMHAA.account.xml";
        String message = "message/CT801/CT801M01.aftn.xml";
        String message2 = "message/CT801/CT801M02.probe.xml";

        // Sending.sendProbe(account, message2);
        for (int i = 0; i < 100; i++) {
            Sending.sendAFTN(message, i);
        }

        // X400MS.deleteMessage(account);
        // AFTNUtil.clean();
//        test("CT401M01", account, message, expected);
//        
//        message = "message/CT401/CT401M02.xml";
//        expected = "message/CT401/CT401M02.aftn.exp.xml";
//        test("CT401M02", account, message, expected);
//        
//        message = "message/CT401/CT401M03.xml";
//        expected = "message/CT401/CT401M03.aftn.exp.xml";
//        test("CT401M03", account, message, expected);
//        
//        message = "message/CT401/CT401M04.xml";
//        expected = "message/CT401/CT401M04.aftn.exp.xml";
//        test("CT401M04", account, message, expected);
//        
//        message = "message/CT401/CT401M05.xml";
//        expected = "message/CT401/CT401M05.aftn.exp.xml";
//        test("CT401M05", account, message, expected);
        /*
         Sending.send(account, message);
        
         amhs.aftn.Message resultMsg = GettingAFTNMessage.getMessage(180);
        
         if (resultMsg == null) {
         System.out.println("CASE CT401M01 [X]");
         return;
         }
        
         amhs.aftn.Message expect = (amhs.aftn.Message) XmlSerializer.deserialize(expected, amhs.aftn.Message.class);
         if (!AssertUtil.equalAFTN(expect, resultMsg)) {
         System.out.println("CASE CT401M01 [X]");
         } else {
         System.out.println("CASE CT401M01 [V]");
         }
         */
    }

    private static void test(String caseNo, String account, String input, String expected) throws X400APIException {
        X400MS.deleteMessage(account);
        AFTNUtil.clean();
        System.out.println(String.format("\r\n\r\n\r\nCase %s \r\n----------------------------------------------------------------------------------", caseNo));
        Sending.send(account, input);
        amhs.aftn.Message resultMsg = AFTNUtil.getMessage(180);

        if (resultMsg == null) {
            Out.print("CASE " + caseNo, false);
            return;
        }

        amhs.aftn.Message expect = (amhs.aftn.Message) XmlSerializer.deserialize(expected, amhs.aftn.Message.class);
        boolean result = AssertUtil.equalAFTN(expect, resultMsg);
        Out.print("Case " + caseNo, result);
    }
}
