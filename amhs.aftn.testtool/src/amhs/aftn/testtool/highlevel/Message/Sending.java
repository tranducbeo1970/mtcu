/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.dao.GatewayInDao;
import amhs.database.entities.GatewayIn;
import com.isode.x400.highlevel.Bodypart;
import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author andh
 */
public class Sending {

        
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static void main(String[] args) throws X400APIException {
        String account = "message/accounts/IUTAMHAA.account.xml";
        // String message = "message/CT424/CT424M01.xml";
        // String message = "message/CT424/CT424M02.xml";
        // String message = "message/CT424/CT424M03.xml";
        String message = "message/CT424/CT424M04.xml";
        send(account, message);
    }
    
    public static void sendAFTN(String message) throws Exception {
        Out.print("Load message " + message);
        amhs.aftn.Message msg = (amhs.aftn.Message) XmlSerializer.deserialize(message, amhs.aftn.Message.class);
        Out.print(true);
        
        GatewayIn gwin = new GatewayIn();
        
        StringBuilder builder = new StringBuilder();
        for (String address: msg.getAddresses()) {
            builder.append(address).append(" ");
        }
        gwin.setAddress(builder.toString().trim());
        gwin.setCpa("A");
        
        String content = buildMessage(msg);
        gwin.setText(content);
        gwin.setTime(new Date());
        
        short priority = getPriotiryInteger(msg.getPriority());
        gwin.setPriority(priority);
        gwin.setSource("REJ");
        
        GatewayInDao dbUtil = new GatewayInDao();
        dbUtil.insert(gwin);
        
        Out.print("Deliver message " + msg.getCircleId(), true);
    }
    
    public static void sendAFTN(String message, int index) throws Exception {
        DecimalFormat format = new DecimalFormat("0000");
        
        Out.print("Load message " + message);
        amhs.aftn.Message msg = (amhs.aftn.Message) XmlSerializer.deserialize(message, amhs.aftn.Message.class);
        Out.print(true);
        
        GatewayIn gwin = new GatewayIn();
        
        StringBuilder builder = new StringBuilder();
        for (String address: msg.getAddresses()) {
            builder.append(address).append(" ");
        }
        gwin.setAddress(builder.toString().trim());
        gwin.setCpa("A");
        
        msg.setCircleId(String.format(msg.getCircleId(), format.format(index)));
        
        String content = buildMessage(msg);
        gwin.setText(content);
        gwin.setTime(new Date());
        
        short priority = getPriotiryInteger(msg.getPriority());
        gwin.setPriority(priority);
        gwin.setSource("REJ");
        
        GatewayInDao dbUtil = new GatewayInDao();
        dbUtil.insert(gwin);
        
        Out.print("Deliver message " + msg.getCircleId(), true);
    }
    
    private static short getPriotiryInteger(String priority) {
        switch (priority) {
            case "SS":
                return 0;
            case "DD":
                return 1;
            case "FF":
                return 2;
            case "GG":
                return 3;
            default:
                return 4;
        }
    }
    
    private static String buildMessage(amhs.aftn.Message message) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (String address: message.getAddresses()) {
            if (index > 0) {
                builder.append(" ");
            }
            index++;
            builder.append(address).append(" ");
            if (index == 7) {
                builder.append("\n");
                index = 0;
            }
        }
        
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("ZCZC %s\r\n", message.getCircleId()));
        messageBuilder.append(String.format("%s %s\r\n", message.getPriority(), builder.toString().trim()));
        messageBuilder.append(String.format("%s %s %s\r\n", message.getFilingTime(), message.getOriginator(), message.getAdditionInfo()));
        messageBuilder.append(String.format("%s",decode(message.getText())));
        messageBuilder.append("\r\nNNNN");
        return messageBuilder.toString();
    }
    
    public static void sendIPN(String account, String message) throws X400APIException {

        System.out.print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        System.out.println("[O]");

        System.out.print("Load message ");
        IPNMessage msg = (IPNMessage) XmlSerializer.deserialize(message, IPNMessage.class);
        System.out.println("[O]");

        P3BindSession session = open(acc);
        X400Msg x400msg = new X400Msg(session);
        setInt(x400msg, X400_att.X400_N_IS_IPN, 1);
        // setStr(x400msg, X400_att.X400_S_CONTENT_IDENTIFIER, msg.getContentID());
        setStr(x400msg, X400_att.X400_S_SUBJECT_IPM, msg.getSubjectIPM());
        setStr(x400msg, X400_att.X400_S_CONVERSION_EITS, msg.getConversionEIT());
        setInt(x400msg, X400_att.X400_N_NON_RECEIPT_REASON, msg.getNonReceiptReason());
        setInt(x400msg, X400_att.X400_N_DISCARD_REASON, msg.getDiscardReason());
        setStr(x400msg, X400_att.X400_S_AUTOFORWARD_COMMENT, msg.getAutoForwardComment());
        setStr(x400msg, X400_att.X400_S_RECEIPT_TIME, msg.getReceiptTime());
        setInt(x400msg, X400_att.X400_N_ACK_MODE, msg.getAckMode());
        setStr(x400msg, X400_att.X400_S_SUPP_RECEIPT_INFO, msg.getSupplementInfo());
        for (Recipient recip : msg.getRecipients()) {
            x400msg.setTo(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
        }
        x400msg.sendMsg(session);
        System.out.print("Deliver IPN message [O] ");
        close(session);
    }
    
    public static String send(String account, String message) throws X400APIException {

        Out.print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        Out.print(true);

        Out.print("Load message ");
        NormalMessage msg = (NormalMessage) XmlSerializer.deserialize(message, NormalMessage.class);
        Out.print(true);

        P3BindSession session = open(acc);
        X400Msg x400msg = new X400Msg(session);
        setStr(x400msg, X400_att.X400_S_SUBJECT, msg.getSubject());

        // setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, conentType);
        setStr(x400msg, X400_att.X400_S_MESSAGE_IDENTIFIER, msg.getMessageId());
        setStr(x400msg, X400_att.X400_S_CONTENT_IDENTIFIER, msg.getContentId());
        setStr(x400msg, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, msg.getOriginEIT());
        setStr(x400msg, X400_att.X400_S_GLOBAL_DOMAIN_ID, msg.getGlobalDomainID());
        setInt(x400msg, X400_att.X400_N_PRIORITY, msg.getPriority());
        setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, msg.getContentType());
        setInt(x400msg, X400_att.X400_N_DISCLOSURE, msg.getDisclosure());
        setInt(x400msg, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, msg.getConversionProhibited());
        setInt(x400msg, X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED, msg.getAlternativeRecipientAllow());
        setInt(x400msg, X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED, msg.getReassignmentProhibited());
        setInt(x400msg, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, msg.getConversionWithLossProhibited());
        setInt(x400msg, X400_att.X400_N_DL_EXPANSION_PROHIBITED, msg.getDlExpasionProhibited());
        setStr(x400msg, X400_att.X400_S_OR_ADDRESS, msg.getOriginator());
        // x400msg.setFrom(msg.getOriginator());

        ATS ats = msg.getAts();
        if (ats != null) {
            setInt(x400msg, AMHS_att.ATS_N_EXTENDED, ats.getExtended());
            setStr(x400msg, AMHS_att.ATS_S_PRIORITY_INDICATOR, ats.getPriority());
            setStr(x400msg, AMHS_att.ATS_S_FILING_TIME, ats.getFilingTime());
            setStr(x400msg, AMHS_att.ATS_S_OPTIONAL_HEADING_INFO, ats.getOptionalHeading());
            setStr(x400msg, AMHS_att.ATS_S_TEXT, encode(ats.getText()));
            setInt(x400msg, AMHS_att.ATS_EOH_MODE, ats.getEohMode());
        }
        
        for (Recipient recip : msg.getRecipients()) {
            x400msg.setTo(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
        }
        
        for (Recipient recip : msg.getHeadingRecipient()) {
            x400msg.setIPMHeadingPrimaryRecipient(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
        }
        
        for (Recipient recip : msg.getCopyRecipient()) {
            x400msg.setCc(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
        }
        
        for (Recipient recip : msg.getBlindCopyRecipient()) {
            x400msg.setBcc(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
        }
        
        Bodypart bodyart ;
        for (Body body : msg.getBodies()) {
            switch(body.getType()) {
                case "General":
                    bodyart = new Bodypart(Bodypart.Bodypart_Type.BODYPART_GENERAL_TEXT);
                    if (body.getCharset() != null) {
                        bodyart.setStringParam(X400_att.X400_S_GENERAL_TEXT_CHARSETS, body.getCharset());
                    }
                    bodyart.setStringParam(X400_att.X400_S_BODY_DATA, decode(body.getText()));
                    x400msg.addBodypart(bodyart);
                    break;
                    
                case "Ia5-Text" :
                    bodyart = new Bodypart(Bodypart.Bodypart_Type.BODYPART_IA5_TEXT);
                    if (body.getCharset() != null) {
                        bodyart.setStringParam(X400_att.X400_S_GENERAL_TEXT_CHARSETS, body.getCharset());
                    }
                    bodyart.setStringParam(X400_att.X400_S_BODY_DATA, decode(body.getText()));
                    x400msg.addBodypart(bodyart);
                    break;
            }
        }

        Out.print("Deliver message " + msg.getContentId());
        x400msg.sendMsg(session);
        
        Out.print(true);
        close(session);
        
        return x400msg.getMessageIdentifier();
    }
    
    public static String send2(String account, String message) throws X400APIException {

        Out.print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        Out.print(true);

        Out.print("Load message ");
        NormalMessage msg = (NormalMessage) XmlSerializer.deserialize(message, NormalMessage.class);
        Out.print(true);

        P3BindSession session = open(acc);
        X400Msg x400msg = new X400Msg(session);
        setStr(x400msg, X400_att.X400_S_SUBJECT, msg.getSubject());

        // setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, conentType);
        setStr(x400msg, X400_att.X400_S_MESSAGE_IDENTIFIER, msg.getMessageId());
        setStr(x400msg, X400_att.X400_S_CONTENT_IDENTIFIER, msg.getContentId());
        setStr(x400msg, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, msg.getOriginEIT());
        setStr(x400msg, X400_att.X400_S_GLOBAL_DOMAIN_ID, msg.getGlobalDomainID());
        setInt(x400msg, X400_att.X400_N_PRIORITY, msg.getPriority());
        setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, msg.getContentType());
        setInt(x400msg, X400_att.X400_N_DISCLOSURE, msg.getDisclosure());
        setInt(x400msg, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, msg.getConversionProhibited());
        setInt(x400msg, X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED, msg.getAlternativeRecipientAllow());
        setInt(x400msg, X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED, msg.getReassignmentProhibited());
        setInt(x400msg, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, msg.getConversionWithLossProhibited());
        setInt(x400msg, X400_att.X400_N_DL_EXPANSION_PROHIBITED, msg.getDlExpasionProhibited());
        setStr(x400msg, X400_att.X400_S_OR_ADDRESS, msg.getOriginator());
        // x400msg.setFrom(msg.getOriginator());

        ATS ats = msg.getAts();
        if (ats != null) {
            setInt(x400msg, AMHS_att.ATS_N_EXTENDED, ats.getExtended());
            setStr(x400msg, AMHS_att.ATS_S_PRIORITY_INDICATOR, ats.getPriority());
            setStr(x400msg, AMHS_att.ATS_S_FILING_TIME, ats.getFilingTime());
            setStr(x400msg, AMHS_att.ATS_S_OPTIONAL_HEADING_INFO, ats.getOptionalHeading());
            setStr(x400msg, AMHS_att.ATS_S_TEXT, encode(ats.getText()));
            setInt(x400msg, AMHS_att.ATS_EOH_MODE, ats.getEohMode());
        }
        
        
        for (Recipient recip : msg.getRecipients()) {
            Recip rcp = new Recip();
            com.isode.x400api.X400ms.x400_ms_recipnew(x400msg, X400_att.X400_RECIP_STANDARD, rcp);
            setStr(rcp, X400_att.X400_S_OR_ADDRESS, recip.getOr(), recip.getOr().length());
            setInt(rcp, X400_att.X400_N_MTA_REPORT_REQUEST, recip.getMtaReportRequest());
            setInt(rcp, X400_att.X400_N_REPORT_REQUEST, recip.getOriginReportRequest());
            setInt(rcp, X400_att.X400_N_NOTIFICATION_REQUEST, recip.getReceiptNotification());
        }
        
        for (Recipient recip : msg.getHeadingRecipient()) {
            // x400msg.setIPMHeadingPrimaryRecipient(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
            Recip rcp = new Recip();
            com.isode.x400api.X400ms.x400_ms_recipnew(x400msg, X400_att.X400_RECIP_PRIMARY, rcp);
            setStr(rcp, X400_att.X400_S_OR_ADDRESS, recip.getOr(), recip.getOr().length());
            setInt(rcp, X400_att.X400_N_MTA_REPORT_REQUEST, recip.getMtaReportRequest());
            setInt(rcp, X400_att.X400_N_REPORT_REQUEST, recip.getOriginReportRequest());
            setInt(rcp, X400_att.X400_N_NOTIFICATION_REQUEST, recip.getReceiptNotification());
        }
        
        for (Recipient recip : msg.getCopyRecipient()) {
            // x400msg.setCc(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
            Recip rcp = new Recip();
            com.isode.x400api.X400ms.x400_ms_recipnew(x400msg, X400_att.X400_RECIP_CC, rcp);
            setStr(rcp, X400_att.X400_S_OR_ADDRESS, recip.getOr(), recip.getOr().length());
            setInt(rcp, X400_att.X400_N_MTA_REPORT_REQUEST, recip.getMtaReportRequest());
            setInt(rcp, X400_att.X400_N_REPORT_REQUEST, recip.getOriginReportRequest());
            setInt(rcp, X400_att.X400_N_NOTIFICATION_REQUEST, recip.getReceiptNotification());
            
        }
        
        for (Recipient recip : msg.getBlindCopyRecipient()) {
            // x400msg.setBcc(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
            Recip rcp = new Recip();
            com.isode.x400api.X400ms.x400_ms_recipnew(x400msg, X400_att.X400_RECIP_BCC, rcp);
            setStr(rcp, X400_att.X400_S_OR_ADDRESS, recip.getOr(), recip.getOr().length());
            setInt(rcp, X400_att.X400_N_MTA_REPORT_REQUEST, recip.getMtaReportRequest());
            setInt(rcp, X400_att.X400_N_REPORT_REQUEST, recip.getOriginReportRequest());
            setInt(rcp, X400_att.X400_N_NOTIFICATION_REQUEST, recip.getReceiptNotification());
        }
        
        Bodypart bodyart ;
        for (Body body : msg.getBodies()) {
            switch(body.getType()) {
                case "General":
                    bodyart = new Bodypart(Bodypart.Bodypart_Type.BODYPART_GENERAL_TEXT);
                    if (body.getCharset() != null) {
                        bodyart.setStringParam(X400_att.X400_S_GENERAL_TEXT_CHARSETS, body.getCharset());
                    }
                    bodyart.setStringParam(X400_att.X400_S_BODY_DATA, decode(body.getText()));
                    x400msg.addBodypart(bodyart);
                    break;
                    
                case "Ia5-Text" :
                    bodyart = new Bodypart(Bodypart.Bodypart_Type.BODYPART_IA5_TEXT);
                    if (body.getCharset() != null) {
                        bodyart.setStringParam(X400_att.X400_S_GENERAL_TEXT_CHARSETS, body.getCharset());
                    }
                    bodyart.setStringParam(X400_att.X400_S_BODY_DATA, decode(body.getText()));
                    x400msg.addBodypart(bodyart);
                    break;
            }
        }

        Out.print("Deliver message " + msg.getContentId());
        x400msg.sendMsg(session);
        
        Out.print(true);
        close(session);
        
        return x400msg.getMessageIdentifier();
    }
    
    public static void sendProbe(String account, String message) throws X400APIException {

        System.out.print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        System.out.println("[V]");

        System.out.print("Load message ");
        Probe msg = (Probe) XmlSerializer.deserialize(message, Probe.class);
        System.out.println("[V]");

        P3BindSession session = open(acc);
        X400Msg x400msg = new X400Msg(session, true);
        
        // setStr(x400msg, X400_att.X400_S_SUBJECT, msg.getSubject());
        // setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, conentType);
        setStr(x400msg, X400_att.X400_S_CONTENT_IDENTIFIER, msg.getContentId());
        setStr(x400msg, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, msg.getOriginEIT());
        setStr(x400msg, X400_att.X400_S_GLOBAL_DOMAIN_ID, msg.getGlobalDomainID());
        setInt(x400msg, X400_att.X400_N_PRIORITY, msg.getPriority());
        setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, msg.getContentType());
        setInt(x400msg, X400_att.X400_N_DISCLOSURE, msg.getDisclosure());
        setInt(x400msg, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, msg.getConversionProhibited());
        setInt(x400msg, X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED, msg.getAlternativeRecipientAllow());
        setInt(x400msg, X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED, msg.getReassignmentProhibited());
        setInt(x400msg, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, msg.getConversionWithLossProhibited());
        setInt(x400msg, X400_att.X400_N_DL_EXPANSION_PROHIBITED, msg.getDlExpasionProhibited());
        setInt(x400msg, X400_att.X400_N_CONTENT_LENGTH, msg.getContentLength());

        for (Recipient recip : msg.getRecipients()) {
            x400msg.setTo(recip.getOr(), recip.getReportRequest(), recip.getReceiptNotification());
        }
        
        x400msg.sendMsg(session);
        System.out.print("Deliver message " + msg.getContentId() + " [V] ");

        close(session);
    }

    public static void close(P3BindSession session) {
        try {
            Out.print("Close connection ");
            session.unbind();
            Out.print(true);
        } catch (X400APIException e) {
            Out.print(false);
            e.printStackTrace();
        }
    }

    public static P3BindSession open(Account account) {
        try {
            Out.print("Open connection ");
            P3BindSession bind_session = new P3BindSession(account.getPresentationAddress(), account.getOr(), account.getPassword(), true);
            bind_session.bind();
            Out.print(true);
            return bind_session;
        } catch (X400APIException e) {
            Out.print(false);
            e.printStackTrace();
            return null;
        }
    }

    public static void setInt(X400Msg msg, int att, Integer value) {
        if (value == null) {
            return;
        }
        // System.out.print("Setting attribute " + att);
        int result = com.isode.x400api.X400ms.x400_ms_msgaddintparam(msg, att, value);
        if (result != X400_att.X400_E_NOERROR) {
            System.out.println("Setting attribute " + att + " [X]");
        }
        // String rs = result == X400_att.X400_E_NOERROR ? "[O]" : "[X]";
        // System.out.println(rs);
    }
    
    public static void setInt(Recip recip, int att, Integer value) {
        if (value == null) return;

        int result = com.isode.x400api.X400ms.x400_ms_recipaddintparam(recip, att, value);
        if (result != X400_att.X400_E_NOERROR) {
            System.out.println("Setting attribute " + att + " [X]");
        }

    }
    
    public static void setStr(Recip msg, int att, String value, int length) {
        if (value == null) return;

        int result = com.isode.x400api.X400ms.x400_ms_recipaddstrparam(msg, att, value, length);
        if (result != X400_att.X400_E_NOERROR) {
            System.out.println("Setting attribute " + att + " [X]");
        }
    }

    public static void setStr(X400Msg msg, int att, String value, int length) {
        if (value == null) {
            return;
        }
        // System.out.print("Setting attribute " + att);
        int result = com.isode.x400api.X400ms.x400_ms_msgaddstrparam(msg, att, value, length);
        if (result != X400_att.X400_E_NOERROR) {
            System.out.println("Setting attribute " + att + " [X]");
        }
        // String rs = result == X400_att.X400_E_NOERROR ? "[O]" : "[X]";
        // System.out.println(rs);
    }

    public static void setStr(X400Msg msg, int att, String value) {
        if (value == null) {
            return;
        }
        // System.out.print("Setting attribute " + att);
        int result = com.isode.x400api.X400ms.x400_ms_msgaddstrparam(msg, att, value, -1);
        if (result != X400_att.X400_E_NOERROR) {
            System.out.println("Setting attribute " + att + " [X]");
        }
        // String rs = result == X400_att.X400_E_NOERROR ? "[O]" : "[X]";
        // System.out.println(rs);
    }
    
    public static String encode(String str) {
        return str.replace("\u0001", "[soh]").replace("\u0002", "[sot]").replace("\r", "[r]").replace("\n", "[n]");
    }
    
    public static String decode(String str) {
        return str.replace("[soh]", "\u0001").replace("[sot]", "\u0002").replace("[r]", "\r").replace("[n]", "\n");
    }
}
