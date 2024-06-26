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
public class CT509 {
    
    private static String account = "message/accounts/IUTAMHAA.account.xml";
    public static void main(String[] args) throws X400APIException {
        
        Config.load("message/accounts/gateway.config.xml");
        CommonUtils.configure("config/database.xml");
        
        String message = "message/CT508/CT508M01.aftn.xml";
        String report = "message/CT508/CT508M01.ndr.exp.xml";
        
        AFTNUtil.clean();
        
        test("message/CT509/CT509M01.aftn.xml", "message/CT509/CT509M01.ndr.xml");
        test("message/CT509/CT509M02.aftn.xml", "message/CT509/CT509M02.ndr.xml");
        test("message/CT509/CT509M03.aftn.xml", "message/CT509/CT509M03.ndr.xml");
      
    }
    
    private static void test(String aftn, String report) throws X400APIException {
        Sending.sendAFTN(aftn);
      
        NormalMessage amhsMsg = X400MS.getMessage(account, 30);
        Out.print("Check AMHS message received", amhsMsg != null);
        
        Report rpt = (Report) XmlSerializer.deserialize(report, Report.class);
        if(rpt.getSubjectID() == null || rpt.getSubjectID().isEmpty()) rpt.setSubjectID(amhsMsg.getMessageId());
        MtSessionManager.sendReport(rpt);
        
        Message aftnMsg = AFTNUtil.getMessage(20);
        Out.print("Check UKN MSG isnot received", aftnMsg == null);
    }
}
