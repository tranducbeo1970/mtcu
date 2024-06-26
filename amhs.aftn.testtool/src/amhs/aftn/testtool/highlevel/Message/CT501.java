/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;

/**
 *
 * @author andh
 */
public class CT501 {
    
    public static void main(String[] args) throws X400APIException {
        
        CommonUtils.configure("config/database.xml");
        
        String account = "message/accounts/IUTAMHAA.account.xml";
        
        test("CT501M01", account, "message/CT501/CT501M01.aftn.xml", "message/CT501/CT501M01.amhs.exp.xml");
        test("CT501M02", account, "message/CT501/CT501M02.aftn.xml", "message/CT501/CT501M02.amhs.exp.xml");
        test("CT501M03", account, "message/CT501/CT501M03.aftn.xml", "message/CT501/CT501M03.amhs.exp.xml");
        test("CT501M04", account, "message/CT501/CT501M04.aftn.xml", "message/CT501/CT501M04.amhs.exp.xml");
        test("CT501M05", account, "message/CT501/CT501M05.aftn.xml", "message/CT501/CT501M05.amhs.exp.xml");
        
    }
    
    public static void test(String caseNo, String account, String input, String expected) throws X400APIException {
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
