/* 
 * Copyright (c) 2008-2010, Isode Limited, London, England.
 * All rights reserved.
 * 
 * Acquisition and use of this software and related materials for any
 * purpose requires a written licence agreement from Isode Limited,
 * or a written licence from an organisation licenced by Isode Limited
 * to grant such a licence.
 */
package amhs.aftn.testtool;

import amhs.database.CommonUtils;
import amhs.database.dao.GatewayInDao;
import amhs.database.dao.GatewayOutDao;
import amhs.database.entities.GatewayIn;
import amhs.database.entities.GatewayOut;
import com.isode.x400.highlevel.Bodypart;
import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.X400_att;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test program that connects to either an MTA's P3 channel or to a P7 Message Store, and submits a test message to itself.
 *
 */
public class SendCT414 {

    private static final boolean use_p3 = true;
    private static final boolean send_military_message = false;

    private static final String prsentationAddress = "\"593\"/URI+0000+URL+itot://laptop";
    private static final String account = "/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/";
    private static final String password = "attech";
    private static final boolean submit_only = true;
    private static final String recip = "/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";

    // private static final String recipient_or_address = "/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/";
    // private static final String recipient_or_address = "/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";
    private static Integer conentType = 22;
    private static Integer prohibited = 1;
    private static Integer extendMode = 0;
    private static String encodingInfomation = "1.0.10021.7.1.0.100 1.0.10021.7.1.0.1 1.0.10021.7.1.0.6";
    private static X400Msg.DR_Request reportRequest = X400Msg.DR_Request.DR_NON_DELIVERY_REPORT;
    private static Integer ipnRequest = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
    private static int count = 1;
    
    public static void main(String[] args) throws Exception {
        CommonUtils.configure("config/database.xml");
        
        count = 0;
        conentType = 22;
        prohibited = 0;
        extendMode = 0;
        encodingInfomation = "1.0.10021.7.1.0.100 1.0.10021.7.1.0.1 1.0.10021.7.1.0.6";
        reportRequest = X400Msg.DR_Request.DR_NON_DELIVERY_REPORT;
        ipnRequest = X400Msg.IPN_RECEIPT_NOTIFICATION;

        P3BindSession session = open(prsentationAddress, account, password);
        sendIA5Message(session, "CT414M01", "SS", "200928", "OHI", "CT414M01");
        close(session);
        
        generateAck();
    }
    
    private static void generateAck() throws Exception {
        GatewayOutDao dbUtil = new GatewayOutDao();

        while (true) {
            List<GatewayOut> gwouts = dbUtil.get(10);
            if (gwouts == null || gwouts.isEmpty()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Waiting ...");
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendCT414.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }

            SimpleDateFormat format = new SimpleDateFormat("ddHHmm");

            GatewayOut out = gwouts.get(0);
            System.out.println("GET message: " + out.getText());

            StringBuilder builder = new StringBuilder();
            builder.append("ZCZC ABC001\r\n");
            builder.append(String.format("%s %s\r\n", getPri(out.getPriority()), out.getOriginator()));
            builder.append(String.format("%s %s\r\n", format.format(new Date()), out.getAddress()));
            builder.append(String.format("R %s %s\r\n", out.getFilingTime(), out.getOriginator()));
            builder.append("NNNN");

            GatewayIn in = new GatewayIn();
            in.setAddress(out.getOriginator());
            in.setCpa("A");
            in.setPriority((short) out.getPriority());
            in.setSource("REJ");
            in.setText(builder.toString());
            in.setTime(new Date());

            GatewayInDao gatewayDB = new GatewayInDao();
            gatewayDB.insert(in);

            dbUtil.delete(out);
            
            break;
        }
    }
    
    private static String getPri(int priority) {
        switch(priority) {
            case 0: return "SS";
            case 1: return "DD";
            case 2: return "FF";
            case 3: return "GG";
            default : return "KK";
        }
    }

    private static void sendIA5Message(P3BindSession bind_session, String subject, String pri, String ft, String ohi, String text) {
        X400Msg x400msg = new X400Msg(bind_session);

        try {
            x400msg.setTo(recip, reportRequest, ipnRequest);
            setStr(x400msg, X400_att.X400_S_SUBJECT, subject);
            setInt(x400msg, AMHS_att.ATS_N_EXTENDED, extendMode);
            setStr(x400msg, AMHS_att.ATS_S_PRIORITY_INDICATOR, pri);
            setStr(x400msg, AMHS_att.ATS_S_FILING_TIME, ft);
            setStr(x400msg, AMHS_att.ATS_S_OPTIONAL_HEADING_INFO, ohi);
            setStr(x400msg, AMHS_att.ATS_S_TEXT, text);
            setInt(x400msg, AMHS_att.ATS_EOH_MODE, AMHS_att.ATS_EOH_MODE_NONE);
            setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, conentType);
            setInt(x400msg, X400_att.X400_N_DL_EXPANSION_PROHIBITED, 1);
            setInt(x400msg, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, prohibited);
            setStr(x400msg, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, encodingInfomation);
            
            String ipm_id = x400msg.getMessageIPMIdentifier();
            
            x400msg.sendMsg(bind_session);
            System.out.println("IA-5 Message has submitted. Message INDEX: " + subject + " [ID] " + ipm_id);

        } catch (X400APIException e1) {
            e1.printStackTrace();
            close(bind_session);
            System.exit(1);
        }
    }
    
    private static void setInt(X400Msg msg, int att, Integer value) {
        if (value == null) return;
        com.isode.x400api.X400ms.x400_ms_msgaddintparam(msg, att, value);
    }
    
    private static void setStr(X400Msg msg, int att, String value, int length) {
        if (value == null) return;
        com.isode.x400api.X400ms.x400_ms_msgaddstrparam(msg, att, value, length);
    }
    
    private static void setStr(X400Msg msg, int att, String value) {
        if (value == null) return;
        com.isode.x400api.X400ms.x400_ms_msgaddstrparam(msg, att, value, -1);
    }
    
    private static void sendGeneralText(P3BindSession bind_session, String subject, String pri, String ft, String ohi, String text) {
        X400Msg x400msg = new X400Msg(bind_session);
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;

        try {
            x400msg.setTo(recip, X400Msg.DR_Request.DR_NON_DELIVERY_REPORT, ipn_request);
            setStr(x400msg, X400_att.X400_S_SUBJECT, subject);
            setInt(x400msg, X400_att.X400_N_CONTENT_TYPE, conentType);
            setInt(x400msg, X400_att.X400_N_DL_EXPANSION_PROHIBITED, 1);
            setInt(x400msg, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, prohibited);
            setStr(x400msg, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, encodingInfomation);

            
            StringBuilder builder = new StringBuilder();
            builder.append("\u0001");
            if (pri != null) builder.append(String.format("PRI: %s\r\n", pri));
            if (ft != null) builder.append(String.format("FT: %s\r\n", ft));
            if (ohi != null) builder.append(String.format("OHI: %s\r\n", ohi));
            
            if (!builder.toString().endsWith("\r\n")) builder.append("\r\n");
            builder.append(String.format("\u0002%s", text));
            
            Bodypart body = new Bodypart(Bodypart.Bodypart_Type.BODYPART_GENERAL_TEXT);
            body.setStringParam(X400_att.X400_S_GENERAL_TEXT_CHARSETS, "1 6 100");
            body.setStringParam(X400_att.X400_S_BODY_DATA, builder.toString());
            x400msg.addBodypart(body);

            x400msg.sendMsg(bind_session);
            // bind_session.unbind();
            String ipm_id = x400msg.getMessageIPMIdentifier();
            System.out.println("General Text Message has submitted. Message INDEX: " + subject);

        } catch (X400APIException e1) {
            e1.printStackTrace();
            close(bind_session);
            System.exit(1);
        }
    }

    private static void close(P3BindSession session) {
        try {
            session.unbind();
        } catch (X400APIException e) {
            e.printStackTrace();
        }
    }

    private static P3BindSession open(String presentationAddress, String user, String password) {
        try {
            P3BindSession bind_session = new P3BindSession(presentationAddress, user, password, submit_only);
            bind_session.bind();
            return bind_session;
        } catch (X400APIException e) {
            e.printStackTrace();
            return null;
        }
    }
}
