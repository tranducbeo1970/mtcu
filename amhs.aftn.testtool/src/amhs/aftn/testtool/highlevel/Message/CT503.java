/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.Message;
import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;

/**
 *
 * @author andh
 */
public class CT503 {
    
    
    public static void main(String[] args) throws X400APIException {
        
        CommonUtils.configure("config/database.xml");
        String caseNo = "CT503M01";
        String account = "message/accounts/IUTAMHAA.account.xml";
        String expectedFile = "message/CT503/CT503M01.aftn.exp.xml";
        String input = "message/CT503/CT503M01.aftn.xml";
        
        Out.printHeader(caseNo);
        AFTNUtil.clean();
        
        Sending.sendAFTN(input);
        NormalMessage message = X400MS.getMessage(account, 30);
        Out.print("Checking non-amhs-message received", message ==null);
        
        Message actual = AFTNUtil.getMessage(10);
        XmlSerializer.serialize(Out.output + "\\CT50301.xml", actual);
        Message expected = (Message) XmlSerializer.deserialize(expectedFile, Message.class);
        
        boolean result = AssertUtil.equalAFTN(expected, actual);
        Out.printResult(caseNo , result);
    }
    
   
}
