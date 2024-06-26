package amhs.aftn.testtool.highlevel.Message;

/*  Copyright (c) 2008, Isode Limited, London, England.
 *  All rights reserved.
 *                                                                       
 *  Acquisition and use of this software and related materials for any      
 *  purpose requires a written licence agreement from Isode Limited,
 *  or a written licence from an organisation licenced by Isode Limited
 *  to grant such a licence.
 *
 */
import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.*;
import java.util.ArrayList;
import java.util.List;

public class X400MSSRV {

    private static int msgCount;
    private static int reportCount;
    
    public static void main(String[] args) throws X400APIException {

        msgCount = 0;
        reportCount = 0;
        String account = "message/accounts/AAAAMHAA.account.xml";
        getMessage(account, 30);
    }

    private static Session open(Account account) {
        print("Open connection");
        int type = 0;	// 0 = P7; 1 = P3 
        Session session = new Session();
        int status = X400ms.x400_ms_open(type, account.getOr(), "", account.getPassword(), account.getMailbox(), session);
        if (status != X400_att.X400_E_NOERROR) {
            print(false);
            return null;
        }
        print(true);
        
        // turn on trace level logging
        print("Set configuration ");
        status = com.isode.x400api.X400ms.x400_ms_setstrdefault(session, X400_att.X400_S_LOG_CONFIGURATION_FILE, "x400api.xml", -1);
        if (status != X400_att.X400_E_NOERROR) {
            print(false);
            System.out.println("x400_ms_setstrdefault failed " + status);
        }
        print(true);
        return session;
    }

    private static void close(Session session) {
        print("Close connection ");
        int close_status = com.isode.x400api.X400ms.x400_ms_close(session);
        if (close_status != X400_att.X400_E_NOERROR) {
            print(false);
            System.out.println("x400_ms_close failed " + close_status);
            return;
        }
        print(true);
    }

    public static Report gettingReport(Session session) {
        print("Getting AMHS report ");
        int status;
        int seqn = 0;

        MSMessage message = new MSMessage();
        Report report = null;
        while (true) {
            status = com.isode.x400api.X400ms.x400_ms_msggetstart(session, seqn, message);
            if (status == X400_att.X400_E_NO_MESSAGE) {
                status = com.isode.x400api.X400ms.x400_ms_waitnew(session, 180);
                if (status == X400_att.X400_E_TIMED_OUT ) continue;
            }
            
            if (status != X400_att.X400_E_NOERROR) {
                print(false);
                System.out.println("Getting message fail" + status);
                break;
            }

            // check what we got back
            int type = message.GetType();
            if (type != X400_att.X400_MSG_REPORT) {
                com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
                continue;
            }

            report = new Report();
            
            report.setOriginator(getStr(message, X400_att.X400_S_OR_ADDRESS));
            report.setSubjectID(getStr(message, X400_att.X400_S_SUBJECT_IDENTIFIER));
            report.setContentID(getStr(message, X400_att.X400_S_CONTENT_IDENTIFIER));
            report.setOriginEIT(getStr(message, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES));
            report.setContentType(getInt(message, X400_att.X400_N_CONTENT_TYPE));

            List<ReportRecipient> recipients = getReportRecipient(message);
            report.setRecipients(recipients);

            com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
            com.isode.x400api.X400ms.x400_ms_msggetfinish(message, 0, 0);
            break;
        }

        print(report != null);
        return report;
    }
    
    public static Report gettingReport(Session session, int timeout) {
        print("Getting message ");
        int status;
        int seqn = 0;

        MSMessage message = new MSMessage();
        Report report = null;
        while (true) {
            status = com.isode.x400api.X400ms.x400_ms_msggetstart(session, seqn, message);
            if (status == X400_att.X400_E_NO_MESSAGE) {
                status = com.isode.x400api.X400ms.x400_ms_waitnew(session, timeout);
                if (status == X400_att.X400_E_TIMED_OUT ) break;
            }
            
            if (status != X400_att.X400_E_NOERROR) {
                print(false);
                System.out.println("Getting message fail" + status);
                break;
            }

            // check what we got back
            int type = message.GetType();
            if (type != X400_att.X400_MSG_REPORT) {
                com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
                continue;
            }

            report = new Report();
            
            report.setOriginator(getStr(message, X400_att.X400_S_OR_ADDRESS));
            report.setSubjectID(getStr(message, X400_att.X400_S_SUBJECT_IDENTIFIER));
            report.setContentID(getStr(message, X400_att.X400_S_CONTENT_IDENTIFIER));
            report.setOriginEIT(getStr(message, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES));
            report.setContentType(getInt(message, X400_att.X400_N_CONTENT_TYPE));

            List<ReportRecipient> recipients = getReportRecipient(message);
            report.setRecipients(recipients);

            com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
            com.isode.x400api.X400ms.x400_ms_msggetfinish(message, 0, 0);
            break;
        }

        print(report != null);
        return report;
    }
    
    public static Report gettingReport(String account) {
        print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        print(true);
        
        Session session = open(acc);
        if (session == null) return null;
        
        Report report  = gettingReport(session);
        close(session);
        return report;
    }
    
    public static Report gettingReport(String account, int timeout) {
        print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        print(true);
        
        Session session = open(acc);
        if (session == null) return null;
        
        Report report  = gettingReport(session, timeout);
        close(session);
        return report;
    }
    
    public static void getMessage(String account, int timeout) throws X400APIException {
        print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        print(true);
        
        Session session = open(acc);
        if (session == null) return ;
        
        print("Getting message ");
        int status;
        int seqn = 0;

        MSMessage message = new MSMessage();
        NormalMessage report = null;
        while (true) {
            
            status = com.isode.x400api.X400ms.x400_ms_msggetstart(session, seqn, message);
            
            if (status == X400_att.X400_E_NO_MESSAGE) {
                status = com.isode.x400api.X400ms.x400_ms_waitnew(session, timeout);
                if (status == X400_att.X400_E_TIMED_OUT ) continue;
            }
            
            if (status != X400_att.X400_E_NOERROR) {
                print(false);
                System.out.println("Getting message fail" + status);
                break;
            }

            // check what we got back
            int type = message.GetType();
            switch(type) {
                case X400_att.X400_MSG_MESSAGE:
                    msgCount++;
                    System.out.println("MSG: " + msgCount);
                    break;

                case X400_att.X400_MSG_REPORT:
                    msgCount++;
                    System.out.println("RPT: " + reportCount);
                    break;
                default:
                    System.out.println("MSG: " + msgCount);
                    System.out.println("RPT: " + reportCount);
                    break;
            }
            com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
            com.isode.x400api.X400ms.x400_ms_msggetfinish(message, 0, 0);
        }

        print(report != null);
        close(session);
    }
    
    public static NormalMessage buildMessage(MSMessage x400msg) throws X400APIException {
        if (x400msg.GetType() != X400_att.X400_MSG_MESSAGE) {
            print(false);
            System.out.println("Receing a message but not a message");
            return null;
        }
        
        NormalMessage message = new NormalMessage();
        message.setOriginator(Common.getStr(x400msg, X400_att.X400_S_OR_ADDRESS));
        message.setAlternativeRecipientAllow(Common.getInt(x400msg, X400_att.X400_S_OR_ADDRESS));
        message.setSubject(Common.getStr(x400msg, X400_att.X400_S_SUBJECT));
        message.setMessageId(Common.getStr(x400msg, X400_att.X400_S_MESSAGE_IDENTIFIER));
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
    
    private static List<Recipient> getRecipient(MSMessage rm, int type) {
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
    
    public static void deleteMessage(Session session) {
        print("Delete message ");
        int status;
        int seqn = 0;

        MSMessage message = new MSMessage();
        Report report = null;
        while (true) {
            status = com.isode.x400api.X400ms.x400_ms_msggetstart(session, seqn, message);
            if (status == X400_att.X400_E_NO_MESSAGE) {
                break;
            }
            
            if (status != X400_att.X400_E_NOERROR) {
                print(false);
                System.out.println("Getting message fail" + status);
                break;
            }

            com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
            com.isode.x400api.X400ms.x400_ms_msggetfinish(message, 0, 0);
        }

        print(true);
    }
    
    public static void deleteMessage(String account) {
        print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        print(true);
        
        Session session = open(acc);
        if (session == null) return;
                
        deleteMessage(session);
        close(session);
    }
    
    private static Integer getInt(MSMessage message, int attribute) {
        int status = com.isode.x400api.X400ms.x400_ms_msggetintparam(message, attribute);
        if (status != X400_att.X400_E_NOERROR) {
            System.out.println("get attribute fail " + status);
            return null;
        }

        return message.GetIntValue();
    }

    private static String getStr(MSMessage message, int attribute) {
        StringBuffer ret_value = new StringBuffer();
        int status = com.isode.x400api.X400ms.x400_ms_msggetstrparam(message, attribute, ret_value);
        if (status == X400_att.X400_E_NO_VALUE || status != X400_att.X400_E_NOERROR) {
            return null;
        }
        return ret_value.toString();
    }

    private static String getStr(Recip recip_obj, int attribute) {
        StringBuffer ret_value = new StringBuffer();
        int status = com.isode.x400api.X400ms.x400_ms_recipgetstrparam(recip_obj, attribute, ret_value);
        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }
        return ret_value.toString();
    }

    private static Integer getInt(Recip recip_obj, int paramtype) {
        int status = com.isode.x400api.X400ms.x400_ms_recipgetintparam(recip_obj, paramtype);
        // int status = com.isode.x400api.X400.x400_recipgetintparam(recip_obj, paramtype);
        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }
        return recip_obj.GetIntValue();

    }

    private static List<ReportRecipient> getReportRecipient(MSMessage message) {
        int index = 0;
        Recip recip = new Recip();
        List<ReportRecipient> recipients = new ArrayList<>();
        for (index = 1; ; index++) {
            int status = com.isode.x400api.X400ms.x400_ms_recipget(message, X400_att.X400_RECIP_REPORT, index, recip);
            if (status == X400_att.X400_E_NO_RECIP || status != X400_att.X400_E_NOERROR) {
                break;
            }
            
            ReportRecipient recipient = new ReportRecipient();
            recipient.setOr(getStr(recip, X400_att.X400_S_OR_ADDRESS));
            recipient.setNonDeliveryReasonCode(getInt(recip, X400_att.X400_N_NON_DELIVERY_REASON));
            recipient.setNonDeliveryDiagnosticCode(getInt(recip, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC));
            recipient.setSupplementInfo(getStr(recip, X400_att.X400_S_SUPPLEMENTARY_INFO));
            recipient.setUserType(getInt(recip, X400_att.X400_N_TYPE_OF_USER));
            recipient.setArrivalTime(getStr(recip, X400_att.X400_S_ARRIVAL_TIME));
            recipient.setDeliverTime(getStr(recip, X400_att.X400_S_MESSAGE_DELIVERY_TIME));
            recipients.add(recipient);
        }
        return recipients;

    }
    
    public static void print(String action, boolean result ) {
        String format = String.format("%-60s%s", action, result ? "[V]" : "[X]");
        System.out.println(format);
    }
    
    public static void print(String action) {
        String format = String.format("%-60s", action);
        System.out.print(format);
    }
    
    public static void print(boolean result) {
        System.out.println(result ? "[V]" : "[X]");
    }
}
