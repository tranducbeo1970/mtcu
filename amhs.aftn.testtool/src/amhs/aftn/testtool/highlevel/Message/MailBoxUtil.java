/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.P7BindSession;
import com.isode.x400.highlevel.ReceiveMsg;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import java.util.List;

/**
 *
 * @author andh
 */
public class MailBoxUtil {

    public static void main(String[] args) {
        String account = "message/accounts/AAAAMHAA.account.xml";
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        getReport(acc);
    }

    public static Report getReport(Account account) {

        try {
            P7BindSession connection = new P7BindSession(account.getMailbox(), account.getOr(), account.getPassword());
            connection.bind();

            // Since the bind was successful, try to read the messages from the Message Store
            int num_of_msgs = connection.getRefreshNumberOfMessages();
            if (num_of_msgs > 0) {
                for (int i = 1; i <= num_of_msgs; i++) {
                    ReceiveMsg rm = connection.receiveNextAvailableMessage();
                    if (rm.GetType() != X400_att.X400_MSG_REPORT) {
                        continue;
                    }
                    getReport(rm);
                    // return null;
                    // connection.deleteMessage(rm);
                }
            }

            // Now wait 10 seconds for new messages to be available
            int wait = 100;
            int status = connection.waitForNewMessages(wait);

            switch (status) {
                case X400_att.X400_E_NOERROR:
                    System.out.println("\nFound a new message, reading it");
                    ReceiveMsg rm = new ReceiveMsg(connection);
                    getReport(rm);
                    break;
                case X400_att.X400_E_TIMED_OUT:
                    //System.out.println("Timed out - no new messages");
                    break;
                case X400_att.X400_E_NO_MESSAGE:
                    System.out.println("No more messages");
                    break;
                default:
                    System.out.println("Failed to set the wait new - " + status);
            }

            // Unbind cleanly from the Message Store
            connection.unbind();
        } catch (X400APIException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Report getReport(ReceiveMsg rm) throws X400APIException {
        String contentID = rm.getStringParam(X400_att.X400_S_CONTENT_IDENTIFIER);
        String subjectID = rm.getStringParam(X400_att.X400_S_SUBJECT_IDENTIFIER);
        String originEIT = rm.getStringParam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES);
        Integer contentType = rm.getIntParam(X400_att.X400_N_CONTENT_TYPE);
        // System.out.println("ContentID: " + contentID);
        // System.out.println("SubjectID: " + subjectID);
        // System.out.println("EIT: " + originEIT);
        // System.out.println("Content Type: " + contentType);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("From: " + rm.getOriginatorORAddress());
        List<com.isode.x400.highlevel.Recipient> recips = rm.getToRecipients();
        System.out.println("Recipient: " + recips.size());
        for (com.isode.x400.highlevel.Recipient r : recips) {
            System.out.println("OR: " + r.getORAddress());
            System.out.println("Type: " + r.GetType());
            Integer reason = getInt(r, X400_att.X400_N_NON_DELIVERY_REASON);
            System.out.println("Reason: " + reason);
            
            reason = getInt(r, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC);
            System.out.println("Diagnostic: " + reason);
            System.out.println(rm.getReportContentAsText());
        }
        return null;
    }

    private static String getStr(ReceiveMsg rm, int attribute) throws X400APIException {
        String contentID = rm.getStringParam(X400_att.X400_S_CONTENT_IDENTIFIER);
        String subjectID = rm.getStringParam(X400_att.X400_S_SUBJECT_IDENTIFIER);
        String originEIT = rm.getStringParam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES);
        Integer contentType = rm.getIntParam(X400_att.X400_N_CONTENT_TYPE);
        System.out.println("ContentID" + contentID);
        System.out.println("SubjectID" + subjectID);
        System.out.println("EIT" + originEIT);
        System.out.println("Content Type" + contentType);

        
        List<com.isode.x400.highlevel.Recipient> recips = rm.getToRecipients();
        return null;
    }
    
    private static Integer getInt(com.isode.x400.highlevel.Recipient rm, int attribute) throws X400APIException {
        int err = com.isode.x400api.X400.x400_recipgetintparam((Recip) rm, attribute);
        System.out.println("ERR: " + err);
        if (err == X400_att.X400_E_NOERROR) {
            return ((Recip) rm).GetIntValue();
        }
        return null;
    }
}
