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
import com.isode.x400api.*;
import java.util.ArrayList;
import java.util.List;

public class X400UA {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static void main(String[] args) {
        String account = "message/accounts/AAAAMHAZ.account.xml";

        print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        print(true);
        
        Session session = open(acc);
        if (session == null) return;
        // deleteMessage(session);
        Report report  = gettingReport(session);
        close(session);
        
//        if (report == null) {
//            System.out.println("No REPORT");
//            return;
//        }
//         System.out.println("Report found");
    }

    private static Session open(Account account) {
        print("Open connection");
        int type = 2;	// 0 = P7; 1 = P3; 2= FUCKING
        Session session = new Session();
        session.SetSummarizeOnBind(true);
        int status = com.isode.x400api.X400ms.x400_ms_open(2, account.getOr(), null, account.getPassword(), account.getPresentationAddress(), session);
        if (status != X400_att.X400_E_NOERROR) {
            print(false);
            return null;
        }
        print(true);
        
        print("Waiting ... ");
        status = com.isode.x400api.X400ms.x400_ms_waitnew(session, 60);
        if (status == X400_att.X400_E_TIMED_OUT) {
            print(true);
        } else if (status != X400_att.X400_E_NOERROR) {
            print(false);
            System.out.println("x400_ms_wait failed " + status);
        } else {
            print(true);
            System.out.println("Message count: " + session.GetNumMsgs());
        }
        
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
                // status = com.isode.x400api.X400ms.x400_ms_waitnew(session, 20);
                // if (status == X400_att.X400_E_TIMED_OUT ) break;
                break;
            }
            
            if (status != X400_att.X400_E_NOERROR) {
                print(false);
                System.out.println("Getting message fail" + status);
                break;
            }

            // check what we got back
            int type = message.GetType();
            if (type != X400_att.X400_MSG_REPORT) {
//                com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
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

//            com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
            com.isode.x400api.X400ms.x400_ms_msggetfinish(message, 0, 0);
            seqn++;
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
    
    public static void deleteMessage(Session session) {
        print("Delete message ");
        int status;
        int seqn = 0;

        MSMessage message = new MSMessage();
        Report report = null;
        while (true) {
            status = com.isode.x400api.X400ms.x400_ms_msggetstart(session, seqn, message);
            if (status == X400_att.X400_E_NO_MESSAGE || status != X400_att.X400_E_NOERROR) {
                break;
            }
            
            // com.isode.x400api.X400ms.x400_ms_msgdel(message, 0);
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
        String format = String.format("%-60s%s", action, result ? ANSI_GREEN + "[V]" + ANSI_RESET : ANSI_RED + "[X]" + ANSI_RESET);
        System.out.println(format);
    }
    
    public static void print(String action) {
        String format = String.format("%-60s", action);
        System.out.print(format);
    }
    
    public static void print(boolean result) {
        System.out.println(result ? ANSI_GREEN + "[V]" + ANSI_RESET : ANSI_RED + "[X]" + ANSI_RESET);
    }
}
