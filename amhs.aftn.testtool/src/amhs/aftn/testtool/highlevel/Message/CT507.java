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
import javax.xml.bind.annotation.XmlSchema;

/**
 *
 * @author andh
 */
public class CT507 {
    public static void main(String[] args) throws X400APIException {
        
        CommonUtils.configure("config/database.xml");
        String account = "message/accounts/IUTAMHAA.account.xml";
        String message = "message/CT507/CT507M01.xml";
        
        AFTNUtil.clean();
        
        Sending.sendIPN(account, message);
      
        Message aftn = AFTNUtil.getMessage(10);
        Out.print("Check non ACK received", aftn == null);
        
        Report report = X400MS.gettingReport(account, 20);
        Out.print("Check REport", report != null);
        
        XmlSerializer.serialize(Out.output + "\\CT507M01.NDR.xml", report);
        
      
    }
}
