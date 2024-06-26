/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT406 {
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        
        String account = "message/accounts/AAAAMHAZ.account.xml";
        CommonCase.testReport("CT406M01", account, "message/CT406/CT406M01.xml", "message/CT406/CT406M01.ndr.exp.xml");
    }
    
    
}
