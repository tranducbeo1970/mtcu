/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.database.entities.MessageConversionLog;
import amhs.database.utils.gateway.ConversionLogDbUtil;
import java.util.Date;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestDBGateway {
    public static void main(String [] args) {
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        
        ConversionLogDbUtil dbUtil = new ConversionLogDbUtil();
        MessageConversionLog messageLog = new MessageConversionLog();
        messageLog.setAmhsMessageType(MessageConversionLog.MSG_TYPE_IPM);
        messageLog.setAttribute("AAAAAAAAAA: 1,\nBBBBBBB: 4");
        messageLog.setContent("HELLO");
        
        messageLog.setConvertedTime(new Date());
        messageLog.setDate("140425");
        messageLog.setFilingTime("140304");
        messageLog.setIpmId("1* [/PRMD=VV/ADMD=ICAO/C=XX/;alaptop.0390401-140425.112439]");
        messageLog.setMessageId("[/PRMD=VV/ADMD=ICAO/C=XX/;alaptop.0390401-140425.112439]");
        messageLog.setOrigin("/CN=VHHHZQZA/OU=VHHH/O=HKGCAD/PRMD=HONGKONG/ADMD=ICAO/C=XX/");
        messageLog.setPriority((short)1);
        messageLog.setRecipients("/OU=VVNBYFYX/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/,\n/OU=VVNBYFYX/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/");
        // messageLog.setReferenceId(0);
        messageLog.setStatus((short)1);
        messageLog.setSubject("SSSS");
        messageLog.setType(MessageConversionLog.TYPE_AFTN);
        
        
        
        for (int i = 0; i < 100000; i++) {
            long time1 = System.nanoTime();
            dbUtil.insert(messageLog);
            long time = System.nanoTime() - time1;
            System.out.println("OK in " + time + " nano second");
        }
        
        
        
    }
}
