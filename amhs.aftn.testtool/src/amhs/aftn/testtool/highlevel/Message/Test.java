/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.AMHS_att;
import java.text.DecimalFormat;

/**
 *
 * @author andh
 */
public class Test {
    public static void main(String [] args) {
        NormalMessage message = new NormalMessage();
        message.setAlternativeRecipientAllow(0);
        message.setContentId("contentID");
        message.setConversionProhibited(0);
        message.setConversionWithLossProhibited(0);
        message.setDisclosure(0);
        message.setDlExpasionProhibited(0);
        message.setGlobalDomainID("/PRMD=IUTLAND/ADMD=ICAO/C=XX/");
        message.setIpmId("ipm");
        message.setMessageId("Message id");
        message.setOriginEIT("IA5-Text");
        message.setPriority(2);
        message.setReassignmentProhibited(0);
        message.setSubject("Hello");
        
        ATS ats = new ATS();
        ats.setEohMode(AMHS_att.ATS_EOH_MODE_NONE);
        ats.setExtended(0);
        ats.setFilingTime("220912");
        ats.setOptionalHeading(null);
        ats.setText("ATS TEXTING");
        message.setAts(ats);
        
        String aphabet = "ABCDEFGHIJKLMNOPQRST";
        DecimalFormat format = new DecimalFormat("000");
        int max = 512;
        for (int i = 0; i < max; i++) {
            String value = format.format(i);
            int i1 = Integer.parseInt(String.valueOf(value.charAt(0)));
            int i2 = Integer.parseInt(String.valueOf(value.charAt(1)));
            int i3 = Integer.parseInt(String.valueOf(value.charAt(2)));
            String address = String.format("/CN=BAAAF%s%s%s/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/", aphabet.charAt(i1), aphabet.charAt(i2), aphabet.charAt(i3));

            Recipient recipient = new Recipient();
            recipient.setOr(address);
            recipient.setReceiptNotification(X400Msg.IPN_NON_RECEIPT_NOTIFICATION);
            recipient.setReportRequest(X400Msg.DR_Request.DR_NO_REPORT);
            message.addRecipient(recipient);
        }
        
        
        
        Body body = new Body();
        body.setCharset("1 6 100");
        body.setType("General");
        body.setText("BODY TEXT");
        message.addBody(body);
        
        XmlSerializer.serialize("D:\\amhs.message.xml", message);
        System.out.println("OK");
        
    }
}
