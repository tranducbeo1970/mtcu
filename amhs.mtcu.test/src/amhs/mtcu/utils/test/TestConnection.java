/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.database.entities.AddressTrans;
import amhs.database.entities.MessageConversionLog;
import amhs.database.utils.gateway.AddTransDbUtils;
import amhs.database.utils.gateway.ConversionLogDbUtil;
import amhs.database.utils.gateway.DeliveriedMessageDBUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestConnection {
    public static void main(String [] args) throws InterruptedException {
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//        
//        AddTransDbUtils db = new AddTransDbUtils();
//        while (true ) {
//            Thread.sleep(2000);
//            List<AddressTrans> addresses =  db.getByAFTN();
//            System.out.println(format.format(new Date()) + ": Get " + (addresses == null ? "NA" : addresses.size()) + " records.");
//        }
        
        
        ConversionLogDbUtil logging = new ConversionLogDbUtil();
        MessageConversionLog log = logging.getReferenceMessage("081851", "VVNBXXXX");
        
        System.out.println(log == null ? "NULL" : "DU LIEU");
        
        /*
        MessageConversionLog log = new MessageConversionLog();
        log.setDate("140522");
        logging.insert(log);
        System.out.println("INSERT OK : [ " + log.getId() + "]");
        */
        
//        MessageConversionLog log = logging.getReferenceMessageByIPM("");
//        assert log == null : "HEHE";
//        
//        log = logging.getReferenceMessageByIPM("1503795320.40629*");
//        assert log != null : "Cannot find message";
//        System.out.println("ID: " + log.getIpmId());
        
        
        // long id = logging.getLastInserted();
        // System.out.println("INSERT ID: " + id);
        
//        DeliveriedMessageDBUtils db = new DeliveriedMessageDBUtils();
//        System.out.println(db.getAFTNAddressIndicator());
        
        
    }
}
