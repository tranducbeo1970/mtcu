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
public class CT408 {
    public static void main(String [] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");
        
        String account = "message/accounts/AAAAMHAZ.account.xml";
        CommonCase.test("CT408M01", account, "message/CT408/CT408M01.xml", "message/CT408/CT408M01.aftn.exp.xml");
        CommonCase.testReport("CT408M02", account, "message/CT408/CT408M02.xml", "message/CT408/CT408M02.ndr.exp.xml");
    }
    
    
}
