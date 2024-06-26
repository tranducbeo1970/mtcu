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
public class CT502 {
    
    
    public static void main(String[] args) throws X400APIException {
        
        CommonUtils.configure("config/database.xml");
        String account = "message/accounts/IUTAMHAA.account.xml";
        
        CommonCase.testIPM01("CT502M01", account, "message/CT502/CT502M01.aftn.xml", "message/CT502/CT502M01.amhs.exp.xml");
        CommonCase.testIPM01("CT502M02", account, "message/CT502/CT502M02.aftn.xml", "message/CT502/CT502M02.amhs.exp.xml");
        CommonCase.testIPM01("CT502M03", account, "message/CT502/CT502M03.aftn.xml", "message/CT502/CT502M03.amhs.exp.xml");
        CommonCase.testIPM01("CT502M04", account, "message/CT502/CT502M04.aftn.xml", "message/CT502/CT502M04.amhs.exp.xml");
    }
    
    public static void save(int index) throws X400APIException {
        NormalMessage message = X400MS.getMessage("message/accounts/IUTAMHAA.account.xml", 30);
        if (message == null) return;
        XmlSerializer.serialize(Out.output + "\\MSG." + index + ".xml", message);
    }
}
