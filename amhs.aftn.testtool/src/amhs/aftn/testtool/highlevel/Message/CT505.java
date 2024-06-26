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
public class CT505 {
    public static void main(String[] args) throws X400APIException {
        
        CommonUtils.configure("config/database.xml");
        Sending.sendAFTN("message/CT505/CT505M01.xml");
        AFTNUtil.clean();
        
        Message aftnMsg = AFTNUtil.getMessage(30);
        XmlSerializer.serialize(Out.output + "\\CT50501.IPN.AFTN.xml", aftnMsg);
        Out.print("Checking ACK received", aftnMsg !=null);
    }
}
