/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.X400Msg;

/**
 *
 * @author andh
 */
public class IPNMessageTest {
    public static void main(String [] args) {
        IPNMessage message = new IPNMessage();
        message.setAckMode(0);
        message.setNonReceiptReason(1);
        message.setDiscardReason(1);
        message.setSubjectIPM("ABC");
        message.setSupplementInfo("Supplement info");
        Recipient recipient = new Recipient();
        recipient.setOr("/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/");
        recipient.setReceiptNotification(X400Msg.IPN_NON_RECEIPT_NOTIFICATION);
        recipient.setReportRequest(X400Msg.DR_Request.DR_DELIVERY_REPORT);
        message.getRecipients().add(recipient);
        message.getRecipients().add(recipient);
        
        XmlSerializer.serialize("D:\\ipn.xml", message);
    }
}
