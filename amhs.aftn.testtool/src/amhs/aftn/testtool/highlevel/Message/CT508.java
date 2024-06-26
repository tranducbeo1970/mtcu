/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.Message;
import amhs.aftn.testtool.highlevel.Message.gateway.Config;
import amhs.aftn.testtool.highlevel.Message.gateway.MtSessionManager;
import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;

/**
 *
 * @author andh
 */
public class CT508 {
    public static void main(String[] args) throws X400APIException {
        
        Config.load("message/accounts/gateway.config.xml");
        CommonUtils.configure("config/database.xml");
        String account = "message/accounts/AAAAMHAA.account.xml";
        String message = "message/CT508/CT508M01.aftn.xml";
        String report = "message/CT508/CT508M01.ndr.exp.xml";
        
        AFTNUtil.clean();
        
        Sending.sendAFTN(message);
      
        NormalMessage amhsMsg = X400MS.getMessage(account, 30);
        Out.print("Check AMHS message received", amhsMsg != null);
        
        Report rpt = (Report) XmlSerializer.deserialize(report, Report.class);
        
        System.out.println("SUBJECT ID: " + amhsMsg.getMessageId());
        
        rpt.setSubjectID(amhsMsg.getMessageId());
        System.out.println("ID :"+ amhsMsg.getMessageId());
        MtSessionManager.sendReport(rpt);
        
        Message aftnMsg = AFTNUtil.getMessage(20);
        Out.print("Check UNKNOWN MSG received", aftnMsg != null);
        
        XmlSerializer.serialize(Out.output + "\\UKNOWN_SVC.xml", aftnMsg);
      
    }
}
