/* 
 * Copyright (c) 2008-2012, Isode Limited, London, England.
 * All rights reserved.
 * 
 * Acquisition and use of this software and related materials for any
 * purpose requires a written licence agreement from Isode Limited,
 * or a written licence from an organisation licenced by Isode Limited
 * to grant such a licence.
 */
package amhs.aftn.testtool.highlevel.Message;

import static amhs.aftn.testtool.highlevel.Message.Sending.encode;
import static amhs.aftn.testtool.highlevel.Message.Sending.setInt;
import static amhs.aftn.testtool.highlevel.Message.Sending.setStr;
import static amhs.aftn.testtool.highlevel.Message.X400UA.print;
import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.ReceiveMsg;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import java.util.ArrayList;
import java.util.List;

/**
 * Test program that connects to either an MTA's P3 channel or to a P7 Message
 * Store, checks if a message is ready to be read, and if it is, reads it and
 * displays some values on the standard output.
 *
 */
public class X400MailUA {

    public static Report gettingReport(String account, int timeout) {
        try {
            
            print("Load account ");
            Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
            print(true);
            
            print("Connect to server ");
            P3BindSession connection = new P3BindSession(acc.getPresentationAddress(), acc.getOr(), acc.getPassword(), false);
            connection.bind();
            print(true);
            
            print("Getting message ");

            int status = connection.waitForNewMessages(timeout);
            if (status == X400_att.X400_E_NOERROR) {

                ReceiveMsg rm = new ReceiveMsg(connection);
                Report report = buildReport(rm);
                rm.finishWithMessage(0, 0);
                connection.unbind();
                print(report != null);
                return report;
            }
            
            print(false);
            if (status == X400_att.X400_E_TIMED_OUT) {
                System.out.println("Timed out - no new messages");
            } else if (status == X400_att.X400_E_NO_MESSAGE) {
                System.out.println("No more messages");
            } else {
                System.out.println("Failed to set the wait new - " + status);
            }
            connection.unbind();
            
        } catch (X400APIException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static NormalMessage gettingMessage(String account, int timeout) {
        try {
            
            print("Load account ");
            Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
            print(true);
            
            print("Connect to server ");
            P3BindSession connection = new P3BindSession(acc.getPresentationAddress(), acc.getOr(), acc.getPassword(), false);
            connection.bind();
            print(true);
            
            print("Getting message ");

            int status = connection.waitForNewMessages(timeout);
            if (status == X400_att.X400_E_NOERROR) {

                ReceiveMsg rm = new ReceiveMsg(connection);
                NormalMessage report = buildMessage(rm);
                rm.finishWithMessage(0, 0);
                connection.unbind();
                print(report != null);
                return report;
            }
            
            print(false);
            if (status == X400_att.X400_E_TIMED_OUT) {
                System.out.println("Timed out - no new messages");
            } else if (status == X400_att.X400_E_NO_MESSAGE) {
                System.out.println("No more messages");
            } else {
                System.out.println("Failed to set the wait new - " + status);
            }
            connection.unbind();
            
        } catch (X400APIException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
     public static IPNMessage gettingIPN(String account, int timeout) {
        try {
            
            print("Load account ");
            Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
            print(true);
            
            print("Connect to server ");
            P3BindSession connection = new P3BindSession(acc.getPresentationAddress(), acc.getOr(), acc.getPassword(), false);
            connection.bind();
            print(true);
            
            print("Getting message ");

            int status = connection.waitForNewMessages(timeout);
            if (status == X400_att.X400_E_NOERROR) {

                ReceiveMsg rm = new ReceiveMsg(connection);
                IPNMessage report = buildIPNMessage(rm);
                rm.finishWithMessage(0, 0);
                connection.unbind();
                print(report != null);
                return report;
            }
            
            print(false);
            if (status == X400_att.X400_E_TIMED_OUT) {
                System.out.println("Timed out - no new messages");
            } else if (status == X400_att.X400_E_NO_MESSAGE) {
                System.out.println("No more messages");
            } else {
                System.out.println("Failed to set the wait new - " + status);
            }
            connection.unbind();
            
        } catch (X400APIException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void cleaningAllMessage(String account) {
        try {
            
            Out.print("Load account ");
            Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
            Out.print(true);
            
            Out.print("Connect to server ");
            P3BindSession connection = new P3BindSession(acc.getPresentationAddress(), acc.getOr(), acc.getPassword(), false);
            connection.bind();
            Out.print(true);
            
            Out.print("Getting all message ");
            int timeout = 5;

            do {
                int status = connection.waitForNewMessages(timeout);
                if (status == X400_att.X400_E_NOERROR) {
                    ReceiveMsg rm = new ReceiveMsg(connection);
                    System.out.print("[]");
                    rm.finishWithMessage(0, 0);
                } else {
                    Out.print(true);
                    if (status == X400_att.X400_E_TIMED_OUT) {
                        System.out.println("Timed out - no new messages");
                    } else if (status == X400_att.X400_E_NO_MESSAGE) {
                        System.out.println("No more messages");
                    } else {
                        System.out.println("Failed to set the wait new - " + status);
                    }
                    break;
                }
            } while (true);
            
            connection.unbind();
            
        } catch (X400APIException ex) {
            ex.printStackTrace();
        }
    }

    public static Report buildReport(ReceiveMsg rm) throws X400APIException {
        if (rm.GetType() != X400_att.X400_MSG_REPORT) {
            print(false);
            System.out.println("Receing a message but not a report");
            return null;
        }

        Report report = new Report();
        report.setOriginator(Common.getStr(rm, X400_att.X400_S_OR_ADDRESS));
        report.setSubjectID(Common.getStr(rm, X400_att.X400_S_SUBJECT_IDENTIFIER));
        report.setContentID(Common.getStr(rm, X400_att.X400_S_CONTENT_IDENTIFIER));
        report.setOriginEIT(Common.getStr(rm, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES));
        report.setContentType(Common.getInt(rm, X400_att.X400_N_CONTENT_TYPE));
            
        List<ReportRecipient> recipients = getReportRecipient(rm);
        report.setRecipients(recipients);
        return report;
    }
    
    public static NormalMessage buildMessage(ReceiveMsg x400msg) throws X400APIException {
        if (x400msg.GetType() != X400_att.X400_MSG_MESSAGE) {
            print(false);
            System.out.println("Receing a message but not a message");
            return null;
        }
        
        NormalMessage message = new NormalMessage();
        message.setOriginator(Common.getStr(x400msg, X400_att.X400_S_OR_ADDRESS));
        message.setAlternativeRecipientAllow(Common.getInt(x400msg, X400_att.X400_S_OR_ADDRESS));
        message.setSubject(x400msg.getSubject());
        message.setMessageId(x400msg.getMessageIdentifier());
        message.setContentId(Common.getStr(x400msg, X400_att.X400_S_CONTENT_IDENTIFIER));
        message.setOriginEIT(Common.getStr(x400msg, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES));
        message.setGlobalDomainID(Common.getStr(x400msg, X400_att.X400_S_GLOBAL_DOMAIN_ID));
        message.setPriority(Common.getInt(x400msg, X400_att.X400_N_PRIORITY));
        message.setContentType(Common.getInt(x400msg, X400_att.X400_N_CONTENT_TYPE));
        message.setDisclosure(Common.getInt(x400msg, X400_att.X400_N_DISCLOSURE));
        message.setConversionProhibited(Common.getInt(x400msg, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED));
        message.setAlternativeRecipientAllow(Common.getInt(x400msg, X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED));
        message.setReassignmentProhibited(Common.getInt(x400msg, X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED));
        message.setConversionWithLossProhibited(Common.getInt(x400msg, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED));
        message.setDlExpasionProhibited(Common.getInt(x400msg, X400_att.X400_N_DL_EXPANSION_PROHIBITED));
        message.setDlExpasionProhibited(Common.getInt(x400msg, X400_att.X400_N_DL_EXPANSION_PROHIBITED));
        message.setEnvelopeRecipient(getRecipient(x400msg, X400_att.X400_RECIP_ENVELOPE));
        message.setRecipients(getRecipient(x400msg, X400_att.X400_RECIP_PRIMARY));
        message.setCopyRecipient(getRecipient(x400msg, X400_att.X400_RECIP_CC));
        message.setBlindCopyRecipient(getRecipient(x400msg, X400_att.X400_RECIP_BCC));
        
        ATS ats = new ATS();
        ats.setExtended(Common.getInt(x400msg, AMHS_att.ATS_N_EXTENDED));
        ats.setPriority(Common.getStr(x400msg, AMHS_att.ATS_S_PRIORITY_INDICATOR));
        ats.setFilingTime(Common.getStr(x400msg, AMHS_att.ATS_S_FILING_TIME));
        ats.setOptionalHeading(Common.getStr(x400msg, AMHS_att.ATS_S_OPTIONAL_HEADING_INFO));
        ats.setText(Common.getStr(x400msg, AMHS_att.ATS_S_TEXT));
        ats.setEohMode(Common.getInt(x400msg, AMHS_att.ATS_EOH_MODE));
        message.setAts(ats);
        
        return message;
    }
    
    public static IPNMessage buildIPNMessage(ReceiveMsg x400msg) {
        if (x400msg.GetType() != X400_att.X400_MSG_MESSAGE) {
            print(false);
            System.out.println("Receing a message but not a IPN");
            return null;
        }
        
        Integer ipn = Common.getInt(x400msg, X400_att.X400_N_IS_IPN);
        if (ipn == null || ipn == 0) {
            print(false);
            System.out.println("Receing a message but not a IPN");
            return null;
        }
        IPNMessage message = new IPNMessage();
        message.setAckMode(Common.getInt(x400msg, X400_att.X400_N_ACK_MODE));
        message.setSubjectIPM(Common.getStr(x400msg, X400_att.X400_S_SUBJECT_IPM));
        message.setConversionEIT(Common.getStr(x400msg, X400_att.X400_S_CONVERSION_EITS));
        message.setNonReceiptReason(Common.getInt(x400msg, X400_att.X400_N_NON_RECEIPT_REASON));
        message.setDiscardReason(Common.getInt(x400msg, X400_att.X400_N_DISCARD_REASON));
        message.setAutoForwardComment(Common.getStr(x400msg, X400_att.X400_S_AUTOFORWARD_COMMENT));
        message.setReceiptTime(Common.getStr(x400msg, X400_att.X400_S_RECEIPT_TIME));
        message.setSupplementInfo(Common.getStr(x400msg, X400_att.X400_S_SUPP_RECEIPT_INFO));
        // message.setRecipients((RecipientList) getRecipient(x400msg, X400_att.X400_RECIP_ENVELOPE));
        return message;
    }
    
    private static List<ReportRecipient> getReportRecipient(ReceiveMsg rm) {
        
        int index = 0;
        Recip recip = new Recip();
        List<ReportRecipient> recipients = new ArrayList<>();
        for (index = 1;; index++) {
            int status = com.isode.x400api.X400ms.x400_ms_recipget(rm, X400_att.X400_RECIP_REPORT, index, recip);
            if (status == X400_att.X400_E_NO_RECIP || status != X400_att.X400_E_NOERROR) {
                break;
            }
            ReportRecipient recipient = new ReportRecipient();
            recipient.setOr(Common.getStr(recip, X400_att.X400_S_OR_ADDRESS));
            recipient.setNonDeliveryReasonCode(Common.getInt(recip, X400_att.X400_N_NON_DELIVERY_REASON));
            recipient.setNonDeliveryDiagnosticCode(Common.getInt(recip, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC));
            recipient.setSupplementInfo(Common.getStr(recip, X400_att.X400_S_SUPPLEMENTARY_INFO));
            recipient.setUserType(Common.getInt(recip, X400_att.X400_N_TYPE_OF_USER));
            recipient.setArrivalTime(Common.getStr(recip, X400_att.X400_S_ARRIVAL_TIME));
            recipient.setDeliverTime(Common.getStr(recip, X400_att.X400_S_MESSAGE_DELIVERY_TIME));
            recipients.add(recipient);
        }
        return recipients;
    }
    
    private static List<Recipient> getRecipient(ReceiveMsg rm, int type) {
        List<Recipient> recipients = new ArrayList<>();
        
        int index = 0;
        Recip recip = new Recip();
        for (index = 1;; index++) {
            int status = com.isode.x400api.X400ms.x400_ms_recipget(rm, type, index, recip);
            if (status == X400_att.X400_E_NO_RECIP || status != X400_att.X400_E_NOERROR) {
                break;
            }

            Recipient recipient = new Recipient();
            recipient.setOr(Common.getStr(recip, X400_att.X400_S_OR_ADDRESS));
            recipient.setNo(Common.getInt(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER));

            switch (type) {

                case X400_att.X400_RECIP_ENVELOPE:
                    recipient.setMtaReportRequest(Common.getInt(recip, X400_att.X400_N_MTA_REPORT_REQUEST));
                    recipient.setOriginReportRequest(Common.getInt(recip, X400_att.X400_N_REPORT_REQUEST));
                    recipients.add(recipient);
                    break;
                
                default:
                    recipient.setReceiptNotification(Common.getInt(recip, X400_att.X400_N_NOTIFICATION_REQUEST));
                    recipients.add(recipient);

            }

        }
        return recipients;
    }

}
