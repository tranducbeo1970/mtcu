/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.X400Msg;
import java.text.DecimalFormat;

/**
 *
 * @author andh
 */
public class SaveProbeSample {
    public static void main(String [] args)  {
        
        
        // Probe p2 = XmlSerializer.deserialize(null, null)
        gen512(667) ;
    }
    
    private static void simpleGen() {
        Probe message = new Probe();
        message.setAlternativeRecipientAllow(0);
        message.setContentId("CT421M01");
        message.setConversionProhibited(0);
        message.setConversionWithLossProhibited(0);
        message.setDisclosure(0);
        message.setDlExpasionProhibited(0);
        message.setOriginEIT("IA5-Text");
        message.setPriority(2);
        message.setReassignmentProhibited(0);
        message.setContentLength(1800);
        
        Recipient recipient = new Recipient();
        recipient.setOr("/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/");
        recipient.setReceiptNotification(X400Msg.IPN_NON_RECEIPT_NOTIFICATION);
        recipient.setReportRequest(X400Msg.DR_Request.DR_DELIVERY_REPORT);
        message.addRecipient(recipient);
        
        XmlSerializer.serialize("D:\\amhs.probe.xml", message);
        System.out.println("Saving OK");
    }
    
    private static void gen512(int maximum) {
        Probe message = new Probe();
        message.setAlternativeRecipientAllow(0);
        message.setContentId("CT421M01");
        message.setConversionProhibited(0);
        message.setConversionWithLossProhibited(0);
        message.setDisclosure(0);
        message.setDlExpasionProhibited(0);
        message.setOriginEIT("IA5-Text");
        message.setPriority(2);
        message.setReassignmentProhibited(0);
        message.setContentLength(1800);
        
        for (int i = 0; i < maximum; i++) {
            String value = getStrFromNumber(i);
            
            Recipient recipient = new Recipient();
            recipient.setOr(String.format("/CN=BAAAF%s/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/", value));
            recipient.setReceiptNotification(X400Msg.IPN_NON_RECEIPT_NOTIFICATION);
            recipient.setReportRequest(X400Msg.DR_Request.DR_DELIVERY_REPORT);
            message.addRecipient(recipient);
        }

        XmlSerializer.serialize("D:\\amhs512.probe.xml", message);
        System.out.println("Saving OK");
    }
    
    private static String getStrFromNumber(int number) {
        String pattern = "ABCEFRHIJK";
        DecimalFormat format = new DecimalFormat("000");
        String n = format.format(number);
        int i0 = Integer.valueOf(String.valueOf(n.charAt(0)));
        int i1 = Integer.valueOf(String.valueOf(n.charAt(1)));
        int i2 = Integer.valueOf(String.valueOf(n.charAt(2)));
        String returnValue = String.format("%s%s%s", pattern.charAt(i0), pattern.charAt(i1), pattern.charAt(i2));
        return returnValue;
    }
}
